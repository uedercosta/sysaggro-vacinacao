package br.com.sysagro.models.enums;

public enum StatusTransferencia {
	
	CANCELADA("Cancelada"), EM_ANDAMENTO("Em Andamento"), CONCLUIDA("Concluída");
	
	private String descricao;
	
	private StatusTransferencia(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
