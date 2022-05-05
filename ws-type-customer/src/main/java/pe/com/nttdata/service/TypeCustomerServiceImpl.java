package pe.com.nttdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.nttdata.model.TypeCustomer;
import pe.com.nttdata.repository.TypeCustomerRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class TypeCustomerServiceImpl implements  TypeCustomerServiceInf {
	@Autowired
	private TypeCustomerRepository repository;

	@Override
	public Mono<TypeCustomer> addTypeCustomer(Mono<TypeCustomer> typeCustomer) {
		return typeCustomer.flatMap(repository::insert);
	}

	@Override
	public Flux<TypeCustomer> gelAllTypeCustomer() {
		return this.repository.findAll();
	}

	

	@Override
	public Flux<TypeCustomer> findAllByIdCustomer(String idCustomer) {
		return this.repository.findAllByIdCustomer(idCustomer)
				.filter(c->c.getStatus().trim().equals("activo"));
	}

}
