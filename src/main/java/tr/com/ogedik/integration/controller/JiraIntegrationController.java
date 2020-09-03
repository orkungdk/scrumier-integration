package tr.com.ogedik.integration.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tr.com.ogedik.commons.constants.Services;
import tr.com.ogedik.commons.rest.AbstractController;
import tr.com.ogedik.commons.rest.request.model.AuthenticationRequest;
import tr.com.ogedik.commons.rest.request.model.JiraConfigurationProperties;
import tr.com.ogedik.commons.rest.response.AbstractResponse;
import tr.com.ogedik.integration.services.jira.JiraIntegrationService;

/**
 * @author orkun.gedik
 */
@RequestMapping(Services.Path.JIRA)
@Controller
public class JiraIntegrationController extends AbstractController {
  private static final Logger logger = LogManager.getLogger(JiraIntegrationController.class);

  @Autowired
  private JiraIntegrationService jiraIntegrationService;

  @PostMapping(Services.Path.JIRA_AUTH)
  public AbstractResponse authenticateJira(@RequestBody AuthenticationRequest authenticationRequest) {
    logger.info("A request has been received to authenticate configured jira instance");
    boolean result = jiraIntegrationService.authenticate(authenticationRequest);

    return result ? AbstractResponse.OK() : AbstractResponse.unauthorized();
  }

  @PostMapping(Services.Path.TEST_CONNECTION)
  public AbstractResponse connectJira(@RequestBody JiraConfigurationProperties properties) {
    logger.info("A request has been received to authenticate configured jira instance");
    boolean result = jiraIntegrationService.connect(properties);

    return result ? AbstractResponse.OK() : AbstractResponse.unauthorized();
  }

  @GetMapping(Services.Path.JIRA_USER)
  public AbstractResponse getJiraUser(@RequestParam(name = "username") String username) {
    logger.info("A request has been received to retrieve jira user with name {}", username);
    return AbstractResponse.build(jiraIntegrationService.getJiraUser(username));
  }

  @GetMapping(Services.Path.WORKLOGS)
  public AbstractResponse getWorkflows(@RequestParam(name = "startDate") String startDate,
                                       @RequestParam(name = "endDate") String endDate){
    logger.info("A request has been received to retrieve all the worklogs of a user between {} and {}", startDate, endDate);
    return AbstractResponse.build(jiraIntegrationService.getWorklogs(startDate,endDate));
  }
}

