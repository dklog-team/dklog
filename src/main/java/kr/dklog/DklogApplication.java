package kr.dklog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class DklogApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(DklogApplication.class);
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    }

}
