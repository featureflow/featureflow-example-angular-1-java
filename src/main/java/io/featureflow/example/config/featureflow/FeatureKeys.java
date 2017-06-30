package io.featureflow.example.config.featureflow;


/**
 * These are the feature keys - used to identify our individual features
 */
public enum FeatureKeys {
    alpha("alpha"),
    beta("beta"),
    delete_transactions("delete_transactions");


    private final String key;
    FeatureKeys(String key) {
        this.key= key;
    }

    public String key() {
        return key;
    }
}
