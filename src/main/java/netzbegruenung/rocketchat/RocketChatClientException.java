package netzbegruenung.rocketchat;

public class RocketChatClientException extends RuntimeException {
    public RocketChatClientException(String errorMessage) {
        super(errorMessage);
    }

    public RocketChatClientException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}