/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.integration.exception;

import lombok.Getter;
import tr.com.ogedik.commons.expection.constants.ErrorType;

/**
 * @author orkun.gedik
 */
@Getter
public enum IntegrationErrorType implements ErrorType {
  INVALID_REQUEST_URL("Invalid request URL.", 500);

  private String title;
  private int status;

  IntegrationErrorType(String title, int status) {
    this.title = title;
    this.status = status;
  }
}
