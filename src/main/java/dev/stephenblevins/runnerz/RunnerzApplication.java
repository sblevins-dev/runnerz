package dev.stephenblevins.runnerz;

import dev.stephenblevins.runnerz.users.UserHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import dev.stephenblevins.runnerz.users.User;
import dev.stephenblevins.runnerz.users.UserRestClient;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

@SpringBootApplication
public class RunnerzApplication {
	private static final Logger log = LoggerFactory.getLogger(RunnerzApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RunnerzApplication.class, args);
		log.info("Application started successfully!");
	}

	@Bean
	UserHttpClient userHttpClient() {
		RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com/");
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
		return factory.createClient(UserHttpClient.class);
	}

	@Bean
	CommandLineRunner runner(UserHttpClient client) {
		return (args) -> {
			List<User> users = client.findAll();
			System.out.println(users);

			User user = client.findById(1);
			System.out.println(user);
		};
	}

//	@Bean
//	CommandLineRunner runner() {
//		return args -> {
//			Run run = new Run(1, "First Run", LocalDateTime.now(),
//					LocalDateTime.now().plus(1, ChronoUnit.HOURS), 5, Location.OUTDOOR);
//			log.info("Run: " + run);
//		};
//	}
}
