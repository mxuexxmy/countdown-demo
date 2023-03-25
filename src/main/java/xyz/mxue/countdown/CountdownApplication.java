package xyz.mxue.countdown;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mxuexxmy
 * @date 2023/3/25 23:54
 * @version 1.0
 */
@RestController
@SpringBootApplication
public class CountdownApplication {

    public static void main(String[] args) {
        SpringApplication.run(CountdownApplication.class, args);
    }

}
