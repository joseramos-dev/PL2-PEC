package compiler.syntax.nonTerminal;

import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.semantic.ScopeIF;

public class Declaraciones extends NonTerminal{
	
	public Declaraciones() {
		super();
	}
	
	public static Declaraciones semantico_1(
				ScopeIF scope, 
				DeclConstantes dc, 
				GrupoDeclaraciones1 gd1
			) {
		Declaraciones d = new Declaraciones();
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		//cb.addQuadruples(dc.getIntermediateCode());
		cb.addQuadruples(gd1.getIntermediateCode());
		d.setIntermediateCode(cb.create());
		return d;
	}
	public static Declaraciones semantico_2(
			ScopeIF scope, 
			GrupoDeclaraciones1 gd1
		) {
		Declaraciones d = new Declaraciones();
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		cb.addQuadruples(gd1.getIntermediateCode());
		d.setIntermediateCode(cb.create());
		return d;
	}
	
}
