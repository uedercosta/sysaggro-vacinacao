package br.com.sysagro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sysagro.models.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

}
