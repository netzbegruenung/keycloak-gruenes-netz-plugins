package netzbegruenung.keycloak.provider;

import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import netzbegruenung.rocketchat.RocketChatClient;

import org.jboss.logging.Logger;

public class RocketChatLoginEventListenerProviderFactory implements EventListenerProviderFactory {

    private static final Logger logger = Logger.getLogger("org.keycloak.events");

    private static final String ENV_ROCKATCHAT_BASE_URL = "ROCKATCHAT_BASE_URL";
    private static final String ENV_ROCKATCHAT_AUTH_TOKEN = "ROCKATCHAT_AUTH_TOKEN";
    private static final String ENV_ROCKATCHAT_USER_ID = "ROCKATCHAT_USER_ID";

    private String baseUrl;
    private String authToken;
    private String userId;

    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {
        RocketChatClient client = new RocketChatClient(baseUrl, authToken, userId);

        return new RocketChatLoginEventListener(client, keycloakSession, logger);
    }

    @Override
    public void init(Config.Scope scope) {
        String baseUrl = System.getenv(ENV_ROCKATCHAT_BASE_URL);
        if (baseUrl != null) {
            this.baseUrl = baseUrl;
        }
        String authToken = System.getenv(ENV_ROCKATCHAT_AUTH_TOKEN);
        if (authToken != null) {
            this.authToken = authToken;
        }
        String userId = System.getenv(ENV_ROCKATCHAT_USER_ID);
        if (userId != null) {
            this.userId = userId;
        }
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return "rocketchat_login_event_listener";
    }
}