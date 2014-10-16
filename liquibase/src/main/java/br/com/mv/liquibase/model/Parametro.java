package br.com.mv.liquibase.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mv_parametro")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor public class Parametro implements Serializable
{
	private static final long serialVersionUID = 1962151124769372760L;

	@Id
	@SequenceGenerator(name = "MV_SEQ_PARAMETROS", sequenceName = "MV_SEQ_PARAMETROS", allocationSize = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MV_SEQ_PARAMETROS")
	@Column(name = "ID")
	private Long id;

	@NotNull
	@Size(max=50)
	@Column(name = "CHAVE", nullable = false, length = 50)
	private String chave;

	@Size(max=200)
	@NotNull
	@Column(name = "DESCRICAO", nullable = false, length = 200)
	private String descricao;

	@NotNull
	@Size(max=300)
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
	
	@OneToMany(mappedBy="parametro", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<OpcaoParametro> opcoesParametros;
	
}