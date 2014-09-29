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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Moesio Medeiros Date: 28/02/2008 08:32:13
 */
@Entity
@Table(name = "mv_parametro")
public class Parametro implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8033407554336414026L;

	@Id
	@SequenceGenerator(name = "MV_SEQ_PARAMETROS", sequenceName = "MV_SEQ_PARAMETROS", allocationSize = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MV_SEQ_PARAMETROS")
	@Column(name = "ID")
	private Long id;

	@Column(name = "CHAVE", nullable = false, length = 50)
	private String chave;

	@Column(name = "DESCRICAO", nullable = false, length = 200)
	private String descricao;

	@Column(name = "VALOR", nullable = false, length = 300)
	private String valor;

	@Column(name = "OBRIGATORIO", nullable = true, length = 1)
	private Boolean tipoObrigatorio;
	
	@Column(name = "TP_PARAMETRO")
	private Long tipoParametro;
	
	@ManyToOne
	@JoinColumn(name = "cd_grupo_parametro")
	private GrupoParametro grupoParametro;

	@Column(name = "TELA", length = 50)
	private String tela;

	@Column(name = "LO_ARQUIVO")
	@Lob
	private byte[] arquivo;

	@Column(name = "TP_MIME_ARQUIVO", length = 255)
	private String tipoMimeArquivo;

	@Column(name = "NM_ARQUIVO", length = 255)
	private String nomeArquivo;
	
	@Column(name = "DS_OBJETIVO", length = 4000)
	private String descricaoObjetivo;
	
	@OneToMany(mappedBy="parametro",fetch=FetchType.LAZY, cascade=CascadeType.ALL,orphanRemoval=true)
	private Collection<OpcaoParametro> opcoesParametros;

	public String getDescricao()
	{
		return descricao;
	}

	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}

	public String getValor()
	{
		return valor;
	}

	public void setValor(String valor)
	{
		this.valor = valor;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getChave()
	{
		return chave;
	}

	public void setChave(String chave)
	{
		this.chave = chave;
	}

	public GrupoParametro getGrupoParametro()
	{
		return grupoParametro;
	}

	public void setGrupoParametro(GrupoParametro grupoParametro)
	{
		this.grupoParametro = grupoParametro;
	}

	public Boolean getTipoObrigatorio()
	{
		return tipoObrigatorio;
	}

	public void setTipoObrigatorio(Boolean obrigatorio)
	{
		this.tipoObrigatorio = obrigatorio;
	}

	public Long getTipoParametro()
    {
        return tipoParametro;
    }

    public void setTipoParametro(Long tipoParametro)
    {
        this.tipoParametro = tipoParametro;
    }

    public String getTela()
	{
		return tela;
	}

	public void setTela(String tela)
	{
		this.tela = tela;
	}

	public String getTipoMimeArquivo()
	{
		return tipoMimeArquivo;
	}

	public void setTipoMimeArquivo(String tipoMimeArquivo)
	{
		this.tipoMimeArquivo = tipoMimeArquivo;
	}

	public String getNomeArquivo()
	{
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo)
	{
		this.nomeArquivo = nomeArquivo;
	}

	public byte[] getArquivo()
	{
		return arquivo;
	}

	public Collection<OpcaoParametro> getOpcoesParametros()
    {
        return opcoesParametros;
    }

    public void setOpcoesParametros(Collection<OpcaoParametro> opcoesParametros)
    {
        this.opcoesParametros = opcoesParametros;
    }
    
    public String getDescricaoObjetivo() {
	return descricaoObjetivo;
    }

    public void setDescricaoObjetivo(String descricaoObjetivo) {
	this.descricaoObjetivo = descricaoObjetivo;
    }

	public void setArquivo(byte[] arquivo)
	{
		this.arquivo = arquivo;
	}
}