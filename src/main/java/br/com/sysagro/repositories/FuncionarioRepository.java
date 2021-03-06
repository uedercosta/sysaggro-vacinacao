package br.com.sysagro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.sysagro.models.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	@Query("SELECT f FROM Funcionario f join fetch f.fazenda where f.nome like :nome order by f.id")
	List<Funcionario> findByNomeContainingOrderById(String nome);
	
	@Query("SELECT f FROM Funcionario f join fetch f.fazenda order by f.id")
	List<Funcionario> findAll();

	@Query("SELECT f FROM Funcionario f join fetch f.fazenda fa where f.nome like :nome order by fa.id")
	List<Funcionario> findByNomeContainingOrderByFazendaId(String nome);
	
	@Query("SELECT f FROM Funcionario f join fetch f.fazenda fa order by fa.id")
	List<Funcionario> findAllOrderByFazendaId();
}
