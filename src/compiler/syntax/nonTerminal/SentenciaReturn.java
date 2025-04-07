package compiler.syntax.nonTerminal;

import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.LabelFactory;
import es.uned.lsi.compiler.intermediate.LabelIF;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;

public class SentenciaReturn extends NonTerminal{
	
	private String tipo;

	public SentenciaReturn() {
		super();
	}
	
	public static SentenciaReturn semantico(ScopeManagerIF scopeManager, Expresion expr) {
		SentenciaReturn sr = new SentenciaReturn();
		sr.setTipo(expr.getTipo());
		//Código intermedio
		ScopeIF scope = scopeManager.getCurrentScope();
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		TemporalIF temp = expr.getTemporal();
		LabelFactory lf = new LabelFactory();
		LabelIF l1 = lf.create('F'+scope.getName());
		cb.addQuadruples(expr.getIntermediateCode());
		cb.addQuadruple("RETURN", l1, temp);
		sr.setIntermediateCode(cb.create());
		return sr;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	

}
