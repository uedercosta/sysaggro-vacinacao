package br.com.sysagro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.sysagro.models.Vacinacao;

public interface VacinacaoRepository extends JpaRepository<Vacinacao, Long>{

	@Query("select v from Vacinacao v where v.fazenda.id = :fazenda and v.ano = :ano order by v.vacina.id")
	List<Vacinacao> findAll(String fazenda, Integer ano);

	@Query("select v from Vacinacao v where v.ano = :ano order by v.vacina.id")
	List<Vacinacao> findAll(Integer ano);
	
	@Query("select v from Vacinacao v")
	List<Vacinacao> findAll();

	@Query("select v from Vacinacao v join fetch v.vacina vv where v.fazenda.id = :fazenda order by v.vacina.id")
	List<Vacinacao> findByFazendaIdOrderById(@Param("fazenda") Long id);
}
