package br.edu.utfpr.constants;  
  
public enum EstadoEnum {  
	AC("Acre"),    
    AL("Alagoas"),    
    AM("Amazonas"),    
    AP("Amapá"),    
    BA("Bahia"),    
    CE("Ceará"),    
    DF("Distrito Federal"),    
    ES("Espírito Santo"),    
    GO("Goiás"),    
    MA("Maranhão"),    
    MG("Minas Gerais"),    
    MS("Mato Grosso Sul"),    
    MT("Mato Grosso"),    
    PA("Pará"),    
    PB("Paraíba"),    
    PE("Pernambuco"),    
    PI("Piauí"),    
    PR("Paraná"),    
    RJ("Rio de Janeiro"),    
    RN("Rio Grande do Norte"),    
    RO("Rondônia"),    
    RR("Roraima"),    
    RS("Rio Grande do Sul"),    
    SC("Santa Catarina"),    
    SE("Sergipe"),    
    SP("São Paulo"),    
    TO("Tocantins");    
    
     private String estado;    
    
    private EstadoEnum(String estado) {    
        this.estado = estado;
    }    
    
    public String getNomeEstado() {    
        return estado;    
    }
    
    public String getUf() {
    	return this.toString();
    }
}  