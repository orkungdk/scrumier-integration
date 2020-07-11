/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.integration.helper;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import tr.com.ogedik.commons.request.model.AuthenticationRequest;
import tr.com.ogedik.commons.request.model.JiraConfigurationProperties;
import tr.com.ogedik.commons.request.rest.HttpRestClient;
import tr.com.ogedik.commons.request.rest.helper.RequestURLDetails;
import tr.com.ogedik.commons.response.RestResponse;
import tr.com.ogedik.commons.validator.MandatoryFieldValidator;

/**
 * @author orkun.gedik
 */
@UtilityClass
public class JiraRequestHelper {

  public boolean authenticate(JiraConfigurationProperties properties) {
    MandatoryFieldValidator.getInstance().validate(properties);

    StringBuilder requestPath = new StringBuilder();
    requestPath.append("/api/auth/");
    requestPath.append(properties.getApiVersion());
    requestPath.append("session");

    RequestURLDetails requestURLDetails = new RequestURLDetails(properties.getBaseURL(), null, requestPath.toString(),
        null);
    AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
        .username(properties.getUsername())
        .password(properties.getPassword())
        .build();
    RestResponse<String> authResponse = HttpRestClient.doPost(requestURLDetails, authenticationRequest, String.class);

    if (authResponse.getHttpStatusCode().intValue() == HttpStatus.OK.value()) {
      return true;
    } else {
      return false;
    }
  }
}
