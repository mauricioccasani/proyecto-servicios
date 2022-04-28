package pe.com.nttdata.servive;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import lombok.extern.log4j.Log4j2;
import pe.com.nttdata.client.CustomerClientInf;
import pe.com.nttdata.client.HistoricoClientInf;
import pe.com.nttdata.client.ProductClientInf;
import pe.com.nttdata.client.TypeCustomerClientInf;
import pe.com.nttdata.model.Customer;
import pe.com.nttdata.model.CustomerResponse;
import pe.com.nttdata.model.Historico;
import pe.com.nttdata.model.Product;
import pe.com.nttdata.model.ProductResponse;
import pe.com.nttdata.model.TypeCustomer;
import pe.com.nttdata.model.TypeProduct;
import pe.com.nttdata.util.Constantes;

@Service
@Log4j2
public class OperacionesService {
	@Autowired
	private CustomerClientInf customerClientInf;

	@Autowired
	private TypeCustomerClientInf typeCustomerClientInf;

	@Autowired
	private ProductClientInf productClient;
	
	@Autowired
	private HistoricoClientInf historicoClientInf;
	public Historico save(Historico historico) {
		return this.historicoClientInf.save(historico);
	}

	public CustomerResponse consultarCliente(String id) {
		CustomerResponse response = new CustomerResponse();
		Customer customer = this.customerClientInf.findById(id);
		List<TypeCustomer> lst = this.typeCustomerClientInf.searchByIdCustomer(customer.getId());
		Product products = this.productClient.findByIdCustomer(customer.getId());
		customer.setTypeCustomers(lst);
		customer.setProducts(products);
		response.setCustomer(customer);
		log.info("Response del cliente: {}", response.toString());
		return response;
	}

	public Product findByIdProduct(String id) {
		return this.productClient.findById(id);
	}

	public ProductResponse save(Product product) {
		ProductResponse response=new ProductResponse();
		Product oProduct = new Product();
		ObjectMapper mapper = new ObjectMapper();
		Historico historico=new Historico();
		
		List<TypeProduct> lstTypeProduct = this.productClient.getAllTypeProduct();
		log.info("Tamanio de lista TypeProduct: {}", lstTypeProduct.size());
		List<TypeProduct> typeProductList = mapper.convertValue(lstTypeProduct, new TypeReference<List<TypeProduct>>() {});
		for (TypeProduct typeProduct : typeProductList) {
			if (typeProduct.isStatus()) {
				Product pr = this.findByIdProduct(product.getId());
				oProduct.setId(pr.getId());
				oProduct.setCommission(pr.getCommission());
				oProduct.setNumberOfMovements(pr.getNumberOfMovements());
				oProduct.setNumberOfCredit(pr.getNumberOfCredit());
				oProduct.setLimitCredit(pr.getLimitCredit());
				oProduct.setIdTypeProduct(pr.getIdTypeProduct());
				oProduct.setIdCustomer(pr.getIdCustomer());
				oProduct.setAction(product.getAction());
				if (product.getAction().equalsIgnoreCase(Constantes.DEPOSITO)) {
					oProduct.setAmount(pr.getAmount() + product.getAmount());
					historico.setMontoActual(oProduct.getAmount());
					historico.setIdOpreracion(oProduct.getId());
					LocalDateTime date=LocalDateTime.now();
					String fecha=date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy:mm:ss.SSS"));
					historico.setFechaOperacion(fecha);
					historico.setIdCliente(oProduct.getIdCustomer());
					historico.setLugarOperacion("001");
					Gson gson=new Gson();
					gson.toJson(historico);
					this.historicoClientInf.save(historico);
				} else if (product.getAction().equalsIgnoreCase(Constantes.RETIRO)) {
					if (pr.getAmount()>0) {
						oProduct.setAmount(pr.getAmount() - product.getAmount());
					}else {
						response.setCodRequest("-1");
						response.setMsgRequest("Salda insuficiente");
						response.setProduct(null);
						return response;
					}
					
				}
				
				
			}
			
			
		}
		
		Product products=this.productClient.save(oProduct);
		response.setCodRequest("0");
		response.setMsgRequest("ok");
		response.setProduct(products);
		return response;

	}

}
