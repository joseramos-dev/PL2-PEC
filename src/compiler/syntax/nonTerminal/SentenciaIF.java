package compiler.syntax.nonTerminal;

import java.util.ArrayList;

import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.LabelFactory;
import es.uned.lsi.compiler.intermediate.LabelIF;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.SemanticErrorManager;

public class SentenciaIF extends NonTerminal{
	
	ArrayList<String> listaReturnsIf;
	ArrayList<String> listaReturnsElse;
	
	public SentenciaIF() {
		super();
		listaReturnsIf = new ArrayList();
		listaReturnsElse = new ArrayList();
	}
	
	public static SentenciaIF semantico(
				ScopeManagerIF scopeManager,
				SemanticErrorManager sm,
				Expresion exp,
				Sentencias sent,
				Sentencias sentElse
			) {
		SentenciaIF sif = new SentenciaIF();
		if(exp.getTipo() != "BOOLEANO") {
			sm.semanticFatalError("[SentenciaIF] - expresión introducida no es de tipo lógico");
		}
		if(sent.tieneReturn()) {
			sif.setListaReturnsIf(sent.getListaReturns());
		}
		if(sentElse != null && sentElse.tieneReturn()) {
			sif.setListaReturnsElse(sentElse.getListaReturns());
		}
		//Codigo intermedio
		LabelFactory ifact = new LabelFactory();
		LabelIF l1 = ifact.create();
		LabelIF l2 = ifact.create();
		TemporalIF expTemp = exp.getTemporal();
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scopeManager.getCurrentScope());
		cb.addQuadruples(exp.getIntermediateCode());
		cb.addQuadruple("BRF",expTemp,l1);
		cb.addQuadruples(sent.getIntermediateCode());
		cb.addQuadruple("BR",l2);
		cb.addQuadruple("INL",l1);
		if(sentElse != null) {
			cb.addQuadruples(sentElse.getIntermediateCode());
		}
		cb.addQuadruple("INL",l2);
		sif.setIntermediateCode(cb.create());
		return sif;
	}

	public ArrayList<String> getListaReturnsIf() {
		return listaReturnsIf;
	}

	public void setListaReturnsIf(ArrayList<String> listaReturnsIf) {
		this.listaReturnsIf = listaReturnsIf;
	}

	public ArrayList<String> getListaReturnsElse() {
		return listaReturnsElse;
	}

	public void setListaReturnsElse(ArrayList<String> listaReturnsElse) {
		this.listaReturnsElse = listaReturnsElse;
	}
	
	
}
