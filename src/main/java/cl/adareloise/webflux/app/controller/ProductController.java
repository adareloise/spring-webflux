package cl.adareloise.webflux.app.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import cl.adareloise.webflux.app.WebfluxApplication;
import cl.adareloise.webflux.app.models.documents.Product;
import cl.adareloise.webflux.app.models.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Controller
public class ProductController {
	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping({"/list", "/"})
	public String list(Model model) {
		
		Flux<Product> products = productRepository.findAll().map(product -> {
			
			product.setName(product.getName().toUpperCase());
			return product;
		});
		
		products.subscribe(prod -> log.info(prod.getName()));
		
		model.addAttribute("products", products);
		model.addAttribute("title", "List Products");
		
		return "index";
	}
	
	@GetMapping("/list-data-driver")
	public String listData(Model model) {
		
		Flux<Product> products = productRepository.findAll().map(product -> {
			
			product.setName(product.getName().toUpperCase());
			return product;
		}).delayElements(Duration.ofSeconds(1));
		
		products.subscribe(prod -> log.info(prod.getName()));
		
		model.addAttribute("products", new ReactiveDataDriverContextVariable(products, 2));
		model.addAttribute("title", "List Products");
		
		return "index";
	}
	
	@GetMapping("/list-full")
	public String listFull(Model model) {
		
		Flux<Product> products = productRepository.findAll().map(product -> {
			
			product.setName(product.getName().toUpperCase());
			return product;
		}).repeat(500);
		
		model.addAttribute("products", products);
		model.addAttribute("title", "List Products");
		
		return "index";
	}
	
	@GetMapping("/list-chuncked")
	public String listChuncked(Model model) {
		
		Flux<Product> products = productRepository.findAll().map(product -> {
			
			product.setName(product.getName().toUpperCase());
			return product;
		}).repeat(5000);
		
		model.addAttribute("products", products);
		model.addAttribute("title", "List Products");
		
		return "index-chuncked";
	}
}
