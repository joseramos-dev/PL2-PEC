package compiler.syntax.nonTerminal;

import java.util.ArrayList;

public class DeclParametro extends NonTerminal {

	ArrayList<String> listaIds;
	String tipo;
	Boolean esReferencia; //VA PRECEDIDO DE UN "OUT"
	
	public DeclParametro() {
		super();
	}

	public ArrayList<String> getListaIds() {
		return listaIds;
	}

	public void setListaIds(ArrayList<String> listaIds) {
		this.listaIds = listaIds;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Boolean getEsReferencia() {
		return esReferencia;
	}

	public void setEsReferencia(Boolean esReferencia) {
		this.esReferencia = esReferencia;
	}
	
	static public DeclParametro semantico_copia(
			ListaParametros lp,
			Tipo tp
			) {
		DeclParametro dp = new DeclParametro();
		dp.setListaIds(lp.getListaIds());
		dp.setTipo(tp.getTipo());
		dp.setEsReferencia(false);
		return dp;
	}
	static public DeclParametro semantico_referencia(
			ListaParametros lp,
			Tipo tp
			) {
		DeclParametro dp = new DeclParametro();
		dp.setListaIds(lp.getListaIds());
		dp.setTipo(tp.getTipo());
		dp.setEsReferencia(true);
		return dp;
	}
	
	
	
	
	
}
