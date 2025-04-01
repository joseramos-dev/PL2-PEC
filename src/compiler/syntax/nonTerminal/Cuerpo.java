package compiler.syntax.nonTerminal;

import java.util.ArrayList;

import compiler.semantic.symbol.SymbolFunction;
import compiler.semantic.symbol.SymbolProcedure;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.SemanticErrorManager;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolTableIF;

public class Cuerpo extends NonTerminal{
	
	private ArrayList<String> listaReturns;
	private String idEnd;

	public Cuerpo() {
		super();
		listaReturns = new ArrayList();
	}
	
	public static Cuerpo semantico(Sentencias s, String _idEnd) {
		Cuerpo cuerpo = new Cuerpo();
		if(s!=null) {
			cuerpo.setListaReturns(s.getListaReturns());
			//Código intermedio
			cuerpo.setIntermediateCode(s.getIntermediateCode());
		}
		cuerpo.idEnd = _idEnd;
		return cuerpo;
	}

	public ArrayList<String> getListaReturns() {
		return listaReturns;
	}

	public void setListaReturns(ArrayList<String> listaReturns) {
		this.listaReturns = listaReturns;
	}
	
	public String getIdEnd() {
		return idEnd;
	}

	public void setIdEnd(String idEnd) {
		this.idEnd = idEnd;
	}

	public void comprobarIdEnd(ScopeManagerIF scopeManager, SemanticErrorManager sm) {
		ScopeIF scope = scopeManager.getCurrentScope();
		SymbolTableIF tablaSimbolos = scope.getSymbolTable();
		if (!tablaSimbolos.containsSymbol(idEnd)) {
			sm.semanticFatalError("[Cuerpo] - El id del final ("+idEnd+") no coincide con el del procedimiento");
		}
		SymbolIF simbolo = tablaSimbolos.getSymbol(idEnd);
		if (!(simbolo instanceof SymbolProcedure) ) {
			sm.semanticFatalError("[Cuerpo] - El id final ("+idEnd+") no corresponde con el de un procedimiento");
		}
		
	}
	
	
}
