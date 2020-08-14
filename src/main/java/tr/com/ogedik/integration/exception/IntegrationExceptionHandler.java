package tr.com.ogedik.integration.exception;

import com.netflix.appinfo.InstanceInfo;

import tr.com.ogedik.commons.expection.ErrorException;
import tr.com.ogedik.commons.expection.model.ErrorMessage;
import tr.com.ogedik.commons.rest.response.RestResponse;

/**
 * @author orkun.gedik
 */
public class IntegrationExceptionHandler {

  public static InstanceInfo handleNotRunningInstance(InstanceInfo instanceInfo) {
    return null;
  }

  public static <T> ErrorException handleInvalidHttpStatus(RestResponse<T> responseEntity) {
    return new ErrorException(new ErrorMessage());
  }
}
