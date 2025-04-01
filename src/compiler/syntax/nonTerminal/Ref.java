package compiler.syntax.nonTerminal;

import compiler.intermediate.Variable;
import compiler.semantic.symbol.SymbolConstant;
import compiler.semantic.symbol.SymbolParameter;
import compiler.semantic.symbol.SymbolVariable;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.TemporalFactory;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.SemanticErrorManager;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolTableIF;

public class Ref extends NonTerminal{
	
	private String tipo;
	private SymbolIF simbolo;

	public Ref() {
		super();
	}
	
	public static Ref semantico_1(
				ScopeManagerIF scopeManager, 
				SemanticErrorManager sm, 
				String s
			) {
		Ref ref = new Ref();
		ScopeIF scope = scopeManager.getCurrentScope();
		SymbolTableIF st = scope.getSymbolTable();
		if(comprobarSimbolo(scopeManager, st, s) ) {
			ref.simbolo = st.getSymbol(s);
			ref.tipo = ref.simbolo.getType().getName();
		}else {
			sm.semanticFatalError("[Ref] - Referencia "+s+" no ha sido creada");
		}
		//Codigo intermedio
		TemporalFactory tf = new TemporalFactory(scope);
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		TemporalIF temp = tf.create();
		SymbolIF simbolo = scopeManager.searchSymbol(s);
		if(simbolo instanceof SymbolConstant) {
			sm.semanticFatalError("[Ref] - Referencia "+s+" no puede ser constant");
		}else if(simbolo instanceof SymbolVariable || simbolo instanceof SymbolParameter) {
			Variable var = new Variable(s,simbolo.getScope());
			cb.addQuadruple("MVA",temp,var);
		}else {
			sm.semanticFatalError("[Ref] - Referencia "+s+" no es ni constant, ni variable, ni parameter");
		}
		ref.setTemporal(temp);
		ref.setIntermediateCode(cb.create());
		return ref;
	}
	public static Ref semantico_2(
				ScopeManagerIF scopeManager, 
				SemanticErrorManager sm,
				ExpresionAccesoRegistro ear
			) {
		Ref ref = new Ref();
		ref.tipo = ear.getTipo(scopeManager, sm);
		//Codigo intermedio
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scopeManager.getCurrentScope());
		cb.addQuadruples(ear.getIntermediateCode());
		ref.setTemporal(ear.getTemporal());
		ref.setIntermediateCode(cb.create());
		return ref;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	private static boolean comprobarSimbolo(ScopeManagerIF scopeManager, SymbolTableIF st, String s) {
		return (st.containsSymbol(s)) && (
				(scopeManager.searchSymbol(s) instanceof SymbolVariable)  ||
				(scopeManager.searchSymbol(s) instanceof SymbolParameter) 
				);
	}

	public SymbolIF getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(SymbolIF simbolo) {
		this.simbolo = simbolo;
	}
	
	
	
}
