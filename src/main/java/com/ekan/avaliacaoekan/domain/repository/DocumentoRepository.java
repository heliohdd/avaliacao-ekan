package com.ekan.avaliacaoekan.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ekan.avaliacaoekan.domain.model.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {

}