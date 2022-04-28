package pe.com.nttdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.nttdata.model.Product;
import pe.com.nttdata.repository.ProductRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class ProductServiceImpl implements  ProductServiceInf {
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Mono<Product> save(Product product) {
		
		return this.productRepository.save(product);
	}

	@Override
	public Flux<Product> findAll() {
		return this.productRepository.findAll();
	}

	@Override
	public Mono<Product> findById(String id) {
		return this.productRepository.findById(id);
	}

	@Override
	public Mono<Void> delete(Product producto) {
		return this.productRepository.delete(producto);
	}
	@Override
	public Mono<Product> findByIdCustomer(String id) {
		return this.productRepository.findByIdCustomer(id);
	}
	



}
