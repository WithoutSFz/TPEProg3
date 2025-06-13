package main;

import grafos.ValorVertice;
import grafos.Vertice;

public class ValorMaquina implements ValorVertice<Maquina> {

	@Override
	public int toInt(Vertice<Maquina> v) {
		// TODO Auto-generated method stub
		return v.getE().getProduccion();
	}

}
