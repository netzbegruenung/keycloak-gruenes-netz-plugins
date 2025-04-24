package netzbegruenung.rocketchat;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import netzbegruenung.rocketchat.dtos.UpdateUserDto;
import netzbegruenung.rocketchat.dtos.UpdatedUserDto;
import netzbegruenung.rocketchat.dtos.UserInfoDto;

public class RocketChatClient {
    private final String baseUrl;
    private final String authToken;
    private final String userId;
    private HttpClient client;

    public RocketChatClient(String baseUrl, String authToken, String userId) {
        this.baseUrl = baseUrl;
        this.authToken = authToken;
        this.userId = userId;
        this.client = HttpClient
                .newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }

    /**
     * @param userId The alphanumerical user id or the username.
     * 
     * @return UserInfoDto or null if user does not exist
     */
    public UserInfoDto getUserInfo(String userId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .timeout(Duration.ofSeconds(5))
                .uri(URI.create(this.baseUrl + "/api/v1/users.info?username=" + userId))
                .header("Content-Type", "application/json")
                .header("X-Auth-Token", this.authToken)
                .header("X-User-Id", this.userId)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // try to get the decoded json body to also have it for error handling
        JsonObject bodyObject = null;
        try {
            bodyObject = JsonParser.parseString(response.body()).getAsJsonObject();
        } catch (Throwable e) {
            // ignore
        }

        if (!(response.statusCode() >= 200 && response.statusCode() < 300)) {
            // return null if user does not exist in Rocket.Chat
            if (response.statusCode() == 400) {
                if (bodyObject != null && bodyObject.has("error")
                        && "User not found.".equals(bodyObject.get("error").getAsString())) {
                    return null;
                }
            }
            throw new RocketChatClientException("request failed with code: " + response.statusCode());
        }

        if (bodyObject == null) {
            throw new RocketChatClientException("Error parsing response body as JSON: " + response.body());
        }

        if (!bodyObject.has("success") || !bodyObject.get("success").getAsBoolean()) {
            throw new RocketChatClientException("Request unsuccessful: " + response.body());
        }

        JsonObject userObject = bodyObject.getAsJsonObject("user");
        return new Gson().fromJson(userObject, UserInfoDto.class);
    }

    public UpdatedUserDto updateUser(UpdateUserDto userData) throws IOException, InterruptedException {
        String body = userData.toJson();
        HttpRequest request = HttpRequest
                .newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .timeout(Duration.ofSeconds(5))
                .uri(URI.create(this.baseUrl + "/api/v1/users.update"))
                .header("Content-Type", "application/json")
                .header("X-Auth-Token", this.authToken)
                .header("X-User-Id", this.userId)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = this.client.send(request, HttpResponse.BodyHandlers.ofString());

        if (!(response.statusCode() >= 200 && response.statusCode() < 300)) {
            throw new RocketChatClientException("request failed with code: " + response.statusCode());
        }

        JsonObject bodyObject = null;
        try {
            bodyObject = JsonParser.parseString(response.body()).getAsJsonObject();
        } catch (Throwable e) {
            throw new RocketChatClientException("Error parsing response body as JSON: " + response.body());
        }

        if (!bodyObject.has("success") || !bodyObject.get("success").getAsBoolean()) {
            throw new RocketChatClientException("Request unsuccessful: " + response.body());
        }

        JsonObject userObject = bodyObject.getAsJsonObject("user");
        return new Gson().fromJson(userObject, UpdatedUserDto.class);
    }
}