package compiler.syntax.nonTerminal;

public class OperadorLogico extends NonTerminal{

	private String tipoOperador;

	public OperadorLogico() {
		super();
	}
	
	public static OperadorLogico semantico(String s) {
		OperadorLogico or = new OperadorLogico();
		or.setTipoOperador(s);
		return or;
	}

	public String getTipoOperador() {
		return tipoOperador;
	}

	public void setTipoOperador(String tipoOperador) {
		this.tipoOperador = tipoOperador;
	}
}
