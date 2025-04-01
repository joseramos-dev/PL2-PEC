package compiler.syntax.nonTerminal;

import java.util.ArrayList;


//LISTA PARAMETROS CONTIENE IDS DE VARIABLES/CONSTANTES/...
public class ListaParametrosInvocacion extends NonTerminal{
	
	private ArrayList<String> listaParametros;

	public ListaParametrosInvocacion() {
		super();
		listaParametros = new ArrayList();
	}
	
	public static ListaParametrosInvocacion semantico_1(String tipo) {
		ListaParametrosInvocacion lpi = new ListaParametrosInvocacion();
		lpi.listaParametros.add(tipo);
		return lpi;
	}
	public static ListaParametrosInvocacion semantico_2(String tipo, ListaParametrosInvocacion lpi) {
		lpi.listaParametros.add(tipo);
		return lpi;
	}

	public ArrayList<String> getListaParametros() {
		return listaParametros;
	}

	public void setListaParametros(ArrayList<String> listaParametros) {
		this.listaParametros = listaParametros;
	}
	
	public int getNumeroParametros() {
		return listaParametros.size();
	}
	
	
}
