package hash;

import java.util.ArrayList;


public class HStruct <T>{
	private int rp;//ranuras por balde
	private int rs;//ranuras de sobrecarga
	protected int mod;//cantidad de baldes
	protected HBalde<T>[] main;						
	private int cant_datos=0;
	public HStruct(int rp,int rs,int mod) {
		this.mod=mod;
		this.rs=rs;
		this.rp=rp;
		this.main=(HBalde<T>[]) new HBalde[mod];
		for(int i=0;i<mod;i++)
			main[i]=new HBalde(rp,rs);
		
	}
	public void add(T elem) {
		this.cant_datos++;
		this.main[elem.hashCode()%mod].add(elem);
		
	}
	public T search(T elem) {
		return this.main[elem.hashCode()%mod].search(elem);
	}
	public T delete(T elem) {
		return this.main[elem.hashCode()%mod].delete(elem);
	}
	public int cantDatos() {
		return this.cant_datos;
	}
	public void view() {
	    System.out.println("=== Tabla Hash ===");
	    for (int i = 0; i < this.mod; i++) {
	        System.out.print("Índice " + i + ": ");
	        if (this.main[i] != null)
	            this.main[i].view();
	        
	    }
	}
	public ArrayList<T> returnList(){
		ArrayList<T> resultado = new ArrayList<T>();
		for(HBalde aux: this.main)
			resultado.addAll(aux.returnList());
		return resultado;
	}
			

	

}

