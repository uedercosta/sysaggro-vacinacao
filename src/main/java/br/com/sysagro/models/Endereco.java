package br.com.sysagro.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

import br.com.sysagro.models.enums.Estado;
import br.com.sysagro.util.Constantes;

@Embeddable
public class Endereco {
	
	@Column(length = 40, nullable = false)
	@NotBlank(message = Constantes.CAMPO_LOGRADOURO_OBRIGATORIO)
	private String logradouro;
	
	@Column(length = 8)
	private String numero;
	
	@Column(length = 40)
	private String complemento;
	
	@Column(length = 40, nullable = false)
	@NotBlank(message = Constantes.CAMPO_BAIRRO_OBRIGATORIO)
	private String bairro;

	@Column(length = 80, nullable = false)
	@NotBlank(message = Constantes.CAMPO_CIDADE_OBRIGATORIO)
	private String cidade;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 2, nullable = false)
	@NotBlank
	private Estado estado;

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro.toUpperCase();
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero.toUpperCase();
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	public String getComplemento() {
		return complemento;
	}
	
	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro.toUpperCase();
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade.toUpperCase();
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Endereco [logradouro=" + logradouro + ", numero=" + numero + ", complemento=" + complemento
				+ ", bairro=" + bairro + ", cidade=" + cidade + ", estado=" + estado + "]";
	}
	

}
