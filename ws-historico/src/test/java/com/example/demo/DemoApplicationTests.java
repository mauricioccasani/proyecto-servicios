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
import pe.com.nttdata.controller.HistoricoController;
import pe.com.nttdata.model.Historico;
import pe.com.nttdata.service.HistoricoServiceInf;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@WebFluxTest(HistoricoController.class)
class DemoApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private HistoricoServiceInf historicoService;

	@Test
	public void createHistorico(){
		var historicoMono = Mono.just(new Historico("1", 500, "001", "14/02/2022", "1", "0526"));

		when(historicoService.save(historicoMono)).thenReturn(historicoMono);
		webTestClient.post().uri("/api/v1/")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(historicoMono), Historico.class)
				.exchange()
				.expectStatus().isCreated();
	}

	@Test
	public void getAllHistoricos(){
		var historicoFlux = Flux.just(new Historico("1", 500, "001", "14/02/2022", "1", "0526"),new Historico("2", 1000, "003", "05/05/2022", "1", "0576"));
		when(historicoService.findAll()).thenReturn(historicoFlux);
		var responseBody = webTestClient.get().uri("/api/v1/")
				.exchange()
				.expectStatus().isOk()
				.returnResult(Historico.class)
				.getResponseBody();
		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNext(new Historico("1", 500, "001", "14/02/2022", "1", "0526"))
				.expectNext(new Historico("2", 1000, "003", "05/05/2022", "1", "0576"))
				.verifyComplete();
	}

	@Test
	public void getById(){
		var historicoMono = Mono.just(new Historico("1", 500, "001", "14/02/2022", "1", "0526"));
		when(historicoService.findById(any())).thenReturn(historicoMono);
		var employeeResp = webTestClient.get().uri("/api/v1/1")
				.exchange()
				.expectStatus().isOk()
				.returnResult(Historico.class)
				.getResponseBody();

		StepVerifier.create(historicoMono)
				.expectSubscription()
				.expectNextMatches(e -> e.getId().equals("1"))
				.verifyComplete();
	}
}
