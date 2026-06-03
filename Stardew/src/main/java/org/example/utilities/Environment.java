package org.example.utilities;

import io.github.cdimascio.dotenv.Dotenv;

public final class Environment {
    private Environment() {}

    public static void load() {
        String mode = System.getenv().getOrDefault("APP_MODE", "dev");

        Dotenv.configure()
                .directory("src/main/resources")
                .filename("env." + mode)
                .systemProperties()
                .load();
    }
}
