package br.com.sysagro.models.enums;

public enum StatusAnimal {
	
	EMPRESTADO("Emprestado"), FALECIDO("Falecido"), VACINADO("Vacinado"), ATIVO("Ativo");
	
	private String descricao;
	
	private StatusAnimal(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
