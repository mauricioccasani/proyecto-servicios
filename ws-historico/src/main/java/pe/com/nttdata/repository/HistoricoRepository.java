package pe.com.nttdata.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import pe.com.nttdata.model.Historico;

@Repository
public interface HistoricoRepository extends ReactiveMongoRepository<Historico, String>{
	
}
