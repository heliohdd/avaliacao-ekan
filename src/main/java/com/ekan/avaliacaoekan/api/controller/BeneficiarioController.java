package com.ekan.avaliacaoekan.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ekan.avaliacaoekan.domain.model.Beneficiario;
import com.ekan.avaliacaoekan.domain.repository.BeneficiarioRepository;

@RestController
@RequestMapping(value = "/beneficiarios")
public class BeneficiarioController {

	@Autowired
	private BeneficiarioRepository beneficiarioRepository;

	@GetMapping
	public List<Beneficiario> findAll() {
		return beneficiarioRepository.findAll();
	}

	@GetMapping(value = "/{beneficiarioId}")
	public ResponseEntity<Beneficiario> findById(@PathVariable Long beneficiarioId) {
		Optional<Beneficiario> beneficiario = beneficiarioRepository.findById(beneficiarioId);

		if (beneficiario.isPresent()) {
			return ResponseEntity.ok(beneficiario.get());
		}
		return ResponseEntity.notFound().build();
	}

}