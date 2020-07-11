/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.integration.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import tr.com.ogedik.integration.exception.IntegrationExceptionHandler;

/**
 * @author orkun.gedik
 */
@Service
public class ServiceAccessor {

  @Qualifier("eurekaClient")
  @Autowired
  private EurekaClient discoveryClient;

    public InstanceInfo getServiceInstanceInfo(String serviceName) {
      InstanceInfo instanceInfo = discoveryClient.getNextServerFromEureka(serviceName, false);

      if (instanceInfo.getStatus().equals(InstanceInfo.InstanceStatus.UP)) {
        return instanceInfo;
      } else {
        return IntegrationExceptionHandler.handleNotRunningInstance(instanceInfo);
      }
    }
}
