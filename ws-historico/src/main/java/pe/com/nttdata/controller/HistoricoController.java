package pe.com.nttdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import lombok.extern.log4j.Log4j2;
import pe.com.nttdata.model.Historico;
import pe.com.nttdata.service.HistoricoServiceInf;
import pe.com.nttdata.util.Constantes;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(Constantes.URL_PATH)
@Log4j2
public class HistoricoController {
	@Autowired
	private HistoricoServiceInf historicoService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Historico> create(@RequestBody Historico historico) {
		log.info("Reques: {}", historico);
		return this.historicoService.save(Mono.just(historico));
	}

	@GetMapping
	@ResponseBody
	public Flux<Historico> getAll() {
		return historicoService.findAll();
	}

	@GetMapping("/{id}")
	@ResponseBody
	public Mono<Historico> findByIds(@PathVariable String id) {
		return historicoService.findById(id);
	}

	@PutMapping("/{id}")
	public Mono<Historico> update(@PathVariable String id, @RequestBody Historico request) {
		return this.historicoService.findById(id).flatMap(p -> {

			p.setId(request.getId());
			p.setMontoActual(request.getMontoActual());
			p.setIdOpreracion(request.getIdOpreracion());
			p.setFechaOperacion(request.getFechaOperacion());
			p.setIdCliente(request.getIdCliente());
			p.setLugarOperacion(request.getLugarOperacion());
		
			return this.historicoService.save(Mono.just(p));
		});
	}

	@DeleteMapping("/{id}")
	public Mono<Void> delete(@PathVariable String id) {
		return this.historicoService.findById(id).flatMap(c -> this.historicoService.delete(c));
	}

	

}
