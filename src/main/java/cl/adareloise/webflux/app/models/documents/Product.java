package cl.adareloise.webflux.app.models.documents;


import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "products")
public class Product {
	
	@Id
	private String id;
	
	private String name;
	
	private Double price;
	
	private Date createAt;

	public Product() {
		super();
	}

	public Product(String name, Double price) {
		super();
		this.name = name;
		this.price = price;
	}
	
	
}
