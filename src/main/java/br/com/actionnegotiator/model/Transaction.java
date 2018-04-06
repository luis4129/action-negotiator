package br.com.actionnegotiator.model;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Transaction {

	@Id
	@GeneratedValue
	Long id;

	@ManyToOne
	Account account;

	@ManyToOne
	Company company;

	Calendar createdIn;

	BigDecimal value;

	BigDecimal quantity;

	TransactionType type;

	public Transaction() {

	}

	public Transaction(TransactionType transactionType, Account account, Company company, BigDecimal value,
			BigDecimal quantity) {
		setType(transactionType);
		setAccount(account);
		setCompany(company);
		setValue(value);
		setQuantity(quantity);
		setCreatedIn(Calendar.getInstance());
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

	public Calendar getCreatedIn() {
		return createdIn;
	}

	public void setCreatedIn(Calendar createdIn) {
		this.createdIn = createdIn;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

}
