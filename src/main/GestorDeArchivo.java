package main;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


public class GestorDeArchivo {
	  private ArrayList<Maquina> maquinas;
	    private int produccion;
	    private File pedido;
	    private boolean exists;
	    private HashMap<Integer,ArrayList<Maquina>> repeticiones;
	    private int instanciasbk;
	    private int instanciasgd;

	    public GestorDeArchivo(String ruta){
	        this.pedido = new File(ruta);
	        this.exists=false;
	        this.maquinas=new ArrayList<>();
	        this.produccion = -1;
	        this.repeticiones=new HashMap<>();
	        this.instanciasgd=0;
	        this.instanciasbk=0;
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
	                	this.exists=false;
	                	System.out.print(e);
	                	System.out.println("La estructura del archivo no es la esperada.\n "
	                			+ "Una estructura valida seria :\n"
	                			+"1ra linea: <Pedido de piezas>\n"
	                			+ "2da linea: M1,<capacidad de produccion>\n"
	                			+ "3ra linea: M2,<capacidad de produccion> \n"
	                			+"......\n"
	                			+ "Nva linea: MN,<capacidad de produccion>");	                
	                	}
	    
	            }catch(Exception e){
	                System.out.println(e);
	            }
	    
	        }
	    public boolean exists() {
	    	return this.exists;
	    }
	    
		private void addMachine(String l) {
			if(!l.isEmpty()) {
				int indice_m=l.indexOf("M");
				if(indice_m==-1)
					indice_m=l.indexOf("m");
				int indice_p=l.indexOf(",");
				String n_maquina=l.substring(indice_m+1, indice_p);
				String n_produ=l.substring(indice_p+1);
				Maquina m= new Maquina(Integer.parseInt(n_maquina),Integer.parseInt(n_produ));
				this.maquinas.add(m);
			}
	    }
		/*
		 * La tactica para esta busqueda exhaustiva fue  generar una coleccion de soluciones parciales
		 * para acotar las llamadas recursivas redundantes .
		 * 
		 * Las maquinas  fueron almacenadas en un atributo de clase usando un ArrayList por practicidad para iterar.
		 * Otro atributo de la clase es repeticiones que es nuestra cache de soluciones parciales.
		 * A causa del diseño el algoritmo solo tiene un unico parametro, siendo meta nuestra "meta de produccion" se ira reduciendo en cada llamado hasta llegar a 0.
		 * El caso base meta=0 implica que ya esta resuelto el sub-problema por lo que devuelve una lista vacia. Si la instancia ya fue resuelta
		 * devuelve del hashmap para esa instancia de meta la solución almacenada en el HashMap. Si no hay registro de una solucion prueba con cada maquina mientras no se exceda hasta dar con la solucion optima.
		 * Una vez encontrada la solucion optima esta se almacena en el cache antes de retornarla.
		*/
		private ArrayList<Maquina> algoBKTK(int meta){
			ArrayList<Maquina> resultado= null;
			ArrayList<Maquina> r_parcial;
			this.instanciasbk++;
			int valor;
			if(meta==0)
				return new ArrayList<>();
			if(this.repeticiones.containsKey(meta))
				return new ArrayList<>(this.repeticiones.get(meta));
			
			for(Maquina aux : this.maquinas) {
				valor=aux.getProduccion();
				
				if(meta-valor>=0) {
					r_parcial=this.algoBKTK(meta-valor);
					
					if(r_parcial!=null) {
						r_parcial.add(aux);
						if(resultado==null||resultado.size()>r_parcial.size())
						resultado=r_parcial;
					}
				}
			}
			if(resultado!=null) 
					this.repeticiones.put(meta,resultado);
				
			

			return resultado;
		}
	    public void backtracking(){
	        ArrayList<Maquina> aux = this.algoBKTK(produccion);
	        int pTotal = 0;
	        for(Maquina m: aux){
	            pTotal += m.getProduccion();
	        }
	        this.imprimir("Backtracking", aux, pTotal,this.instanciasbk);

	    }
		private ArrayList<Maquina> algoGD(int meta){
			ArrayList<Maquina> resultado= new ArrayList<>();
			Maquina r_parcial=null;
			this.instanciasgd++;
			int valor=0;
			for(Maquina aux: this.maquinas) {
				if(meta>=aux.getProduccion()&&valor<aux.getProduccion()) {
						valor=aux.getProduccion();
						r_parcial=aux;
					
					
				}
			}
			if(valor!=0) {
				resultado.add(r_parcial);
				resultado.addAll(this.algoGD(meta-valor));
			}
			return resultado;
		}
	    public void greedy(){
	        ArrayList<Maquina> aux = this.algoGD(produccion);
	        int pTotal = 0;
	        for(Maquina m: aux){
	            pTotal += m.getProduccion();
	        }
	        this.imprimir("Greedy",aux,pTotal,this.instanciasgd);
			
	    }
		private void imprimir(String titulo, ArrayList<Maquina> maquinasAux, int piezasProducidas, int instancias){
        	System.out.println("======= " + titulo + " =======");
        	System.out.println("Secuencia de maquinas:");
        	for(Maquina m : maquinasAux){
            	System.out.println(m);
        	}
        	System.out.println("Piezas a producir: " + this.produccion);
        	System.out.println("Total piezas producidas: " + piezasProducidas);
			System.out.println("Puestas en funcionamiento: " + (maquinasAux.size()));
        	System.out.println("Estados generados:  " + instancias);
    	}
	}


