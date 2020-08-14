package tr.com.ogedik.integration.services;

import org.springframework.http.HttpStatus;
import tr.com.ogedik.commons.abstraction.AbstractService;
import tr.com.ogedik.commons.rest.response.RestResponse;
import tr.com.ogedik.commons.rest.request.client.helper.RequestURLDetails;
import tr.com.ogedik.integration.exception.IntegrationExceptionHandler;

import java.util.Arrays;
import java.util.Map;

/**
 * @author orkun.gedik
 */
public abstract class AbstractIntegrationService extends AbstractService {

    public RequestURLDetails generateRequestInfo(String serviceName, String path, Map<String, String> queryParams) {
        return new RequestURLDetails(getRequestUrl(serviceName), path, queryParams);
    }

    public <T> T resolve(RestResponse<T> response, HttpStatus... expectedStatuses) {
        if (expectedStatuses == null || expectedStatuses.length == 0) {
            return response.getBody();
        } else if (Arrays.asList(expectedStatuses).contains(response.getHttpStatus())) {
            return response.getBody();
        } else {
            throw IntegrationExceptionHandler.handleInvalidHttpStatus(response);
        }
    }
}
