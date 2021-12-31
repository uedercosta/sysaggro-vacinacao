package br.com.sysagro.dto;

import br.com.sysagro.models.Animal;

public class AnimalDTO {

	private Long id;
	private String nome;
	private String brinco;

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
		this.nome = nome;
	}
	
	public String getBrinco() {
		return brinco;
	}

	public void setBrinco(String brinco) {
		this.brinco = brinco;
	}

	public AnimalDTO toDTO(Animal animal) {
		AnimalDTO dto = new AnimalDTO();
		dto.setId(animal.getId());
		dto.setNome(animal.getNome());
		dto.setBrinco(animal.getBrinco());
		return dto;
	}

}
