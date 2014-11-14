package br.edu.utfpr.mbean.usuario;

import static javax.faces.context.FacesContext.getCurrentInstance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;

import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Perfil;
import br.edu.utfpr.model.Usuario;
import br.edu.utfpr.service.PerfilService;
import br.edu.utfpr.service.UsuarioService;
import br.edu.utfpr.utils.FormatUtils;

@ManagedBean
@ViewScoped
public class EditarUsuarioMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioService usuarioService;
	@Inject
	private PerfilService perfilService;
	
	private Usuario usuarioSelecionado;
	private List<Perfil> perfisDisponiveis;
	private List<Perfil> perfisUsuario;
	private Perfil perfilSelecionado;
	
	private String menuInclude;
	
	@PostConstruct
	public void init() {
		HttpServletRequest request = (HttpServletRequest) getCurrentInstance().getExternalContext().getRequest();
		String idUsuario = request.getParameter("idUsuario");
		
		if (StringUtils.isNotEmpty(idUsuario) && StringUtils.isNumeric(idUsuario)) {
			this.usuarioSelecionado = usuarioService.retornarUsuario(Long.parseLong(idUsuario));
			this.perfisUsuario = new ArrayList<>(this.usuarioSelecionado.getPerfisUsuario());
		} else {
			this.usuarioSelecionado = new Usuario();
			this.perfisUsuario = new ArrayList<>();
		}
		this.listarPerfisDisponiveis();
	}
	
	private void listarPerfisDisponiveis() {
		this.perfisDisponiveis = perfilService.retornarPerfis();
		
		for (Perfil p : this.usuarioSelecionado.getPerfisUsuario()) {
			this.perfisDisponiveis.remove(p);
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
				usuarioService.salvarUsuario(getUsuarioLogado(), usuarioSelecionado);
	
				if (isNew) {
					addInfoMessage(getMsgs().getString("usuario.criar.sucesso"), true);
				} else {
					addInfoMessage(getMsgs().getString("usuario.editar.sucesso"), true);
				}
				
				return "/secure/usuarios/usuarios.xhtml?faces-redirect=true";
			} catch (AppException e) {
				addressException(e);
			}
		}
		return null;
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
	
}
