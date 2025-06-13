package grafos;

import java.util.ArrayList;

import hash.*;

public abstract class Grafo<T> {
	protected HCrecimiento<Vertice<T>> migrafo;
	protected Vertice<T> inicio=null;
	public Grafo() {
		migrafo=new HCrecimiento<Vertice<T>>(0.7,12);
	}
	public Vertice<T> getInicio() {
		return inicio;
	}
	public abstract void addA(T n1,T n2); 

	public void view() {
		this.migrafo.view();
	}
	public T getNodo(T elem) {
		return this.migrafo.search(new Vertice<T>(elem)).getE();
	}
	public ArrayList<Vertice<T>> camino(Vertice<T> a, Vertice<T> b,ArrayList<Vertice<T>> visitados){
		
		
		ArrayList<Vertice<T>> resultado= new ArrayList<Vertice<T>>();
		resultado.add(a);// si devuelve una lista con un unico elemento y es el inicio del recorrido; no hay camino
		if(this.migrafo.search(a)!=null&&this.migrafo.search(b)!=null) {
			
			
			visitados.add(this.migrafo.search(a));
			Vertice<T> temp_a =this.migrafo.search(a);
			Vertice<T> temp_b=this.migrafo.search(b);
			
			
			if(!temp_a.conexion(temp_b)) {
				
				ArrayList<Vertice<T>> vertices_de_a = temp_a.getAristas();
				
				if(!vertices_de_a.isEmpty()) {
					for(Vertice<T> aux :  vertices_de_a) {
							if(aux.conexion(temp_b)) {
								resultado.add(aux);
								resultado.add(temp_b);
								return resultado;
							}
					}
					for(Vertice<T> aux :  vertices_de_a) {
						if(!visitados.contains(aux)) {
							visitados.add(aux);
							ArrayList<Vertice<T>> recu=camino(aux,b,visitados);
							if(recu.size()!=1) {
								resultado.addAll(recu);
								return resultado;
							}
						}
					}
				
				}
				
			}
			else
				resultado.add(temp_b);
		}
			
		return resultado;
		
	}
	
}
