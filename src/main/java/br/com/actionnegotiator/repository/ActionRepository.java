package br.com.actionnegotiator.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.actionnegotiator.model.Action;

public interface ActionRepository extends CrudRepository<Action, Long>  {

}
