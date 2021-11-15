package br.com.sysagro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sysagro.models.Proprietario;

public interface ProprietarioRepository extends JpaRepository<Proprietario, Long> {

	List<Proprietario> findByNomeContainingOrderById(String nome);

}
