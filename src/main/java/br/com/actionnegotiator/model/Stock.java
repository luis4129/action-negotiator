package br.com.actionnegotiator.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Stock {

	@Id
	@GeneratedValue
	Long id;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	Account account;

	@ManyToOne
	Company company;

	BigDecimal quantity;

	public Stock() {

	}

	public Stock(Account account, Company company, BigDecimal quantity) {
		setAccount(account);
		setCompany(company);
		setQuantity(quantity);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

}
