package compiler.semantic.type;

import java.util.ArrayList;

import compiler.syntax.nonTerminal.DeclParametros.AuxParameter;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeBase;

/**
 * Class for TypeProcedure.
 */

// TODO: Student work
//       Include properties to characterize procedure declarations

public class TypeProcedure
    extends TypeBase
{   
	
	private ArrayList<AuxParameter> listaParametros;
	
   /**
     * Constructor for TypeProcedure.
     * @param scope The declaration scope.
     */
    public TypeProcedure (ScopeIF scope)
    {
        super (scope);
    }

    /**
     * Constructor for TypeProcedure.
     * @param scope The declaration scope
     * @param name The name of the procedure.
     */
    public TypeProcedure (ScopeIF scope, String name, ArrayList<AuxParameter> listaParametros)
    {
        super (scope, name);
        this.listaParametros = listaParametros;
    }
    
    /**
     * Returns the size of the type.
     * @return the size of the type.
     */
    @Override
    public int getSize ()
    {
        // TODO: Student work
        return 1;
    }

	public ArrayList<AuxParameter> getListaParametros() {
		return listaParametros;
	}

	public void setListaParametros(ArrayList<AuxParameter> listaParametros) {
		this.listaParametros = listaParametros;
	}
    
    
}
