package br.edu.utfpr.mbean.usuario;

import static javax.faces.context.FacesContext.getCurrentInstance;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;

import br.edu.utfpr.constants.Constantes;
import br.edu.utfpr.constants.EstadoEnum;
import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Convenio;
import br.edu.utfpr.model.Especialidade;
import br.edu.utfpr.model.Perfil;
import br.edu.utfpr.model.Usuario;
import br.edu.utfpr.service.ConvenioService;
import br.edu.utfpr.service.EspecialidadeService;
import br.edu.utfpr.service.PerfilService;
import br.edu.utfpr.service.UsuarioService;
import br.edu.utfpr.utils.CepUtil;
import br.edu.utfpr.utils.EnderecoCorreios;
import br.edu.utfpr.utils.FormatUtils;

import com.ocpsoft.pretty.PrettyContext;

@ManagedBean
@ViewScoped
public class EditarUsuarioMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioService usuarioService;
	@Inject
	private PerfilService perfilService;
	@Inject
	private EspecialidadeService especialidadeService;
	@Inject
	private ConvenioService convenioService;
	
	private Usuario usuarioSelecionado;
	
	private List<Perfil> perfisDisponiveis;
	private List<Perfil> perfisUsuario;
	private Perfil perfilSelecionado;
	
	private List<Especialidade> especialidadesUsuario;
	private List<Especialidade> especialidadesDisponiveis;
	private Especialidade especialidadeSelecionada;
	
	private List<Convenio> conveniosUsuario;
	private List<Convenio> conveniosDisponiveis;
	private Convenio convenioSelecionado;
	
	private List<EstadoEnum> estados;

	private String menuInclude;
	private boolean editarInfoPessoal;
	private boolean editarPaciente;
	
	@PostConstruct
	public void init() {
		String url = PrettyContext.getCurrentInstance().getRequestURL().toURL();
		String idUsuario;
		
		//se url eh de edicao de informacoes pessoais, vai editar usuario logado
		if (StringUtils.contains(url, Constantes.EDITAR_INFO_PESSOAL_URL)) {
			idUsuario = getUsuarioLogado().getIdUsuario().toString();
			editarInfoPessoal = true;
		} else { 
			editarPaciente = StringUtils.contains(url, Constantes.EDITAR_PACIENTE_URL);

			HttpServletRequest request = (HttpServletRequest) getCurrentInstance().getExternalContext().getRequest();
			idUsuario = request.getParameter("idUsuario");
		}

		if (StringUtils.isNotEmpty(idUsuario)) {
			
			if (new Scanner(idUsuario).hasNextLong()) {
				this.usuarioSelecionado = usuarioService.retornarUsuario(Long.parseLong(idUsuario));
			}
			
			//se nao achou usuario ou quer editar paciente e usuario nao eh paciente, retorna msg de erro
			if (this.usuarioSelecionado == null || (this.editarPaciente && !this.usuarioSelecionado.isPaciente())) {
				String msg = this.editarPaciente ? getMsgs().getString("paciente.erro.naoencontrado") : getMsgs().getString("usuario.erro.naoencontrado");
				addErrorMessage(MessageFormat.format(msg, idUsuario));
				this.usuarioSelecionado = null;
				return;
			}
			
			this.perfisUsuario = new ArrayList<>(this.usuarioSelecionado.getPerfisUsuario());
			this.especialidadesUsuario = new ArrayList<>(this.usuarioSelecionado.getEspecialidades());
			this.conveniosUsuario = new ArrayList<>(this.usuarioSelecionado.getConvenios());
		} else {
			this.usuarioSelecionado = new Usuario();
			this.perfisUsuario = new ArrayList<>();
			this.especialidadesUsuario = new ArrayList<>();
			this.conveniosUsuario = new ArrayList<>();
		}
		
		this.estados = Arrays.asList(EstadoEnum.values());
	}
	
	public void listarPerfisDisponiveis() {
		this.perfisDisponiveis = perfilService.retornarPerfis();
		for (Perfil p : this.usuarioSelecionado.getPerfisUsuario()) {
			this.perfisDisponiveis.remove(p);
		}
		this.ordenaListasPerfis();
	}
	
	public void listarEspecialidadesDisponiveis() {
		this.especialidadesDisponiveis = especialidadeService.retornarEspecialidades();
		for (Especialidade e : this.usuarioSelecionado.getEspecialidades()) {
			this.especialidadesDisponiveis.remove(e);
		}
		this.ordenaListasEspecialidades();
	}
	
	public void listarConveniosDisponiveis() {
		this.conveniosDisponiveis = convenioService.retornarConvenios();
		for (Convenio c : this.usuarioSelecionado.getConvenios()) {
			this.conveniosDisponiveis.remove(c);
		}
		this.ordenaListasConvenios();
	}
	
	public void onCepAlterado() {
		EnderecoCorreios endereco = CepUtil.buscarEnderecoPorCep(this.usuarioSelecionado.getCep());
		
		if (endereco.isRetornoOk()) {
			this.usuarioSelecionado.setLogradouro(endereco.getLogradouro());
			this.usuarioSelecionado.setBairro(endereco.getBairro());
			this.usuarioSelecionado.setCidade(endereco.getCidade());
			this.usuarioSelecionado.setUf(endereco.getUf());
		}
	}
	
	public String salvar() {
		usuarioSelecionado.setTelefone( FormatUtils.somenteDigitos(usuarioSelecionado.getTelefone()) );
		usuarioSelecionado.setCelular( FormatUtils.somenteDigitos(usuarioSelecionado.getCelular()) );
		usuarioSelecionado.setCpf( FormatUtils.somenteDigitos(usuarioSelecionado.getCpf()) );
		
		if (validarCampos()) {
			try {
				boolean isNew = usuarioSelecionado.isNew();
				usuarioSelecionado.setPerfisUsuario(new LinkedHashSet<>(this.perfisUsuario));
				usuarioSelecionado.setEspecialidades(getPossuiEspecialidades() 
						? new LinkedHashSet<>(this.especialidadesUsuario) : new LinkedHashSet<Especialidade>());
				usuarioSelecionado.setConvenios(getPossuiConvenios() 
						? new LinkedHashSet<>(this.conveniosUsuario) : new LinkedHashSet<Convenio>());

				usuarioService.salvarUsuario(getUsuarioLogado(), usuarioSelecionado);
				
				getUsuarioLogado().setNomeExibicao(usuarioSelecionado.getNomeFantasia());
	
				if (isNew) {
					addInfoMessage(getMsgs().getString("usuario.criar.sucesso"), true);
				} else {
					addInfoMessage(getMsgs().getString("usuario.editar.sucesso"), true);
				}
				
				return getCaminhoRetorno();
			} catch (AppException e) {
				addressException(e);
			}
		}
		return null;
	}
	
	public String cancelar() {
		return getCaminhoRetorno();
	}
	
	private String getCaminhoRetorno() {
		String retorno;
		if (editarInfoPessoal) { 
			retorno = "/secure/home.xhtml";
		} else if (editarPaciente) {
			retorno = "/secure/paciente/pacientes.xhtml";
		} else {
			retorno = "/secure/usuarios/usuarios.xhtml";
		}
		return retorno + "?faces-redirect=true";
	}
	
	public boolean getPossuiEspecialidades() {
		if (perfisUsuario != null) {
			for (Perfil perfil : perfisUsuario) {
				if (perfil.getPossuiEspecialidades()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean getPossuiConvenios() {
		if (perfisUsuario != null) {
			for (Perfil perfil : perfisUsuario) {
				if (perfil.getPossuiConvenios()) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean validarCampos() {
		boolean valido = true;
		
		String txTelefone = usuarioSelecionado.getTelefone();
		if (StringUtils.isNotBlank(txTelefone) && !GenericValidator.isInRange(txTelefone.length(), 10, 11)) {
			addErrorMessage(getMsgs().getString("usuario.salvar.erro.telefoneinvalido"));
			valido = false;
		}
		
		String txCelular = usuarioSelecionado.getCelular();
		if (StringUtils.isNotBlank(txCelular) && !GenericValidator.isInRange(txCelular.length(), 10, 11)) {
			addErrorMessage(getMsgs().getString("usuario.salvar.erro.celularinvalido"));
			valido = false;
		}
		
		return valido;
	}
	
	public void removerPerfil(Perfil perfil) {
		this.perfisUsuario.remove(perfil);
		this.perfisDisponiveis.add(perfil);
		this.ordenaListasPerfis();
	}
	
	public void adicionarPerfil() {
		if (this.perfilSelecionado != null) {
			this.perfisDisponiveis.remove(this.perfilSelecionado);
			this.perfisUsuario.add(this.perfilSelecionado);
			this.ordenaListasPerfis();
			this.perfilSelecionado = null;
		}
	}
	
	public void adicionarEspecialidade() {
		if (this.especialidadeSelecionada != null) {
			this.especialidadesDisponiveis.remove(this.especialidadeSelecionada);
			this.especialidadesUsuario.add(this.especialidadeSelecionada);
			this.ordenaListasEspecialidades();
			this.especialidadeSelecionada = null;
		}
	}
	
	public void removerEspecialidade(Especialidade especialidade) {
		this.especialidadesUsuario.remove(especialidade);
		this.especialidadesDisponiveis.add(especialidade);
		this.ordenaListasEspecialidades();
	}
	
	public void adicionarConvenio() {
		if (this.convenioSelecionado != null) {
			this.conveniosDisponiveis.remove(this.convenioSelecionado);
			this.conveniosUsuario.add(this.convenioSelecionado);
			this.ordenaListasConvenios();
			this.convenioSelecionado = null;
		}
	}
	
	public void removerConvenio(Convenio convenio) {
		this.conveniosUsuario.remove(convenio);
		this.conveniosDisponiveis.add(convenio);
		this.ordenaListasConvenios();
	}
	
	private void ordenaListasPerfis() {
		Comparator<Perfil> comparator = new Comparator<Perfil>() {
			@Override
			public int compare(Perfil o1, Perfil o2) {
				return o1.getNome().compareTo(o2.getNome());
			}
		};
		
		Collections.sort(this.perfisDisponiveis, comparator);
		Collections.sort(this.perfisUsuario, comparator);
	}

	private void ordenaListasEspecialidades() {
		Comparator<Especialidade> comparator = new Comparator<Especialidade>() {
			@Override
			public int compare(Especialidade o1, Especialidade o2) {
				return o1.getDescricao().compareTo(o2.getDescricao());
			}
		};
		
		Collections.sort(this.especialidadesDisponiveis, comparator);
		Collections.sort(this.especialidadesUsuario, comparator);
	}
	
	private void ordenaListasConvenios() {
		Comparator<Convenio> comparator = new Comparator<Convenio>() {
			@Override
			public int compare(Convenio o1, Convenio o2) {
				return o1.getDescricao().compareTo(o2.getDescricao());
			}
		};
		
		Collections.sort(this.conveniosDisponiveis, comparator);
		Collections.sort(this.conveniosUsuario, comparator);
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public String getMenuInclude() {
		return menuInclude;
	}

	public void setMenuInclude(String menuInclude) {
		this.menuInclude = menuInclude;
	}

	public List<Perfil> getPerfisUsuario() {
		return perfisUsuario;
	}

	public void setPerfisUsuario(List<Perfil> perfisUsuario) {
		this.perfisUsuario = perfisUsuario;
	}

	public Perfil getPerfilSelecionado() {
		return perfilSelecionado;
	}

	public void setPerfilSelecionado(Perfil perfilSelecionado) {
		this.perfilSelecionado = perfilSelecionado;
	}

	public List<Perfil> getPerfisDisponiveis() {
		return perfisDisponiveis;
	}

	public void setPerfisDisponiveis(List<Perfil> perfisDisponiveis) {
		this.perfisDisponiveis = perfisDisponiveis;
	}

	public List<Especialidade> getEspecialidadesUsuario() {
		return especialidadesUsuario;
	}

	public void setEspecialidadesUsuario(List<Especialidade> especialidadesUsuario) {
		this.especialidadesUsuario = especialidadesUsuario;
	}

	public List<Especialidade> getEspecialidadesDisponiveis() {
		return especialidadesDisponiveis;
	}

	public void setEspecialidadesDisponiveis(List<Especialidade> especialidadesDisponiveis) {
		this.especialidadesDisponiveis = especialidadesDisponiveis;
	}

	public Especialidade getEspecialidadeSelecionada() {
		return especialidadeSelecionada;
	}

	public void setEspecialidadeSelecionada(Especialidade especialidadeSelecionada) {
		this.especialidadeSelecionada = especialidadeSelecionada;
	}

	public List<Convenio> getConveniosUsuario() {
		return conveniosUsuario;
	}

	public void setConveniosUsuario(List<Convenio> conveniosUsuario) {
		this.conveniosUsuario = conveniosUsuario;
	}

	public List<Convenio> getConveniosDisponiveis() {
		return conveniosDisponiveis;
	}

	public void setConveniosDisponiveis(List<Convenio> conveniosDisponiveis) {
		this.conveniosDisponiveis = conveniosDisponiveis;
	}

	public Convenio getConvenioSelecionado() {
		return convenioSelecionado;
	}

	public void setConvenioSelecionado(Convenio convenioSelecionado) {
		this.convenioSelecionado = convenioSelecionado;
	}

	public List<EstadoEnum> getEstados() {
		return estados;
	}

	public void setEstados(List<EstadoEnum> estados) {
		this.estados = estados;
	}

	public boolean isEditarInfoPessoal() {
		return editarInfoPessoal;
	}

	public void setEditarInfoPessoal(boolean editarInfoPessoal) {
		this.editarInfoPessoal = editarInfoPessoal;
	}

	public boolean isEditarPaciente() {
		return editarPaciente;
	}

	public void setEditarPaciente(boolean editarPaciente) {
		this.editarPaciente = editarPaciente;
	}
}
