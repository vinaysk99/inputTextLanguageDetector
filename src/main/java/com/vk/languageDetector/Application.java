package com.vk.languageDetector;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring application configuration.
 */
@SpringBootApplication(scanBasePackages = "com.vk.languageDetector")
public class Application implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static final String LOG_FILE_NAME = "LOG_FILE_NAME";

    @Value("${server.name}")
    private String serverName;

    /**
     * Init the application.
     */
    @PostConstruct
    public void init() {
        log.info("[{}] Initializing: {}", DateTime.now(), serverName);
    }

    /**
     * Destroying application.
     */
    @PreDestroy
    public void destroy() {
        log.info("[{}] Shutting down: {}", DateTime.now(), serverName);
    }

    /**
     * Spring Boot initialization and runner.
     * @param args Default commandline arguments.
     * @throws UnknownHostException Error during lookup of local IP.
     */
    public static void main(String[] args) throws UnknownHostException {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        String logFileName = System.getenv(LOG_FILE_NAME);
        System.setProperty("log.file.name", logFileName + "-" + hostAddress);
        SpringApplication.run(Application.class, args);
    }
}
