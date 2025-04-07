package compiler.syntax.nonTerminal;

import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.TemporalFactory;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.ScopeManager;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.SemanticErrorManager;

public class Expresion extends NonTerminal{
	
	private String tipo = "NO_TIPO_ERROR";
	
	public Expresion() {
		super();
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public static Expresion semantico_operadorLogico(
				ScopeManagerIF scopeManager,
				SemanticErrorManager sm,
				Expresion ex1, 
				Expresion ex2,
				String op
			) {
		Expresion ex = new Expresion();
		ex.setTipo("BOOLEANO");
		if(ex1.getTipo() != "BOOLEANO" || ex2.getTipo()!="BOOLEANO") {
			sm.semanticFatalError("[Expresion] - Los tipos de la expresión del operador lógico no son validos");
		}	
		
		return codigoIntermedioOperadores(scopeManager,ex,ex1,ex2,op);
	}
	
	public static Expresion semantico_operadorRelacional(
				ScopeManagerIF scopeManager,
				SemanticErrorManager sm,
				Expresion ex1, 
				Expresion ex2,
				String op
			) {
		Expresion ex = new Expresion();
		ex.setTipo("BOOLEANO");
		if(ex1.getTipo() != "ENTERO" || ex2.getTipo()!="ENTERO") {
			sm.semanticFatalError("[Expresion] - Los tipos de la expresión del operador relacional no son validos");
		}
		return codigoIntermedioOperadores(scopeManager,ex,ex1,ex2,op);
	}
	
	public static Expresion semantico_operadorAritmetico(
				ScopeManagerIF scopeManager,
				SemanticErrorManager sm,
				Expresion ex1, 
				Expresion ex2,
				String op
			) {
		Expresion ex = new Expresion();
		ex.setTipo("ENTERO");
		if(ex1.getTipo() != "ENTERO" || ex2.getTipo()!="ENTERO") {
			sm.semanticFatalError("[Expresion] - Los tipos de la expresión del operador aritmetico no son validos");
		}
		return codigoIntermedioOperadores(scopeManager,ex,ex1,ex2,op);
	}
	
	private static Expresion codigoIntermedioOperadores(
				ScopeManagerIF scopeManager,
				Expresion ex,
				Expresion ex1,
				Expresion ex2,
				String op
			) {
		ScopeIF scope = scopeManager.getCurrentScope();
		TemporalFactory tf = new TemporalFactory(scope);
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		TemporalIF temp1 = ex1.getTemporal();
		TemporalIF temp2 = ex2.getTemporal();
		TemporalIF temp = tf.create();
		cb.addQuadruples(ex1.getIntermediateCode());
		cb.addQuadruples(ex2.getIntermediateCode());
		cb.addQuadruple(op,temp,temp1,temp2);
		ex.setTemporal(temp);
		ex.setIntermediateCode(cb.create());
		return ex;
	}
	
	public static Expresion semantico_expresionAccesoRegistro(
				ScopeManagerIF scopeManager,
				SemanticErrorManager sm,
				ExpresionAccesoRegistro ear
			) {
		Expresion ex = new Expresion();
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scopeManager.getCurrentScope());
		cb.addQuadruples(ear.getIntermediateCode());
		ex.setTipo(ear.getTipo(scopeManager,sm));
		ex.setTemporal(ear.getTemporal());
		ex.setIntermediateCode(cb.create());
		return ex;
	}
	
	public static Expresion semantico_exprFuncion(
				ScopeManagerIF scopeManager,
				ExprFuncion exf
			) {
		Expresion ex = new Expresion();
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scopeManager.getCurrentScope());
		TemporalFactory tf = new TemporalFactory(scopeManager.getCurrentScope());
		cb.addQuadruples(exf.getIntermediateCode());
		ex.setTipo( exf.getTipoRetorno() );
		ex.setTemporal(exf.getTemporal());
		ex.setIntermediateCode(cb.create());
		return ex;
	}
	
	public static Expresion semantico_operando(Operando op) {
		Expresion ex = new Expresion();
		ex.tipo = op.getTipo();
		ex.setTemporal(op.getTemporal());
		ex.setIntermediateCode(op.getIntermediateCode());
		return ex;
	}
	
	public static Expresion semantico_expresion(Expresion ex2) {
		Expresion ex = new Expresion();
		ex.tipo = ex2.getTipo();
		ex.setTemporal(ex2.getTemporal());
		ex.setIntermediateCode(ex2.getIntermediateCode());
		return ex;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
