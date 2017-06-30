package io.featureflow.example.config.featureflow;

import io.featureflow.client.FeatureflowClient;
import io.featureflow.client.model.Feature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class FeatureflowClientConfiguration {



    @Bean
    FeatureflowClient featureflowClient(){
        FeatureflowClient client = FeatureflowClient.builder("srv-env-9edc144cab6a40f199afc55eb2e889e5")
            .withFeatures(Arrays.asList(
                new Feature(FeatureKeys.beta.key())
            ))
            .build();
        return client;
    }
}
