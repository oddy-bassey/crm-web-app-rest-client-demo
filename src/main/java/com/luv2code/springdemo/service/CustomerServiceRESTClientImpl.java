package com.luv2code.springdemo.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.luv2code.springdemo.model.Customer;

@Service
public class CustomerServiceRESTClientImpl implements CustomerService {

	private RestTemplate restTemplate;
	
	private String crmRestURL;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	public CustomerServiceRESTClientImpl(RestTemplate template, @Value("${crm.rest.url}") String url) {
		
		restTemplate = template;
		crmRestURL = url;
		
		logger.info("Loaded property crm.rest.url - " + crmRestURL);
	}

	@Override
	public List<Customer> getCustomers() {
		
		logger.info("In getCustomers, calling REST api: " + crmRestURL);
		
		//make REST call to HTTP GET method (exchange)
		ResponseEntity<List<Customer>> responseEntity = restTemplate.exchange(crmRestURL, HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<Customer>>() {}) ;
		
		//return response to cutsomer object
		List<Customer> customer = responseEntity.getBody();
		
		logger.info("Exit getCustomers, calling REST api: " + crmRestURL);
		
		return customer;
	}

	@Override
	public Customer getCustomer(int id) {
		
		logger.info("In getCustomer, calling REST api: "+crmRestURL);
	
		//make REST call to HTTP GET method (getForObject)
		Customer customer = restTemplate.getForObject(crmRestURL+"/"+id, Customer.class);
		
		logger.info("Exit getCustomer, calling REST api: " + crmRestURL);
		
		return customer;
	}

	@Override
	public void saveCustomer(Customer customer) {

		logger.info("In saveCustomer, calling REST api: "+crmRestURL);
		
		//retrieve id to know if its an update or a save operation
		int customerId = customer.getId();
		
		//make REST call to HTTP POST method (postForEntity or put)
		if(customerId == 0) {
			restTemplate.postForEntity(crmRestURL, customer, String.class);
		}else {
			restTemplate.put(crmRestURL, customer);
		}

		logger.info("Exit saveCustomer, calling REST api: " + crmRestURL);
	}

	@Override
	public void deleteCustomer(int id) {
		
		logger.info("In deleteCustomer, calling REST api: "+crmRestURL);
		
		//make REST call to HTTP DELETE method (delete)
		restTemplate.delete(crmRestURL + "/" + id);
	}

}
