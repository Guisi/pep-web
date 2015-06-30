package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.utfpr.dao.DoencaDiagnosticadaAtendimentoDao;
import br.edu.utfpr.model.Doenca;
import br.edu.utfpr.model.DoencaDiagnosticadaAtendimento;

@Named
@Stateless
public class DoencaDiagnosticadaAtendimentoService {

	@Inject
	private DoencaDiagnosticadaAtendimentoDao doencaDiagnosticadaAtendimentoDao;
	
	public List<DoencaDiagnosticadaAtendimento> retornarDoencasDiagnosticadasAtendimento(Long idAtendimento) {
		return doencaDiagnosticadaAtendimentoDao.retornarDoencasDiagnosticadasAtendimento(idAtendimento);
	}

	public List<DoencaDiagnosticadaAtendimento> retornarDoencasDiagnosticadasAtendimentosAnteriores(Long idPaciente, Long idAtendimentoIgnorar) {
		return doencaDiagnosticadaAtendimentoDao.retornarDoencasDiagnosticadasAtendimentosAnteriores(idPaciente, idAtendimentoIgnorar);
	}
	
	public List<Doenca> retornarDoencasDiagnosticadasMaisUsadas(Integer quantidade, List<Long> idsDoencasIgnorar, Boolean chkAtivo) {
		return doencaDiagnosticadaAtendimentoDao.retornarDoencasDiagnosticadasMaisUsadas(quantidade, idsDoencasIgnorar, chkAtivo);
	}
	
	public void salvarDoencasDiagnosticadasAtendimento(List<DoencaDiagnosticadaAtendimento> doencasDiagnosticadasAtendimento) {
		for (DoencaDiagnosticadaAtendimento doencaDiagnosticadaAtendimento : doencasDiagnosticadasAtendimento) {
			doencaDiagnosticadaAtendimentoDao.save(doencaDiagnosticadaAtendimento);
		}
	}
	
	public void removerDoencasDiagnosticadasExcluidas(Long idAtendimento, List<DoencaDiagnosticadaAtendimento> doencasDiagnosticadas) {
		List<DoencaDiagnosticadaAtendimento> doencasDiagnosticadasBase = this.retornarDoencasDiagnosticadasAtendimento(idAtendimento);
		for (DoencaDiagnosticadaAtendimento doencaDiagnosticadaBase : doencasDiagnosticadasBase) {
			boolean excluido = true;
			for (DoencaDiagnosticadaAtendimento doencaDiagnosticadaAtendimento : doencasDiagnosticadas) {
				if (doencaDiagnosticadaBase.equals(doencaDiagnosticadaAtendimento)) {
					excluido = false;
					break;
				}
			}
			
			if (excluido) {
				doencaDiagnosticadaAtendimentoDao.remove(doencaDiagnosticadaBase);
			}
		}
	}
}
