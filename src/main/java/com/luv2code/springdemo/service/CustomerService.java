package com.luv2code.springdemo.service;

import java.util.List;

import com.luv2code.springdemo.model.Customer;

public interface CustomerService {
	
	//customer service for loading customers
	public List<Customer> getCustomers();
	
	//customer service for loading a particular customer
	public Customer getCustomer(int id);
	
	//customer service for saving customers
	public void saveCustomer(Customer customer);
	
	//customer service for deleting customers
	public void deleteCustomer(int id);
}
