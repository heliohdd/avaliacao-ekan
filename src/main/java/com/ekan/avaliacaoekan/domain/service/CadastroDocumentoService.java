package com.ekan.avaliacaoekan.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ekan.avaliacaoekan.domain.exception.EntidadeEmUsoException;
import com.ekan.avaliacaoekan.domain.exception.EntidadeNaoEncontradaException;
import com.ekan.avaliacaoekan.domain.model.Beneficiario;
import com.ekan.avaliacaoekan.domain.model.Documento;
import com.ekan.avaliacaoekan.domain.repository.BeneficiarioRepository;
import com.ekan.avaliacaoekan.domain.repository.DocumentoRepository;

@Service
public class CadastroDocumentoService {

	@Autowired
	private DocumentoRepository documentoRepository;
	
	@Autowired
	private BeneficiarioRepository beneficiarioRepository;
	
	public Documento salvar(Documento documento) {
		Long beneficiarioId = documento.getBeneficiario().getId();
		
		Beneficiario beneficiario = beneficiarioRepository.findById(beneficiarioId)
			.orElseThrow(() -> new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de beneficiário com código %d", beneficiarioId)));
		
		documento.setBeneficiario(beneficiario);
		
		return documentoRepository.save(documento);
	}

	
		public void excluir(Long documentoId) {
		try {
			documentoRepository.deleteById(documentoId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
				String.format("Não existe um cadastro de documento com código %d", documentoId));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format("O documento de código %d não pode ser removido, pois está em uso", documentoId));
		}
	}
	
}
