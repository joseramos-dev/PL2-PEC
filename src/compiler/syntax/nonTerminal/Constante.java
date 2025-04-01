package compiler.syntax.nonTerminal;

import compiler.semantic.symbol.SymbolConstant;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.SemanticErrorManager;
import es.uned.lsi.compiler.semantic.symbol.SymbolTableIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;
import es.uned.lsi.compiler.semantic.type.TypeTableIF;


public class Constante {

	public Constante() {
		super();
	}
	
	static public Constante semantico(
			ScopeManagerIF scopeManager, 
			SemanticErrorManager sm, 
			String id, 
			Literal lit
			) 
	{
		Constante constante = new Constante();
		ScopeIF scope = scopeManager.getCurrentScope();
		SymbolTableIF tablaSimbolos = scope.getSymbolTable();
		String nombre = id;
		if(tablaSimbolos.containsSymbol(nombre)){
			sm.semanticFatalError("[Constante] - Constante "+nombre+" ya declarada");
		}else{
			TypeTableIF tablaTipos = scope.getTypeTable();
			TypeIF tipo = scopeManager.searchType(lit.getTipo());
			SymbolConstant simboloConstante = new SymbolConstant(scope, nombre, tipo);
			simboloConstante.setValor(lit.getValor());
			tablaSimbolos.addSymbol(nombre, simboloConstante);
		}
		return constante;
	}
	
}
