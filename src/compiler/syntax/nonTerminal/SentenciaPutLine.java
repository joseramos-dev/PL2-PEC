package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.intermediate.Label;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.LabelFactory;
import es.uned.lsi.compiler.intermediate.LabelIF;
import es.uned.lsi.compiler.intermediate.Quadruple;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.intermediate.TemporalFactory;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.SemanticErrorManager;

public class SentenciaPutLine extends NonTerminal {

	public SentenciaPutLine() {
		super();
	}

	public static SentenciaPutLine semantico(
				ScopeManagerIF scopeManager, 
				SemanticErrorManager sm, 
				List<QuadrupleIF> listaCadenas,
				Parametro p
			) {
		SentenciaPutLine spl = new SentenciaPutLine();
		ScopeIF scope = scopeManager.getCurrentScope();
		if( p.isEsCadena() ) {
			return codigoIntermedio_cadena(scope,listaCadenas,spl,p);			
		}else if ( p.getTipoExpresion() == "ENTERO"  || p.getTipoExpresion() == "BOOLEANO") {
			return codigoIntermedio_expresion(scope,spl,p);
		}else {
			sm.semanticFatalError("[SentenciaPutLine] - Introduzca un valor de tipo CADENA, ENTERO o BOOLEANO");
		}
		return spl;
	}
	
	private static SentenciaPutLine codigoIntermedio_cadena(ScopeIF scope, List<QuadrupleIF> listaCadenas, SentenciaPutLine spl, Parametro p) {
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		TemporalFactory tf = new TemporalFactory(scope);
		LabelFactory lf = new LabelFactory();
		LabelIF lb = lf.create();
		TemporalIF temp = tf.create();
		cb.addQuadruple("WRITESTRING",temp, lb);
		listaCadenas.add(new Quadruple("CADENA",new Label(p.getCadena()),lb));
		spl.setIntermediateCode(cb.create());
		return spl;
	}
	private static SentenciaPutLine codigoIntermedio_expresion(ScopeIF scope, SentenciaPutLine spl, Parametro p) {
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		cb.addQuadruples(p.getIntermediateCode());
		cb.addQuadruple("WRITEINT",p.getTemporal()  );
		spl.setIntermediateCode(cb.create());
		return spl;
		
	}
}
