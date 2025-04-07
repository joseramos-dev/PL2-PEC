package compiler.syntax.nonTerminal;

import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.semantic.ScopeIF;

public class DeclSubprogramas extends NonTerminal{
	public DeclSubprogramas() {
		super();
	}
	
	public static DeclSubprogramas semantico_1(ScopeIF scope, DeclSubprograma d) {
		DeclSubprogramas ds = new DeclSubprogramas();
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		cb.addQuadruples(d.getIntermediateCode());
		ds.setIntermediateCode(cb.create());
		return ds;
		
	}
	public static DeclSubprogramas semantico_2(
			ScopeIF scope,
			DeclSubprogramas ds, 
			DeclSubprograma d
			) {
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		cb.addQuadruples(d.getIntermediateCode());
		cb.addQuadruples(ds.getIntermediateCode());
		ds.setIntermediateCode(cb.create());
		return ds;
		
	}
}
