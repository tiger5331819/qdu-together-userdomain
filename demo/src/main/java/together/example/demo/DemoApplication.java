package together.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import together.example.togethercore.amqp.AMQPNet;

@ComponentScan("together")
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {	
		SpringApplication.run(DemoApplication.class, args);
		AMQPNet.AMQPConfiguration("Test");
	}

}
