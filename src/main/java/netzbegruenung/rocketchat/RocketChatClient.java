package netzbegruenung.rocketchat;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

    public String findUserIdByUsername(String username) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .timeout(Duration.ofSeconds(5))
                .uri(URI.create(this.baseUrl + "/api/v1/users.info?username=" + username))
                .header("Content-Type", "application/json")
                .header("X-Auth-Token", this.authToken)
                .header("X-User-Id", this.userId)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (!(response.statusCode() >= 200 && response.statusCode() < 300)) {

            // return null if user does not exist in Rocket.Chat
            if (response.statusCode() == 400) {
                try {
                    JsonObject bodyObject = JsonParser.parseString(response.body()).getAsJsonObject();
                    if (bodyObject.has("success") && !bodyObject.get("success").getAsBoolean() &&
                        bodyObject.has("error") && "User not found.".equals(bodyObject.get("error").getAsString())) {
                        return null;
                    }
                } catch (Throwable e) {
                    // ignore
                }
            }
            throw new RocketChatClientException("request failed with code: " + response.statusCode());
        }

        JsonObject bodyObject = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject userObject = bodyObject.getAsJsonObject("user");
        return userObject.get("_id").getAsString();
    }

    public void activateUser(String userId) throws IOException, InterruptedException {
        String body = String.format("{\"userId\": \"%s\", \"data\": {\"active\": true}}", userId);
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
            System.out.println(response.body());
            throw new RocketChatClientException("request failed with code: " + response.statusCode());
        }
    }
}