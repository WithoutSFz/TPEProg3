package algoritmos;
import java.util.ArrayList;
import java.util.Collections;
import main.*;

public class backtracking {
    private ArrayList<Maquina> mejorComb;                        //aca almacenamos la mejor combinacion hasta el momento
    private int minMaqEncontradas;                                       //aca se almacena la cantidad de maquinas recorridas hasta llegar a lo pedido
    private int piezasProducidasComb;
    private int produccion;
    private int instancias;
    private ArrayList<Maquina> maquinas;

    public backtracking(){                         //instanciamos
        this.mejorComb = new ArrayList<>();
        this.minMaqEncontradas = Integer.MAX_VALUE;
        this.piezasProducidasComb = Integer.MAX_VALUE;
    }
    
    public int getPiezasTotales(){
        return this.piezasProducidasComb;
    }

    public ArrayList<Maquina> getCombMaquinas(){
        return this.mejorComb;
    }

    public int getPuestas(){
        return this.instancias;
    }

    public ArrayList<Maquina> backtrackingTest(int prod, ArrayList<Maquina> list) {
        this.produccion = prod;
        this.maquinas = list;
        this.mejorComb.clear();                                                                //limpiamos cualquier contenido anterior
        this.minMaqEncontradas = Integer.MAX_VALUE;                                             //le damos el maximo valor posible asi empieza desde lo mas alto
        this.piezasProducidasComb = Integer.MAX_VALUE;                                          //lo mismo
        Collections.sort(maquinas, Collections.reverseOrder());                                 //ordenamos las maquinas de mayor a menor produccion

        backtrackMaquinas(new ArrayList<>(), 0, maquinas);                                  // inicia el backtracking
        return mejorComb;                                                                     //devolvemos la mejor combinacion post recursiones
    }


    private void backtrackMaquinas(ArrayList<Maquina> combActual, int piezasGeneradas, ArrayList<Maquina> maquinasDisponibles) {
        instancias++;
        if (piezasGeneradas >= produccion) {                    //comprobamos si todavia no llegamos al total buscado
            
            if (combActual.size() < minMaqEncontradas) {        // verificamos si esta combinación es mejor que la anterior

                minMaqEncontradas = combActual.size();
                mejorComb = new ArrayList<>(combActual);
                piezasProducidasComb = piezasGeneradas;

            } else if (combActual.size() == minMaqEncontradas) { //verificamos si son el mismo numero de maquinas pero con menor sobra de piezas
        
                if (piezasGeneradas < piezasProducidasComb) {
                    mejorComb = new ArrayList<>(combActual);
                    piezasProducidasComb = piezasGeneradas;
                }

            }
            return;                                                         // si alcanzamos el valor de produccion lo terminamos
        }

        
        if (combActual.size() >= minMaqEncontradas) {                       // otro criterio de poda segun cantidad de maquinas usadas (buscamos la menor)
            return;
        }

        
        
        for (int i = 0; i < maquinasDisponibles.size(); i++) {
            Maquina aux = maquinasDisponibles.get(i);
            int piezasMaquina = aux.getProduccion();                 //cuantas piezas genera la maquina

            if (piezasGeneradas + piezasMaquina > produccion && (combActual.size() + 1 >= minMaqEncontradas)) { 
                return;                                                   // nos saltamos la opcion si es el mismo resultado que el anterior
            }

            combActual.add(aux);                                                          // Añadimos la maquina a la combinacion actual
            backtrackMaquinas(combActual, piezasGeneradas + piezasMaquina, maquinasDisponibles); //llamamos a recursion
            combActual.remove(combActual.size() - 1);                         //elimina la ultima maquina agregada para probar la siguiente opcion
        }
    }
}
