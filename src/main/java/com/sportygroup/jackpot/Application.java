package com.sportygroup.jackpot;
import com.sportygroup.jackpot.properties.JackpotConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@ConfigurationPropertiesScan("com.sportygroup.jackpot")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
