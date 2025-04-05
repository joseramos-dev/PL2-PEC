package compiler.syntax.nonTerminal;

import java.util.ArrayList;

import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.TemporalFactory;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.semantic.ScopeIF;


//LISTA PARAMETROS CONTIENE IDS DE VARIABLES/CONSTANTES/...
public class ListaParametrosInvocacion extends NonTerminal{
	
	private ArrayList<String> listaParametros;

	public ListaParametrosInvocacion() {
		super();
		listaParametros = new ArrayList();
	}
	
	public static ListaParametrosInvocacion semantico_1(
				ScopeIF scope,
				Expresion e
			) {
		ListaParametrosInvocacion lpi = new ListaParametrosInvocacion();
		lpi.listaParametros.add(e.getTipo());
		return codigoIntermedio(scope,lpi,e);
	}
	public static ListaParametrosInvocacion semantico_2(
			ScopeIF scope,
			Expresion e, 
			ListaParametrosInvocacion lpi
			) {
		lpi.listaParametros.add(e.getTipo());
		return codigoIntermedio(scope,lpi,e);
	}
	
	private static ListaParametrosInvocacion codigoIntermedio(
				ScopeIF scope,
				ListaParametrosInvocacion lpi,
				Expresion e
			) {
		TemporalFactory tf = new TemporalFactory(scope);
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		TemporalIF temp = tf.create();
		cb.addQuadruples(lpi.getIntermediateCode());
		cb.addQuadruples(e.getIntermediateCode());
		cb.addQuadruple("PARAM",temp);
		lpi.setIntermediateCode(cb.create());		
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
