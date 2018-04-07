package br.com.actionnegotiator.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Account account;

	@NotNull
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Company company;

	private BigDecimal quantity;

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
