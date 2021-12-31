package br.com.sysagro.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import br.com.sysagro.util.Constantes;

@Entity
@Table(name = "USUARIOS")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 90, nullable = false)
	@NotBlank(message = Constantes.CAMPO_NOME_OBRIGATORIO)
	private String nome;

	@Column(length = 90, nullable = false, unique = true)
	@NotBlank(message = Constantes.CAMPO_LOGIN_OBRIGATORIO)
	private String login;

	@Column(nullable = false)
	@NotBlank(message = Constantes.CAMPO_SENHA_OBRIGATORIO)
	private String senha;

	private boolean ativo;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Perfil> perfis;

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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login.toUpperCase();
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}
	
	public List<Perfil> getPerfis() {
		return perfis;
	}

}
