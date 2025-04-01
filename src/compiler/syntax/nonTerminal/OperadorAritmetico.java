package compiler.syntax.nonTerminal;

public class OperadorAritmetico extends NonTerminal{

	private String tipoOperador;

	public OperadorAritmetico() {
		super();
	}
	
	public static OperadorAritmetico semantico(String s) {
		OperadorAritmetico or = new OperadorAritmetico();
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
