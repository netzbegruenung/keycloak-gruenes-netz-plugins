# Keycloak Grünes Netz Plugins

A Keycloak module with customizations for the Grünes Netz Keycloak instance (https://saml.gruene.de)

## Features

### RocketChat Login EventListener

This EventListener is triggered when a user logs into Chatbegrünung. It sends a request to the Rocket.Chat API to activate the user.
This ensures that inactive users can activate their accounts automatically on login and we can safely deactivate their accounts to save on seats.
Enable the EventListener under: Realm settings > Events

The Rocket.Chat API client requires an admin user with an auth token that doesn't need Two Factor Authentication.
This can be selected when creating the auth token.

## Development

### Requirements

- JDK 17 `apt install openjdk-17-jdk`
- JRE 17 `apt install openjdk-17-jre`
- maven `apt install maven`

### Build

```sh
mvn package
```

### Testing with Keycloak in Docker Setup

- copy the jar file to keycloak/providers
- restart keycloak container and it will install the provider on startup
