package compiler.syntax.nonTerminal;

import compiler.semantic.symbol.SymbolFunction;
import compiler.semantic.symbol.SymbolParameter;
import compiler.semantic.symbol.SymbolProcedure;
import compiler.semantic.symbol.SymbolVariable;
import compiler.semantic.type.TypeFunction;
import compiler.semantic.type.TypeProcedure;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.SemanticErrorManager;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolTableIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class ExprFuncion extends NonTerminal{
	
	private String identificador;
	private boolean procedimiento;
	private String tipoRetorno = "";

	public ExprFuncion() {
		super();
	}
	
	public static ExprFuncion semantico(
				ScopeManagerIF scopeManager,
				SemanticErrorManager sm,
				ListaParametrosInvocacion lpi,
				String id
			) {
		ExprFuncion funcion = new ExprFuncion();
		funcion.setIdentificador(id);
		ScopeIF scope = scopeManager.getCurrentScope();
		SymbolTableIF st = scope.getSymbolTable();
		if(scopeManager.containsType(id)){
			TypeIF type = scopeManager.searchType(id);
			//tratar parametros?
			if(type instanceof TypeFunction) {
				//funcion
				return funcion(scopeManager, sm, lpi, funcion, (TypeFunction)type);
				
			}else if(type instanceof TypeProcedure){
				//procedimiento
				return procedimiento(scopeManager, sm, lpi, funcion, (TypeProcedure)type);
			}else {
				sm.semanticFatalError("[ExprFuncion] - El identificador "+id+" no pertenece a una función o procedimiento");
			}
		}else {
			sm.semanticFatalError("[ExprFuncion] - La función con identificador "+id+" no existe");
		}
		
		return funcion;
	}
	
	private static ExprFuncion funcion(
				ScopeManagerIF scopeManager,
				SemanticErrorManager sm,
				ListaParametrosInvocacion lpi,
				ExprFuncion funcion,
				TypeFunction type
			) {
		
		
		// comprobamos nº parametros es igual
		if(lpi.getNumeroParametros() != type.getListaParametros().size()) {
			sm.semanticFatalError("[ExprFuncion] - Se ha intentado ejecutar el comando "+type.getName()+" con un número de parametros inadecuado");
		}
		//comprobamos tipo de variables usadas (lpi) es igual al requerido por el procedimiento
		//si se pasa por valor que sea entero/booleano(typeSimple), y si se pasa por referencia entero/booleano/record(typeSimple o TypeRecord)
		for(int i=0; i<lpi.getNumeroParametros(); i++) {
			String tipoParametroPasado = lpi.getListaParametros().get(i);
			if(scopeManager.containsType(tipoParametroPasado)) {
				String tipoRequerido = type.getListaParametros().get(i).getTipo();
				if(!tipoParametroPasado.equals(tipoRequerido)) {
					sm.semanticFatalError("[ExprFuncion] - El parametro "+tipoParametroPasado+") no es del tipo "+tipoRequerido);
				}
			}else {
				sm.semanticFatalError("ExprFuncion] - El simbolo "+tipoParametroPasado+" usado como parametro no existe");
			}
			
		}
		funcion.setProcedimiento(false);
		funcion.setTipoRetorno(type.getTipoRetorno());
		return funcion;
	}
	private static ExprFuncion procedimiento(
				ScopeManagerIF scopeManager,
				SemanticErrorManager sm,
				ListaParametrosInvocacion lpi,
				ExprFuncion funcion,
				TypeProcedure type
			) {
		// comprobamos nº parametros es igual
		if(lpi.getNumeroParametros() != type.getListaParametros().size()) {
			sm.semanticFatalError("[ExprFuncion] - Se ha intentado ejecutar el comando "+type.getName()+" con un número de parametros inadecuado");
		}
		//comprobamos tipo de variables usadas (lpi) es igual al requerido por el procedimiento
		//si se pasa por valor que sea entero/booleano, y si se pasa por referencia entero/booleano/record
		for(int i=0; i<lpi.getNumeroParametros(); i++) {
			String tipoParametroPasado = lpi.getListaParametros().get(i);
			if(scopeManager.containsType(tipoParametroPasado)) {
				String tipoRequerido = type.getListaParametros().get(i).getTipo();
				if(!tipoParametroPasado.equals(tipoRequerido)) {
					sm.semanticFatalError("[ExprFuncion] - El parametro "+tipoParametroPasado+" no es del tipo "+tipoRequerido);
				}
			}else {
				sm.semanticFatalError("ExprFuncion] - El simbolo "+tipoParametroPasado+" usado como parametro no existe");
			}
			
		}
		funcion.setProcedimiento(true);
		return funcion;
	}
	

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public boolean isProcedimiento() {
		return procedimiento;
	}

	public void setProcedimiento(boolean procedimiento) {
		this.procedimiento = procedimiento;
	}

	public String getTipoRetorno() {
		return tipoRetorno;
	}

	public void setTipoRetorno(String tipoRetorno) {
		this.tipoRetorno = tipoRetorno;
	}

	
	
	
	
}
