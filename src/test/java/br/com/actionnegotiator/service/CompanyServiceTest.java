package br.com.actionnegotiator.service;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.actionnegotiator.exception.BigDecimalLengthException;
import br.com.actionnegotiator.exception.DuplicateConstraintException;
import br.com.actionnegotiator.exception.StringLengthException;
import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.repository.CompanyRepository;

@RunWith(SpringRunner.class)
public class CompanyServiceTest {
	
	@MockBean
	private CompanyRepository companyRepository;
	
	private CompanyService companyService;
	
	private Company company;
	
	private static final String name = "Google";
	private static final BigDecimal value = BigDecimal.valueOf(10.37);
	
	@Before
	public void setUp() {
		companyService = new CompanyService(companyRepository);
		company = new Company(name, value);
	}
	
	@Test
	public void mustExecuteRepositoryFindAll() {
		companyService.findAll();
		Mockito.verify(companyRepository).findAll();
	}
	
	@Test
	public void mustExecuteRepositorySave() throws DuplicateConstraintException, StringLengthException, BigDecimalLengthException {
		companyService.save(company);
		Mockito.verify(companyRepository).save(company);
	}
	
	@Test(expected = DuplicateConstraintException.class)
	public void mustRespectUniqueKey() throws DuplicateConstraintException, StringLengthException, BigDecimalLengthException {
		Mockito.when(companyRepository.findByName(name)).thenReturn(company);
		companyService.save(company);		
	}
	
	@Test(expected = StringLengthException.class)
	public void mustRespectNameLength() throws DuplicateConstraintException, StringLengthException, BigDecimalLengthException {
		company.setName("ABCDEFGHIJKLMNOPQRSTUVXYWZABCDEFGHIJKLMNOPQRSTUVXYWZABCDEFGHIJKLMNOPQRSTUVXYWZABCDEFGHIJKLMNOPQRSTUVXYWZ"
				+ "ABCDEFGHIJKLMNOPQRSTUVXYWZABCDEFGHIJKLMNOPQRSTUVXYWZABCDEFGHIJKLMNOPQRSTUVXYWZABCDEFGHIJKLMNOPQRSTUVXYWZ"
				+ "ABCDEFGHIJKLMNOPQRSTUVXYWZABCDEFGHIJKLMNOPQRSTUVXYWZ");
		companyService.save(company);		
	}
	
	@Test(expected = BigDecimalLengthException.class)
	public void mustRespectValueLength() throws DuplicateConstraintException, StringLengthException, BigDecimalLengthException {
		company.setValue(BigDecimal.valueOf(1111111111111111111L).multiply(BigDecimal.valueOf(1000)));
		companyService.save(company);		
	}
	
}
