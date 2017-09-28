package br.edu.utfpr.mbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ThemeSwitcherView implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> themes;
	private String theme;

	@PostConstruct
	public void init() {
		themes = new ArrayList<>();
		theme = "cupertino";

        themes.add("afternoon");
        themes.add("afterwork");
        themes.add("black-tie");
        themes.add("bootstrap");
        themes.add("casablanca");
        themes.add("cupertino");
        themes.add("delta");
        themes.add("glass-x");
        themes.add("home");
        themes.add("humanity");
        themes.add("pepper-grinder");
        themes.add("smoothness");
        themes.add("sunny");
	}
	
	public void saveTheme() {
		
	}

	public List<String> getThemes() {
		return themes;
	}

	public void setThemes(List<String> themes) {
		this.themes = themes;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
}