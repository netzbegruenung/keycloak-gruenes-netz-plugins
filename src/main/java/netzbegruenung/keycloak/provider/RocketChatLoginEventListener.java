package netzbegruenung.keycloak.provider;

import java.io.IOException;
import org.jboss.logging.Logger;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.UserModel;

import netzbegruenung.rocketchat.RocketChatClient;

public class RocketChatLoginEventListener implements EventListenerProvider {

    private static final String ROCKETCHAT_CLIENT_ID = "https://chatbegruenung.de/_saml/metadata/gruenesnetz";

    private KeycloakSession session;
    private Logger logger;

    private RocketChatClient client;

    public RocketChatLoginEventListener(
            RocketChatClient client,
            KeycloakSession session,
            Logger logger) {
        this.client = client;
        this.session = session;
        this.logger = logger;
    }

    @Override
    public void onEvent(Event event) {
        if (event.getType() == EventType.LOGIN) {
            if (event.getClientId().equals(ROCKETCHAT_CLIENT_ID)) {
                logger.warn("User " + event.getUserId() + "logged in to rocketchat");
                try {
                    activateUser(event.getUserId());
                } catch (Throwable e) {
                    logger.error("Failed to activate user in Rocket.Chat", e);
                }
            }
        }
    }

    @Override
    public void onEvent(AdminEvent event, boolean includeRepresentation) {
    }

    private void activateUser(String userId) throws IOException, InterruptedException {
        UserModel user = getUserModelById(userId);
        String rocketChatUserId = client.findUserIdByUsername(user.getUsername());
        if (rocketChatUserId == null) {
            return;
        }
        client.activateUser(rocketChatUserId);
    }

    private UserModel getUserModelById(String userId) {
        var realmModel = session.getContext().getRealm();
        return session.users().getUserById(realmModel, userId);
    }

    @Override
    public void close() {
    }

}