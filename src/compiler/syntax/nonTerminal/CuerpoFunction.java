package compiler.syntax.nonTerminal;

import java.util.ArrayList;

import compiler.semantic.symbol.SymbolFunction;
import compiler.semantic.symbol.SymbolProcedure;
import compiler.semantic.type.TypeFunction;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.SemanticErrorManager;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolTableIF;

public class CuerpoFunction extends NonTerminal{
	
	private ArrayList<String> listaReturns;
	private ArrayList<String> listaReturnsCondicionales;
	private String idEnd;
	
	public CuerpoFunction() {
		super();
		listaReturns = new ArrayList();
		listaReturnsCondicionales = new ArrayList();
	}
	
	public static CuerpoFunction semantico(Sentencias s, String _idEnd) {
		CuerpoFunction cf = new CuerpoFunction();
		cf.setListaReturns(s.getListaReturns());
		cf.setListaReturnsCondicionales(s.getListaReturnsCondicionales());
		cf.setIdEnd(_idEnd);
		//Código intermedio
		cf.setIntermediateCode(s.getIntermediateCode());
		return cf;
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

	public ArrayList<String> getListaReturnsCondicionales() {
		return listaReturnsCondicionales;
	}

	public void setListaReturnsCondicionales(ArrayList<String> listaReturnsCondicionales) {
		this.listaReturnsCondicionales = listaReturnsCondicionales;
	}

	public void comprobarIdEnd(ScopeManagerIF scopeManager, SemanticErrorManager sm) {
		ScopeIF scope = scopeManager.getCurrentScope();
		String nombreScope = scope.getName();
		if (!nombreScope.equals(idEnd)) {
			sm.semanticFatalError("[CuerpoFunction] - El id del final ("+idEnd+") no esta contenido en la tabla de simbolos");
		}
	}
	
	public void comprobarReturns(
			ScopeManagerIF scopeManager, 
			SemanticErrorManager sm
			) {
		ScopeIF scope = scopeManager.getCurrentScope();
		SymbolTableIF tablaSimbolos = scope.getParentScope().getSymbolTable();
		SymbolIF simbolo = tablaSimbolos.getSymbol(idEnd);
		if( !(simbolo.getType() instanceof TypeFunction ) ) {
			sm.semanticFatalError("[CuerpoFunction] - El tipo de la funcion "+idEnd+" no es TypeFunction");
		}
		String tipo = ((SymbolFunction)simbolo).getTipoRetorno();
		for(String s : listaReturns) {
			if(s != tipo) {
				sm.semanticFatalError("[CuerpoFunction] - El tipo del return ("+s+") no coincide con "+tipo);
			}
		}
		for(String s : listaReturnsCondicionales) {
			if(s != tipo) {
				sm.semanticFatalError("[CuerpoFunction] - El tipo del return ("+s+") no coincide "+tipo);
			}
		}
	}
	
	

}
