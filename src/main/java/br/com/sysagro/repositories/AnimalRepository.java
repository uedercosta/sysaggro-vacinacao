package br.com.sysagro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.sysagro.models.Animal;
import br.com.sysagro.models.enums.Sexo;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

	@Query("select a from Animal a join fetch a.raca r join fetch a.fazenda f join fetch f.proprietario p order by a.id")
	List<Animal> findAll();
	
	@Query("select a from Animal a join fetch a.raca r join fetch a.fazenda f join fetch f.proprietario p where a.nome like :nome order by a.id")
	List<Animal> findByNome(String nome);

	@Query("select a from Animal a join fetch a.raca r join fetch a.fazenda f join fetch f.proprietario p where a.sexo = :sexo order by a.id")
	List<Animal> findAllBySexo(Sexo sexo);

	@Query("select a from Animal a where a.fazenda.id = :id order by a.nome")
	List<Animal> findByFazenda(@Param("id") Long id);
	
}
