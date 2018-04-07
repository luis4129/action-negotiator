package br.com.actionnegotiator.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.actionnegotiator.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long>  {
	
	@Transactional
	@Modifying
	@Query("update Transaction t set t.recent = false")
	public void setAllRecentToFalse();
	
	public Iterable<Transaction> findByRecentOrderByCreatedInDesc(Boolean isRecent);
	
	public Iterable<Transaction> findByOrderByCreatedInDesc();

}
