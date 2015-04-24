package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.utfpr.constants.TipoProcedimento;
import br.edu.utfpr.dao.AntecedenteCirurgicoAtendimentoDao;
import br.edu.utfpr.model.AntecedenteCirurgicoAtendimento;
import br.edu.utfpr.model.Procedimento;

@Named
@Stateless
public class AntecedenteCirurgicoAtendimentoService {

	@Inject
	private AntecedenteCirurgicoAtendimentoDao antecedenteCirurgicoAtendimentoDao;
	
	public List<AntecedenteCirurgicoAtendimento> retornarAntecedentesCirurgicosAtendimento(Long idAtendimento) {
		return antecedenteCirurgicoAtendimentoDao.retornarAntecedentesCirurgicosAtendimento(idAtendimento);
	}

	public List<AntecedenteCirurgicoAtendimento> retornarAntecedentesCirurgicosAtendimentosAnteriores(Long idPaciente, Long idAtendimentoIgnorar) {
		return antecedenteCirurgicoAtendimentoDao.retornarAntecedentesCirurgicosAtendimentosAnteriores(idPaciente, idAtendimentoIgnorar);
	}
	
	public List<Procedimento> retornarAntecedentesCirurgicosMaisUsados(Integer quantidade, List<Long> idsProcedimentosIgnorar, Boolean chkAtivo, TipoProcedimento tipoProcedimento) {
		return antecedenteCirurgicoAtendimentoDao.retornarAntecedentesCirurgicosMaisUsados(quantidade, idsProcedimentosIgnorar, chkAtivo, tipoProcedimento);
	}
	
	public void removerAntecedenteCirurgicoAtendimento(AntecedenteCirurgicoAtendimento AntecedenteCirurgicoAtendimento) {
		antecedenteCirurgicoAtendimentoDao.remove(AntecedenteCirurgicoAtendimento);
	}
	
	public void salvarAntecedentesCirurgicosAtendimento(List<AntecedenteCirurgicoAtendimento> antecedentesCirurgicosAtendimento) {
		for (AntecedenteCirurgicoAtendimento antecedenteCirurgicoAtendimento : antecedentesCirurgicosAtendimento) {
			antecedenteCirurgicoAtendimentoDao.save(antecedenteCirurgicoAtendimento);
		}
	}
}
