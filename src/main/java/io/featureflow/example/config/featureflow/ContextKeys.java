package io.featureflow.example.config.featureflow;


/**
 * These are the keys used in our featureflow context object
 */
public enum ContextKeys {
    name("name"),
    user_role("user_role"),
    login("login"),
    brand("brand"),
    state("state"),
    signup_date("signup_date");


    private final String key;
    ContextKeys(String key) {
        this.key= key;
    }

    public String key() {
        return key;
    }
}
