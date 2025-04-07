package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.intermediate.Variable;
import compiler.semantic.symbol.SymbolFunction;
import compiler.semantic.symbol.SymbolParameter;
import compiler.semantic.symbol.SymbolProcedure;
import compiler.semantic.symbol.SymbolVariable;
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

public class DeclProcedimiento extends NonTerminal{
	
	private DeclParametros declParametros;


	public DeclProcedimiento() {
		super();
	}
	
	static public void semantico_1(
			ScopeManagerIF scopeManager,
			SemanticErrorManager sm,
			DeclParametros dp,
			String id
			) {
		//creamos el tipo y lo añadimos a la tabla de tipos
		if(scopeManager.containsType(id) || scopeManager.containsSymbol(id)) {
			sm.semanticFatalError("[DeclProcedure] - El id "+id+" no puede ser usado para creaar una funcion porque ya se encuentra en uso");
		}
		TypeProcedure tipoProc = new TypeProcedure(scopeManager.getCurrentScope(),id, dp.getListaTipoParametros());
		scopeManager.getCurrentScope().getTypeTable().addType(id, tipoProc);
		//abrimos un nuevo scope
		scopeManager.openScope(id);
		ScopeIF scope = scopeManager.getCurrentScope();
		SymbolTableIF tablaSimbolos = scope.getSymbolTable();
		//Añadimos parametros a tabla de simbolos
		ArrayList<SymbolParameter> listaSimbolosParametro = new ArrayList();
		for(DeclParametro declParametro : dp.getListaDeclParametro()) {
			TypeIF tipo = scopeManager.searchType(declParametro.getTipo());
			for(String idParametro : declParametro.getListaIds()) {
				if(scopeManager.containsSymbol(idParametro)) {
					sm.semanticFatalError("[DeclProcedimiento] - El nombre de parametro "+idParametro+" ya ha sido declarado");
				}
				SymbolParameter sParametro = new SymbolParameter(scope.getParentScope(),idParametro,tipo,declParametro.getEsReferencia());
				tablaSimbolos.addSymbol(idParametro, sParametro);
				listaSimbolosParametro.add(sParametro);
			}
		}
		//Añadimos el simbolo del procedimiento a la tabla de simbolos y a la tabla de tipos
		SymbolProcedure simboloProcedimiento = new SymbolProcedure(scope,id,tipoProc,listaSimbolosParametro);
		scope.getParentScope().getSymbolTable().addSymbol(id,simboloProcedimiento);

	}
	static public void semantico_2(
			ScopeManagerIF scopeManager,
			SemanticErrorManager sm,
			String id
			) {
		//creamos el tipo y lo añadimos a la tabla de tipos
		if(scopeManager.containsType(id) || scopeManager.containsSymbol(id)) {
			sm.semanticFatalError("[DeclProcedure] - El id "+id+"no puede ser usado para creaar una funcion porque ya se encuentra en uso");
		}
		TypeProcedure tipoProc = new TypeProcedure(scopeManager.getCurrentScope(),id, new ArrayList());
		scopeManager.getCurrentScope().getTypeTable().addType(id, tipoProc);
		//abrimos un nuevo scope
		scopeManager.openScope();
		ScopeIF scope = scopeManager.getCurrentScope();
		SymbolTableIF tablaSimbolos = scope.getSymbolTable();
		//Añadimos el simbolo del procedimiento a la tabla de simbolos
		SymbolProcedure simboloProcedimiento = new SymbolProcedure(scope.getParentScope(),id,tipoProc, new ArrayList());
		scope.getParentScope().getSymbolTable().addSymbol(id,simboloProcedimiento);
	}
	
	public static DeclProcedimiento codigoIntermedio(
			ScopeManagerIF scopeManager,
			SemanticErrorManager sm,
			Cuerpo cuerpo
		) {
	DeclProcedimiento dp = new DeclProcedimiento();
	ScopeIF scope = scopeManager.getCurrentScope();
	String nameScope = scope.getName();
	SymbolTableIF tablaSimbolos = scope.getParentScope().getSymbolTable();
	SymbolProcedure simbolo = (SymbolProcedure)tablaSimbolos.getSymbol(nameScope);
	
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
	dp.setIntermediateCode(cb.create());
	scopeManager.closeScope();
	return dp;
	
}
	
	public DeclParametros getDeclParametros() {
		return declParametros;
	}

	public void setDeclParametros(DeclParametros declParametros) {
		this.declParametros = declParametros;
	}
}
