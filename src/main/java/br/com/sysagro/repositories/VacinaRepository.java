package br.com.sysagro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.sysagro.models.Vacina;

public interface VacinaRepository extends JpaRepository<Vacina, Long> {

	@Query("SELECT v FROM Vacina v join fetch v.tipoVacina t where v.descricao like :descricao order by v.id")
	List<Vacina> findByDescricaoContaining(String descricao);
	
	@Query("SELECT v FROM Vacina v join fetch v.tipoVacina t order by v.id")
	List<Vacina> findAll();

}
