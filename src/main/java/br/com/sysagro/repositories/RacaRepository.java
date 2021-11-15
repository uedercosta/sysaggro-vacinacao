package br.com.sysagro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sysagro.models.Raca;

public interface RacaRepository extends JpaRepository<Raca, Long> {

	List<Raca> findByDescricaoContaining(String descricao);

}
