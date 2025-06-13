package grafos;

import java.util.ArrayList;

import hash.*;


public class Vertice <T>{
	private T elem;
	private HCrecimiento<Vertice<T>> aristas;
	public Vertice(T elem) {
		this.elem=elem;
		aristas = new HCrecimiento<Vertice<T>>(0.7,12);
	}
	public T getE() {
		return this.elem;
	}
	public void addArista(Vertice<T> aris) {
		if(this.aristas.search(aris)==null)
			this.aristas.add(aris);
	}
	public boolean conexion(Vertice<T> aris) {
		boolean resultado = false;
		if (this.aristas.search(aris)!=null)
			resultado =true;
		return resultado;
	}
	public ArrayList<Vertice<T>> getAristas(){
		ArrayList<Vertice<T>> resultado = new ArrayList<Vertice<T>>();
		resultado.addAll(this.aristas.returnList());
		return resultado;
		
	}
	public void addAllAristas(Vertice<T> v) {
		ArrayList<Vertice<T>> aristas = this.getAristas();
		for(Vertice<T> aux :aristas) {
			aux.addArista(v);
			if(!v.conexion(aux))// esto es para evitar que se agregue dos veces asi mismo porque v ya esta en las aristas de inicio
				v.addArista(aux);
		}
		
	}
	public boolean equals(Object o) {
		if(this.elem==o)
			return true;
		Vertice<T> c =(Vertice<T>) o;
		return this.elem.equals(c.getE());
	} 	
	public int hashCode() {
		return this.elem.hashCode();
	}
	public String toString() {
		return this.elem.toString();
	}
}
