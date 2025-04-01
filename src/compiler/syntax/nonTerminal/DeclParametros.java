package compiler.syntax.nonTerminal;

import java.util.ArrayList;

public class DeclParametros extends NonTerminal{

	private ArrayList<DeclParametro> listaDeclParametro;
	
	public DeclParametros() {
		super();
		listaDeclParametro = new ArrayList();
	}

	public ArrayList<DeclParametro> getListaDeclParametro() {
		return listaDeclParametro;
	}
	
	public ArrayList<AuxParameter> getListaTipoParametros(){
		ArrayList<AuxParameter> listaTipos = new ArrayList();
		for( DeclParametro parametro : listaDeclParametro) {
			for( String s : parametro.listaIds) {
				listaTipos.add(new AuxParameter(s,parametro.getTipo(), parametro.getEsReferencia()));
			}
		}
		return listaTipos;
	}

	public void addDeclParametro(DeclParametro dp) {
		listaDeclParametro.add(dp);
	}
	
	static public DeclParametros semantico_1(
			DeclParametros dps,
			DeclParametro dp
			) {
		dps.addDeclParametro(dp);
		return dps;
	}
	static public DeclParametros semantico_2(
		DeclParametro dp
		) {
	DeclParametros dps = new DeclParametros();
	dps.addDeclParametro(dp);
	return dps;
	}
	
	// AUX CLASS PARA TRABAJAR CON PARAMETROS
	public class AuxParameter {
		private String id;
		private String tipo;
		private boolean out;
		
		public AuxParameter(String id, String tipo, boolean out) {
			this.id = id;
			this.tipo = tipo;
			this.out = out;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public boolean isOut() {
			return out;
		}
		public void setOut(boolean out) {
			this.out = out;
		}
		public String getTipo() {
			return tipo;
		}
		public void setTipo(String tipo) {
			this.tipo = tipo;
		}
		
		
		
	}
	
	
	
	
	
	
	
}
