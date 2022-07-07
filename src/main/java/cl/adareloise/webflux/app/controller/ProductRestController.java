package cl.adareloise.webflux.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cl.adareloise.webflux.app.models.documents.Product;
import cl.adareloise.webflux.app.models.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class ProductRestController {

	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping
	public Flux<Product> index(){

        Flux<Product> products = productRepository.findAll()
        		.map(product -> {
        			product.setName(product.getName().toUpperCase());
        			return product;
        			})
        		.doOnNext(prod -> log.info(prod.getName()));
        
        return products;
	}

	@GetMapping("/{id}")
	public Mono<Product> show(@PathVariable String id){
		
		/* Mono<Producto> producto = dao.findById(id); */
		
		Flux<Product> products = productRepository.findAll();
		
		Mono<Product> product = products
				.filter(p -> p.getId().equals(id))
				.next()
				.doOnNext(prod -> log.info(prod.getName()));
				
		return product;
	}
	
}
