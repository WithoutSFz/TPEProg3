package main;

public class Maquina implements Comparable<Maquina>{
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
	public String toString() {
		return "Maquina: "+this.numero+". Capacidad de produccion: "+this.produccion;
	}
	@Override
	public int compareTo(Maquina o) {
		Maquina aux = (Maquina)o;
		return Integer.compare(this.getProduccion(), aux.getProduccion());
	}
	
}
