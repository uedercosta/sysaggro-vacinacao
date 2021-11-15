package br.com.sysagro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sysagro.models.TipoVacina;

public interface TipoVacinaRepository extends JpaRepository<TipoVacina, Long> {
	

	List<TipoVacina> findByDescricaoContaining(String descricao);

}
