package com.rest1.config;

import org.apache.catalina.valves.ErrorReportValve;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Component
public class TomcatConfig implements WebServerFactoryCustomizer<ConfigurableTomcatWebServerFactory> {

  @Override
  public void customize(ConfigurableTomcatWebServerFactory factory) {

    factory.addContextCustomizers((context) -> {

      ErrorReportValve valve = new ErrorReportValve();
      valve.setShowServerInfo(false);
      valve.setShowReport(false);

      context.getParent().getPipeline().addValve(valve);
    });

  }

}
