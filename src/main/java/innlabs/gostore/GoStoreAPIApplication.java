package innlabs.gostore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

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
