package br.com.sysagro.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

import br.com.sysagro.util.Constantes;

@Entity
@Table(name = "PROPRIETARIOS")
public class Proprietario implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 100, nullable = false)
	@NotBlank(message = Constantes.CAMPO_NOME_OBRIGATORIO)
	private String nome;

	@CPF(message=Constantes.CPF_INVALIDO)
	@Column(length = 14, nullable = false)
	@NotBlank(message = Constantes.CAMPO_CPF_OBRIGATORIO)
	private String cpf;
	
	@Column(length = 20)
	private String rg;
	
	@Column(length = 20)
	private String telefone;
	
	@Column(length = 255, nullable = false)
	@Email(message=Constantes.EMAIL_INVALIDO)
	@NotBlank(message = Constantes.CAMPO_EMAIL_OBRIGATORIO)
	private String email;
	
	@Embedded
	private Endereco endereco;
	
	@Column(length = 800)
	private String observacoes;

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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone.toUpperCase();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getObservacoes() {
		return observacoes;
	}
	
	public void setRg(String rg) {
		this.rg = rg;
	}
	
	public String getRg() {
		return rg;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes.toUpperCase();
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
		Proprietario other = (Proprietario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

//	@Override
//	public String toString() {
//		return "Proprietario [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", telefone=" + telefone + ", email="
//				+ email + ", endereco=" + endereco + ", observacoes=" + observacoes + "]";
//	}
	
	
}
