import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class Categorize {
	/*	This is a Java implementation of a PHP function similar_text().
	 * 	This shall return the categoryid of the string to be categorized.
	 * 	Categorization is based on the highest percentage of the matched
	 * 	per category.
	 */
	
	private int NO_OF_CATEGORIES = 10;
	
	protected int start_categorize(Connection connection, String t_key) throws SQLException {
	
		int i;
		float[] simContainer = new float[NO_OF_CATEGORIES+1];
		
		for (i = 1; i <= NO_OF_CATEGORIES; i++) {
			Statement st = null;
			st = connection.createStatement();
			
			String query = "select keyname from t_keys where categoryid = "+i;
			ResultSet result = st.executeQuery(query);
			
			float temp = 0;						//temporary storage for max percentage
			while(result.next()){
				float sim = similarText(t_key, result.getString(1));
				if(sim > temp) temp = sim;		//get maximum percentage in category
			}
			simContainer[i] = temp;				//store highest percentage
		}//end for loop
		
		//get category number
		int catg_id = 1;
		float temp = simContainer[1];
		for (i = 1; i < NO_OF_CATEGORIES; i++) {
			if(simContainer[i] >= temp) {
				catg_id = i;
				temp = simContainer[i];
			}
		}
		
		return catg_id;
	}
	
	
	//Returns the similarity of strings in percentage
	private float similarText(String first, String second)   {
	    first = first.toLowerCase();
	    second = second.toLowerCase();
	    return (float)(this.similar(first, second)*200)/(first.length()+second.length());
	}

	//Returns similarity of strings according to character matches
	private int similar(String first, String second)  { 
	    int p, q, l, sum;
	    int pos1=0;
	    int pos2=0;
	    int max=0;
	    char[] arr1 = first.toCharArray();
	    char[] arr2 = second.toCharArray();
	    int firstLength = arr1.length;
	    int secondLength = arr2.length;

	    for (p = 0; p < firstLength; p++) {
	        for (q = 0; q < secondLength; q++) {
	            for (l = 0; (p + l < firstLength) && (q + l < secondLength) && (arr1[p+l] == arr2[q+l]); l++);            
	            if (l > max) {
	                max = l;
	                pos1 = p;
	                pos2 = q;
	            }
	        }
	    }
	    sum = max;
	    if (sum > 0) {
	        if (pos1 > 0 && pos2 > 0) {
	            sum += this.similar(first.substring(0, pos1>firstLength ? firstLength : pos1), second.substring(0, pos2>secondLength ? secondLength : pos2));
	        }

	        if ((pos1 + max < firstLength) && (pos2 + max < secondLength)) {
	            sum += this.similar(first.substring(pos1 + max, firstLength), second.substring(pos2 + max, secondLength));
	        }
	    }       
	    return sum;
	}
	
}