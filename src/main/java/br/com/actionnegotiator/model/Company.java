package br.com.actionnegotiator.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints={
	    @UniqueConstraint(columnNames = {"name"})
})
public class Company {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private BigDecimal value;
	
	public Company() {
		super();
	}	

	public Company(String name, BigDecimal value) {
		super();
		this.name = name;
		this.value = value;
	}

	public Company(Long id) {
		this.setId(id);
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

}
