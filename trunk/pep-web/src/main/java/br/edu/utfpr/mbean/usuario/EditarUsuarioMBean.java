package br.edu.utfpr.mbean.usuario;

import static javax.faces.context.FacesContext.getCurrentInstance;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.primefaces.model.DualListModel;

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
	private DualListModel<Perfil> perfisPickList;
	private String menuInclude;
	
	@PostConstruct
	public void init() {
		HttpServletRequest request = (HttpServletRequest) getCurrentInstance().getExternalContext().getRequest();
		String idUsuario = request.getParameter("idUsuario");
		
		if (StringUtils.isNotEmpty(idUsuario) && StringUtils.isNumeric(idUsuario)) {
			this.usuarioSelecionado = usuarioService.retornarUsuario(Long.parseLong(idUsuario));
		} else {
			this.usuarioSelecionado = new Usuario();
		}
		this.listarPerfis();
	}
	
	private void listarPerfis() {
		List<Perfil> perfis = perfilService.retornarPerfis();
		Set<Perfil> perfisUsuario = usuarioSelecionado.getPerfisUsuario();
		if (perfisUsuario != null) {
			for (Perfil perfil : perfisUsuario) {
				perfis.remove(perfil);
			}
		} else {
			perfisUsuario = new HashSet<>();
		}
		perfisPickList = new DualListModel<Perfil>(perfis, new ArrayList<>(perfisUsuario));
	}
	
	public String salvar() {
		usuarioSelecionado.setTelefone( FormatUtils.somenteDigitos(usuarioSelecionado.getTelefone()) );
		usuarioSelecionado.setCelular( FormatUtils.somenteDigitos(usuarioSelecionado.getCelular()) );
		usuarioSelecionado.setCpf( FormatUtils.somenteDigitos(usuarioSelecionado.getCpf()) );
		
		if (validarCampos()) {
			boolean isNew = usuarioSelecionado.isNew();
			usuarioSelecionado.setPerfisUsuario(new LinkedHashSet<>(perfisPickList.getTarget()));
			try {
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

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public DualListModel<Perfil> getPerfisPickList() {
		return perfisPickList;
	}

	public void setPerfisPickList(DualListModel<Perfil> perfisPickList) {
		this.perfisPickList = perfisPickList;
	}

	public String getMenuInclude() {
		return menuInclude;
	}

	public void setMenuInclude(String menuInclude) {
		this.menuInclude = menuInclude;
	}
}