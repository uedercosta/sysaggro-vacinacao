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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.sysagro.models.enums.StatusVacinacao;

@Entity
@Table(name = "VACINACOES")
public class Vacinacao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Min(value = 2020, message = "O ano de vacinação deve ser maior do que 2020")
	private Integer ano = LocalDate.now().getYear();
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "A DATA DA VACINAÇÃO deve ser informada")
	private LocalDate dataVacinacao = LocalDate.now();
	
	@Column(nullable = false, length = 100)
	private String descricao;
	
	@NotNull(message = "A VACINA deve ser informada")
	@ManyToOne(optional = false)
	private Vacina vacina;

	@NotNull(message = "A FAZENDA deve ser informada")
	@ManyToOne(optional = false)
	private Fazenda fazenda;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private StatusVacinacao status = StatusVacinacao.EM_ANDAMENTO;
	
	@Column(length = 800)	
	private String observacao;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataCancelamento;
	
	@Column(length = 800)	
	private String motivoCancelamento;
	
//	@OneToMany(mappedBy = "vacinacao")
//	private Set<VacinacaoItem> items = new HashSet<VacinacaoItem>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDataVacinacao() {
		return dataVacinacao;
	}

	public void setDataVacinacao(LocalDate dataVacinacao) {
		this.dataVacinacao = dataVacinacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao.toUpperCase();
	}

	public Vacina getVacina() {
		return vacina;
	}

	public void setVacina(Vacina vacina) {
		this.vacina = vacina;
	}

	public Fazenda getFazenda() {
		return fazenda;
	}

	public void setFazenda(Fazenda fazenda) {
		this.fazenda = fazenda;
	}

	public StatusVacinacao getStatus() {
		return status;
	}

	public void setStatus(StatusVacinacao status) {
		this.status = status;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao.toUpperCase();
	}

	public LocalDate getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(LocalDate dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento.toUpperCase();
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}	
	
	public boolean permiteAlteracao() {
		if (this.status != StatusVacinacao.EM_ANDAMENTO) {
			return false;	
		} else {
			return true;
		}
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
		Vacinacao other = (Vacinacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vacinacao [id=" + id + ", ano=" + ano + ", dataVacinacao=" + dataVacinacao + ", descricao=" + descricao
				+ ", vacina=" + vacina + ", fazenda=" + fazenda + ", status=" + status + ", observacao=" + observacao
				+ ", dataCancelamento=" + dataCancelamento + ", motivoCancelamento=" + motivoCancelamento + "]";
	}

	
}
