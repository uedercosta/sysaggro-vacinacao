package br.com.sysagro.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.sysagro.services.VacinacaoItemService;

@Entity
@Table(name = "VACINACAO_ITEMS")
public class VacinacaoItem implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@NotNull
	private Animal animal;
	
	@ManyToOne
	private Vacinacao vacinacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public Vacinacao getVacinacao() {
		return vacinacao;
	}

	public void setVacinacao(Vacinacao vacinacao) {
		this.vacinacao = vacinacao;
	}
	
	public boolean animalJaVacinado(VacinacaoItemService service) {
		List<VacinacaoItem> animaisVacinados = service.getAnimaisVacinados(this.vacinacao.getId());
		for (VacinacaoItem vacinacaoItem : animaisVacinados) {
			if (vacinacaoItem.getAnimal().equals(this.getAnimal())) {
				return true;
			}
		}
		return false;
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
		VacinacaoItem other = (VacinacaoItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VacinacaoItem [id=" + id + ", animal=" + animal + ", vacinacao=" + vacinacao + "]";
	}
	
	
}
