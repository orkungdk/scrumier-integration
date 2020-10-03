package tr.com.ogedik.integration.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tr.com.ogedik.commons.constants.Services;
import tr.com.ogedik.commons.rest.AbstractController;
import tr.com.ogedik.commons.rest.request.model.AuthenticationRequest;
import tr.com.ogedik.commons.rest.request.model.CreateWorklogRequest;
import tr.com.ogedik.commons.rest.request.model.JiraConfigurationProperties;
import tr.com.ogedik.commons.rest.request.model.sessions.JiraSession;
import tr.com.ogedik.commons.rest.response.AbstractResponse;
import tr.com.ogedik.integration.services.jira.JiraIntegrationService;
import tr.com.ogedik.integration.services.jira.JiraWorklogCreationService;
import tr.com.ogedik.integration.services.jira.JiraWorklogRetrievalService;

/**
 * @author orkun.gedik
 */
@RestController
public class JiraIntegrationController extends AbstractController {
  private static final Logger logger = LogManager.getLogger(JiraIntegrationController.class);

  @Autowired
  private JiraIntegrationService jiraIntegrationService;

  @Autowired
  private JiraWorklogRetrievalService jiraWorklogRetrievalService;

  @Autowired
  private JiraWorklogCreationService jiraWorklogCreationService;

  @PostMapping(Services.Path.JIRA_AUTH)
  public AbstractResponse authenticateJira(@RequestBody AuthenticationRequest authenticationRequest) {
    logger.info("A request has been received to authenticate configured jira instance");
    JiraSession result = jiraIntegrationService.authenticate(authenticationRequest);

    return AbstractResponse.build(result, result.isAuthorized() ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
  }

  @PostMapping(Services.Path.TEST_CONNECTION)
  public AbstractResponse connectJira(@RequestBody JiraConfigurationProperties properties) {
    logger.info("A request has been received to authenticate configured jira instance");
    JiraSession result = jiraIntegrationService.connect(properties);

    return AbstractResponse.build(result, result.isAuthorized() ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
  }

  @GetMapping(Services.Path.JIRA_USER)
  public AbstractResponse getJiraUser(@RequestParam(name = "username") String username) {
    logger.info("A request has been received to retrieve jira user with name {}", username);
    return AbstractResponse.build(jiraIntegrationService.getJiraUser(username));
  }

  @GetMapping(Services.Path.LOGGED_ISSUES)
  public AbstractResponse getIssuesWithWorklogs(@RequestParam(name = "username") String username,
                                                @RequestParam(name = "startDate") String startDate,
                                                @RequestParam(name = "endDate") String endDate){
    logger.info("A request has been received to retrieve all the worklogs of a user between {} and {}", startDate, endDate);
    return AbstractResponse.build(jiraWorklogRetrievalService.getWorklogSearchResult(username, startDate,endDate));
  }

  @PostMapping(Services.Path.CREATE_LOG)
  public AbstractResponse createNewWorklog(@RequestBody CreateWorklogRequest createWorklogRequest){
    logger.info("A request has been received to create a new worklog in issue {}", createWorklogRequest.getIssueKey());
    return AbstractResponse.build(jiraWorklogCreationService.createWorklog(createWorklogRequest));

  }
}

