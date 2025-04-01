package compiler.syntax.nonTerminal;


public class Literal extends NonTerminal {
	private String tipo;
	private String valor;
	
	public Literal() {
		super();
	}
	
	public String getTipo() {
		return tipo;
	}
	public String getValor() {
		return valor;
	}
	
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public static Literal semantico_entero(String valor) {
		Literal l = new Literal();
		l.setTipo("ENTERO");
		l.setValor(valor);
		return l;
		
	}
	public static Literal semantico_booleano(String valor) {
		Literal l = new Literal();
		l.setTipo("BOOLEANO");
		l.setValor(valor);
		return l;
	}
}
