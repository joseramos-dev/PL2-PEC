package compiler.syntax.nonTerminal;

public class OperadorRelacional extends NonTerminal{
	
	private String tipoOperador;

	public OperadorRelacional() {
		super();
	}
	
	public static OperadorRelacional semantico(String s) {
		OperadorRelacional or = new OperadorRelacional();
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
