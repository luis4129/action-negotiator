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
public class InvestmentRule {

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
	private BigDecimal purchasePrice;

	@NotNull
	private BigDecimal salePrice;
	
	public InvestmentRule() {
		super();
	}	

	public InvestmentRule(Long id, Account account, Company company, BigDecimal purchasePrice, BigDecimal salePrice) {
		super();
		this.id = id;
		this.account = account;
		this.company = company;
		this.purchasePrice = purchasePrice;
		this.salePrice = salePrice;
	}
	
	public InvestmentRule(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
