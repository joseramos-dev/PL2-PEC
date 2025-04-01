package compiler.syntax.nonTerminal;

public class Tipo extends NonTerminal{
	
	private String tipo;

	public Tipo() {
		super();
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String _tipo) {
		tipo = _tipo;
	}
	
	public static Tipo semantico(String tipo) {
		Tipo t = new Tipo();
		t.setTipo(tipo);
		return t;
	}
	
}
