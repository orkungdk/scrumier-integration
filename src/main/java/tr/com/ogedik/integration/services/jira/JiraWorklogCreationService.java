package tr.com.ogedik.integration.services.jira;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tr.com.ogedik.commons.rest.request.client.HttpRestClient;
import tr.com.ogedik.commons.rest.request.client.helper.RequestURLDetails;
import tr.com.ogedik.commons.rest.request.model.CreateWorklogRequest;
import tr.com.ogedik.commons.rest.request.model.JiraConfigurationProperties;
import tr.com.ogedik.commons.rest.request.model.JiraCreateWorklogRequest;
import tr.com.ogedik.commons.rest.response.RestResponse;
import tr.com.ogedik.commons.validator.MandatoryFieldValidator;
import tr.com.ogedik.integration.constants.JiraRestConstants;
import tr.com.ogedik.integration.services.configuration.ConfigurationIntegrationService;
import tr.com.ogedik.integration.util.IntegrationUtil;

/*
 * @author enes.erciyes
 */
@Service
public class JiraWorklogCreationService {

  @Autowired private ConfigurationIntegrationService configurationService;

  public Boolean createWorklog(CreateWorklogRequest createWorklogRequest) {
    JiraConfigurationProperties properties = configurationService.getJiraConfigurationProperties();
    MandatoryFieldValidator.getInstance().validate(properties);

    JiraCreateWorklogRequest request =
        JiraCreateWorklogRequest.builder()
            .comment(createWorklogRequest.getComment())
            .started(createWorklogRequest.getStarted())
            .timeSpentSeconds(createWorklogRequest.getTimeSpentSeconds())
            .build();

    RequestURLDetails requestURLDetails =
        new RequestURLDetails(
            properties.getBaseURL(),
            JiraRestConstants.EndPoint.CREATE(createWorklogRequest.getIssueKey()),
            null);

    RestResponse<String> response =
        HttpRestClient.doPost(
            requestURLDetails, request, IntegrationUtil.initJiraHeaders(properties), String.class);

    return response.getHttpStatusCode() == HttpStatus.CREATED.value();
  }
}
