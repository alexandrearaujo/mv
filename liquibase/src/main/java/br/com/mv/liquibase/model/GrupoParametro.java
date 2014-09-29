package br.com.mv.liquibase.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Moesio Medeiros
 * @date Thu Oct 30 16:12:09 GMT-03:00 2008
 */
@Entity
@Table(name = "MV_GRUPO_PARAMETRO")
public class GrupoParametro implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1151478368823472217L;

	@Id
    @SequenceGenerator(name = "MV_SEQ_GRUPO_PARAMETRO", sequenceName = "MV_SEQ_GRUPO_PARAMETRO", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MV_SEQ_GRUPO_PARAMETRO")
    @Column(name = "cd_grupo_parametro")
    private Long id;

    @Column(name = "ds_descricao")
    private String descricao;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "grupoParametro", targetEntity = Parametro.class, fetch = FetchType.LAZY)
    private Collection<Parametro> parametros;

    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getDescricao()
    {
        return descricao;
    }

    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }

    public Collection<Parametro> getParametros()
    {
        return parametros;
    }

    public void setParametros(Collection<Parametro> parametros)
    {
        this.parametros = parametros;
    }

}
