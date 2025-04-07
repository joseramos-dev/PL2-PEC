package compiler.syntax.nonTerminal;

import java.util.ArrayList;

import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.LabelFactory;
import es.uned.lsi.compiler.intermediate.LabelIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.SemanticErrorManager;

public class SentenciaWhile extends NonTerminal{

	ArrayList<String> listaReturns;
	
	public SentenciaWhile() {
		super();
		listaReturns = new ArrayList();
	}
	
	public static SentenciaWhile semantico(
				ScopeManagerIF scopeManager,
				SemanticErrorManager sm,
				Expresion ex,
				Sentencias s
			) {
		SentenciaWhile sw = new SentenciaWhile();
		if(ex.getTipo() != "BOOLEANO") {
			sm.semanticFatalError("[SentenciaWhile] - La expresión no es de tipo lógico");
		}
		sw.listaReturns.addAll(s.getListaReturns());
		
		//Código intermedio
		ScopeIF scope = scopeManager.getCurrentScope();
		LabelFactory lF = new LabelFactory();
		LabelIF l1 = lF.create(); 
		LabelIF l2 = lF.create(); 
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		cb.addQuadruple("INL", l1); 
		cb.addQuadruples(ex.getIntermediateCode()); 
		cb.addQuadruple("BRF", ex.getTemporal(), l2); 
		cb.addQuadruples(s.getIntermediateCode()); 
		cb.addQuadruple("BR", l1);
		cb.addQuadruple("INL", l2);
		sw.setIntermediateCode(cb.create());
		return sw;
	}

	public ArrayList<String> getListaReturns() {
		return listaReturns;
	}

	public void setListaReturns(ArrayList<String> listaReturns) {
		this.listaReturns = listaReturns;
	}
	
	
}
