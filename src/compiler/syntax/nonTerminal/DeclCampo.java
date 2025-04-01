package compiler.syntax.nonTerminal;

import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.SemanticErrorManager;

public class DeclCampo extends NonTerminal{
	
	private String nombre;
	private String tipo;
	private int size;
	
	public DeclCampo() {
		super();
	}
	
	public static DeclCampo semantico(
				ScopeManagerIF scopeManager, 
				SemanticErrorManager  sm, 
				String id, 
				String t
			) {
		if(!scopeManager.containsType(t)) {
			sm.semanticFatalError("[DeclCampo] - El tipo "+t+" del campo "+id+" no existe");
		}
		DeclCampo dc = new DeclCampo();
		dc.tipo = t;
		dc.nombre = id;
		dc.size = scopeManager.searchType(t).getSize();
		return dc;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int _size) {
		this.size = _size;
	}
	
	
	

}
