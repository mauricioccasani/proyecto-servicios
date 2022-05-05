package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import pe.com.nttdata.controller.TypeCustomerController;
import pe.com.nttdata.model.TypeCustomer;
import pe.com.nttdata.service.TypeCustomerServiceInf;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@WebFluxTest(TypeCustomerController.class)
class DemoApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private TypeCustomerServiceInf customerService;

	@Test
	public void createCustomer(){
		var customerMono = Mono.just(new TypeCustomer("1", "Empresarial", "activo", "1"));

		when(customerService.addTypeCustomer(customerMono)).thenReturn(customerMono);
		webTestClient.post().uri("/api/v1/")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(customerMono), TypeCustomer.class)
				.exchange()
				.expectStatus().isCreated();
	}

	@Test
	public void getAllTypeCustomers(){
		var typeCustomerFlux = Flux.just(new TypeCustomer("1", "Empresarial", "activo", "1"),new TypeCustomer("2", "Personal", "activo", "2"));
		when(customerService.gelAllTypeCustomer()).thenReturn(typeCustomerFlux);
		var responseBody = webTestClient.get().uri("/api/v1/")
				.exchange()
				.expectStatus().isOk()
				.returnResult(TypeCustomer.class)
				.getResponseBody();
		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNext(new TypeCustomer("1", "Empresarial", "activo", "1"))
				.expectNext(new TypeCustomer("2", "Personal", "activo", "2"))
				.verifyComplete();
	}

	@Test
	public void getById(){
		var typeCustomerFlux = Flux.just(new TypeCustomer("1", "Empresarial", "activo", "1"));
		when(customerService.findAllByIdCustomer(any())).thenReturn(typeCustomerFlux);
		var employeeResp = webTestClient.get().uri("/api/v1/1")
				.exchange()
				.expectStatus().isOk()
				.returnResult(TypeCustomer.class)
				.getResponseBody();

		StepVerifier.create(typeCustomerFlux)
				.expectSubscription()
				.expectNextMatches(e -> e.getId().equals("1"))
				.verifyComplete();
	}

}
