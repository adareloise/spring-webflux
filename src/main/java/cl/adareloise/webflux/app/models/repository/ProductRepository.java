package cl.adareloise.webflux.app.models.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import cl.adareloise.webflux.app.models.documents.Product;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

}
