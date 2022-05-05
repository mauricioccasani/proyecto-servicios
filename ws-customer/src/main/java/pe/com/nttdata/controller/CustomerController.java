package pe.com.nttdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import pe.com.nttdata.model.Customer;
import pe.com.nttdata.service.CustomerServiceInf;
import pe.com.nttdata.util.Constantes;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(Constantes.URL_PATH_REST)
public class CustomerController {
	@Autowired // Inyeccion de dependencias
	private CustomerServiceInf customerService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	// Crear empleado
	public Mono<Customer> create(@RequestBody Customer customer) {
		return this.customerService.save(Mono.just(customer));
	}

	@GetMapping
	@ResponseBody
	// Listar empleados
	public Flux<Customer> getAll() {
		return customerService.findAll();
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	// Buscar por id
	public Mono<Customer> findByIds(@PathVariable String id) {
		return customerService.findById(id);
	}
	
	
	@PutMapping("/{id}")
	// Actualizar empleado
	public Mono<Customer> update(@PathVariable String id,  @RequestBody Customer customer) {
		return this.customerService.findById(id)
				.flatMap(c->{
					c.setId(customer.getId());
					c.setName(customer.getName());
					c.setSurname(customer.getSurname());
					return this.customerService.save(Mono.just(c));
				});
	}
	
	
	@DeleteMapping("/{id}")
	// Borrar empleado
	public Mono<Void> delete(@PathVariable String id) {
		return this.customerService.findById(id)
				.flatMap(c->this.customerService.delete(c));
	}
	
	

}
