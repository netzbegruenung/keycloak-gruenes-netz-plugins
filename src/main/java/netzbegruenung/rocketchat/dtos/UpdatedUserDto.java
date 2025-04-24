package netzbegruenung.rocketchat.dtos;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

/**
 * The user payload returned from the `users.update` endpoint.
 */
public class UpdatedUserDto {

    @SerializedName("_id")
    public String id;

    @SerializedName("createdAt")
    public String createdAt;

    @SerializedName("username")
    public String username;

    @SerializedName("emails")
    public List<Email> emails;

    @SerializedName("type")
    public String type;

    @SerializedName("roles")
    public List<String> roles;

    @SerializedName("status")
    public String status;

    @SerializedName("active")
    public boolean active;

    @SerializedName("_updatedAt")
    public String updatedAt;

    @SerializedName("name")
    public String name;

    @SerializedName("requirePasswordChange")
    public boolean requirePasswordChange;

    @SerializedName("settings")
    public Map<String, Object> settings;

    @SerializedName("lastLogin")
    public String lastLogin;

    @SerializedName("statusConnection")
    public String statusConnection;

    @SerializedName("__rooms")
    public List<String> rooms;

    @SerializedName("statusDefault")
    public String statusDefault;

    @SerializedName("statusText")
    public String statusText;

    @SerializedName("utcOffset")
    public int utcOffset;

    @SerializedName("requirePasswordChangeReason")
    public String requirePasswordChangeReason;

    public static class Email {
        @SerializedName("address")
        public String address;

        @SerializedName("verified")
        public boolean verified;
    }
}