import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

class Insert_db {
	
	protected void start_insert(Connection connection, String t_key, int categoryid) throws SQLException {
		
		Statement st = null;
		st = connection.createStatement();
		
		String query = "insert into t_keys (keyname, categoryid) values ('"+t_key+"', "+categoryid+")";
		st.executeUpdate(query);
		
		//System.out.println("...Key "+t_key+" inserted.");
		System.out.println("Inserted");
	}
	
}