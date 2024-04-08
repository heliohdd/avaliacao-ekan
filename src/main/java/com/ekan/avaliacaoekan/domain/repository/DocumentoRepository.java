package com.ekan.avaliacaoekan.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ekan.avaliacaoekan.domain.model.Documento;

@Repository
	public interface DocumentoRepository extends JpaRepository<Documento, Long> {

//	List<Documento> consultarPorDescricao(String descricao);
	
}