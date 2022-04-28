package pe.com.nttdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import pe.com.nttdata.model.CustomerResponse;
import pe.com.nttdata.model.Product;
import pe.com.nttdata.model.ProductResponse;
import pe.com.nttdata.servive.OperacionesService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/servicios")
@Log4j2
public class ServicioController {
	
	@Autowired
	private OperacionesService customerService;

	
	@GetMapping("/customer/{id}")
	public Mono<CustomerResponse> getCustomer(@PathVariable String id) {
		CustomerResponse response=this.customerService.consultarCliente(id);
		return Mono.just(response);
	}


	
	@PostMapping
	public Mono<ProductResponse>  getCustomerOpreracion(@RequestBody Product product) {
		log.info("insertar productos:======================: {}",product.toString());
		ProductResponse  response=this.customerService.save(product);
		return  Mono.just(response);
	}
	
	


}
