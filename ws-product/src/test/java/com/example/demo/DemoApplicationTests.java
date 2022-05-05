package com.example.demo;

import static org.mockito.ArgumentMatchers.any;
import  static  org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import pe.com.nttdata.controller.ProductController;
import pe.com.nttdata.model.Product;
import pe.com.nttdata.service.ProductServiceInf;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ProductController.class)
class DemoApplicationTests {
	@Autowired
	private WebTestClient webTestClient;
	@MockBean
	private ProductServiceInf productService;

	@Test
	void createProduct() {
		var product = Mono.just(new Product("62729009d6707618a0089517", 0.0, 0, 0, 200.0, 0, "deposito", "62616f58c5524c223a20212e", "62617e4560c2270554aafb26"));
		when(productService.save(product)).thenReturn(product);
		webTestClient.post().uri("/api/v1/products/save")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(product),Product.class)
				.exchange()
				.expectStatus().isCreated();
	}

	@Test
	public void getAllProducts(){
		var productFlux = Flux.just(new Product("1", 0.0, 8, 10, 500.0, 1000, "retiro", "5", "8"),new Product("2", 0.10, 4, 6, 200.0, 900, "deposito", "6", "2"));
		when(productService.findAll()).thenReturn(productFlux);
		var responseBody = webTestClient.get().uri("/api/v1/")
				.exchange()
				.expectStatus().isOk()
				.returnResult(Product.class)
				.getResponseBody();
		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNext(new Product("1", 0.0, 8, 10, 500.0, 1000, "retiro", "5", "8"))
				.expectNext(new Product("2", 0.10, 4, 6, 200.0, 900, "deposito", "6", "2"))
				.verifyComplete();
	}

	@Test
	public void getById(){
		var productMono = Mono.just(new Product("1", 0.0, 8, 10, 500.0, 1000, "retiro", "5", "8"));
		when(productService.findById(any())).thenReturn(productMono);
		var employeeResp = webTestClient.get().uri("/api/v1/1")
				.exchange()
				.expectStatus().isOk()
				.returnResult(Product.class)
				.getResponseBody();

		StepVerifier.create(productMono)
				.expectSubscription()
				.expectNextMatches(e -> e.getId().equals("1"))
				.verifyComplete();
	}
}
