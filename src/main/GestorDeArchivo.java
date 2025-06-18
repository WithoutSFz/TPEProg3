package main;
import java.io.*;
import java.util.ArrayList;
import algoritmos.*;


public class GestorDeArchivo {
	  	private ArrayList<Maquina> maquinas;
	    private int produccion;
	    private File pedido;
	    private boolean exists;
	    private int instanciasbk;
	    private int instanciasgd;
		private backtracking back;

	    public GestorDeArchivo(String ruta){
	        this.pedido = new File(ruta);
	        this.exists=false;
	        this.maquinas=new ArrayList<>();
	        this.produccion = -1;
	        this.instanciasgd=0;
	        this.instanciasbk=0;
	        this.verificarTexto();
			this.back = new backtracking();
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
			int ultimo_v=0;
			if(meta>0) {
				for(Maquina aux : this.maquinas) {
					valor=aux.getProduccion();
					
					if(meta-ultimo_v>meta-valor) {
						r_parcial=this.algoBKTK(meta-valor);
						r_parcial.add(aux);
						if(resultado.isEmpty()||resultado.size()>r_parcial.size())
							resultado=r_parcial;
						
					}
				}
			}
			return resultado;
		}
		
	    public void backtracking(){
	        ArrayList<Maquina> aux = this.algoBKTK(produccion);
	        int pTotal = 0;
	        for(Maquina m: aux){
	            pTotal += m.getProduccion();
	        }
	        this.imprimir("Backtracking", aux, pTotal, this.instanciasbk);
	    }

		private ArrayList<Maquina> algoGD(int meta){
			ArrayList<Maquina> resultado= new ArrayList<>();
			Maquina r_parcial=null;
			this.instanciasgd++;
			int valor=0;
			if(meta<=0)
				return resultado;
			for(Maquina aux: this.maquinas) {
				if(meta<aux.getProduccion()&&valor!=0) {
					if(valor>aux.getProduccion()) {
						valor=aux.getProduccion();
						r_parcial=aux;
					}
					
				}
				else {
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
			this.imprimir("Greedy", aux, pTotal, this.instanciasgd);
	    }

		public void pruebaBacktrack(){
			back.backtrackingTest(produccion, maquinas);
			this.imprimir("Backtracking de prueba",back.getCombMaquinas(), back.getPiezasTotales(),  back.getPuestas());
		}

		public void imprimir(String titulo, ArrayList<Maquina> maquinas, int piezasProducidas, int puestas){
        	System.out.println("======= " + titulo + " =======");
        	System.out.println("Secuencia de maquinas:");
        	for(Maquina m : maquinas){
            	System.out.println(m);
        	}
        	System.out.println("Piezas a producir: " + this.produccion);
        	System.out.println("Total piezas producidas: " + piezasProducidas);
        	System.out.println("Instancias generadas: " + puestas);
    	}
	}


