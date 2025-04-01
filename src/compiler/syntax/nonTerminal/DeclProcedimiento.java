package compiler.syntax.nonTerminal;

import java.util.ArrayList;

import compiler.semantic.symbol.SymbolParameter;
import compiler.semantic.symbol.SymbolProcedure;
import compiler.semantic.type.TypeProcedure;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.SemanticErrorManager;
import es.uned.lsi.compiler.semantic.symbol.SymbolTableIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class DeclProcedimiento extends NonTerminal{
	
	private DeclParametros declParametros;


	public DeclProcedimiento() {
		super();
	}
	
	static public DeclProcedimiento semantico_1(
			ScopeManagerIF scopeManager,
			SemanticErrorManager sm,
			DeclParametros dp,
			String id
			) {
		//creamos el tipo y lo añadimos a la tabla de tipos
		TypeProcedure tipoProc = new TypeProcedure(scopeManager.getCurrentScope(),id, dp.getListaTipoParametros());
		scopeManager.getCurrentScope().getTypeTable().addType(id, tipoProc);
		//abrimos un nuevo scope
		scopeManager.openScope(id);
		ScopeIF scope = scopeManager.getCurrentScope();
		SymbolTableIF tablaSimbolos = scope.getSymbolTable();
		//comprobamos que el nombre no este ya declarado
		if(tablaSimbolos.containsSymbol(id)){
			sm.semanticFatalError("[DeclProcedimiento] - Procedimiento "+id+" ya declarado");
		}
		//Añadimos parametros a tabla de simbolos
		ArrayList<SymbolParameter> listaSimbolosParametro = new ArrayList();
		for(DeclParametro declParametro : dp.getListaDeclParametro()) {
			TypeIF tipo = scopeManager.searchType(declParametro.getTipo());
			for(String idParametro : declParametro.getListaIds()) {
				if(scopeManager.containsSymbol(idParametro)) {
					sm.semanticFatalError("[DeclProcedimiento] - El nombre de parametro "+idParametro+" ya ha sido declarado");
				}
				SymbolParameter sParametro = new SymbolParameter(scope,idParametro,tipo,declParametro.getEsReferencia());
				tablaSimbolos.addSymbol(idParametro, sParametro);
				listaSimbolosParametro.add(sParametro);
			}
		}
		//Añadimos el simbolo del procedimiento a la tabla de simbolos y a la tabla de tipos
		SymbolProcedure simboloProcedimiento = new SymbolProcedure(scope,id,tipoProc,listaSimbolosParametro);
		tablaSimbolos.addSymbol(id, simboloProcedimiento);
		DeclProcedimiento dProcedimiento = new DeclProcedimiento();
		dProcedimiento.setDeclParametros(dp);
		return dProcedimiento;

	}
	static public DeclProcedimiento semantico_2(
			ScopeManagerIF scopeManager,
			SemanticErrorManager sm,
			String id
			) {
		//creamos el tipo y lo añadimos a la tabla de tipos
		TypeProcedure tipoProc = new TypeProcedure(scopeManager.getCurrentScope(),id, new ArrayList());
		scopeManager.getCurrentScope().getTypeTable().addType(id, tipoProc);
		//abrimos un nuevo scope
		scopeManager.openScope();
		ScopeIF scope = scopeManager.getCurrentScope();
		SymbolTableIF tablaSimbolos = scope.getSymbolTable();
		//comprobamos que el nombre no este ya declarado
		if(tablaSimbolos.containsSymbol(id)){
			sm.semanticFatalError("[DeclProcedimiento] - Procedimiento "+id+" ya declarado");
		}
		//Añadimos el simbolo del procedimiento a la tabla de simbolos
		SymbolProcedure simboloProcedimiento = new SymbolProcedure(scope,id,tipoProc, new ArrayList());
		tablaSimbolos.addSymbol(id, simboloProcedimiento);

		DeclProcedimiento dProcedimiento = new DeclProcedimiento();
		return dProcedimiento;
		
	}
	
	public DeclParametros getDeclParametros() {
		return declParametros;
	}

	public void setDeclParametros(DeclParametros declParametros) {
		this.declParametros = declParametros;
	}
}
