package main;

import java.util.ArrayList;

public class imprimir {
    public void mostrar(String titulo, ArrayList<Maquina> maquinas, int piezasPedidas, int piezasProducidas, int puestas){
        System.out.println("======= " + titulo + " =======");
        System.out.println("Secuencia de maquinas:");
        for(Maquina m : maquinas){
            System.out.println(m);
        }
        System.out.println("Piezas a producir: " + piezasPedidas);
        System.out.println("Total piezas producidas: " + piezasProducidas);
        System.out.println("Instancias generadas: " + puestas);
    }
}
