package br.com.actionnegotiator.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.actionnegotiator.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long>  {

}
