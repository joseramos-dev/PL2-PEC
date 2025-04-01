package compiler.syntax.nonTerminal;

import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.TemporalFactory;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.SemanticErrorManager;

public class SentenciaAsignacion extends NonTerminal{

	public SentenciaAsignacion() {
		super();
	}
	
	public static SentenciaAsignacion semantico(
				ScopeManagerIF scopeManager,
				SemanticErrorManager sm,
				Ref ref,
				Expresion expresion				
			) {
		SentenciaAsignacion sa = new SentenciaAsignacion();
		if(ref.getTipo() != expresion.getTipo()){
			sm.semanticFatalError("[SentenciaAsignacion] - El tipo de las expresiones a ambos lados de la sentencia de asignacion no coinciden: ref ->"+ref.getTipo()+"    expr -> "+expresion.getTipo());
		}
		//CODIGO INTERMEDIO
		ScopeIF scope = scopeManager.getCurrentScope();
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		TemporalIF temp = ref.getTemporal();
		TemporalIF etemp = expresion.getTemporal();
		cb.addQuadruples(expresion.getIntermediateCode());
		cb.addQuadruples(ref.getIntermediateCode());
		cb.addQuadruple("STP",temp,etemp);
		sa.setIntermediateCode(cb.create());		
		return sa;
	}
	
}
