package br.com.actionnegotiator.model;

import java.math.BigDecimal;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

@Entity
public class Account {
	
	@Id
	@GeneratedValue
	Long id;
	
	String email;
	
	BigDecimal fund;
	
	@OneToMany
	Collection<Transaction> transaction;
	
	@OneToMany
	Collection<InvestmentRule> investmentRule;
	
	@OneToMany(cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
	Collection<Action> action;
	
	public Account() {
		
	}

	public Account(String email, BigDecimal fund) {
		this.setEmail(email);
		this.setFund(fund);
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

	public Collection<Transaction> getTransaction() {
		return transaction;
	}

	public void setTransaction(Collection<Transaction> transaction) {
		this.transaction = transaction;
	}

	public Collection<InvestmentRule> getInvestmentRule() {
		return investmentRule;
	}

	public void setInvestmentRule(Collection<InvestmentRule> investmentRule) {
		this.investmentRule = investmentRule;
	}

	public Collection<Action> getAction() {
		return action;
	}

	public void setAction(Collection<Action> action) {
		this.action = action;
	}
	
}
