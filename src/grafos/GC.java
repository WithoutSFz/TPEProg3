package grafos;

import java.util.ArrayList;

public class GC<T> extends Grafo<T> {
	public GC() {
		super();
	}

	@Override
	public void addA(T n1, T n2) {
		if(this.getInicio()==null) {		
			Vertice<T> vertice1 =new Vertice<T>(n1);	
			Vertice<T> vertice2 =new Vertice<T>(n2);
			vertice2.addArista(vertice1);
			vertice2.addArista(vertice2);
			
			vertice1.addArista(vertice2);
			vertice1.addArista(vertice1);
			this.migrafo.add(vertice2);
			this.migrafo.add(vertice1);
			this.inicio=vertice1;
								
		}
		else
			this.add(n1, n2);				
			
	}
	public void addN(T n) {
		Vertice<T> vertice =new Vertice<T>(n);
		if(this.getInicio()==null) {
			vertice.addArista(vertice);
			this.migrafo.add(vertice);
			this.inicio=vertice;
			
		}
		else {
			Vertice<T> v = this.migrafo.search(vertice);
			if(v==null) {
				this.inicio.addArista(vertice);
				this.inicio.addAllAristas(vertice);
				this.migrafo.add(vertice);
			}
		}
		
	}
	private void add(T n1, T n2) {
		Vertice<T> vertice1 =new Vertice<T>(n1);	
		Vertice<T> vertice2 =new Vertice<T>(n2);
		
		Vertice<T> v1 = this.migrafo.search(vertice1);
		Vertice<T> v2 = this.migrafo.search(vertice2);
		if(v1==null) {
			
			
			if(v2==null) {
				this.inicio.addArista(vertice2);
				this.inicio.addArista(vertice1);
				
				this.inicio.addAllAristas(vertice2);
				this.inicio.addAllAristas(vertice1);
				this.migrafo.add(vertice2);

			}
			else { 
				this.inicio.addArista(vertice1);
				this.inicio.addAllAristas(vertice1);
			}
			
			this.migrafo.add(vertice1);	
			
		}
		else 
			if(v2==null) {
				this.add(n2, n1);
				/*es el caso inverso  a v1=null y v2 existe 
				por lo que llamo add con los parametros invertido*/
			}			
			
		
	}
	public ArrayList<T> sumaDeVerticesBackTracking(int meta, ValorVertice<T> valor){
		ArrayList<T> resultado =new ArrayList<T>();
		ArrayList<T> r_parcial =new ArrayList<T>();
		ArrayList<Vertice<T>> vertices=this.inicio.getAristas();
		int valorv;
		if(meta!=0) {
			for(Vertice<T> aux : vertices) {
				valorv=valor.toInt(aux);
				if(valorv<=meta) {
					r_parcial= this.sumaDeVerticesBackTracking(meta-valorv, valor);
					r_parcial.add(aux.getE());
					if(resultado.isEmpty()||resultado.size()>r_parcial.size()) 
						resultado=r_parcial;
					
				}
			}
		}
		return resultado;
	}

	public ArrayList<T> sumaDeVerticesGreedy(int meta, ValorVertice<T> valor){
		ArrayList<T> resultado =new ArrayList<T>();
		T r_parcial = null;
		ArrayList<Vertice<T>> vertices=this.inicio.getAristas();
		int valorv=0;
		for(Vertice<T> aux : vertices) {
			if(valor.toInt(aux)>valorv&&(meta>=valor.toInt(aux))){
				valorv=valor.toInt(aux);
				r_parcial=aux.getE();
				
			}
		}
		if(valorv!=0) {	
			resultado.add(r_parcial);
			resultado.addAll(this.sumaDeVerticesGreedy(meta-valorv, valor));
		}
		return resultado;
		
	}
}
