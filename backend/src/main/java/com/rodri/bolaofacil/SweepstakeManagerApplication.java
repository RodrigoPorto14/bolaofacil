package com.rodri.bolaofacil;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SweepstakeManagerApplication extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(SweepstakeManagerApplication.class, args);
	}
	
	@Bean
    public ConfigurableServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(redirectConnector());
        return tomcat;
    }

    private Connector redirectConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(8080); // Porta HTTP padrão
        connector.setSecure(false);
        connector.setRedirectPort(443); // Porta HTTPS
        return connector;
    }
}
