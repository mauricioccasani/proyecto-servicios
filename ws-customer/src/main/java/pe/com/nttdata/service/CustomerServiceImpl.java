package pe.com.nttdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import pe.com.nttdata.model.Customer;
import pe.com.nttdata.repository.CustomerRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class CustomerServiceImpl implements  CustomerServiceInf {
	@Autowired
	private CustomerRepository repository;

	@Override
	public Mono<Customer> save(Mono<Customer> customer) {
		return customer.flatMap(repository::insert);
	}

	@Override
	public Flux<Customer> findAll() {
		return this.repository.findAll();
	}

	@Override
	@Cacheable(value = "customerCache")
	public Mono<Customer> findById(String id) {
		return this.repository.findById(id);
	}

	@Override
	public Mono<Void> delete(Customer producto) {
		return this.repository.delete(producto);
	}
}
