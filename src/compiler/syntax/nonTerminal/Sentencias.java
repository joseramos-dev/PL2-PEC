package compiler.syntax.nonTerminal;

import java.util.ArrayList;

import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;

public class Sentencias extends NonTerminal {
	
	private ArrayList<String> listaReturns;
	private ArrayList<String> listaReturnsCondicionales;

	public Sentencias() {
		super();
		listaReturns = new ArrayList();
		listaReturnsCondicionales = new ArrayList();
	}
	
	public static Sentencias semantico_1(ScopeManagerIF scopeManager, Sentencia sentencia, Sentencias sentencias) {
		if(sentencia.isReturn()) {
			sentencias.listaReturns.add(sentencia.getTipoReturn());
			sentencias.listaReturnsCondicionales.addAll(sentencia.getListaReturnsIf());
			sentencias.listaReturnsCondicionales.addAll(sentencia.getListaReturnsElse());
			sentencias.listaReturnsCondicionales.addAll(sentencia.getListaReturnsWhile());
		}
		//Código intermedio
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scopeManager.getCurrentScope());
		cb.addQuadruples(sentencia.getIntermediateCode());
		cb.addQuadruples(sentencias.getIntermediateCode());
		sentencias.setIntermediateCode(cb.create());
		return sentencias;
	}
	public static Sentencias semantico_2(ScopeManagerIF scopeManager, Sentencia s) {
		Sentencias sentencias = new Sentencias();
		if(s.isReturn()) {
			sentencias.listaReturns.add(s.getTipoReturn());
			sentencias.listaReturnsCondicionales.addAll(s.getListaReturnsIf());
			sentencias.listaReturnsCondicionales.addAll(s.getListaReturnsElse());
			sentencias.listaReturnsCondicionales.addAll(s.getListaReturnsWhile());
		}
		//Código intermedio
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scopeManager.getCurrentScope());
		cb.addQuadruples(s.getIntermediateCode());
		sentencias.setIntermediateCode(cb.create());
		return sentencias;
	}

	public ArrayList<String> getListaReturns() {
		return listaReturns;
	}

	public void setListaReturns(ArrayList<String> listaReturns) {
		this.listaReturns = listaReturns;
	}
	
	public boolean tieneReturn() {
		return listaReturns.size() > 0;
	}

	public ArrayList<String> getListaReturnsCondicionales() {
		return listaReturnsCondicionales;
	}

	public void setListaReturnsCondicionales(ArrayList<String> listaReturnsCondicionales) {
		this.listaReturnsCondicionales = listaReturnsCondicionales;
	}
	
	
	
}
