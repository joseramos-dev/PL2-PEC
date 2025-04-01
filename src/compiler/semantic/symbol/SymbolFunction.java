package compiler.semantic.symbol;

import java.util.ArrayList;

import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

/**
 * Class for SymbolFunction.
 */

// TODO: Student work
//       Include properties to characterize function calls

public class SymbolFunction
    extends SymbolProcedure
{
    String tipoRetorno;
	
      
    /**
     * Constructor for SymbolFunction.
     * @param scope The declaration scope.
     * @param name The symbol name.
     * @param type The symbol type.
     */
    public SymbolFunction (ScopeIF scope, 
                           String name,
                           TypeIF type,
                           ArrayList<SymbolParameter> listaParametros,
                           String tipoRetorno)
    {
        super (scope, name, type, listaParametros);
        this.tipoRetorno = tipoRetorno;
    }


	public String getTipoRetorno() {
		return tipoRetorno;
	}


	public void setTipoRetorno(String tipoRetorno) {
		this.tipoRetorno = tipoRetorno;
	} 
    
    
}
