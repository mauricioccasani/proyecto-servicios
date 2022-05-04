package pe.com.nttdata.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import pe.com.nttdata.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String>{
	public Mono<Product>  findByIdCustomer(String idCustomer);
	public Flux<Product>findTop2ByOrderByIdCustomerDesc(String idCustomer);
}
