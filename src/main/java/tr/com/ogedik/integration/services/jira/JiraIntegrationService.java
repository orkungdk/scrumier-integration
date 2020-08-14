package tr.com.ogedik.integration.services.jira;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tr.com.ogedik.commons.model.JiraUser;
import tr.com.ogedik.commons.rest.response.RestResponse;
import tr.com.ogedik.commons.util.MapUtils;
import tr.com.ogedik.commons.validator.MandatoryFieldValidator;
import tr.com.ogedik.commons.rest.request.model.AuthenticationRequest;
import tr.com.ogedik.commons.rest.request.model.JiraConfigurationProperties;
import tr.com.ogedik.commons.rest.request.client.HttpRestClient;
import tr.com.ogedik.commons.rest.request.client.helper.RequestURLDetails;
import tr.com.ogedik.integration.constants.JiraRestConstants;
import tr.com.ogedik.integration.services.AbstractIntegrationService;
import tr.com.ogedik.integration.services.configuration.ConfigurationIntegrationService;

import java.util.Base64;
import java.util.Map;

/**
 * This service processes the received request from other microservices in the project and routes those requests to the
 * integration Jira instance. In order to resolve request URL of the Jira, sends a http request to retrieve Jira
 * configurations to configuration microservice.
 *
 * Request structure is as follows:
 * Any service -> Integration <-> Configuration -> Jira
 *
 * @author orkun.gedik
 */
@Service
public class JiraIntegrationService extends AbstractIntegrationService {

    @Autowired
    private ConfigurationIntegrationService configurationService;

    /**
     * Authenticates to configured Jira instance.
     *
     * @param authenticationRequest authentication request information
     * @return {@code true} if the given request is able to authenticate.
     */
    public boolean authenticate(AuthenticationRequest authenticationRequest) {
        JiraConfigurationProperties properties = configurationService.getJiraConfigurationProperties();
        properties.setUsername(authenticationRequest.getUsername());
        properties.setPassword(authenticationRequest.getPassword());

        return connect(properties);
    }

    /**
     * Creates a session on the Jira instance
     *
     * @param properties
     * @return {@code true} if session is created successfully.
     *         {@code false} if the request returns a status code which is not 200
     *
     * @implNote Jira instance returns 404 in case of Jira is down
     *           Jira instance returns 401 in case of given username and password in the {@code properties} are wrong.
     *           Jira instance returns 500 in case of there is an internal problem in Jira.
     */
    public boolean connect(JiraConfigurationProperties properties) {
        MandatoryFieldValidator.getInstance().validate(properties);

        RequestURLDetails requestURLDetails = new RequestURLDetails(properties.getBaseURL(),
                JiraRestConstants.EndPoint.SESSION, null);
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .username(properties.getUsername())
                .password(properties.getPassword())
                .build();
        RestResponse<String> authResponse = HttpRestClient.doPost(requestURLDetails, authenticationRequest, String.class);

        if (authResponse.getHttpStatusCode().intValue() == HttpStatus.OK.value()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sends a get request with the Authorization header to Jira instance to be able to retrieve given user details.
     * Username passes as a query parameter in the request.
     *
     * @param username to be retrieved username
     * @return retrieved {@link JiraUser}
     */
    public JiraUser getJiraUser(String username) {
        JiraConfigurationProperties properties = configurationService.getJiraConfigurationProperties();
        MandatoryFieldValidator.getInstance().validate(properties);

        RequestURLDetails requestURLDetails = new RequestURLDetails(properties.getBaseURL(),
                JiraRestConstants.EndPoint.USER, MapUtils.of("username", username));
        RestResponse<JiraUser> userResponse = HttpRestClient.doGet(requestURLDetails, initJiraHeaders(properties),
                JiraUser.class);

        return resolve(userResponse);
    }


    private Map<String, String> initJiraHeaders(JiraConfigurationProperties properties) {
        String input = properties.getUsername() + ":" + properties.getPassword();
        String encoded = Base64.getEncoder().encodeToString(input.getBytes());

        return MapUtils.of(JiraRestConstants.Headers.AUTHORIZATION, "Basic " + encoded);
    }
}
