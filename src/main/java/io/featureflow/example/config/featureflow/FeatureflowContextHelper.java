package io.featureflow.example.config.featureflow;


import io.featureflow.client.FeatureflowContext;
import io.featureflow.example.domain.User;
import io.featureflow.example.service.UserService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Returns a known base context with basic user information
 */
@Service
public class FeatureflowContextHelper {

    private final UserService userService;

    public FeatureflowContextHelper(UserService userService) {
        this.userService = userService;
    }

    public FeatureflowContext.Builder getBaseContext(){
        User user = userService.getUserWithAuthorities();
        return FeatureflowContext.keyedContext(user.getId().toString())
            .withValues(ContextKeys.user_role.key(), userService.getAuthorities())
            .withValue(ContextKeys.name.key(), user.getFirstName() + " "  + user.getLastName())
            .withValue(ContextKeys.login.key(), user.getLogin())
            .withValue(ContextKeys.signup_date.key(), new DateTime(Date.from(user.getCreatedDate())));

    }
}
