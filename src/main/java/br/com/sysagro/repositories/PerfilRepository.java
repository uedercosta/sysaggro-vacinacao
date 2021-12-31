package br.com.sysagro.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.sysagro.models.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long>{
	
	@Query("select p from Perfil p where p.descricao = :descricao")
	Optional<Perfil> findByDescricao(String descricao);

}
