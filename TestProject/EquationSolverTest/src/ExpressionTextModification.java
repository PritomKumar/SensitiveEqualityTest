public class ExpressionTextModification {
            
	 private String eqsn,output="";
	 
	 
	 public ExpressionTextModification(String str) {
	             
		    eqsn = str;
		    //this.addSpace();
		    //this.removeTrailingAndLeadingSpaces();
	 }
	 
	 public String getRightInput()
	 {
		 this.addSpace();
		 this.removeTrailingAndLeadingSpaces();
		 System.out.println(eqsn);
		 return eqsn;
	 }
	 
	 private void removeTrailingAndLeadingSpaces()
	 {
		 String str=output;
		 
		 //System.out.println("OK"+eqsn);
		 eqsn = "";
		 
		 boolean haveSpace=false;
		 for(int i=0;i<str.length();i++)
		 {
			 
			 if(haveSpace==false && str.charAt(i)==' ')
			 {
				 eqsn+=str.charAt(i);
				 haveSpace=true;
			 }
			 
			 else
			 {
				 eqsn+=str.charAt(i);
				 haveSpace=false;
			 }
			 
		 }

	 }
	 
	 private void addSpace()
	 {
		 for(int i=0;i<eqsn.length();i++)
		 {
			 if(isOperator(eqsn.charAt(i)))
			 {
				 output+=' ';
				 output+=eqsn.charAt(i);
				 output+=' ';
			 }
			 else
			 {
				 output+=eqsn.charAt(i);
			 }
			 
		 }
		 //System.out.println(output);
	 }
	 
	 private boolean isOperator(char ch)
	 {
		 if(ch=='+'||ch=='-'||ch=='*'||ch=='/'||ch=='('||ch==')')
			 return true;
		 
		 else
			 return false;
	 }
	 
}
