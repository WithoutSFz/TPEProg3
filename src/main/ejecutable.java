package main;


public class ejecutable {

	public static void main(String[] args){
		//Cree una clase verificador para no dejar en el main la tarea de checkear el txt, de paso deje los ejecutables de backtraking y greedy ahi
		//para simplificar el main de ser posible, sino era traer los datos aca y a partir de ahi clavar for para imprimir
		String ruta = "src/pedido.txt";
		GestorDeArchivo datos = new GestorDeArchivo(ruta);
		if(datos.exists()) {
			datos.greedy();
			datos.pruebaBacktrack();			//lo puse antes para que puedas ver el funcionamiento
			datos.backtracking();
		}
	}
}
