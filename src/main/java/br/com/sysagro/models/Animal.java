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
import br.com.sysagro.models.enums.StatusAnimal;
import br.com.sysagro.util.Constantes;

@Entity
@Table(name = "ANIMAIS")
public class Animal implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private StatusAnimal status = StatusAnimal.ATIVO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome.toUpperCase();
	}

	public Raca getRaca() {
		return raca;
	}

	public void setRaca(Raca raca) {
		this.raca = raca;
	}

	public Proprietario getProprietario() {
		return proprietario;
	}

	public void setProprietario(Proprietario proprietario) {
		this.proprietario = proprietario;
	}

	public Fazenda getFazenda() {
		return fazenda;
	}

	public void setFazenda(Fazenda fazenda) {
		this.fazenda = fazenda;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getBrinco() {
		return brinco;
	}

	public void setBrinco(String brinco) {
		this.brinco = brinco.toUpperCase();
	}

	public Animal getPai() {
		return pai;
	}

	public void setPai(Animal pai) {
		this.pai = pai;
	}

	public Animal getMae() {
		return mae;
	}

	public void setMae(Animal mae) {
		this.mae = mae;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes.toUpperCase();
	}
	
	public StatusAnimal getStatus() {
		return status;
	}
	
	public void setStatus(StatusAnimal status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Animal [id=" + id + ", nome=" + nome + ", raca=" + raca + ", proprietario=" + proprietario
				+ ", fazenda=" + fazenda + ", sexo=" + sexo + ", dataNascimento=" + dataNascimento + ", brinco="
				+ brinco + ", pai=" + pai + ", mae=" + mae + ", observacoes=" + observacoes + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Animal other = (Animal) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
