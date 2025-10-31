package nhnacademy.springfinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SpringFinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringFinalApplication.class, args);
    }

}
