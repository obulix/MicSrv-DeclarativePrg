package bootcamp.micsrv.decprg.user.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import bootcamp.micsrv.decprg.user.model.User;
import bootcamp.micsrv.decprg.user.repository.UserRepository;

@Configuration
public class DatabaseLoader {

	@Bean
	CommandLineRunner init(UserRepository repository) {
		return args -> {
			repository.save(new User("Kumar","kumar@test.com","21 car street,samplecity,TX-77410",true));
			repository.save(new User("Nirmal","Nirmal@test.com","22 car street,samplecity,TX-77312",true));
			repository.save(new User("Sampath","Sampath@test.com","1231 car street,samplecity,TX-77754",false));
			repository.save(new User("Manohar","Manohar@test.com","2222 car street,samplecity,TX-72312",true));
			repository.save(new User("Siva","Siva@test.com","6644 car street,samplecity,TX-77234",false));
		};
	}
}