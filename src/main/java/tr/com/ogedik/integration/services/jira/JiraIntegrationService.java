/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.integration.services.jira;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.ogedik.commons.request.model.AuthenticationRequest;
import tr.com.ogedik.commons.request.model.JiraConfigurationProperties;
import tr.com.ogedik.integration.helper.JiraRequestHelper;
import tr.com.ogedik.integration.services.AbstractIntegrationService;
import tr.com.ogedik.integration.services.configuration.ConfigurationIntegrationService;

/**
 * @author orkun.gedik
 */
@Service
public class JiraIntegrationService extends AbstractIntegrationService {

  @Autowired
  private ConfigurationIntegrationService configurationService;

  public boolean authenticate(AuthenticationRequest authenticationRequest) {
    JiraConfigurationProperties properties = configurationService.getJiraConfigurationProperties();
    properties.setUsername(authenticationRequest.getUsername());
    properties.setPassword(authenticationRequest.getPassword());

    return JiraRequestHelper.authenticate(properties);
  }

  public boolean connect(JiraConfigurationProperties properties) {
    return JiraRequestHelper.authenticate(properties);
  }
}
