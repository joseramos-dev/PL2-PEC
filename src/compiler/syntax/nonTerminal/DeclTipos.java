package compiler.syntax.nonTerminal;

public class DeclTipos extends NonTerminal{
	
	

	public DeclTipos() {
		super();
	}
	
	public static DeclTipos semantico() {
		DeclTipos dt = new DeclTipos();
		return dt;
	}
	public static DeclTipos semantico(DeclTipo d) {
		DeclTipos dt = new DeclTipos();
		return dt;
	}
}
