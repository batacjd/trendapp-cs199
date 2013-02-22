import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

class Insert_db {
	
	public void insert(Connection connection, String t_key) throws SQLException {
		Statement st = null;
		st = connection.createStatement();
		
		String query = "insert into t_keys (key) values ('"+t_key+"')";
		st.executeUpdate(query);
		
		System.out.println("...Key "+t_key+" inserted.");
	}
	
}