package br.com.sysagro.models.enums;

public enum StatusVacinacao {
	
	EM_ANDAMENTO("Em Andamento"), FINALIZADO("Finalizada"), CANCELADO("Cancelada");
	
	private String descricao;
	
	private StatusVacinacao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
