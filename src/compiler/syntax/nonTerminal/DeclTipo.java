package compiler.syntax.nonTerminal;

import java.util.LinkedHashMap;

import compiler.semantic.type.TypeRecord;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.SemanticErrorManager;
import es.uned.lsi.compiler.semantic.type.TypeTableIF;

public class DeclTipo extends NonTerminal {

	DeclCampos declCampos;
	
	public DeclTipo() {
		super();
	}
	
	public static DeclTipo semantico(
				ScopeManagerIF scopeManager,
				SemanticErrorManager sm,
				String id, 
				DeclCampos dc
			) {
		DeclTipo dt = new DeclTipo();
		dt.declCampos = dc;
		ScopeIF scope = scopeManager.getCurrentScope();
		if(scopeManager.containsType(id)) {
			sm.semanticFatalError("[DeclTipo] - Error con el identificador usado para denominar al record "+id+" ya esta en uso");
		}else {
			TypeTableIF tablaTipos = scope.getTypeTable();
			TypeRecord tr = new TypeRecord(scope,id);
			tr.setCampos(dt.declCampos.getCampos());		
			tablaTipos.addType(id, tr);
			System.out.println("id de tipo : "+id);
		}
		return dt;
	}
	
	
}
