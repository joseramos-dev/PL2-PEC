package compiler.syntax.nonTerminal;

import java.util.ArrayList;

import compiler.semantic.symbol.SymbolVariable;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.SemanticErrorManager;
import es.uned.lsi.compiler.semantic.symbol.SymbolTableIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class DeclVariable extends NonTerminal{

	private String tipo;
	ArrayList<String> listaId;
	
	public DeclVariable() {
		super();
		listaId = new ArrayList<String>();
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String _tipo) {
		tipo = _tipo;
	}

	public ArrayList<String> getListaId() {
		return listaId;
	}
	public void addId(String id) {
		listaId.add(id);
	}
	
	public static DeclVariable semantico_1(
			ScopeManagerIF scopeManager,
			String id,
			SemanticErrorManager sm,
			Tipo tp
			) {
		ScopeIF scope = scopeManager.getCurrentScope();
		String nombre = id;
		SymbolTableIF tablaSimbolos = scope.getSymbolTable();
		if(tablaSimbolos.containsSymbol(id)){
			sm.semanticFatalError("[DeclVariable] - Variable "+nombre+" ya declarada");
		}else{
			TypeIF tipo = scopeManager.searchType(tp.getTipo());
			if(tipo==null){
				sm.semanticFatalError("[DeclVariable] - Variable tipo nulo= "+tipo);
			}else{
				SymbolVariable sV = new SymbolVariable(scope, nombre, tipo);
				tablaSimbolos.addSymbol(sV);
			}
		}
		DeclVariable dv = new DeclVariable();
		dv.setTipo(tp.getTipo());
		dv.addId(nombre);
		return dv;
	}
	
	public static DeclVariable semantico_2(
			ScopeManagerIF scopeManager,
			String id,
			SemanticErrorManager sm,
			DeclVariable dv
			) {
		ScopeIF scope = scopeManager.getCurrentScope();
		String nombre = id;
		SymbolTableIF tablaSimbolos = scope.getSymbolTable();
		if(tablaSimbolos.containsSymbol(id)){
			sm.semanticFatalError("[DeclVariable] - Variable "+nombre+" ya declarada");
		}else{
			TypeIF tipo = scopeManager.searchType(dv.getTipo());
			if(tipo==null){
				sm.semanticFatalError("[DeclVariable] - Variable tipo nulo= "+tipo);
			}else{
				SymbolVariable sV = new SymbolVariable(scope, nombre, tipo);
				tablaSimbolos.addSymbol(sV);
			}
		}
		dv.addId(nombre);
		return dv;
	}
	
}
