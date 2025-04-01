package compiler.syntax.nonTerminal;

import java.util.ArrayList;

public class DeclConstantes extends NonTerminal{
	
	private ArrayList<Constante> listaConstantes;

	public DeclConstantes() {
		super();
		listaConstantes = new ArrayList();
	}
	
	public void addConstante(Constante c) {
		listaConstantes.add(c);
	}
	public ArrayList<Constante> getListaConstantes() {
		return listaConstantes;
	}
	
}
