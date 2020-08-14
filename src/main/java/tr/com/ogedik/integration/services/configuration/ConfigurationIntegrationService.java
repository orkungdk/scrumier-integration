package tr.com.ogedik.integration.services.configuration;

import org.springframework.stereotype.Service;
import tr.com.ogedik.commons.constants.Services;
import tr.com.ogedik.commons.rest.request.model.JiraConfigurationProperties;
import tr.com.ogedik.commons.rest.request.client.HttpRestClient;
import tr.com.ogedik.commons.rest.request.client.helper.RequestURLDetails;
import tr.com.ogedik.commons.rest.response.RestResponse;
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
