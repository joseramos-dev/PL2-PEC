package compiler.syntax.nonTerminal;

import java.util.ArrayList;

import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.semantic.ScopeIF;

public class Sentencia extends NonTerminal {

	private boolean esReturn = false;
	private String tipoReturn = "";
	ArrayList<String> listaReturnsIf;
	ArrayList<String> listaReturnsElse;
	ArrayList<String> listaReturnsWhile;

	public Sentencia() {
		super();
		listaReturnsIf = new ArrayList();
		listaReturnsElse = new ArrayList();
		listaReturnsWhile = new ArrayList();
	}

	public static Sentencia semantico_putline(SentenciaPutLine sp, ScopeIF scope) {
		Sentencia sentencia = new Sentencia();
		//codigo intermedio
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		cb.addQuadruples(sp.getIntermediateCode());
		sentencia.setIntermediateCode(cb.create());
		return sentencia;
	}

	public static Sentencia semantico_asignacion(SentenciaAsignacion sa, ScopeIF scope) {
		Sentencia sentencia = new Sentencia();
		//codigo intermedio
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		cb.addQuadruples(sa.getIntermediateCode());
		sentencia.setIntermediateCode(cb.create());
		return sentencia;
	}

	public static Sentencia semantico_if(SentenciaIF sif, ScopeIF scope) {
		Sentencia sentencia = new Sentencia();
		sentencia.setListaReturnsIf(sif.getListaReturnsIf());
		sentencia.setListaReturnsElse(sif.getListaReturnsElse());
		//codigo intermedio
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		cb.addQuadruples(sif.getIntermediateCode());
		sentencia.setIntermediateCode(cb.create());
		return sentencia;
	}

	public static Sentencia semantico_while(SentenciaWhile sw, ScopeIF scope) {
		Sentencia sentencia = new Sentencia();
		sentencia.listaReturnsWhile.addAll(sw.getListaReturns());
		//codigo intermedio
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		cb.addQuadruples(sw.getIntermediateCode());
		sentencia.setIntermediateCode(cb.create());
		return sentencia;
	}

	public static Sentencia semantico_return(SentenciaReturn sr, ScopeIF scope) {
		Sentencia sentencia = new Sentencia();
		sentencia.setReturn(true);
		sentencia.setTipoReturn(sr.getTipo());
		//codigo intermedio
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		cb.addQuadruples(sr.getIntermediateCode());
		sentencia.setIntermediateCode(cb.create());
		return sentencia;

	}

	public static Sentencia semantico_procedimiento(SentenciaProcedimiento sp, ScopeIF scope) {
		Sentencia sentencia = new Sentencia();
		//codigo intermedio
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		cb.addQuadruples(sp.getIntermediateCode());
		sentencia.setIntermediateCode(cb.create());
		return sentencia;
	}

	public boolean isReturn() {
		return esReturn;
	}

	public void setReturn(boolean esReturn) {
		this.esReturn = esReturn;
	}

	public String getTipoReturn() {
		return tipoReturn;
	}

	public void setTipoReturn(String tipoReturn) {
		this.tipoReturn = tipoReturn;
	}

	public ArrayList<String> getListaReturnsIf() {
		return listaReturnsIf;
	}

	public void setListaReturnsIf(ArrayList<String> listaReturnsIf) {
		this.listaReturnsIf = listaReturnsIf;
	}

	public ArrayList<String> getListaReturnsElse() {
		return listaReturnsElse;
	}

	public void setListaReturnsElse(ArrayList<String> listaReturnsElse) {
		this.listaReturnsElse = listaReturnsElse;
	}

	public ArrayList<String> getListaReturnsWhile() {
		return listaReturnsWhile;
	}

	public void setListaReturnsWhile(ArrayList<String> listaReturnsWhile) {
		this.listaReturnsWhile = listaReturnsWhile;
	}
	
	
	
	
}
