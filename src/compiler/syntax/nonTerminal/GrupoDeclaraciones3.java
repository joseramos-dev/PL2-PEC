package compiler.syntax.nonTerminal;

import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.semantic.ScopeIF;

public class GrupoDeclaraciones3 extends NonTerminal{

	public GrupoDeclaraciones3() {
		super();
	}
	
	public static GrupoDeclaraciones3 semantico(ScopeIF scope, DeclSubprogramas ds) {
		GrupoDeclaraciones3 gd = new GrupoDeclaraciones3();
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		cb.addQuadruples(ds.getIntermediateCode());
		gd.setIntermediateCode(cb.create());
		return gd;
	}
}
