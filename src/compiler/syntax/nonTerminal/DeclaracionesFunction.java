package compiler.syntax.nonTerminal;

import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.semantic.ScopeIF;

public class DeclaracionesFunction extends NonTerminal{

	public DeclaracionesFunction() {
		super();
	}

	public static DeclaracionesFunction semantico(
				ScopeIF scope, 
				GrupoDeclaraciones1 gd1
			) {
		DeclaracionesFunction df = new DeclaracionesFunction();
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		cb.addQuadruples(gd1.getIntermediateCode());
		df.setIntermediateCode(cb.create());
		return df;
	}
}
