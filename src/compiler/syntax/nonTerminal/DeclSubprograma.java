package compiler.syntax.nonTerminal;

import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.semantic.ScopeIF;

public class DeclSubprograma extends NonTerminal{
	public DeclSubprograma() {
		super();
	}
	
	public static DeclSubprograma semantico_fun(ScopeIF scope, DeclFunction df) {
		DeclSubprograma dc = new DeclSubprograma();
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		cb.addQuadruples(df.getIntermediateCode());
		dc.setIntermediateCode(cb.create());
		return dc;
		
	}
	
	public static DeclSubprograma semantico_proc(ScopeIF scope, DeclProcedimiento dp) {
		DeclSubprograma dc = new DeclSubprograma();
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		cb.addQuadruples(dp.getIntermediateCode());
		dc.setIntermediateCode(cb.create());
		return dc;
	}
	
}
