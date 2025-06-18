package main;


public class ejecutable {

	public static void main(String[] args){
		String ruta = "pedido.txt";
		GestorDeArchivo datos = new GestorDeArchivo(ruta);
		if(datos.exists()) {
			
			datos.greedy();
			datos.backtracking();
		}

	} 
}
