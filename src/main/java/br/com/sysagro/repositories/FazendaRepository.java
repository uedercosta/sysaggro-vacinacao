package br.com.sysagro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.sysagro.models.Fazenda;

public interface FazendaRepository extends JpaRepository<Fazenda, Long> {

	@Query("select f from Fazenda f join fetch f.proprietario where f.nome like :descricao order by f.id")
	List<Fazenda> findNomeContaining(String descricao);
	
	@Query("select f from Fazenda f join fetch f.proprietario order by f.id")
	List<Fazenda> findAll();

}
