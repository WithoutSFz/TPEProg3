package hash;

public class HCrecimiento<T> extends HStruct<T> {
	private double pd;
	
	public HCrecimiento(double pd, int mod){
		super(1,1,mod);
		this.pd=pd;
	}
	public int limite() {
		double aux = this.pd *this.mod*1.0;
		aux= Math.floor(aux);
		return (int)aux;
		
	}
	public void reHashing() {
		this.mod=1+(this.mod*2);
		 HBalde[] nw= new HBalde[this.mod];
			for(int i=0;i<mod;i++)
				nw[i]=new HBalde<T>(1,1);
		 for(HBalde<T> aux :this.main) {
			 for(T aux2:aux.returnList())	
			 	nw[aux2.hashCode()%this.mod].add(aux2);	 
		 }
		 this.main=nw;
		 
		
	}
	public void add(T elem) {
		super.add(elem);
		if(this.cantDatos()>=this.limite())
			reHashing();
	}

}


