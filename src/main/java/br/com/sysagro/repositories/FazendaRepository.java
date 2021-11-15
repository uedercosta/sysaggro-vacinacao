package br.com.sysagro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sysagro.models.Fazenda;

public interface FazendaRepository extends JpaRepository<Fazenda, Long> {

}
