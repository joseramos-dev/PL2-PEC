package compiler.syntax.nonTerminal;

public class SentenciaProcedimiento extends NonTerminal{
	
	private String identificador;

	public SentenciaProcedimiento(){
		super();
	}
	
	public static SentenciaProcedimiento semantico(ExprFuncion expr) {
		SentenciaProcedimiento sp = new SentenciaProcedimiento();
		sp.setIdentificador(expr.getIdentificador());
		//Código intermedio
		sp.setIntermediateCode(expr.getIntermediateCode());
		return sp;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	
	
}
