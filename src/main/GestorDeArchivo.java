package main;
import grafos.*;
import java.io.*;
import java.util.ArrayList;

public class GestorDeArchivo {
	  private GC<Maquina> maquinas;
	    private int produccion;
	    private File pedido;
	    private ValorMaquina v;
	    private boolean exists;

	    public GestorDeArchivo(String ruta){
	        this.pedido = new File(ruta);
	        this.exists=false;
	        this.produccion = -1;
	        this.v = new ValorMaquina();
	        this.verificarTexto();
	    }
	    
	    public void verificarTexto(){
	        try{
	                if(!this.pedido.exists()) {
	                    System.out.println("Error: Inexistencia del archivo pedido.txt en la raiz del programa.");
	                    return;
	                }
	                else {
	                	this.exists=true;
	                	this.maquinas=new GC<Maquina>();
	                }
	                BufferedReader reader= new BufferedReader(new FileReader(this.pedido));
	                String linea;
	                linea= reader.readLine();
	                try {
		                if(linea.isEmpty()) {
		                    linea=reader.readLine();	
		                    while(linea!=null||this.produccion==-1) {
		                        this.produccion=Integer.parseInt(linea);
		                        linea=reader.readLine();
		                    }
		                }
		                else {
		                    this.produccion=Integer.parseInt(linea);
		                    linea=reader.readLine();
		                }
		                while(linea!=null) {
		                    addMachine(linea);
		                    linea=reader.readLine();
		                }
		                reader.close();
	                }catch(Exception e){
	                	System.out.println("La estructura del archivo no es la esperada.\n ej."
	                			+ "1ra linea: <Pedido de produccion>\n"
	                			+ "2da linea: M1,<capacidad de produccion> \n"
	                			+"......"
	                			+ "Nva linea: MN,<capacidad de produccion>");	                }
	            }catch(Exception e){
	                System.out.println(e);
	            }
	    
	        }
	    public boolean exists() {
	    	return this.exists;
	    }
	    
		public void addMachine(String l) {
			if(!l.isEmpty()) {
				int indice_m=l.indexOf("M");
				if(indice_m==-1)
					indice_m=l.indexOf("m");
				int indice_p=l.indexOf(",");
				String n_maquina=l.substring(indice_m+1, indice_p);
				String n_produ=l.substring(indice_p+1);
				Maquina m= new Maquina(Integer.parseInt(n_maquina),Integer.parseInt(n_produ));
				this.maquinas.addN(m);
			}
	    }
	    public void backtracking(){
	        ArrayList<Maquina> aux = this.maquinas.sumaDeVerticesBackTracking(produccion, v);
	        int pTotal = 0;
	        System.out.println("=== Backtracking ===");
			System.out.println("Secuencia de maquinas:");
	        for(Maquina m: aux){
	            System.out.println(m);
	        }
	        for(Maquina m: aux){
	            pTotal += m.getProduccion();
	        }
	        System.out.println("Total piezas producidas: " + pTotal);
	        System.out.println("Puestas en funcionamiento: " + aux.size());
	    }
	    public void greedy(){
	        ArrayList<Maquina> aux = this.maquinas.sumaDeVerticesGreedy(produccion, v);
	        int pTotal = 0;
	        System.out.println("=== Greedy ===");
	        System.out.println("Secuencia de maquinas:");
	         for(Maquina m: aux){
	            System.out.println(m);
	        }
	        for(Maquina m: aux){
	            pTotal += m.getProduccion();
	        }
			System.out.println("Total piezas producidas: " + pTotal);
	        System.out.println("Puestas en funcionamiento: " + aux.size());
	    }
	}


