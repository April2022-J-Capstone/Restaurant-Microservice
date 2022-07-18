package com.smoothstack.restaurantmicroservice.service;

import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.error.MissingEnvironmentVariableException;

@Service
public class ConfigService {

    public static final String frontendAddress = "FRONTEND_ADDRESS";
    public static final String frontendAddressDefault = "http://localhost:8080";

    public static final String awsAccessKeyId = "AWS_ACCESS_KEY_ID";
    public static final String awsSecretAccessKey = "AWS_SECRET_ACCESS_KEY";
    
    public String getFrontendAddress() {
        return getenv(frontendAddress, frontendAddressDefault);
    }

    public String getAwsAccessKeyId() {
        return getenv(awsAccessKeyId, "UNSPECIFIED");
    }

    public String getAwsSecretAccessKey() {
        return getenv(awsSecretAccessKey, "UNSPECIFIED");
    }

    // Provides a fail-safe means of querying environment variables.
    public String getenv(String name, String defaultValue) {
        String result = null;
        try {
            result = System.getenv(name);
        } catch (NullPointerException | SecurityException e) {
            String msg = String.format("Environment variable \"%s\" not found. "
            + "Value will be \"%s\".", name, defaultValue);
            System.err.println(msg);
        }

        if (result == null)
            result = defaultValue;

        return result;
    }

    // Emits an error if the environment variable isn't specified.
    public String getenvRequired(String name) {
        String value = getenv(name, null);
        if (value == null)
            throw new MissingEnvironmentVariableException(name);

        return value;
    }
}
