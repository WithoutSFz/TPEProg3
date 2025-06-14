package main;

import java.io.IOException;

public class ejecutable {

	public static void main(String[] args){
		//Cree una clase verificador para no dejar en el main la tarea de checkear el txt, de paso deje los ejecutables de backtraking y greedy ahi
		//para simplificar el main de ser posible, sino era traer los datos aca y a partir de ahi clavar for para imprimir
		String ruta = "../TPE/pedido.txt";
		GestorDeArchivo datos = new GestorDeArchivo(ruta);
		if(datos.exists()) {
			datos.backtracking();
			datos.greedy();
		}
		//te dejo comentado el view porque vi que no era necesario en el TPE por lo que entendi
    	//datos.getMaquinas().view();
	}
}
