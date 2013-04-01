import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Reducer{

	int outputCtr;
	
	protected void start_reduce()throws IOException, ParseException{
		
		String basepath = "C:/Users/Joem/Documents/GitHub/trendapp-cs199/ext_files/R_raw_files/crawledFiles";
		File file = new File(basepath);
		String[] directories = file.list(new FilenameFilter() {
  		@Override
  		public boolean accept(File dir, String name) {
    		return new File(dir, name).isDirectory();
  		}
		});
		System.out.println(Arrays.toString(directories));
		System.out.println(directories.length);
		
		for (int q=0; q < directories.length; q++){
			File tempDir= new File(file+"/"+directories[q]);
			System.out.println(tempDir.toString());
			if(tempDir.exists()){
				File subDir = new File(file, directories[q]);
				
				String[] subDirectories = subDir.list(new FilenameFilter() {
	  			@Override
	  			public boolean accept(File dir, String name) {
	    			return new File(dir, name).isDirectory();
	  			}
				});
				System.out.println("Subdirectories from"+ subDir.toString() +"are: \n" +Arrays.toString(subDirectories));
				
				System.out.println("-------- Starting reduction process... ------------");
				//Read from file
			
				for(int v=0; v < subDirectories.length; v++){
				
					File folder = new File(subDir.toString()+File.separator+subDirectories[v]);
					File[] listOfFiles = folder.listFiles();
					int i = 0;

					for( i = 0; i < listOfFiles.length; i++){
						
						if( listOfFiles[i].isFile()){
							String files = listOfFiles[i].getName();
							System.out.println("Reading file "+files+"...");
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
						
						String sample = procln;
						
						Pattern date1 = Pattern.compile("([12]\\d|3[01])/(Jan(uary)?|Feb(ruary)?|Mar(ch)?|Apr(il)?|May|Jun(e)?|Jul(y)?|Aug(ust)?|Sep(t(ember)?)?|Oct(ober)?|Nov(ember)?|Dec(ember)?)/\\d{4}");
						Matcher matcher1 = date1.matcher(sample);
						Pattern date2 = Pattern.compile("([12]\\d|3[01])-(Jan(uary)?|Feb(ruary)?|Mar(ch)?|Apr(il)?|May|Jun(e)?|Jul(y)?|Aug(ust)?|Sep(t(ember)?)?|Oct(ober)?|Nov(ember)?|Dec(ember)?)-\\d{4}");
						Matcher matcher2 = date2.matcher(sample);
						Pattern date3 = Pattern.compile("(Jan(uary)?|Feb(ruary)?|Mar(ch)?|Apr(il)?|May|Jun(e)?|Jul(y)?|Aug(ust)?|Sep(t(ember)?)?|Oct(ober)?|Nov(ember)?|Dec(ember)?)/([12]\\d|3[01])/\\d{4}");
						Matcher matcher3 = date3.matcher(sample);
						Pattern date4 = Pattern.compile("(Jan(uary)?|Feb(ruary)?|Mar(ch)?|Apr(il)?|May|Jun(e)?|Jul(y)?|Aug(ust)?|Sep(t(ember)?)?|Oct(ober)?|Nov(ember)?|Dec(ember)?)-([12]\\d|3[01])-\\d{4}");
						Matcher matcher4 = date4.matcher(sample);
						Pattern date5 = Pattern.compile("(Jan(uary)?|Feb(ruary)?|Mar(ch)?|Apr(il)?|May|Jun(e)?|Jul(y)?|Aug(ust)?|Sep(t(ember)?)?|Oct(ober)?|Nov(ember)?|Dec(ember)?)\\s([12]\\d|3[01]),\\s\\d{4}");
						Matcher matcher5 = date5.matcher(sample);
						Pattern date6 = Pattern.compile("([12]\\d|3[01])/[0-1][1-2]/(18|19|20|21)\\d{2}");
						Matcher matcher6 = date6.matcher(sample);
						Pattern date7 = Pattern.compile("[0-1][1-2]/([12]\\d|3[01])/(18|19|20|21)\\d{2}");
						Matcher matcher7 = date7.matcher(sample);
						Pattern date8 = Pattern.compile("([12]\\d|3[01])-[0-1][1-2]-(18|19|20|21)\\d{2}");
						Matcher matcher8 = date8.matcher(sample);
						Pattern date9 = Pattern.compile("[0-1][1-2]-([12]\\d|3[01])-(18|19|20|21)\\d{2}");
						Matcher matcher9 = date9.matcher(sample);
						
						SimpleDateFormat df1 = new SimpleDateFormat("dd/MMM/yyyy");
						SimpleDateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");
						SimpleDateFormat df3 = new SimpleDateFormat("MMM/dd/yyyy");	
						SimpleDateFormat df4 = new SimpleDateFormat("MMM-dd-yyyy");	
						SimpleDateFormat df5 = new SimpleDateFormat("MMM dd, yyyy");
						SimpleDateFormat df6 = new SimpleDateFormat("dd/MM/yyyy");
						SimpleDateFormat df7 = new SimpleDateFormat("MM/dd/yyyy");
						SimpleDateFormat df8 = new SimpleDateFormat("dd-MM-yyyy");
						SimpleDateFormat df9 = new SimpleDateFormat("MM-dd-yyyy");
						
						String dateLimit1 = "01/Jan/2000";
						String dateLimit2 = "01-Jan-2000";
						String dateLimit3 = "Jan/01/2000";
						String dateLimit4 = "Jan-01-2000";
						String dateLimit5 = "Jan 01, 2000";
						String dateLimit6 = "01/01/2000";
						String dateLimit7 = "01/01/2000";
						String dateLimit8 = "01-01-2000";
						String dateLimit9 = "01-01-2000";
						
						//System.out.println(df.parse("31/01/2012"));
						int ctr = 0;
						if(ctr < 1){
							while(matcher1.find()){
								Date date_dateLimit = df1.parse(dateLimit1);
								Date x = df1.parse(matcher1.group());
								if(x.after(date_dateLimit)){ ctr++; break; };
								//System.out.println(x+" 1- "+x.after(date_dateLimit));
							}
						}
						
						if(ctr < 1){
							while(matcher2.find()){
								Date date_dateLimit = df2.parse(dateLimit2);
								Date x = df2.parse(matcher2.group());
								if(x.after(date_dateLimit)){ ctr++; };
								//System.out.println(x+" 2- "+x.after(date_dateLimit));
							}
						}
						
						if(ctr < 1){
							while(matcher3.find()){
								Date date_dateLimit = df3.parse(dateLimit3);
								Date x = df3.parse(matcher3.group());
								if(x.after(date_dateLimit)){ ctr++; };
								//System.out.println(x+" 3- "+x.after(date_dateLimit));
							}
						}
						
						if(ctr < 1){
							while(matcher4.find()){
								Date date_dateLimit = df4.parse(dateLimit4);
								Date x = df4.parse(matcher4.group());
								if(x.after(date_dateLimit)){ ctr++; };
								//System.out.println(x+" 4- "+x.after(date_dateLimit));
							}
						}
						
						if(ctr < 1){
							while(matcher5.find()){
								Date date_dateLimit = df5.parse(dateLimit5);
								Date x = df5.parse(matcher5.group());
								if(x.after(date_dateLimit)){ ctr++; };
								//System.out.println(x+" 5- "+x.after(date_dateLimit));
							}
						}
						
						if(ctr < 1){
							while(matcher6.find()){
								Date date_dateLimit = df6.parse(dateLimit6);
								Date x = df6.parse(matcher6.group());
								if(x.after(date_dateLimit)){ ctr++; };
								//System.out.println(x+" 6- "+x.after(date_dateLimit));
							}
						}
						
						if(ctr < 1){
							while(matcher7.find()){
								Date date_dateLimit = df7.parse(dateLimit7);
								Date x = df7.parse(matcher7.group());
								if(x.after(date_dateLimit)){ ctr++; };
								//System.out.println(x+" 7- "+x.after(date_dateLimit));
							}
						}
						
						if(ctr < 1){
							while(matcher8.find()){
								Date date_dateLimit = df8.parse(dateLimit8);
								Date x = df8.parse(matcher8.group());
								if(x.after(date_dateLimit)){ ctr++; };
								//System.out.println(x+" 8- "+x.after(date_dateLimit));
							}
						}
						
						if(ctr < 1){
							while(matcher9.find()){
								Date date_dateLimit = df9.parse(dateLimit9);
								Date x = df9.parse(matcher9.group());
								if(x.after(date_dateLimit)){ ctr++; };
								//System.out.println(x+" 9- "+x.after(date_dateLimit));
							}
						}
						
						if(ctr > 0){
							//Write output to file
							FileWriter writer = new FileWriter("ext_files/R_output_files/output"+outputCtr+".txt");
							outputCtr++;
							BufferedWriter bw = new BufferedWriter(writer);
							bw.write(procln);
							System.out.println("Created file output"+i);
							br.close();
							bw.close();
						}
						
					}//end for loop
					
					//Notify user that program has ended by printing to console
					System.out.println("\nAll files' contents have been reduced.");
					
				}
			}
			
			
			
		}
		
	}
	
}