package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.utfpr.dao.AntecedenteFamiliarAtendimentoDao;
import br.edu.utfpr.model.AntecedenteFamiliarAtendimento;
import br.edu.utfpr.model.Doenca;

@Named
@Stateless
public class AntecedenteFamiliarAtendimentoService {

	@Inject
	private AntecedenteFamiliarAtendimentoDao antecedenteFamiliarAtendimentoDao;
	
	public List<AntecedenteFamiliarAtendimento> retornarAntecedentesFamiliaresAtendimento(Long idAtendimento) {
		return antecedenteFamiliarAtendimentoDao.retornarAntecedentesFamiliaresAtendimento(idAtendimento);
	}

	public List<AntecedenteFamiliarAtendimento> retornarAntecedentesFamiliaresAtendimentosAnteriores(Long idPaciente, Long idAtendimentoIgnorar) {
		return antecedenteFamiliarAtendimentoDao.retornarAntecedentesFamiliaresAtendimentosAnteriores(idPaciente, idAtendimentoIgnorar);
	}
	
	public List<Doenca> retornarAntecedentesFamiliaresMaisUsados(Integer quantidade, List<Long> idsDoencasIgnorar, Boolean chkAtivo) {
		return antecedenteFamiliarAtendimentoDao.retornarAntecedentesFamiliaresMaisUsados(quantidade, idsDoencasIgnorar, chkAtivo);
	}
	
	public void salvarAntecedentesFamiliaresAtendimento(List<AntecedenteFamiliarAtendimento> antecedentesFamiliaresAtendimento) {
		for (AntecedenteFamiliarAtendimento antecedenteFamiliarAtendimento : antecedentesFamiliaresAtendimento) {
			antecedenteFamiliarAtendimentoDao.save(antecedenteFamiliarAtendimento);
		}
	}
	
	public void removerAntecedentesFamiliaresExcluidos(Long idAtendimento, List<AntecedenteFamiliarAtendimento> antecedentesFamiliares) {
		List<AntecedenteFamiliarAtendimento> antecedentesBase = this.retornarAntecedentesFamiliaresAtendimento(idAtendimento);
		for (AntecedenteFamiliarAtendimento antecedenteBase : antecedentesBase) {
			boolean excluido = true;
			for (AntecedenteFamiliarAtendimento antecedenteAtendimento : antecedentesFamiliares) {
				if (antecedenteBase.equals(antecedenteAtendimento)) {
					excluido = false;
					break;
				}
			}
			
			if (excluido) {
				antecedenteFamiliarAtendimentoDao.remove(antecedenteBase);
			}
		}
	}
}
