package compiler.syntax.nonTerminal;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import es.uned.lsi.compiler.semantic.SemanticErrorManager;

public class DeclCampos extends NonTerminal {
	
	LinkedHashMap<String,DeclCampo> campos; // <nombre, tipo>

	public DeclCampos() {
		super();
		campos = new LinkedHashMap();
	}
	
	
	
	public LinkedHashMap<String,DeclCampo> getCampos() {
		return campos;
	}



	public void setCampos(LinkedHashMap<String,DeclCampo> campos) {
		this.campos = campos;
	}



	public static DeclCampos semantico_1(DeclCampo campo) {
		DeclCampos dc = new DeclCampos();
		dc.campos.put(campo.getNombre(), campo);
		return dc;
	}
	public static DeclCampos semantico_2(SemanticErrorManager sm, DeclCampos dc, DeclCampo campo) {
		if(dc.campos.containsKey(campo.getNombre())){
			sm.semanticFatalError("[DeclCampos] - El campo "+campo.getNombre()+" ya esta declarado");
		}
		dc.campos.put(campo.getNombre(), campo);
		return dc;
	}
	
	public int getDesplazamientoByIndex(int i) throws Exception {
		if(i>campos.size() || i<0) {
			throw new Exception("[DeclCampos] - getDesplazamientoByIndex: el indice introducido no es valido");
		}
		int result = 0;
		for(int u=0; u<campos.size(); u++) {
			result += campos.get(u).getSize();
		}
		return result;
	}
	public int getDesplazamientoByName(String nombreCampo) throws Exception {
		if(!campos.containsKey(nombreCampo)) {
			throw new Exception("[DeclCampos] - getDesplazamientoByName: nombre de campo no valido");
		}
		int result = 0;
		Iterator<DeclCampo> it = campos.values().iterator();
		DeclCampo campo = it.next();
		while(campo.getNombre()!=nombreCampo) {
			result += campo.getSize();
			if(it.hasNext()) 
				it.next();
		};
		return result;
	}
}
