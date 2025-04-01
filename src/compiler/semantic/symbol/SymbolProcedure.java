package compiler.semantic.symbol;

import java.util.ArrayList;

import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolBase;
import es.uned.lsi.compiler.semantic.type.TypeIF;

/**
 * Class for SymbolProcedure.
 */

// TODO: Student work
//       Include properties to characterize procedure calls

public class SymbolProcedure
    extends SymbolBase
{
	ArrayList<SymbolParameter> listaParametros;
   
    /**
     * Constructor for SymbolProcedure.
     * @param scope The declaration scope.
     * @param name The symbol name.
     * @param type The symbol type.
     */
    public SymbolProcedure (ScopeIF scope, 
                            String name,
                            TypeIF type,
                            ArrayList<SymbolParameter> listaParametros)
    {
        super (scope, name, type);
        this.listaParametros = listaParametros;
    }

	public ArrayList<SymbolParameter> getListaParametros() {
		return listaParametros;
	}

	public void setListaParametros(ArrayList<SymbolParameter> listaParametros) {
		this.listaParametros = listaParametros;
	} 
    
    
}
