package compiler.syntax.nonTerminal;

import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.semantic.ScopeIF;

public class GrupoDeclaraciones1 extends NonTerminal{

	public GrupoDeclaraciones1() {
		super();
	}
	
	public static GrupoDeclaraciones1 semantico_1(
			ScopeIF scope, 
			DeclTipos dt,
			GrupoDeclaraciones2 gd2
		) {
		GrupoDeclaraciones1 gd1 = new GrupoDeclaraciones1();
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		cb.addQuadruples(dt.getIntermediateCode());
		cb.addQuadruples(gd2.getIntermediateCode());
		gd1.setIntermediateCode(cb.create());
		return gd1;
	}
	public static GrupoDeclaraciones1 semantico_2(
				ScopeIF scope, 
				GrupoDeclaraciones2 gd2
			) {
		GrupoDeclaraciones1 gd1 = new GrupoDeclaraciones1();
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		cb.addQuadruples(gd2.getIntermediateCode());
		gd1.setIntermediateCode(cb.create());
		return gd1;
	}
	
}
