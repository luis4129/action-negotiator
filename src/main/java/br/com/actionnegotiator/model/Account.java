package br.com.actionnegotiator.model;

import java.math.BigDecimal;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints={
	    @UniqueConstraint(columnNames = {"email"})
})
public class Account {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private String email;

	@NotNull
	private BigDecimal fund;

	@OneToMany(mappedBy="account")
	private Collection<InvestmentRule> investmentRule;

	@OneToMany(mappedBy="account")
	private Collection<Stock> stock;
	
	public Account() {
		super();
	}

	public Account(String email, BigDecimal fund) {
		this.setEmail(email);
		this.setFund(fund);
	}

	public Account(Long id) {
		this.setId(id);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getFund() {
		return fund;
	}

	public void setFund(BigDecimal fund) {
		this.fund = fund;
	}

	public Collection<InvestmentRule> getInvestmentRule() {
		return investmentRule;
	}

	public void setInvestmentRule(Collection<InvestmentRule> investmentRule) {
		this.investmentRule = investmentRule;
	}

	public Collection<Stock> getStock() {
		return stock;
	}

	public void setStock(Collection<Stock> stock) {
		this.stock = stock;
	}	
	
}
