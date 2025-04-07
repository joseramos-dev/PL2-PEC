package compiler.syntax.nonTerminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import compiler.CompilerContext;
import compiler.intermediate.Temporal;
import compiler.intermediate.Variable;
import compiler.semantic.symbol.SymbolParameter;
import compiler.semantic.symbol.SymbolVariable;
import es.uned.lsi.compiler.code.FinalCodeFactoryIF;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.intermediate.TemporalFactory;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;
import es.uned.lsi.compiler.syntax.SyntaxErrorManager;

/**
 * Abstract Class for Axiom non terminal.
 */
public abstract class Axiom
    extends NonTerminal
{
    /**
     * Constructor for Axiom.
     */
    public Axiom ()
    {
        super (); 
    }
    
    public static void codigoFinal(
    		ScopeManagerIF scopeManager,
    		FinalCodeFactoryIF   finalCodeFactory,
    		SyntaxErrorManager syntaxErrorManager,
    		Axiom ax
    		) throws IOException  {
    	List<ScopeIF> scopes = scopeManager.getAllScopes();
    	for(ScopeIF scope: scopes) {
    		int direccionRA = 4;
    		List<SymbolIF> simbolos = scope.getSymbolTable().getSymbols();
    		for(SymbolIF simbolo:simbolos) {
    			if(simbolo instanceof SymbolParameter) {
    				((SymbolParameter)simbolo).setAddress(direccionRA);
    				direccionRA = direccionRA + simbolo.getType().getSize();
    			}
    		}
    		if(scope.getLevel()!=0) {
    			direccionRA = direccionRA +1;
    		}
    		for(SymbolIF simbolo:simbolos) {
    			if(simbolo instanceof SymbolVariable) {
    				((SymbolVariable)simbolo).setAddress(direccionRA);
    				direccionRA = direccionRA + simbolo.getType().getSize();
    			}
    		}
    		List<TemporalIF> temporales = scope.getTemporalTable().getTemporals();
    		for(TemporalIF t:temporales) {
    			if(t instanceof Temporal) {
    				((Temporal)t).setAddress(direccionRA);
    				direccionRA = direccionRA + ((Temporal)t).getSize();
    			}
    		}
    	}
    	for(ScopeIF scope:scopes) {
    		IntermediateCodeBuilder cb = new IntermediateCodeBuilder(scope);
    		TemporalFactory tf = new TemporalFactory(scope);
    		TemporalIF temp = tf.create();
    		int tamanoRA;
			tamanoRA = scope.getSymbolTable().getSize() + scope.getTemporalTable().getSize() + 4;
			if (scope.getLevel() == 0) {
				cb.addQuadruple("STARTGLOBAL"); // Prepara el R.A. global
				List<SymbolIF> simbolos = scope.getSymbolTable().getSymbols();
				for (SymbolIF simbolo : simbolos) {
					if (simbolo instanceof SymbolVariable) { // Comprobar si el simbolo es una variable
						int direccion = ((SymbolVariable) simbolo).getAddress();
						Variable var = new Variable(simbolo.getName(), simbolo.getScope());
						// introduce la variable en el R.A. GLOBAL inicializadas a 0
						cb.addQuadruple("VARGLOBAL", var, 0);
					}
				}
				cb.addQuadruple("PUNTEROGLOBAL", temp, tamanoRA);
			}
			cb.addQuadruples(ax.getIntermediateCode());
			ax.setIntermediateCode(cb.create());
    	}
    	List intermediateCode = colocarSubprogramasAlFinal(ax.getIntermediateCode ());
		finalCodeFactory.setEnvironment(CompilerContext.getExecutionEnvironment());
  		finalCodeFactory.create(intermediateCode);
		mostrarIntermediateCode(intermediateCode); 		
  		syntaxErrorManager.syntaxInfo ("Parsing process ended.");
    }
    
    private static List<QuadrupleIF> colocarSubprogramasAlFinal(List<QuadrupleIF> intermediateCode) {
    	ArrayList<QuadrupleIF> programa = new ArrayList();
    	ArrayList<QuadrupleIF> subprograma = new ArrayList();
    	boolean mainPrograma = true;
    	for(QuadrupleIF q: intermediateCode) {
    		String operacion = q.getOperation();
    		if(mainPrograma) {
    			if(operacion.equals("ETIQUETA")) {
    				subprograma.add(q);
    				mainPrograma = false;
    			}else {
    				programa.add(q);
    			}
    			
    		}else {
    			if(operacion.equals("FINSUBPROGRAMA")) {
    				subprograma.add(q);
    				mainPrograma = true;
    			}else {
    				subprograma.add(q);
    			}
    		}
    	}
    	programa.addAll(subprograma);
    	return programa;
    	
    }
    
    private static void mostrarIntermediateCode(List<QuadrupleIF> lista) {
    	System.out.println("CÓDIGO INTERMEDIO:");  
    	for(QuadrupleIF q : lista) {
    		System.out.println(q);
    	}
    	System.out.println("FIN CÓDIGO INTERMEDIO: \n\\n\\n"); 
    }
   
    
}
