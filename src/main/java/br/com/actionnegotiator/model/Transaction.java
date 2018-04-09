package br.com.actionnegotiator.model;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import br.com.actionnegotiator.enums.TransactionType;

@Entity
public class Transaction {

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
	private Calendar createdIn;

	@NotNull
	private BigDecimal value;

	@NotNull
	private BigDecimal quantity;

	@NotNull
	private TransactionType type;
	
	private Boolean recent;
	
	public Transaction() {
		super();
	}	

	public Transaction(Account account, Company company, Calendar createdIn, BigDecimal value, BigDecimal quantity,
			TransactionType type, Boolean recent) {
		super();
		this.account = account;
		this.company = company;
		this.createdIn = createdIn;
		this.value = value;
		this.quantity = quantity;
		this.type = type;
		this.recent = recent;
	}	
	
	public Transaction(Account account, Company company, TransactionType type) {
		super();
		this.account = account;
		this.company = company;
		this.type = type;
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

	public Boolean getRecent() {
		return recent;
	}

	public void setRecent(Boolean recent) {
		this.recent = recent;
	}
	
	public Boolean isRecent() {
		return getRecent();
	}	

}
