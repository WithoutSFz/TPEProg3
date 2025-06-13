package main;

import grafos.GC;
import java.io. *;

public class ejecutable {
	private static GC<Maquina> maquinas;

	public static void main(String[] args) throws IOException {
		
		maquinas=new GC<Maquina>();
		ValorMaquina v= new ValorMaquina();
		String ruta="../../pedido.txt";
		File pedido = new File(ruta);
		if(!pedido.exists()) {
			pedido.createNewFile();
			BufferedWriter writer =new BufferedWriter(new FileWriter(pedido));
			writer.write("12\n");
			writer.write("M1,7\n");
			writer.write("M2,3\n");
			writer.write("M3,4\n");
			writer.write("M4,1\n");
			System.out.println("creado");
			writer.close();
		}
		else
			System.out.println("ya existe");
		BufferedReader reader= new BufferedReader(new FileReader(pedido));
		String linea;
		linea=reader.readLine();
		int produccion =-1;
		if(linea.isEmpty()) {
			linea=reader.readLine();	
			while(linea!=null||produccion==-1) {
				produccion=Integer.parseInt(linea);
				linea=reader.readLine();
				
			}
			
		}
		else {
			produccion=Integer.parseInt(linea);
			linea=reader.readLine();
			
		}
		while(linea!=null) {
			addM(linea);
			linea=reader.readLine();
			
		}
		for(Maquina aux :maquinas.sumaDeVerticesGreedy(produccion, v)) {
			System.out.println(aux);
		}
		for(Maquina aux :maquinas.sumaDeVerticesBackTracking(produccion, v)) {
			System.out.println(aux);
		}
		maquinas.view();

	}
	public static void addM(String l) {
		if(!l.isEmpty()) {
			int indice_m=l.indexOf("M");
			if(indice_m==-1)
				indice_m=l.indexOf("m");
			int indice_p=l.indexOf(",");
			String n_maquina=l.substring(indice_m+1, indice_p);
			String n_produ=l.substring(indice_p+1);
			Maquina m= new Maquina(Integer.parseInt(n_maquina),Integer.parseInt(n_produ));
			maquinas.addN(m);
				
				
			
		}
		

	}

}
