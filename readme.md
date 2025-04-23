# Keycloak Grünes Netz Plugins

A Keycloak module with customizations for the Grünes Netz Keycloak instance (https://saml.gruene.de)

## Features

### RocketChat Login EventListener

This EventListener is triggered when a user logs into Chatbegrünung. It sends a request to the Rocket.Chat API to activate the user.
This ensures that inactive users can activate their accounts automatically on login and we can safely deactivate their accounts to save on seats.
Enable the EventListener under: Realm settings" > Events

## Development

### Requirements

- JDK 11 `apt install openjdk-11-jdk`
- JRE 11 `apt install openjdk-11-jre`
- maven `apt install maven`

### Build

```sh
mvn package
```

### Testing with Keycloak in Docker Setup

- copy the jar file to keycloak/providers
- restart keycloak container and it will install the provider on startup
