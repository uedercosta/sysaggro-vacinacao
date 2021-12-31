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
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.sysagro.models.enums.StatusTransferencia;
import br.com.sysagro.repositories.TransferenciaRepository;

@Entity
@Table(name = "TRANSFERENCIAS")
public class Transferencia implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 20, nullable = false)
	private String numero;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate data = LocalDate.now();
	
	@NotNull
	@ManyToOne
	private Fazenda origem;
	
	@NotNull
	@ManyToOne
	private Fazenda destino;

	@Column(length = 2000)
	private String motivo;
	
	@Enumerated(EnumType.STRING)
	private StatusTransferencia status = StatusTransferencia.EM_ANDAMENTO;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataCancelamento;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataConclusao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public LocalDate getData() {
		return data;
	}

	public Fazenda getOrigem() {
		return origem;
	}

	public void setOrigem(Fazenda origem) {
		this.origem = origem;
	}

	public Fazenda getDestino() {
		return destino;
	}

	public void setDestino(Fazenda destino) {
		this.destino = destino;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public StatusTransferencia getStatus() {
		return status;
	}

	public void setStatus(StatusTransferencia status) {
		this.status = status;
	}

	public LocalDate getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(LocalDate dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public LocalDate getDataConclusao() {
		return dataConclusao;
	}

	public void setDataConclusao(LocalDate dataConclusao) {
		this.dataConclusao = dataConclusao;
	}
	
	public void gerarNumero(TransferenciaRepository repository) {
		int ano = this.data.getYear();
		int contador = repository.contadorAno(ano);
		String contadorFormatado = String.format("%06d", contador + 1);
		String numeroGerado = "TR-"+ ano + contadorFormatado;
		this.numero = numeroGerado;
	}

}
