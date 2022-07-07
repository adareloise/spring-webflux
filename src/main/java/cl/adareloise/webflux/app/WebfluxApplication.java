package cl.adareloise.webflux.app;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import cl.adareloise.webflux.app.models.documents.Product;
import cl.adareloise.webflux.app.models.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@SpringBootApplication
public class WebfluxApplication implements CommandLineRunner {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(WebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("products").subscribe();
		Flux.just(
					new Product("TV Panasonic Pantalla LCD", 456.89),
					new Product("Sony Camara HD Digital", 177.89),
					new Product("Apple iPod", 46.89),
					new Product("Sony Notebook", 846.89),
					new Product("Hewlett Packard Multifuncional", 200.89),
					new Product("Bianchi Bicicleta", 70.89),
					new Product("HP Notebook Omen 17", 2500.89),
					new Product("Mica Cómoda 5 Cajones", 150.89),
					new Product("TV Sony Bravia OLED 4K Ultra HD", 2255.89)
				)
			.flatMap(product -> {
				product.setCreateAt(new Date());
				return productRepository.save(product);
			})
			.subscribe(product -> log.info("insert:"+ product));
	}

}
