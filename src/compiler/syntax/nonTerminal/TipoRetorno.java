package compiler.syntax.nonTerminal;

public class TipoRetorno extends NonTerminal {
	
	private String tipo;

	public TipoRetorno() {
		super();
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public static TipoRetorno semantico(String tipo) {
		TipoRetorno tp = new TipoRetorno();
		tp.setTipo(tipo);
		return tp;
	}
	
	
}
