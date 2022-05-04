package pe.com.nttdata.service;

import pe.com.nttdata.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductServiceInf {
	public Mono<Product>  save(Mono<Product> product);
	public Flux<Product>findAll();
	public Mono<Product>  findById(String id);
	public Mono<Void> delete(Product producto);
	public Mono<Product>  findByIdCustomer(String id);
	public Flux<Product>findTop2ByOrderByIdCustomerDesc(String idCustomer);
	
}
