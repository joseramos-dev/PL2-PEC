package compiler.code;

import java.util.Arrays;
import java.util.List;

import compiler.intermediate.Label;
import compiler.intermediate.Temporal;
import compiler.intermediate.Value;
import compiler.intermediate.Variable;
import compiler.semantic.type.TypeSimple;

import es.uned.lsi.compiler.code.ExecutionEnvironmentIF;
import es.uned.lsi.compiler.code.MemoryDescriptorIF;
import es.uned.lsi.compiler.code.RegisterDescriptorIF;
import es.uned.lsi.compiler.intermediate.OperandIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;

/**
 * Class for the ENS2001 Execution environment.
 */

public class ExecutionEnvironmentEns2001 
    implements ExecutionEnvironmentIF
{    
    private final static int      MAX_ADDRESS = 65535; 
    private final static String[] REGISTERS   = {
       ".PC", ".SP", ".SR", ".IX", ".IY", ".A", 
       ".R0", ".R1", ".R2", ".R3", ".R4", 
       ".R5", ".R6", ".R7", ".R8", ".R9"
    };
    
    private RegisterDescriptorIF registerDescriptor;
    private MemoryDescriptorIF   memoryDescriptor;
    
    /**
     * Constructor for ENS2001Environment.
     */
    public ExecutionEnvironmentEns2001 ()
    {       
        super ();
    }
    
    /**
	 * Returns the size of the type within the architecture.
	 * 
	 * @return the size of the type within the architecture.
	 */
	@Override
	public final int getTypeSize(TypeSimple type) {
		return 1;
	}

	/**
	 * Returns the registers.
	 * 
	 * @return the registers.
	 */
	@Override
	public final List<String> getRegisters() {
		return Arrays.asList(REGISTERS);
	}

	/**
	 * Returns the memory size.
	 * 
	 * @return the memory size.
	 */
	@Override
	public final int getMemorySize() {
		return MAX_ADDRESS;
	}

	/**
	 * Returns the registerDescriptor.
	 * 
	 * @return Returns the registerDescriptor.
	 */
	@Override
	public final RegisterDescriptorIF getRegisterDescriptor() {
		return registerDescriptor;
	}

	/**
	 * Returns the memoryDescriptor.
	 * 
	 * @return Returns the memoryDescriptor.
	 */
	@Override
	public final MemoryDescriptorIF getMemoryDescriptor() {
		return memoryDescriptor;
	}

	/**
	 * Translate a quadruple into a set of final code instructions.
	 * 
	 * @param cuadruple The quadruple to be translated.
	 * @return a quadruple into a set of final code instructions.
	 */
	@Override
	public final String translate(QuadrupleIF quadruple) {
		StringBuffer b = new StringBuffer();
		String o1 = operacion(quadruple.getFirstOperand());
		String o2 = operacion(quadruple.getSecondOperand());
		String r = operacion(quadruple.getResult());
		b.append(";" + quadruple.toString() + "\n");

		switch (quadruple.getOperation()) {
		case "SUB": // Restar (-)
			b.append("SUB " + o1 + ", " + o2+"\n" );
			b.append("MOVE .A, " + r+"\n");
			break;
		case "MUL": // Multiplicación
			b.append("MUL " + o1 + ", " + o2+"\n");
			b.append("MOVE .A, " + r+"\n");
			break;
		case "NQ": // Distinto (!=)
			b.append("CMP " + o1 + ", " + o2+"\n");
			b.append("BNZ " + r+"\n");
			break;
		case "GR": // Mayor (>)
			b.append("CMP " + o1 + ", " + o2+"\n");
			b.append("BG " + r+"\n");
			break;
		case "AND": // Operación lógica AND
			b.append("AND " + o1 + ", " + o2+"\n");
			b.append("MOVE .A, " + r+"\n");
			break;
		case "STARTGLOBAL":
			b.append("MOVE .SP, .IX \n");
			b.append("PUSH #-1 \n");
			b.append("PUSH .IX \n");
			b.append("PUSH .SR \n");
			b.append("PUSH .IX \n");
			break;
		case "VARGLOBAL":
			b.append("PUSH "+o1+" \n");
			break;
		case "PUNTEROGLOBAL":
			b.append("SUB .IX ,"+o1+"\n");
			b.append("MOVE .A, .SP \n");
			break;
		case "WRITESTRING":
			b.append("WRSTR /" + o1 +"\n");
			break;
		case "WRITEINT":
			b.append("WRINT " + r +"\n");
			break;
		case "BR": // Salto incondicional
			b.append("BR /" + r+"\n");
			break;
		case "BRF": // Salto si es falso
			b.append("CMP " + r +"\n");
			b.append("BP /" + o1+"\n");
			break;
		case "INL": // Insertar etiqueta
			b.append(r + ": NOP \n");
			break;
		case "MV":
            b.append("MOVE " + o1 + ", "+r+"\n");
            break;
        case "MVP":
            b.append("MOVE " + o1 + ", "+ r +" \n");
            break;
        case "MVA":
        	Variable v = (Variable) quadruple.getFirstOperand();
    		b.append("SUB .IX, #"+v.getAddress()+"\n");
    		b.append("MOVE .A, "+r+"\n");
            break;
		case "STP": // Almacenamiento en puntero
			b.append("MOVE " + r + ", .R1\n");
			b.append("MOVE " + o1 + ", [.R1]\n");
			break;
		case "HALT": // Detener ejecución
			b.append("HALT\n");
			break;
		case "CADENA":
            b.append(o1 + ": DATA \"" + r + "\" \n");
            break;
		default:
			b.append("; ERROR: Operación no reconocida: " + quadruple.getOperation() );
		}

		return b.toString();
	}

	private String operacion(OperandIF o) {
		if (o instanceof Variable) {
			return "#-"+((Variable)o).getAddress() + "[.IX]";
		}
		if (o instanceof Value) {
			return "#" + ((Value) o).getValue();
		}
		if (o instanceof Temporal) {
			return "#-" + ((Temporal) o).getAddress() + "[.IX]";
		}
		if (o instanceof Label) {
			return ((Label) o).getName();
		}
		return null;
	}
	
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
