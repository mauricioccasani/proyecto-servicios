package pe.com.nttdata.model;


import java.util.Date;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
@AllArgsConstructor
@Document(collection = "historico")

public class Historico {
	@Id
	private String id;
	private double montoActual;
	private String idOpreracion;
	private String fechaOperacion;
	private String idCliente;
	private String lugarOperacion;
	
	

}
