package pe.com.nttdata.model;

import java.util.List;

import lombok.Data;

@Data
public class Customer {
	private String id;
	private String name;
	private String surname;
	private List<TypeCustomer>typeCustomers;
	private Product  products;
}
