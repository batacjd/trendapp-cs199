import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

class Reducer{

	public void start_reduce()throws IOException{
		
		System.out.println("-------- Starting reduction process... ------------");
		//Read from file
		File folder = new File("ext_files/R_raw_files");
		File[] listOfFiles = folder.listFiles();
		int i = 0;
		
		for( i = 0; i < listOfFiles.length; i++){
			
			if( listOfFiles[i].isFile()){
				String files = listOfFiles[i].getName();
				System.out.println("...Reading file "+files);
			}
			
			FileReader reader = new FileReader(listOfFiles[i]);
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(reader);
			
			//Read each line from file and append to string
			String line;
			while( (line=br.readLine()) != null ){
				sb.append(line);
			}
			
			//Parse the string using Jsoup
			Document parsedln = Jsoup.parse(sb.toString());
			
			//Process the string to remove XML, scripts, and styles
			String sbString = parsedln.toString();
			String procln = sbString.replaceAll("<script.*?</script>","");
			procln = procln.replaceAll("<style.*?</style>","");
			procln = procln.replaceAll("\\<.*?>","");
			
			//Write output to file
			FileWriter writer = new FileWriter("ext_files/R_output_files/output"+i+".txt");
			BufferedWriter bw = new BufferedWriter(writer);
			bw.write(procln);
			System.out.println("...created file output"+i);
			br.close();
			bw.close();
			
		}//end for loop
		
		//Notify user that program has ended by printing to console
		System.out.println("End of reduction!");
		
		
	}
	
}