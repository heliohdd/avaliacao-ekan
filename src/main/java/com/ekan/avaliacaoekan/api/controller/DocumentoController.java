package com.ekan.avaliacaoekan.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ekan.avaliacaoekan.domain.model.Documento;
import com.ekan.avaliacaoekan.domain.repository.DocumentoRepository;

@RestController
@RequestMapping(value = "/documentos")
public class DocumentoController {

	@Autowired
	private DocumentoRepository documentoRepository;

	@GetMapping
	public List<Documento> findAll() {
		return documentoRepository.findAll();
	}

	@GetMapping(value = "/{documentoId}")
	public ResponseEntity<Documento> findById(@PathVariable Long documentoId) {
		Optional<Documento> documento = documentoRepository.findById(documentoId);

		if (documento.isPresent()) {
			return ResponseEntity.ok(documento.get());
		}
		return ResponseEntity.notFound().build();
	}

}