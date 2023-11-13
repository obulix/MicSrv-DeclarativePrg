package bootcamp.micsrv.decprg.product.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import bootcamp.micsrv.decprg.product.model.Product;
import bootcamp.micsrv.decprg.product.repository.ProductRepository;
@Configuration
public class DatabaseLoader {

	@Bean
	CommandLineRunner init(ProductRepository repository) {
		return args -> {
			repository.save(new Product("Surf",15.5));
			repository.save(new Product("Nirma",11.8));
			repository.save(new Product("Arial",12.8));
			repository.save(new Product("PowerSoap",3.8));
			repository.save(new Product("Tide",21.8));
		};
	}
}