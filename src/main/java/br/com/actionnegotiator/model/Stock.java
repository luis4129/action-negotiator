package br.com.actionnegotiator.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints={
	    @UniqueConstraint(columnNames = {"account_id", "company_id"})
})
public class Stock {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@ManyToOne
	private Account account;

	@NotNull
	@ManyToOne
	private Company company;

	@NotNull
	private BigDecimal quantity;
	
	public Stock() {
		super();
	}	

	public Stock(Account account, Company company, BigDecimal quantity) {
		super();
		this.account = account;
		this.company = company;
		this.quantity = quantity;
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
