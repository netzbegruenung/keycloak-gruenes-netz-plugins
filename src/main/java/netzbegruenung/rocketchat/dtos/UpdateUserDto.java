package netzbegruenung.rocketchat.dtos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UpdateUserDto {

    /**
     * The user ID to update. This value must not be empty.
     * Example: BsNr28znDkG8aeo7W
     */
    private final String userId;

    /**
     * The object that includes the user information to update with the following parameters.
     * Note: If you provide an empty object, the user details are returned.
     */
    private final Data data;

    public UpdateUserDto(String userId) {
        this.userId = Objects.requireNonNull(userId, "userId is required");
        this.data = new Data();
    }

    public UpdateUserDto withName(String name) {
        this.data.name = name;
        return this;
    }

    public UpdateUserDto withEmail(String email) {
        this.data.email = email;
        return this;
    }

    public UpdateUserDto withPassword(String password) {
        this.data.password = password;
        return this;
    }

    public UpdateUserDto withUsername(String username) {
        this.data.username = username;
        return this;
    }

    public UpdateUserDto withActive(Boolean active) {
        this.data.active = active;
        return this;
    }

    public UpdateUserDto withRoles(List<String> roles) {
        this.data.roles = roles;
        return this;
    }

    public UpdateUserDto withRequirePasswordChange(Boolean requirePasswordChange) {
        this.data.requirePasswordChange = requirePasswordChange;
        return this;
    }

    public UpdateUserDto withSendWelcomeEmail(Boolean sendWelcomeEmail) {
        this.data.sendWelcomeEmail = sendWelcomeEmail;
        return this;
    }

    public UpdateUserDto withVerified(Boolean verified) {
        this.data.verified = verified;
        return this;
    }

    public UpdateUserDto withCustomFields(Map<String, Object> customFields) {
        this.data.customFields = customFields;
        return this;
    }

    public String toJson() {
        Gson gson = new GsonBuilder()
                .create();
        return gson.toJson(this);
    }

    private static class Data {

        /** The name of the user. Example: Example User */
        @SerializedName("name")
        private String name;

        /** The email ID of the user. Example: example@example.com */
        @SerializedName("email")
        private String email;

        /** The password for the user. Example: passw0rd */
        @SerializedName("password")
        private String password;

        /** The username for the user. Example: example */
        @SerializedName("username")
        private String username;

        /** Whether the user is active, which determines if they can login or not. Default: true */
        @SerializedName("active")
        private Boolean active;

        /** The roles the user has been assigned. */
        @SerializedName("roles")
        private List<String> roles;

        /** Whether the user should be required to change their password when they login. Default: false */
        @SerializedName("requirePasswordChange")
        private Boolean requirePasswordChange;

        /** Whether the user should get a welcome email. Default: false */
        @SerializedName("sendWelcomeEmail")
        private Boolean sendWelcomeEmail;

        /** Whether the user's email address should be verified. Default: true */
        @SerializedName("verified")
        private Boolean verified;

        /** Any custom fields the user should have on their account. */
        @SerializedName("customFields")
        private Map<String, Object> customFields;
    }

    // Getters (optional if used only for serialization)
    public String getUserId() {
        return userId;
    }

    public Data getData() {
        return data;
    }
} 
