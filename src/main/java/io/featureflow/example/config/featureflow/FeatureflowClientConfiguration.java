package io.featureflow.example.config.featureflow;

import io.featureflow.client.FeatureflowClient;
import io.featureflow.client.FeatureflowUser;
import io.featureflow.client.FeatureflowUserProvider;
import io.featureflow.client.model.Feature;
import io.featureflow.example.domain.User;
import io.featureflow.example.service.UserService;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Date;

@Configuration
public class FeatureflowClientConfiguration {



    private final UserService userService;

    public FeatureflowClientConfiguration(UserService userService) {
        this.userService = userService;
    }


    @Bean
    FeatureflowClient featureflowClient(){
        FeatureflowClient client = FeatureflowClient.builder("srv-env-9edc144cab6a40f199afc55eb2e889e5")
            .withUserProvider(() -> {
                User user = userService.getUserWithAuthorities();
                return new FeatureflowUser(user.getId().toString())
                    .withStringAttributes(ContextKeys.user_role.key(), userService.getAuthorities())
                    .withAttribute(ContextKeys.name.key(), user.getFirstName() + " "  + user.getLastName())
                    .withAttribute(ContextKeys.login.key(), user.getLogin())
                    .withAttribute(ContextKeys.signup_date.key(), new DateTime(Date.from(user.getCreatedDate())));
            })
            .withFeatures(Arrays.asList(
                new Feature(FeatureKeys.beta.key())
            ))
            .build();
        return client;
    }
}
