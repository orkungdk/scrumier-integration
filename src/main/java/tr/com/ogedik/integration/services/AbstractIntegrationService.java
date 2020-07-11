/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.integration.services;

import com.netflix.appinfo.InstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import tr.com.ogedik.commons.request.model.JiraConfigurationProperties;
import tr.com.ogedik.commons.response.RestResponse;
import tr.com.ogedik.integration.exception.IntegrationExceptionHandler;
import tr.com.ogedik.integration.helper.ServiceAccessor;
import tr.com.ogedik.commons.request.rest.helper.RequestURLDetails;

import java.util.Arrays;
import java.util.Map;

/**
 * @author orkun.gedik
 */
public abstract class AbstractIntegrationService {

  @Autowired
  protected ServiceAccessor serviceAccessor;

  public RequestURLDetails generateRequestInfo(String serviceName, String path, Map<String, String> queryParams) {
    InstanceInfo configurationInstanceInfo = serviceAccessor.getServiceInstanceInfo(serviceName);
    return new RequestURLDetails(configurationInstanceInfo.getHomePageUrl(), configurationInstanceInfo.getVIPAddress(),
        path, queryParams);
  }

  public JiraConfigurationProperties resolve(RestResponse<JiraConfigurationProperties> response,
      HttpStatus... expectedStatuses) {
    if (Arrays.asList(expectedStatuses).contains(response.getHttpStatus())) {
      return response.getBody();
    } else {
      throw IntegrationExceptionHandler.handleInvalidHttpStatus(response);
    }
  }
}
