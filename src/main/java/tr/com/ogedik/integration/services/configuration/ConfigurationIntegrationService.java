package tr.com.ogedik.integration.services.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.com.ogedik.commons.rest.request.model.JiraConfigurationProperties;
import tr.com.ogedik.scrumier.proxy.clients.ConfigurationProxy;

/**
 * @author orkun.gedik
 */
@Service
public class ConfigurationIntegrationService {

  @Autowired
  private ConfigurationProxy configurationProxy;

  public JiraConfigurationProperties getJiraConfigurationProperties() {
    return configurationProxy.getJiraInstanceConfig();
  }
}
