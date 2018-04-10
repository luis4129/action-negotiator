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
	private Collection<InvestmentRule> investmentRules;

	@OneToMany(mappedBy="account")
	private Collection<Stock> stocks;
	
	public Account() {
		super();
	}	
	
	public Account(Long id, String email, BigDecimal fund) {
		super();
		this.id = id;
		this.email = email;
		this.fund = fund;
	}

	public Account(String email, BigDecimal fund) {
		super();
		this.email = email;
		this.fund = fund;
	}	

	public Account(Long id) {
		super();
		this.id = id;
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

	public Collection<InvestmentRule> getInvestmentRules() {
		return investmentRules;
	}

	public void setInvestmentRules(Collection<InvestmentRule> investmentRules) {
		this.investmentRules = investmentRules;
	}

	public Collection<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(Collection<Stock> stocks) {
		this.stocks = stocks;
	}	
	
}
