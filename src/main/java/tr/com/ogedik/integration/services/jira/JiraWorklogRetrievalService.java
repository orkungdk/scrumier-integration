package tr.com.ogedik.integration.services.jira;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.com.ogedik.commons.abstraction.AbstractService;
import tr.com.ogedik.commons.rest.request.client.HttpRestClient;
import tr.com.ogedik.commons.rest.request.client.helper.RequestURLDetails;
import tr.com.ogedik.commons.rest.request.model.JiraConfigurationProperties;
import tr.com.ogedik.commons.rest.response.RestResponse;
import tr.com.ogedik.commons.rest.response.model.JQLSearchResult;
import tr.com.ogedik.commons.util.MapUtils;
import tr.com.ogedik.commons.validator.MandatoryFieldValidator;
import tr.com.ogedik.integration.constants.JiraRestConstants;
import tr.com.ogedik.integration.services.configuration.ConfigurationIntegrationService;
import tr.com.ogedik.integration.util.IntegrationUtil;

@Service
public class JiraWorklogRetrievalService extends AbstractService {

    public static final String WORKLOG = "worklog";
    public static final String SUMMARY = "summary";

    @Autowired
    private ConfigurationIntegrationService configurationService;

    public JQLSearchResult getWorklogSearchResult(String username, String startDate, String endDate) {
        JiraConfigurationProperties properties = configurationService.getJiraConfigurationProperties();
        MandatoryFieldValidator.getInstance().validate(properties);

        //TODO: start and end date validation
        final String jql = String.format("worklogAuthor=%s and worklogDate >= %s and worklogDate <= %s",
                username, startDate, endDate);

        return queryJQL(properties, jql, WORKLOG, SUMMARY);
    }

    private JQLSearchResult queryJQL(JiraConfigurationProperties properties, String jql) {
        RequestURLDetails requestURLDetails = new RequestURLDetails(properties.getBaseURL(),
                JiraRestConstants.EndPoint.SEARCH, MapUtils.of("jql", jql, "startAt", "0"));

        RestResponse<JQLSearchResult> searchResponse = HttpRestClient.doGet(requestURLDetails, IntegrationUtil.initJiraHeaders(properties),
                JQLSearchResult.class);

        return resolve(searchResponse);
    }


    private JQLSearchResult queryJQL(JiraConfigurationProperties properties, String jql, String... fields) {
        RequestURLDetails requestURLDetails = new RequestURLDetails(properties.getBaseURL(),
                JiraRestConstants.EndPoint.SEARCH, MapUtils.of("jql", jql, "fields", String.join(",", fields)));

        RestResponse<JQLSearchResult> searchResponse = HttpRestClient.doGet(requestURLDetails, IntegrationUtil.initJiraHeaders(properties),
                JQLSearchResult.class);

        return resolve(searchResponse);
    }
}
