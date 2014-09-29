package br.com.mv.liquibase.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Classe de modelo da entidade OpcaoParametro
 * @author Natanael Ramalho
 * @date Wed Aug 10 17:59:02 GMT-03:00 2011
 * @version 1
 */
@Entity
@Table(name = "OPCAO_PARAMETRO")
public class OpcaoParametro implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1010236451284357510L;

	@Id
    @SequenceGenerator(name = "SEQ_OPCAO_PARAMETRO", sequenceName = "SEQ_OPCAO_PARAMETRO", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_OPCAO_PARAMETRO")
    @Column(name = "CD_OPCAO_PARAMETRO", nullable = false)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "CD_PARAMETRO", referencedColumnName = "ID", nullable = false)
    private Parametro parametro;
	
	@Column(name = "DS_OPCAO_PARAMETRO", length = 200, nullable = false)
	private String descricaoOpcaoParametro;
	
	@Column(name = "SN_ATIVO", length = 1, nullable = false)
	private Boolean flagAtivo;
	
	// adicione os atributos da entidade e depois crie os getters e setters (Eclipse: alt + shift + S R)
	
	public Long getId()
	{
		return this.id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}

    public Parametro getParametro()
    {
        return parametro;
    }

    public void setParametro(Parametro parametro)
    {
        this.parametro = parametro;
    }

    public String getDescricaoOpcaoParametro()
    {
        return descricaoOpcaoParametro;
    }

    public void setDescricaoOpcaoParametro(String descricaoOpcaoParametro)
    {
        this.descricaoOpcaoParametro = descricaoOpcaoParametro;
    }

    public Boolean getFlagAtivo()
    {
        return flagAtivo;
    }

    public void setFlagAtivo(Boolean flagAtivo)
    {
        this.flagAtivo = flagAtivo;
    }

}