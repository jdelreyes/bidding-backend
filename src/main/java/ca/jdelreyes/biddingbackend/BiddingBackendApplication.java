package ca.jdelreyes.biddingbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BiddingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiddingBackendApplication.class, args);
    }

}
