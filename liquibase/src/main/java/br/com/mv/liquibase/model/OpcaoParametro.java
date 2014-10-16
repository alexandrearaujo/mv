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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "OPCAO_PARAMETRO")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor public class OpcaoParametro implements Serializable
{
	private static final long serialVersionUID = 8493640701260998032L;

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

}