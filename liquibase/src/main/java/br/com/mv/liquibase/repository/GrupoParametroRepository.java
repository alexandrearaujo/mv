package br.com.mv.liquibase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.mv.liquibase.model.GrupoParametro;

public interface GrupoParametroRepository extends CrudRepository<GrupoParametro, Long> {

	@Query("select gp from GrupoParametro gp")
	List<GrupoParametro> findAll();
	
}
