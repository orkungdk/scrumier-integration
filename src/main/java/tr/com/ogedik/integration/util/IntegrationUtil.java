package tr.com.ogedik.integration.util;

import tr.com.ogedik.commons.rest.request.model.JiraConfigurationProperties;
import tr.com.ogedik.commons.util.MapUtils;
import tr.com.ogedik.integration.constants.JiraRestConstants;

import java.util.Base64;
import java.util.Map;

public class IntegrationUtil {

    public static Map<String, String> initJiraHeaders(JiraConfigurationProperties properties) {
        String input = properties.getUsername() + ":" + properties.getPassword();
        String encoded = Base64.getEncoder().encodeToString(input.getBytes());

        return MapUtils.of(JiraRestConstants.Headers.AUTHORIZATION, "Basic " + encoded);
    }
}
