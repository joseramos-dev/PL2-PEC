package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.intermediate.Value;
import compiler.intermediate.Variable;
import compiler.semantic.symbol.SymbolConstant;
import compiler.semantic.symbol.SymbolFunction;
import compiler.semantic.symbol.SymbolParameter;
import compiler.semantic.symbol.SymbolVariable;
import compiler.semantic.type.TypeFunction;
import compiler.semantic.type.TypeProcedure;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.LabelFactory;
import es.uned.lsi.compiler.intermediate.LabelIF;
import es.uned.lsi.compiler.intermediate.TemporalFactory;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.SemanticErrorManager;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolTableIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;
import es.uned.lsi.compiler.semantic.type.TypeTableIF;

public class DeclFunction extends NonTerminal{
	
	private String tipoRetorno;
	
	public DeclFunction() {
		super();
	}
	
	public static void semantico_parametros(
			ScopeManagerIF scopeManager,
			SemanticErrorManager sm,
			String id,
			DeclParametros dp,
			String _tipoRetorno		
			) {
		
		//creamos el tipo y lo añadimos a la tabla de tipos
		if(scopeManager.containsType(id) || scopeManager.containsSymbol(id)) {
			sm.semanticFatalError("[DeclFunction] - El id "+id+"no puede ser usado para creaar una funcion porque ya se encuentra en uso");
		}
		TypeFunction tipoFuncion = new TypeFunction(scopeManager.getCurrentScope(),id, dp.getListaTipoParametros(), _tipoRetorno);
		scopeManager.getCurrentScope().getTypeTable().addType(id, tipoFuncion);
		//abrimos un nuevo scope
		scopeManager.openScope(id);
		ScopeIF scope = scopeManager.getCurrentScope();
		SymbolTableIF tablaSimbolos = scope.getSymbolTable();
		TypeTableIF tablaTipos = scope.getTypeTable();
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
		SymbolFunction simboloFuncion = new SymbolFunction(scope.getParentScope(),id,tipoFuncion, listaSimbolosParametro,_tipoRetorno);
		scope.getParentScope().getSymbolTable().addSymbol(id,simboloFuncion);
	}
	
	public static void semantico_sinParametros(
			ScopeManagerIF scopeManager,
			SemanticErrorManager sm,
			String id,
			String _tipoRetorno
			) {
		//creamos el tipo y lo añadimos a la tabla de tipos
		if(scopeManager.containsType(id) || scopeManager.containsSymbol(id)) {
			sm.semanticFatalError("[DeclFunction] - El id "+id+"no puede ser usado para creaar una funcion porque ya se encuentra en uso");
		}
		TypeFunction tipoFuncion = new TypeFunction(scopeManager.getCurrentScope(),id, new ArrayList(), _tipoRetorno);
		scopeManager.getCurrentScope().getTypeTable().addType(id, tipoFuncion);
		//abrimos un nuevo scope
		scopeManager.openScope(id);
		ScopeIF scope = scopeManager.getCurrentScope();
		SymbolTableIF tablaSimbolos = scope.getSymbolTable();
		//Añadimos el simbolo de la funcion a la tabla de simbolos
		SymbolFunction simboloFuncion = new SymbolFunction(scope.getParentScope(),id,tipoFuncion, new ArrayList(), _tipoRetorno);
		scope.getParentScope().getSymbolTable().addSymbol(id,simboloFuncion);
	}
	
	public static DeclFunction codigoIntermedio(
				ScopeManagerIF scopeManager,
				SemanticErrorManager sm,
				CuerpoFunction cuerpo
			) {
		DeclFunction df = new DeclFunction();
		ScopeIF scope = scopeManager.getCurrentScope();
		String nameScope = scope.getName();
		SymbolTableIF tablaSimbolos = scope.getParentScope().getSymbolTable();
		SymbolFunction simbolo = (SymbolFunction)tablaSimbolos.getSymbol(nameScope);
		df.tipoRetorno = simbolo.getTipoRetorno();
		
		TemporalFactory tf = new TemporalFactory(scope);
		TemporalIF temp = tf.create();
		LabelFactory lf = new LabelFactory();
		LabelIF l1 = lf.create(nameScope);
		LabelIF l2 = lf.create("F"+nameScope);
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		cb.addQuadruple("ETIQUETA",l1);
		List<SymbolIF> symbols = scope.getSymbolTable().getSymbols();
		int tamanoVariables = 0;
		for(SymbolIF s: symbols) {
			if(s instanceof SymbolVariable) {
				Variable var = new Variable(s.getName(),s.getScope());
				cb.addQuadruple("VARSUBPROGRAMA",temp,var);
				TypeIF type = ((SymbolVariable)s).getType();
				tamanoVariables += type.getSize();
			}
		}
		int tamano = tamanoVariables + scope.getTemporalTable().getSize() + 5;
		cb.addQuadruple("PUNTEROSUBPROGRAMA",temp,tamano);
		cb.addQuadruples(cuerpo.getIntermediateCode());
		cb.addQuadruple("FINSUBPROGRAMA",l2,simbolo.getSizeParametros()+5);
		df.setIntermediateCode(cb.create());
		scopeManager.closeScope();
		return df;
		
	}
	



	public String getTipoRetorno() {
		return tipoRetorno;
	}

	public void setTipoRetorno(String tipoRetorno) {
		this.tipoRetorno = tipoRetorno;
	}
	
	
	
	

}
