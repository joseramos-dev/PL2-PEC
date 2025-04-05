package compiler.syntax.nonTerminal;

import compiler.intermediate.Value;
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

public class Operando extends NonTerminal{
	
	private String tipo;

	public Operando() {
		super();
	}
	
	public static Operando semantico_entero(
				ScopeManagerIF scopeManager, 
				SemanticErrorManager sm, 
				String s
			) {
		Operando o = new Operando();
		o.setTipo("ENTERO");
		
		//CÓDIGO INTERMEDIO
		ScopeIF scope = scopeManager.getCurrentScope();
		TemporalFactory tf = new TemporalFactory(scope);
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		TemporalIF temp = tf.create();
		Value valor = new Value(Integer.parseInt(s));
		cb.addQuadruple("MV",temp,valor);
		o.setTemporal(temp);
		o.setIntermediateCode(cb.create());
		
		return o;
	}
	public static Operando semantico_booleano(
				ScopeManagerIF scopeManager, 
				SemanticErrorManager sm, 
				String s
			) {
		Operando o = new Operando();
		o.setTipo("BOOLEANO");
		
		//CÓDIGO INTERMEDIO
		ScopeIF scope = scopeManager.getCurrentScope();
		TemporalFactory tf = new TemporalFactory(scope);
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		TemporalIF temp = tf.create();
		Value valor = null;
		if(s.equals("True")) {
			valor = new Value(1);
		}else if(s.equals("False")) {
			valor = new Value(0);
		}else {
			sm.semanticFatalError("[Operando] - el valor booleano introducido ("+s+") no es valido ");
		}
		cb.addQuadruple("MV",temp,valor);
		o.setTemporal(temp);
		o.setIntermediateCode(cb.create());
		
		return o;
	}

	public static Operando semantico_identificador(
				ScopeManagerIF scopeManager, 
				SemanticErrorManager sm, 
				String s
			) {
		Operando o = new Operando();
		if (scopeManager.containsSymbol(s)) {
			SymbolIF symbol = scopeManager.searchSymbol(s);
			if (scopeManager.containsType(symbol.getType().getName())) {
				o.tipo = symbol.getType().getName();
			} else {
				sm.semanticFatalError("[Operando] - El tipo (" + symbol.getType().getName()
						+ " del simbolo que se ha intentado usar no existe");
			}
		} else {
			sm.semanticFatalError("[Operando] - El simbolo " + s + " no existe");
		}
		
		//CÓDIGO INTERMEDIO
		ScopeIF scope = scopeManager.getCurrentScope();
		TemporalFactory tf = new TemporalFactory(scope);
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		TemporalIF temp = tf.create();
		SymbolIF simbolo = scopeManager.searchSymbol(s);
		if(simbolo instanceof SymbolConstant) {
			Value valor = new Value(Integer.parseInt(((SymbolConstant)simbolo).getValor()));
			cb.addQuadruple("MV",temp,valor);
		}else if(simbolo instanceof SymbolVariable || simbolo instanceof SymbolParameter) {
			Variable var = new Variable(s,simbolo.getScope());
			cb.addQuadruple("MVP",temp,var);
		}else {
			sm.semanticFatalError("[Operando] - no se trata ni de una variable ni de una constante");
		}
		o.setTemporal(temp);
		o.setIntermediateCode(cb.create());

		return o;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	

}
