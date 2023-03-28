package com.paopaolee.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author paopaolee
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableConfigurationProperties
public class InsuranceReportMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsuranceReportMonitorApplication.class, args);
    }

}
