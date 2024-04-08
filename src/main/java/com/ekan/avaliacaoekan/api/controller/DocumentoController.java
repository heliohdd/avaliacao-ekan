package com.ekan.avaliacaoekan.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ekan.avaliacaoekan.domain.exception.EntidadeEmUsoException;
import com.ekan.avaliacaoekan.domain.exception.EntidadeNaoEncontradaException;
import com.ekan.avaliacaoekan.domain.model.Documento;
import com.ekan.avaliacaoekan.domain.repository.DocumentoRepository;
import com.ekan.avaliacaoekan.domain.service.CadastroDocumentoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/documentos")
public class DocumentoController {

		@Autowired
		private DocumentoRepository documentoRepository;
		
		@Autowired
		private CadastroDocumentoService cadastroDocumento;
		
		@GetMapping
		public List<Documento> listar() {
			return documentoRepository.findAll();
		}
		
		@GetMapping("/{documentoId}")
		public ResponseEntity<Documento> buscar(@PathVariable Long documentoId) {
			Optional<Documento> documento = documentoRepository.findById(documentoId);
			
			if (documento.isPresent()) {
				return ResponseEntity.ok(documento.get());
			}
			
			return ResponseEntity.notFound().build();
		}
		
		@PostMapping
		public ResponseEntity<?> adicionar(@RequestBody Documento documento) {
			try {
				documento = cadastroDocumento.salvar(documento);
				
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(documento);
			} catch (EntidadeNaoEncontradaException e) {
				return ResponseEntity.badRequest()
						.body(e.getMessage());
			}
		}
		
		@PutMapping("/{documentoId}")
		public ResponseEntity<?> atualizar(@PathVariable Long documentoId,
				@RequestBody Documento documento) {
			try {
				Documento documentoAtual = documentoRepository
						.findById(documentoId).orElse(null);
				
				if (documentoAtual != null) {
					BeanUtils.copyProperties(documento, documentoAtual, "id");
					
					documentoAtual = cadastroDocumento.salvar(documentoAtual);
					return ResponseEntity.ok(documentoAtual);
				}
				
				return ResponseEntity.notFound().build();
			
			} catch (EntidadeNaoEncontradaException e) {
				return ResponseEntity.badRequest()
						.body(e.getMessage());
			}
		}
		
		@PatchMapping("/{documentoId}")
		public ResponseEntity<?> atualizarParcial(@PathVariable Long documentoId,
				@RequestBody Map<String, Object> campos) {
			Documento documentoAtual = documentoRepository
					.findById(documentoId).orElse(null);
			
			if (documentoAtual == null) {
				return ResponseEntity.notFound().build();
			}
			
			merge(campos, documentoAtual);
			
			return atualizar(documentoId, documentoAtual);
		}

		private void merge(Map<String, Object> dadosOrigem, Documento documentoDestino) {
			ObjectMapper objectMapper = new ObjectMapper();
			Documento documentoOrigem = objectMapper.convertValue(dadosOrigem, Documento.class);
			
			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				Field field = ReflectionUtils.findField(Documento.class, nomePropriedade);
				field.setAccessible(true);
				
				Object novoValor = ReflectionUtils.getField(field, documentoOrigem);
				
				ReflectionUtils.setField(field, documentoDestino, novoValor);
			});
		}
		
		@DeleteMapping("/{documentoId}")
		public ResponseEntity<?> remover(@PathVariable Long documentoId) {
			try {
				cadastroDocumento.excluir(documentoId);	
				return ResponseEntity.noContent().build();
				
			} catch (EntidadeNaoEncontradaException e) {
				return ResponseEntity.notFound().build();
				
			} catch (EntidadeEmUsoException e) {
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(e.getMessage());
			}
		}
		
	}
	