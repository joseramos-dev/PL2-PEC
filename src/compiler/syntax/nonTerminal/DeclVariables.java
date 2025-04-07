package compiler.syntax.nonTerminal;

import java.util.ArrayList;

public class DeclVariables extends NonTerminal{
	
	private ArrayList<DeclVariable> listaDeclVariable;

	public DeclVariables() {
		super();
		this.listaDeclVariable = new ArrayList();
	}
	
	public static DeclVariables semantico_1(DeclVariable d) {
		DeclVariables dv = new DeclVariables();
		dv.listaDeclVariable.add(d);
		return dv;
	}
	public static DeclVariables semantico_2(DeclVariables dv, DeclVariable d) {
		dv.listaDeclVariable.add(d);
		return dv;
	}
	
	
}
