package pe.com.nttdata.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Document(collection = "type_customer")
public class TypeCustomer {
	@Id
	private String id;
	private String description;
	private String status;
	private String idCustomer; 
	
	

}
