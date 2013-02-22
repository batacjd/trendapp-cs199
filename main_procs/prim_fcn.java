import java.io.IOException;

class Prim_fcn {
	
	public static void main(String[] args) throws IOException {
		
		//Constructors for different classes
		Reducer reducer_cons = new Reducer();
		Stemmer stemmer_cons = new Stemmer();
		
		reducer_cons.start_reduce();
		stemmer_cons.start_stem();
		
		System.out.println("..Program ended..");
		
	}
	
}