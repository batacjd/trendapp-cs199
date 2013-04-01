import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;

class Prim_fcn {
	
	public static void main(String[] args) throws IOException, ParseException {
		
		//Constructors for different classes
		Reducer reducer_cons = new Reducer();
		//Stemmer stemmer_cons = new Stemmer();
		
		reducer_cons.start_reduce();
		
		//Connect to database.
		//System.out.println("Connecting now to database.");
		//Connect_db cd = new Connect_db();
		//Connection connection;
		//connection = cd.connect();
		
		//stemmer_cons.start_stem(connection);

		System.out.println("\nProgram ended..");
		
	}
	
}