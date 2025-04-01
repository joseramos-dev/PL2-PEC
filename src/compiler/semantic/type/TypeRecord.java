package compiler.semantic.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import compiler.syntax.nonTerminal.DeclCampo;
import compiler.syntax.nonTerminal.DeclCampos;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeBase;

/**
 * Class for TypeRecord.
 */

// TODO: Student work
//       Include properties to characterize records

public class TypeRecord
    extends TypeBase
{   
       
	private LinkedHashMap<String, DeclCampo> campos;
	private int size = 0;
	
    /**
     * Constructor for TypeRecord.
     * @param scope The declaration scope.
     */
    public TypeRecord (ScopeIF scope)
    {
        super (scope);
        //usaremos linkedhashmap para que mantenga orden inserccion
        campos = new LinkedHashMap<String,DeclCampo>();
    }

    /**
     * Constructor for TypeRecord.
     * @param scope The declaration scope.
     * @param name The name of the type.
     */
    public TypeRecord (ScopeIF scope, String name)
    {   
        super (scope, name);
    }
   
    /**
     * Constructor for TypeRecord.
     * @param record The record to copy.
     */
    public TypeRecord (TypeRecord record)
    {
        super (record.getScope (), record.getName ());
    } 
    
    /**
     * Returns the size of the type.
     * @return the size of the type.
     */
    @Override
    public int getSize ()
    {
        return size;
    }

	public LinkedHashMap<String,DeclCampo> getCampos() {
		return campos;
	}

	public void setCampos(LinkedHashMap<String,DeclCampo> campos) {
		this.campos = campos;
		int sumaSizes = 0;
		for(DeclCampo campo : campos.values()) {
			sumaSizes += campo.getSize();
		}
		size = sumaSizes;
	}
	
	public int getPosCampo(String idCampo) {
		int i=0;
		for(String s: campos.keySet()) {
			if(idCampo == s) {
				return i;
			}
			i++;
		}
		return -1;
	}
    
    
}
