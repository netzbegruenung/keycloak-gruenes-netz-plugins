package netzbegruenung.rocketchat.dtos;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * The user payload returned from the `users.info` endpoint.
 */
public class UserInfoDto {

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

    @SerializedName("status")
    public String status;

    @SerializedName("active")
    public boolean active;

    @SerializedName("roles")
    public List<String> roles;

    @SerializedName("name")
    public String name;

    @SerializedName("requirePasswordChange")
    public boolean requirePasswordChange;

    @SerializedName("lastLogin")
    public String lastLogin;

    @SerializedName("statusConnection")
    public String statusConnection;

    @SerializedName("utcOffset")
    public int utcOffset;

    @SerializedName("canViewAllInfo")
    public boolean canViewAllInfo;

    public static class Email {
        @SerializedName("address")
        public String address;

        @SerializedName("verified")
        public boolean verified;
    }
}