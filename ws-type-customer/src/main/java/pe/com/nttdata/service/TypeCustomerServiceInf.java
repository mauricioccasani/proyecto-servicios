package pe.com.nttdata.service;

import pe.com.nttdata.model.TypeCustomer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TypeCustomerServiceInf {
	 Mono<TypeCustomer>  add(TypeCustomer typeCustomer);
	 Flux<TypeCustomer>gelAll();
	
	 Flux<TypeCustomer>buscar(String idCustomer);
	 
	 
}
