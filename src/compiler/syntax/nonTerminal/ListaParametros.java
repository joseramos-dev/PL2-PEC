package compiler.syntax.nonTerminal;

import java.util.ArrayList;

public class ListaParametros extends NonTerminal{
	
	ArrayList<String> listaIds;
	
	public ListaParametros() {
		super();
		listaIds = new ArrayList();
	}

	public ArrayList<String> getListaIds() {
		return listaIds;
	}

	public void addId(String id) {
		listaIds.add(id);
	}
	
	public static ListaParametros semantico_1(
			ListaParametros lp,
			String id
			) {
		lp.addId(id);
		return lp;
	}
	
	public static ListaParametros semantico_2(
			String id
			) {
		ListaParametros lp = new ListaParametros();
		lp.addId(id);
		return lp;
	}
	
	
}
