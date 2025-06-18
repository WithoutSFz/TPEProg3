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
	                this.exists=false;
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
				this.maquinas.add(m);
			}
	    }
		
		
		private ArrayList<Maquina> algoBKTK(int meta){
			ArrayList<Maquina> resultado= new ArrayList<>();
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
					if((r_parcial==null&&meta-valor==0)||r_parcial!=null) {
						r_parcial.add(aux);
												
					}

					
					if(resultado.isEmpty()||resultado.size()>r_parcial.size())
						resultado=r_parcial;
					
				}
			}
			if(resultado!=null) 
					this.repeticiones.put(meta,resultado);
				
			

			return resultado;
		}
	    public void backtracking(){
	        ArrayList<Maquina> aux = this.algoBKTK(produccion);
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
	        System.out.println("Instancias generadas: "+this.instanciasbk);
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
	        System.out.println("Instancias generadas: "+this.instanciasgd);
	    }
	}


