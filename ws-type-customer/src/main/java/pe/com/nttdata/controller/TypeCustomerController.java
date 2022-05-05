package pe.com.nttdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import pe.com.nttdata.model.TypeCustomer;
import pe.com.nttdata.service.TypeCustomerServiceInf;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/type-customers")
public class TypeCustomerController {
	@Autowired
	private TypeCustomerServiceInf serviceInf;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<TypeCustomer> addTypeCustomer(@RequestBody TypeCustomer typeCustomer) {
		return this.serviceInf.addTypeCustomer(Mono.just(typeCustomer));
	}

	@GetMapping
	@ResponseBody
	public Flux<TypeCustomer> getTypeCustomer() {
		return serviceInf.gelAllTypeCustomer();
	}
	
	@GetMapping("/buscar/{idCustomer}")
	@ResponseBody
	public Flux<TypeCustomer> findAllByIdCustomer(@PathVariable String idCustomer) {
	
		return serviceInf.findAllByIdCustomer(idCustomer);
	}
}
