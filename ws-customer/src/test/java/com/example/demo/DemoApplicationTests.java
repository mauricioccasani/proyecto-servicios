package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import pe.com.nttdata.controller.CustomerController;
import pe.com.nttdata.model.Customer;
import pe.com.nttdata.service.CustomerServiceInf;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@WebFluxTest(CustomerController.class)

class DemoApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private CustomerServiceInf customerService;

	@Test
	public void createCustomer(){
		var customerMono = Mono.just(new Customer("1", "Mary", "Pérez"));

		when(customerService.save(customerMono)).thenReturn(customerMono);
		webTestClient.post().uri("/api/v1/")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(customerMono), Customer.class)
				.exchange()
				.expectStatus().isCreated();
	}

	@Test
	public void getAllCustomers(){
		var customerFlux = Flux.just(new Customer("1","Mary","Pérez"),new Customer("2","Ana","Fernandez"));
		when(customerService.findAll()).thenReturn(customerFlux);
		var responseBody = webTestClient.get().uri("/api/v1/")
				.exchange()
				.expectStatus().isOk()
				.returnResult(Customer.class)
				.getResponseBody();
		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNext(new Customer("1","Mary","Pérez"))
				.expectNext(new Customer("2","Ana","Fernandez"))
				.verifyComplete();
	}

	@Test
	public void getById(){
		var customerMono = Mono.just(new Customer("1","Mary","Pérez"));
		when(customerService.findById(any())).thenReturn(customerMono);
		var employeeResp = webTestClient.get().uri("/api/v1/1")
				.exchange()
				.expectStatus().isOk()
				.returnResult(Customer.class)
				.getResponseBody();

		StepVerifier.create(customerMono)
				.expectSubscription()
				.expectNextMatches(e -> e.getName().equals("Mary"))
				.verifyComplete();
	}
}
