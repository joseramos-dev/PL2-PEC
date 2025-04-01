package compiler.syntax.nonTerminal;

import java.util.ArrayList;

import compiler.semantic.symbol.SymbolFunction;
import compiler.semantic.symbol.SymbolParameter;
import compiler.semantic.type.TypeFunction;
import compiler.semantic.type.TypeProcedure;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.SemanticErrorManager;
import es.uned.lsi.compiler.semantic.symbol.SymbolTableIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;
import es.uned.lsi.compiler.semantic.type.TypeTableIF;

public class DeclFunction extends NonTerminal{
	
	private DeclParametros declParametros;
	private String tipoRetorno;
	
	public DeclFunction() {
		super();
	}
	
	public static DeclFunction semantico_parametros(
			ScopeManagerIF scopeManager,
			SemanticErrorManager sm,
			String id,
			DeclParametros dp,
			String _tipoRetorno		
			) {
		
		//creamos el tipo y lo añadimos a la tabla de tipos
		TypeFunction tipoFuncion = new TypeFunction(scopeManager.getCurrentScope(),id, dp.getListaTipoParametros(), _tipoRetorno);
		scopeManager.getCurrentScope().getTypeTable().addType(id, tipoFuncion);
		//abrimos un nuevo scope
		scopeManager.openScope(id);
		ScopeIF scope = scopeManager.getCurrentScope();
		SymbolTableIF tablaSimbolos = scope.getSymbolTable();
		TypeTableIF tablaTipos = scope.getTypeTable();
		//comprobamos que el nombre no este ya declarado
		if(tablaSimbolos.containsSymbol(id)){
			sm.semanticFatalError("[DeclFunction] - Funcion "+id+" ya declarada");
		}
		//Añadimos parametros a tabla de simbolos
		ArrayList<SymbolParameter> listaSimbolosParametro = new ArrayList();
		for(DeclParametro declParametro : dp.getListaDeclParametro()) {
			TypeIF tipo = scopeManager.searchType(declParametro.getTipo());
			for(String idParametro : declParametro.getListaIds()) {
				if(scopeManager.containsSymbol(idParametro)) {
					sm.semanticFatalError("[DeclFunction] - El nombre de parametro "+idParametro+" ya ha sido declarado");
				}
				SymbolParameter sParametro = new SymbolParameter(scope,idParametro,tipo,declParametro.getEsReferencia());
				tablaSimbolos.addSymbol(idParametro, sParametro);
				listaSimbolosParametro.add(sParametro);
			}
		}
		//Añadimos el simbolo de la funcion a la tabla de simbolos
		SymbolFunction simboloFuncion = new SymbolFunction(scope,id,tipoFuncion, listaSimbolosParametro,_tipoRetorno);
		tablaSimbolos.addSymbol(id, simboloFuncion);
		
		DeclFunction dFuncion = new DeclFunction();
		dFuncion.setDeclParametros(dp);
		dFuncion.setTipoRetorno(_tipoRetorno);
		return dFuncion;
	}
	
	public static DeclFunction semantico_sinParametros(
			ScopeManagerIF scopeManager,
			SemanticErrorManager sm,
			String id,
			String _tipoRetorno
			) {
		//creamos el tipo y lo añadimos a la tabla de tipos
		TypeFunction tipoFuncion = new TypeFunction(scopeManager.getCurrentScope(),id, new ArrayList(), _tipoRetorno);
		scopeManager.getCurrentScope().getTypeTable().addType(id, tipoFuncion);
		//abrimos un nuevo scope
		scopeManager.openScope(id);
		ScopeIF scope = scopeManager.getCurrentScope();
		SymbolTableIF tablaSimbolos = scope.getSymbolTable();
		//comprobamos que el nombre no este ya declarado
		if(tablaSimbolos.containsSymbol(id)){
			sm.semanticFatalError("[DeclFunction] - Funcion "+id+" ya declarada");
		}
		//Añadimos el simbolo de la funcion a la tabla de simbolos
		SymbolFunction simboloFuncion = new SymbolFunction(scope,id,tipoFuncion, new ArrayList(), _tipoRetorno);
		tablaSimbolos.addSymbol(id, simboloFuncion);
		
		DeclFunction dFuncion = new DeclFunction();
		dFuncion.setTipoRetorno(_tipoRetorno);
		return dFuncion;
	}


	public DeclParametros getDeclParametros() {
		return declParametros;
	}

	public void setDeclParametros(DeclParametros declParametros) {
		this.declParametros = declParametros;
	}

	public String getTipoRetorno() {
		return tipoRetorno;
	}

	public void setTipoRetorno(String tipoRetorno) {
		this.tipoRetorno = tipoRetorno;
	}
	
	
	
	

}
