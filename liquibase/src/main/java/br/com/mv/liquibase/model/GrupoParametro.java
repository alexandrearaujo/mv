package br.com.mv.liquibase.model;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MV_GRUPO_PARAMETRO")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor 
public class GrupoParametro implements Serializable, ChangeSetLiquibaseInterface {
	
	private static final long serialVersionUID = -1151478368823472217L;

	@Id
    @SequenceGenerator(name = "MV_SEQ_GRUPO_PARAMETRO", sequenceName = "MV_SEQ_GRUPO_PARAMETRO", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MV_SEQ_GRUPO_PARAMETRO")
    @Column(name = "cd_grupo_parametro")
    private Long id;

    @Column(name = "ds_descricao")
    private String descricao;

//    @OneToMany(cascade=CascadeType.ALL, mappedBy = "grupoParametro", targetEntity = Parametro.class, fetch = FetchType.LAZY)
//    private Collection<Parametro> parametros;
    

	@Override
	public String getSqlPrecondition() {
		return "SELECT COUNT(1) FROM DBAMVFOR.MV_GRUPO_PARAMETRO WHERE DS_DESCRICAO = '" + descricao + "'";
	}

	@Override
	public boolean isFieldNotInsertable(Field field) {
		return false;
	}

	@Override
	public String getWhereClause(Field field) {
		return null;
	}

	@Override
	public String getChangeSetComments() {
		return "Inclusão do grupo parâmetro " + descricao;
	}

	@Override
	public String getIdChangeSetDescription() {
		return "insert_mv_grupo_parametro_" + descricao;
	}

}
