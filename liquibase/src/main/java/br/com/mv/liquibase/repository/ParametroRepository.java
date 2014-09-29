package br.com.mv.liquibase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.mv.liquibase.model.Parametro;

public interface ParametroRepository extends CrudRepository<Parametro, Long> {

	@Query("select p from Parametro p")
	List<Parametro> findAll();
}
