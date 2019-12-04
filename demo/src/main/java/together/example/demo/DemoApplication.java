package together.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import together.togethercore.amqp.AMQPCore;


@ComponentScan("together")
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(DemoApplication.class, args);
		AMQPCore.getInstance().Configuration("Test", "together");
	}

}
