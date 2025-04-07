package compiler.syntax.nonTerminal;

import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.semantic.ScopeIF;

public class GrupoDeclaraciones2 extends NonTerminal{

	public GrupoDeclaraciones2() {
		super();
	}
	
	public static GrupoDeclaraciones2 semantico_1(
				ScopeIF scope,
				DeclVariables dv,
				GrupoDeclaraciones3 gd3
			) {
		GrupoDeclaraciones2 gd2 = new GrupoDeclaraciones2();
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		cb.addQuadruples(dv.getIntermediateCode());
		cb.addQuadruples(gd3.getIntermediateCode());
		gd2.setIntermediateCode(cb.create());
		return gd2;
	}
	public static GrupoDeclaraciones2 semantico_2(
				ScopeIF scope,
				GrupoDeclaraciones3 gd3
			) {
		GrupoDeclaraciones2 gd2 = new GrupoDeclaraciones2();
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		cb.addQuadruples(gd3.getIntermediateCode());
		gd2.setIntermediateCode(cb.create());
		return gd2;
	}
}
