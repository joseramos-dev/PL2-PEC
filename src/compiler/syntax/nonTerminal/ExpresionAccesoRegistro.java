package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import compiler.intermediate.Variable;
import compiler.semantic.symbol.SymbolVariable;
import compiler.semantic.type.TypeRecord;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.TemporalFactory;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.SemanticErrorManager;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolTableIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;
import es.uned.lsi.compiler.semantic.type.TypeTableIF;

public class ExpresionAccesoRegistro extends NonTerminal {
	
	private String tipoExpresion;
	private SymbolIF simbolo;
	private boolean comprobado = false;
	private ArrayList<String> listaDeclaracion;
	private int desplazamiento = 0;

	public ExpresionAccesoRegistro() {
		super();
		listaDeclaracion = new ArrayList();
	}
	
	//busca en tabla simbolos si existe id1, luego busca en typetable el tipo y si el campo (id2) existe
	public static ExpresionAccesoRegistro semantico_1(
				ScopeManagerIF scopeManager,
				SemanticErrorManager sm,
				String id1,
				String id2
			) {
		ExpresionAccesoRegistro ear = new ExpresionAccesoRegistro();
		ear.addListaDeclaracion(id2);
		ear.addListaDeclaracion(id1);
		return ear;
	}
	
	public static ExpresionAccesoRegistro semantico_2(
			ScopeManagerIF scopeManager,
			SemanticErrorManager sm,
			String id,
			ExpresionAccesoRegistro ear
		) {
		ear.addListaDeclaracion(id);
		return ear;
	}
	
	private void addListaDeclaracion(String s) {
		listaDeclaracion.addFirst(s);
	}
	
	
	public String getTipo(
				ScopeManagerIF scopeManager, 
				SemanticErrorManager sm
			) {
		if (!comprobado) {
			realizarComprobacionSemantica(scopeManager, sm);
			comprobado = true;
		}
		return tipoExpresion;
	}

	private void realizarComprobacionSemantica(
				ScopeManagerIF scopeManager, 
				SemanticErrorManager sm
			) {
		ScopeIF scope = scopeManager.getCurrentScope();
		TypeTableIF typeTable = scope.getTypeTable();
		// comprobamos que existe el simbolo
		String nombreSimbolo = this.listaDeclaracion.get(0);
		if (scopeManager.containsSymbol(nombreSimbolo)) {
			simbolo = scopeManager.searchSymbol(nombreSimbolo);
			if (!(simbolo.getType() instanceof TypeRecord)) {
				sm.semanticFatalError(
						"[ExpresionAccesoRegistro] - El simbolo " + nombreSimbolo + " no es de tipo Record");
			}
			TypeIF tipo = simbolo.getType();
			LinkedHashMap<String, DeclCampo> campos = ((TypeRecord) tipo).getCampos();
			// iteramos en la lista de strings de la declaracion de la expresion
			for (int i = 1; i < listaDeclaracion.size(); i++) {
				String nombreCampo = listaDeclaracion.get(i);
				// comprobamos que el tipo contenga un campo con el nombre de la expresion
				// actual
				if (campos != null && campos.containsKey(nombreCampo)) {
					// añadimos el desplazamiento del campo actual
					desplazamiento += ((TypeRecord) tipo).getPosCampo(nombreCampo);
					// modificamos los valores de campos para la siguiente iteracion
					DeclCampo campo = campos.get(nombreCampo);
					tipo = scopeManager.searchType(campo.getTipo());
					if (tipo instanceof TypeRecord) {
						campos = ((TypeRecord) tipo).getCampos();
					} else {
						campos = null;
					}
				} else {
					sm.semanticFatalError("[ExpresionAccesoRegistro] - El campo " + nombreCampo + " no existe");
				}
			}
			this.tipoExpresion = tipo.getName();

		} else {
			sm.semanticFatalError("[ExpresionAccesoRegistro] - No existe simbolo con el nombre " + nombreSimbolo);
		}
		// CÓDIGO INTERMEDIO
		TemporalFactory tf = new TemporalFactory(scope);
		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
		TemporalIF temp = tf.create();
		TemporalIF temp1 = tf.create();
		TemporalIF temp2 = tf.create();
		TemporalIF temp3 = tf.create();
		Variable var = new Variable(nombreSimbolo, simbolo.getScope());
		cb.addQuadruple("MVA", temp1, var);
		cb.addQuadruple("MV", temp2, desplazamiento);
		cb.addQuadruple("ADD", temp3, temp1, temp2);
		cb.addQuadruple("MVP", temp, temp3);
		setTemporal(temp);
		setIntermediateCode(cb.create());
	}


}
