package br.com.sysagro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.sysagro.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Usuario findByLogin(String login);

	@Query("select u from Usuario u where nome like %:nome%")
	List<Usuario> findByNome(@Param("nome") String nome);
}
