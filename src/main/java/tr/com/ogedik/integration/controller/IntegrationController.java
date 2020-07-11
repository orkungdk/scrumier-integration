/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.integration.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tr.com.ogedik.commons.constants.Services;
import tr.com.ogedik.commons.request.model.AuthenticationRequest;
import tr.com.ogedik.commons.request.model.JiraConfigurationProperties;
import tr.com.ogedik.commons.response.CommonResponse;
import tr.com.ogedik.integration.services.jira.JiraIntegrationService;

/**
 * @author orkun.gedik
 */
@RestController
public class IntegrationController {
  private static final Logger logger = LogManager.getLogger(IntegrationController.class);

  @Autowired
  private JiraIntegrationService jiraIntegrationService;

  @PostMapping(Services.Path.JIRA_AUTH)
  public CommonResponse authenticateJira(@RequestBody AuthenticationRequest authenticationRequest) {
    logger.info("A request has been received to authenticate configured jira instance");
    boolean result = jiraIntegrationService.authenticate(authenticationRequest);

    return result ? CommonResponse.OK() : CommonResponse.unauthorized();
  }

  @PostMapping(Services.Path.TEST_CONNECTION)
  public CommonResponse connectJira(@RequestBody JiraConfigurationProperties properties) {
    logger.info("A request has been received to authenticate configured jira instance");
    boolean result = jiraIntegrationService.connect(properties);

    return result ? CommonResponse.OK() : CommonResponse.unauthorized();
  }
}
