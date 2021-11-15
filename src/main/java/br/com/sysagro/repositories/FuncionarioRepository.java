package br.com.sysagro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.sysagro.models.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	@Query("SELECT f FROM Funcionario f join fetch f.fazenda where f.nome like :nome order by f.id")
	List<Funcionario> findByNomeContainingOrderById(String nome);
	
	@Query("SELECT f FROM Funcionario f join fetch f.fazenda")
	List<Funcionario> findAll();

}
