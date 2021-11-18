package br.com.sysagro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.sysagro.models.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

	@Query("select a from Animal a join fetch a.raca r join fetch a.proprietario p order by a.id")
	List<Animal> findAll();
	
	@Query("select a from Animal a join fetch a.raca r join fetch a.proprietario p where a.nome like :nome order by a.id")
	List<Animal> findByNome(String nome);

}
