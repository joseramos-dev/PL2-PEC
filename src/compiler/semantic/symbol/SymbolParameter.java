package compiler.semantic.symbol;

import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolBase;
import es.uned.lsi.compiler.semantic.type.TypeIF;

/**
 * Class for SymbolVariable.
 */

// TODO: Student work
//       Include properties to characterize parameters

public class SymbolParameter
    extends SymbolBase
{  
	
	private boolean referencia;
	private int address ;
   
    /**
     * Constructor for SymbolParameter.
     * @param scope The declaration scope.
     * @param name The symbol name.
     * @param type The symbol type.
     */
    public SymbolParameter (ScopeIF scope, 
                           String name,
                           TypeIF type,
                           boolean referencia)
    {
        super (scope, name, type);
        this.referencia = referencia;
    }

	public boolean isReferencia() {
		return referencia;
	}

	public void setReferencia(boolean referencia) {
		this.referencia = referencia;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}
    
	
    
}
