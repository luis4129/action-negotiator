package br.com.actionnegotiator.model;

import java.math.BigDecimal;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Company {
	
	@Id
	@GeneratedValue
	Long id;
	
	@OneToMany
	Collection<Action> action;
	
	String name;
	
	BigDecimal value;
	
	public Company() {
		
	}
	
	public Company(String name, BigDecimal value) {
		this.setName(name);
		this.setValue(value);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Collection<Action> getAction() {
		return action;
	}

	public void setAction(Collection<Action> action) {
		this.action = action;
	}
	
	

}
