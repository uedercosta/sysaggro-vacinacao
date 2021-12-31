package br.com.sysagro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.sysagro.models.Transferencia;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long>{

	@Query("select count(t.id) as total from Transferencia t where year(t.data) = :ano")
	int contadorAno(@Param("ano") int ano);

}
