package compiler.syntax.nonTerminal;

import java.util.List;

import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.semantic.ScopeIF;

public class Axiom1 extends Axiom{

	public Axiom1() {
		super();
	}
	
	 public static Axiom intermediateCode(
				 ScopeIF scope, 
				 List<QuadrupleIF> listaCadenas,
				 Declaraciones d,
				 Cuerpo c
			 ) {
			Axiom1 a = new Axiom1();
			IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
			cb.addQuadruples(d.getIntermediateCode());
			cb.addQuadruples(c.getIntermediateCode());
			cb.addQuadruple("HALT");
			cb.addQuadruples(listaCadenas);
			
			a.setIntermediateCode(cb.create());
	    	
	    	return a;
	    	
	    }
}
