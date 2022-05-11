package pe.com.nttdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import pe.com.nttdata.model.Historico;
import pe.com.nttdata.repository.HistoricoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class HistoricoServiceImpl implements  HistoricoServiceInf {
	@Autowired
	private HistoricoRepository historicoRepository;

	@Override
	public Mono<Historico> save(Mono<Historico> historico) {
		
		return historico.flatMap(historicoRepository::insert);
	}

	@Override
	public Flux<Historico> findAll() {
		return this.historicoRepository.findAll();
	}

	@Override
	@Cacheable(value = "customerCache")
	public Mono<Historico> findById(String id) {
		return this.historicoRepository.findById(id);
	}

	@Override
	public Mono<Void> delete(Historico producto) {
		return this.historicoRepository.delete(producto);
	}
	



}
