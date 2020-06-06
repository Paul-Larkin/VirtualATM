package main;

import java.io.Serializable;

public class Account implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String accountNumber;
	private String password; 
	private float balance;
	private Customer customer;
	
	public Account(String accountNumber, String password) {
		this.accountNumber = accountNumber;
		this.password = password;
	}
	
	public Account(String accountNumber, String password, Customer c) {
		this.accountNumber = accountNumber;
		this.password = password;
		this.customer = c;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Customer getCustomer() {
		return this.customer;
	}
	
	public float getBalance() {
		return balance;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}

	public void withdraw(float amount) {
		balance = balance - amount;
	}
	
	public void deposit(float amount) {
		balance = balance + amount;
	}
	
	public String getPassword() {
		return this.password;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Account) {
			if(this.accountNumber.equals(((Account)obj).accountNumber)) {
				return true;
			}
			return false;
		}
		return false;
	}
}
