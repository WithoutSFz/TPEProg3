package algoritmos;
import java.util.ArrayList;
import java.util.Collections;
import main.*;

public class backtracking {
    private ArrayList<Maquina> mejorCombinacionActual;                        //aca almacenamos la mejor combinacion hasta el momento
    private int minMaquinasEncontradas;                                       //aca se almacena la cantidad de maquinas recorridas hasta llegar a lo pedido
    private int piezasProducidasMejorCombinacion;
    private int produccion;
    private int instancias;
    private ArrayList<Maquina> maquinas;

    public backtracking(){                         //instanciamos
        this.mejorCombinacionActual = new ArrayList<>();
        this.minMaquinasEncontradas = Integer.MAX_VALUE;
        this.piezasProducidasMejorCombinacion = Integer.MAX_VALUE;
    }
    
    public int getPiezasTotales(){
        return this.piezasProducidasMejorCombinacion;
    }

    public ArrayList<Maquina> getCombMaquinas(){
        return this.mejorCombinacionActual;
    }

    public int getPuestas(){
        return this.instancias;
    }

    public ArrayList<Maquina> backtrackingTest(int prod, ArrayList<Maquina> list) {
        this.produccion = prod;
        this.maquinas = list;
        this.mejorCombinacionActual.clear();                                                                //limpiamos cualquier contenido anterior
        this.minMaquinasEncontradas = Integer.MAX_VALUE;                                                    //le damos el maximo valor posible asi empieza desde lo mas alto
        this.piezasProducidasMejorCombinacion = Integer.MAX_VALUE;                                          //lo mismo
        Collections.sort(maquinas, Collections.reverseOrder());                                             //ordenamos las maquinas de mayor a menor produccion

        backtrackMaquinas(new ArrayList<>(), 0, maquinas);                                  // inicia el backtracking
        return mejorCombinacionActual;                                                                     //devolvemos la mejor combinacion post recursiones
    }


    private void backtrackMaquinas(ArrayList<Maquina> combinacionActual, int piezasGeneradas, ArrayList<Maquina> maquinasDisponibles) {
        instancias++;
        if (piezasGeneradas >= produccion) {
            
            if (combinacionActual.size() < minMaquinasEncontradas) {        // verificamos si esta combinación es mejor que la anterior

                minMaquinasEncontradas = combinacionActual.size();
                mejorCombinacionActual = new ArrayList<>(combinacionActual);
                piezasProducidasMejorCombinacion = piezasGeneradas;

            } else if (combinacionActual.size() == minMaquinasEncontradas) { //verificamos si son el mismo numero de maquinas pero con menor sobra de piezas
        
                if (piezasGeneradas < piezasProducidasMejorCombinacion) {
                    mejorCombinacionActual = new ArrayList<>(combinacionActual);
                    piezasProducidasMejorCombinacion = piezasGeneradas;
                }

            }
            return;                                                         // si alcanzamos el valor de produccion lo terminamos
        }

        
        if (combinacionActual.size() >= minMaquinasEncontradas) {           // otro criterio de corte segun cantidad de maquinas usadas
            return;
        }

        
        
        for (int i = 0; i < maquinasDisponibles.size(); i++) {              // aca empieza la recursion
            Maquina maquinaAux = maquinasDisponibles.get(i);
            int piezasMaquina = maquinaAux.getProduccion();

            
            if (piezasGeneradas + piezasMaquina > produccion && (combinacionActual.size() + 1 >= minMaquinasEncontradas)) {     //criterio de poda
                continue;                                                   // nos saltamos la opcion si es el mismo resultado que el anterior
            }

            combinacionActual.add(maquinaAux);                                                          // Añadimos la maquina a la combinacion actual
            backtrackMaquinas(combinacionActual, piezasGeneradas + piezasMaquina, maquinasDisponibles); // le enviamos el valor de i desde 0 asi recorre todo el List
            combinacionActual.remove(combinacionActual.size() - 1);                         //eliminar la última máquina añadida para comprobar otra de ser el caso
        }
    }
}
