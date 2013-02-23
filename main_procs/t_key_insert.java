import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

class T_key_insert {
	
	
	protected void start_t_insert() throws IOException {
		
		Connect_db cd = new Connect_db();
		Connection connection;
		connection = cd.connect();
		String filename = "datasets/health.txt";
		
		FileReader reader = new FileReader(filename);
		BufferedReader br = new BufferedReader(reader);
	      
	    String line;
	    int categoryid = 9;
	    while ((line = br.readLine()) != null){
	    	System.out.println(line);
	    	Insert_db id = new Insert_db();
	  		  try {
	  			id.start_insert(connection,line,categoryid);
	  		  } catch (SQLException e) {
	  			// TODO Auto-generated catch block
	  			  System.out.println("error");
	  		  }
	    	
	    }
		
	}
	
	
	
}