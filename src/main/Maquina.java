package main;

public class Maquina {
	private int numero;
	private int produccion;
	public Maquina(int n , int p) {
		this.numero=n;
		this.produccion=p;
	}
	public int getNumero() {
		return numero;
	}
	public int getProduccion() {
		return produccion;
	}
	public boolean equals(Object o) {
		Maquina segundo=(Maquina) o;
		return this.numero==segundo.getNumero();
	} 	
	public int hashCode() {
		return this.numero;
	}
	public String toString() {
		return "Maquina: "+this.numero+". Capacidad de produccion: "+this.produccion;
	}
	
}
