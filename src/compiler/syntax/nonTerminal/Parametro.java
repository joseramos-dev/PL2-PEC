package compiler.syntax.nonTerminal;

public class Parametro extends NonTerminal{

	
	private boolean esCadena = false;
	private String cadena = "";
	private String tipoExpresion = "";
	
	public Parametro() {
		super();
	}
	
	public static Parametro semantico_expresion(Expresion ex) {
		Parametro p = new Parametro();
		p.tipoExpresion = ex.getTipo();
		p.setTemporal(ex.getTemporal());
		p.setIntermediateCode(ex.getIntermediateCode());
		return p;
	}
	
	public static Parametro semantico_cadena(String s) {
		Parametro p = new Parametro();
		p.esCadena = true;
		p.cadena = s;
		return p;
	}

	public boolean isEsCadena() {
		return esCadena;
	}

	public void setEsCadena(boolean esCadena) {
		this.esCadena = esCadena;
	}

	public String getCadena() {
		return cadena;
	}

	public void setCadena(String cadena) {
		this.cadena = cadena;
	}

	public String getTipoExpresion() {
		return tipoExpresion;
	}

	public void setTipoExpresion(String tipoExpresion) {
		this.tipoExpresion = tipoExpresion;
	}
	
	
}
