/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.integration.services.configuration;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tr.com.ogedik.commons.constants.Services;
import tr.com.ogedik.commons.request.model.JiraConfigurationProperties;
import tr.com.ogedik.commons.request.rest.HttpRestClient;
import tr.com.ogedik.commons.request.rest.helper.RequestURLDetails;
import tr.com.ogedik.commons.response.RestResponse;
import tr.com.ogedik.integration.services.AbstractIntegrationService;

/**
 * @author orkun.gedik
 */
@Service
public class ConfigurationIntegrationService extends AbstractIntegrationService {

  public JiraConfigurationProperties getJiraConfigurationProperties() {
    RequestURLDetails requestUrlInfo = generateRequestInfo(Services.CONFIGURATION, "/jira", null);
    RestResponse<JiraConfigurationProperties> responseEntity = HttpRestClient.doGet(requestUrlInfo,
        JiraConfigurationProperties.class);

    return resolve(responseEntity);
  }
}
