package innlabs.gostore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by mathias on 13/11/16.
 */
@SpringBootApplication
@ComponentScan
public class GoStoreAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoStoreAPIApplication.class, args);
    }
}
