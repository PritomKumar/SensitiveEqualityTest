import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SensititiveEqualitySmell {

	  private static File folder = new File("E:\\Java\\SpamBase\\src\\testing");
	  static String temp = "";
	  private static int errorCount=0;
	  
	  private static String errorLog = "------Error Log------" + '\n' + '\n';

	  public static void main(String[] args) {
	    // TODO Auto-generated method stub
	    //System.out.println("Reading files under the folder "+ folder.getAbsolutePath());
	    chooseFolder(folder);
		/*
	    try {
			//deadFliedTestSmell("E:\\Java\\SpamBase\\src\\util\\TabelaLimiar.java");
			sensitiveEqualitySmell("E:\\Java\\SpamBase\\src\\testing\\DimTest.java");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		 */
	  }

	  public static void chooseFolder(final File folder) {

	    for (final File fileEntry : folder.listFiles()) {
	      if (fileEntry.isDirectory()) {
	        // System.out.println("Reading files under the folder "+folder.getAbsolutePath());
	        chooseFolder(fileEntry);
	      } else {
	        if (fileEntry.isFile()) {
	          temp = fileEntry.getName();
	          if ((temp.substring(temp.lastIndexOf('.') + 1, temp.length()).toLowerCase()).equals("java")){
	            
	        	  //System.out.println("File= " + folder.getAbsolutePath()+ "\\" + fileEntry.getName());
	        	  
	        	  String path = folder.getAbsolutePath()+ "\\" + fileEntry.getName();
	        	  try {
	      			//deadFliedTestSmell("E:\\Java\\SpamBase\\src\\util\\TabelaLimiar.java");
	      			chooseFile(path);
	      		  } catch (IOException e) {
	      			// TODO Auto-generated catch block
	      			e.printStackTrace();
	      		  } 
	      			
	        	  
	          }
	            
	        
	        }

	      }
	    }
	  }
	  
	  public String showErrors() {
		  return errorLog;
	  }
	  
	  public static int getErrorCount() {
		return errorCount;
	}

	public static void printMessage( String path ,ArrayList<ErrorRecords> errors) {
		  System.out.println("File Path = " + path);
		  errorLog = errorLog + "File Path = " + path +'\n' +'\n';
		  System.out.println();
		  for(int i=0 ; i <errors.size() ; i++ ) {
			  System.out.println("Line Number = " + errors.get(i).getLineNumber() ); 
			  System.out.println("Error = "+ errors.get(i).getError());
			  
			  errorCount++;
			  errorLog = errorLog + "Line Number = " + errors.get(i).getLineNumber()   +'\n';
			  errorLog = errorLog +"Error = "+ errors.get(i).getError()  +'\n';
		  }
		  System.out.println();
		  System.out.println();
		  
		  errorLog = errorLog +'\n'+'\n'; 
	  }
	  
	  public static void chooseFile(String fileName) throws IOException {
		  
		  File file = new File(fileName); 
		  
		  BufferedReader br = new BufferedReader(new FileReader(file)); 
		  boolean fileShow = false;
		  ArrayList<ErrorRecords> errors = new ArrayList<ErrorRecords>();
		  
		  String st="";
		  int lineNumber=0;
		  
		  while ((st = br.readLine()) != null) {
			  lineNumber++;
			  
			  String currentLine = st;
			  currentLine = currentLine.trim();
			  String merger = "";
			  if(currentLine.contains("assertEquals")) {
				  merger = currentLine;
				  String stTemp = st;
				  while ((st = br.readLine()) != null) {
				  	 
				  	  st = st.trim();
				  	  stTemp = stTemp.trim();
				  	  
				  	  if(stTemp.contains(".toString()")) {
				  		/*  
				  		 System.out.println("Line Number " + lineNumber ); 
						 System.out.println("Merger = "+ stTemp);
						 */
				  		  ErrorRecords newError = new ErrorRecords(lineNumber, stTemp);
				  		  errors.add(newError);
				  		  fileShow = true;
				  	  }
					  merger = merger + st;
					  
					  lineNumber++;
					  if(stTemp.contains(";")) {
						  break;
					  }
					  stTemp = st;

				  		
				  }
				  /*
				  if(merger!="") {
					  System.out.println("Line Number " + lineNumber ); 
					  System.out.println("Merger = "+ merger);
			  
				  }
				  */
			  }
			 
		  }
		  if(fileShow == true) {
			  printMessage(fileName,errors);
		  }
		  
		  
	  }
	  
	  public static void deadFliedTestSmell(String fileName) throws IOException {
		  
		  File file = new File(fileName); 
		  
		  BufferedReader br = new BufferedReader(new FileReader(file)); 
		  
		 // System.out.println(file.getName());
		  String st="";
		  Integer secondBracketCount = 0;
		  int i=0;
		  ArrayList<String> allVariables = new ArrayList<String>();
		  
		  while ((st = br.readLine()) != null) {
			  i++;
			  
			 
			  if(st.contains("{")) {
				  secondBracketCount++;
				  /*
				  System.out.println("Line Number " + i ); 
				  System.out.println(st);
				  System.out.println("Second Bracket Counter == "  + secondBracketCount ); 
				  System.out.println();
				  */
			  }
			  
			  if(st.contains("}")) {
				  secondBracketCount--;
				  /*
				  System.out.println("Line Number " + i ); 
				  System.out.println(st);
				  System.out.println("Second Bracket Counter == "  + secondBracketCount ); 
				  System.out.println();
				  */
			  }
			  
			  if(secondBracketCount==1) {
				  if(st.endsWith(";")) {
					  
					  String currentLine = st;
					  currentLine = currentLine.trim();
					/*
					  System.out.println("Line Number " + i ); 
					  System.out.println(currentLine);
					  System.out.println("Second Bracket Counter == "  + secondBracketCount ); 
					  System.out.println();
					  
					 */
					  currentLine = currentLine.replace(";", "");
					  currentLine = currentLine.replace("[", "");
					  currentLine = currentLine.replace("]", "");
					  currentLine = currentLine.trim();
					  String [] wordArray = currentLine.split(" "); 
					  
				      for (String word : wordArray) {
				          //System.out.println(word); 
				      }
				      
				      //System.out.println();
				      //System.out.println("Variable Name = " + wordArray[wordArray.length-1]);
				      
				      allVariables.add(wordArray[wordArray.length-1]);
				  }
			  }
			  
			  
			  if(st.contains("testUp") ) {
				  //System.out.println(st + "\nLine Number " + i ); 
			  }
			 
		    
		  }
		  

		  for(String s: allVariables) {
			 // System.out.println(s);
		  }
			  
		  BufferedReader br2 = new BufferedReader(new FileReader(file)); 
		  
		  int ct=0;
		  
		  while ((st = br2.readLine()) != null) {
			  ct++;
			  
			  String currentLine = st;
			  currentLine = currentLine.trim();
			/*
			  System.out.println("Line Number " + i ); 
			  System.out.println(currentLine);
			  System.out.println("Second Bracket Counter == "  + secondBracketCount ); 
			  System.out.println();
			  
			 */
			  currentLine = currentLine.replace(";", "");
			  currentLine = currentLine.replace("[", "");
			  currentLine = currentLine.replace("]", "");
			  currentLine = currentLine.trim();
			  //System.out.println(st);
			  
			  if(currentLine.contains("{")) {
				  secondBracketCount++;
			  }
			  
			  if(currentLine.contains("}")) {
				  secondBracketCount--;
			  }
			  
			  if(secondBracketCount!=1 && secondBracketCount!=0 ) {
				  
				  if(currentLine.contains("{") ) {
					  String firstBracket = currentLine.substring(currentLine.lastIndexOf("(")+1,
							  currentLine.lastIndexOf(")"));
					 // System.out.println(firstBracket);
					  firstBracket = firstBracket.replace(",", "");
					  firstBracket = firstBracket.replace("[", "");
					  firstBracket = firstBracket.replace("]", "");
					  String [] firstBracketWordArray = firstBracket.split(" "); 

					  ArrayList<String> checkVariable = new ArrayList<String>();
					  
					  for (String word : firstBracketWordArray) {
				    	  for(String s : allVariables) {
				    		  if(word.contains(s)) {
				    			//  System.out.println("Line Number " + ct ); 
				    			  checkVariable.add(s);
								  System.out.println(word);
				    		  }
				    	  }
				          //System.out.println(word); 
				      }
					  
					  String afterSecondBracket = currentLine.substring(currentLine.lastIndexOf("{")+1); 
					  // System.out.println(afterSecondBracket);
					  String [] wordArray = afterSecondBracket.split(" "); 
					  
				      for (String word : wordArray) {
				    	  for(String s : allVariables) {
				    		  if(word.contains(s)) {
				    			//  System.out.println("Line Number " + ct ); 
								  System.out.println(word);
				    		  }
				    	  }
				          //System.out.println(word); 
				      }

				  
				  }
				  for(String s : allVariables) {
					  if(currentLine.contains(s)) {
						//  System.out.println("Line Number " + ct ); 
						//  System.out.println(currentLine);
					  }
				  }
			  }
			  
			  
		  }
		  
		  
	  }
	  
	  
}
