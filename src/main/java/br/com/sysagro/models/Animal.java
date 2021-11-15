package br.com.sysagro.models;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.sysagro.models.enums.Sexo;
import br.com.sysagro.util.Constantes;

@Entity
@Table(name = "ANIMAIS")
public class Animal implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 80, nullable = false)
	@NotBlank(message = Constantes.CAMPO_NOME_OBRIGATORIO)
	private String nome;
	
	@ManyToOne(optional = false)
	@NotBlank(message = Constantes.CAMPO_RACA_OBRIGATORIO)
	private Raca raca;
	
	@ManyToOne(optional = false)
	@NotBlank(message = Constantes.CAMPO_PROPRIETARIO_OBRIGATORIO)
	private Proprietario proprietario;
	
	@ManyToOne(optional = false)
	@NotBlank(message = Constantes.CAMPO_FAZENDA_OBRIGATORIO)
	private Fazenda fazenda;
	
	@Enumerated(EnumType.STRING)
	@NotBlank(message = Constantes.CAMPO_SEXO_OBRIGATORIO)
	private Sexo sexo;
	
	@DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-mm-dd")
	@Column(nullable = false)
	@NotBlank(message = Constantes.CAMPO_NASCIMENTO_OBRIGATORIO)
	private LocalDate dataNascimento;
	
	@Column(length = 20, nullable = false)
	@NotBlank(message = Constantes.CAMPO_BRINCO_OBRIGATORIO)
	private String brinco;
	
	@ManyToOne
	private Animal pai;
	
	@ManyToOne
	private Animal mae;

	@Column(length = 800)
	private String observacoes;
}
