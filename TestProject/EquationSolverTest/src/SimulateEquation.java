import java.rmi.server.Operation;
import java.util.*;
public class SimulateEquation {
        
	String exp;
	Stack operator,operand;
	int op1,op2;
	
     public int expressionSimulation()
     {
    	  operator = new Stack();
    	  operand = new Stack();
    	  
    	  exp=this.addSpace(exp);
    	//  System.out.println(exp);
    	  String[] temp = this.addSpace(exp).split(" ");
    	 
    	  
    	   String[] input = new String [1000];
    	  int id=0;
    	  
    	  for(int i=0;i<temp.length;i++)
    	  {
    		  if(!temp[i].equals(""))
    			  input[id++]=temp[i];
    	  }
    	  
    	  System.out.println("OK");
    	 
    	  
    	 // System.out.println("OK"+id+" "+input.length);
    	  for(int i=0;i<id;i++)
    	  {
    		  //System.out.println(input[i]);
    		  if(isOperator(input[i]))
    		  {
    			  if(operator.empty() || input[i].equals("(") || operator.peek().toString().equals("("))
    			  {
    				  operator.push(input[i]);
    			  }
    			  
    			  else
    			  {
    				  String operatr;
    				  int ans;
    				  
    				  if(input[i].equals(")"))
    				  {
    					  while(!operator.peek().toString().equals("("))
    					  {
    						  op1 = Integer.parseInt(operand.peek().toString());
    						  operand.pop();
    						  op2 = Integer.parseInt(operand.peek().toString());
    						  operand.pop();
    						  operatr=operator.peek().toString();
    						  operator.pop();
    						   ans = opration(op1, op2, operatr); 
    						   System.out.println(op1+" "+op2+" "+ans); 
    						  operand.push(ans);
    								  
    					  }
    					  
    					  operator.pop();
    				  }
    				  
    				  else
    				  {
    					  while(!operator.empty() && getValue(operator.peek().toString()) >= getValue(input[i]))
    					  {
    						  operatr = operator.peek().toString();
    					  operator.pop();
    					  op1 = Integer.parseInt(operand.peek().toString());
						  operand.pop();
						  op2 = Integer.parseInt(operand.peek().toString());
						  operand.pop();
    					  
						  ans = opration(op1, op2, operatr);
						  //System.out.println(ans);
						  //System.out.println(op1+" "+op2+" "+ans); 
						  operand.push(ans);
    					  
    					  }
    					  
    					  operator.push(input[i]);
    				  }
    				  
    			  }
    			  
    		  }
    		  
    		  else
    			 operand.push(input[i]);
    		  
    	  }
    	  
    	//  System.out.println(operator.empty());
    	  
    	  while(!operator.empty())
    	  {
    		  String operatr = operator.peek().toString();
			  operator.pop();
			  op1 = Integer.parseInt(operand.peek().toString());
			  operand.pop();
			  op2 = Integer.parseInt(operand.peek().toString());
			  operand.pop();
			  
			  int ans = opration(op1, op2, operatr);
		//	  System.out.println(ans);
			  operand.push(ans);
			    
    	  }
    	  
    	System.out.println(operand.peek());
    	return (int) operand.peek();  
     }
	
     
	
	 int getValue(String str)
	 {
		 if(str.equals("("))
			 return 1;
		 
		 else if(str.equals("+")||str.equals("-"))
			 return 2;
		 
		 else
			 return 3;
	 }
	
	
	int opration(int b, int a,String opeartor)
	{
		if(opeartor.equals("+"))
			return a+b;
		
		else if(opeartor.equals("-"))
			return a-b;
		
		else if(opeartor.equals("/"))
			return a/b;
		 
		else //if(opeartor.equals("-"))
			return a*b;
		
	}
	
	
	boolean isOperator(String str)
	{
		if(str.equals("+")||str.equals("-")||str.equals("*")||str.equals("/") || str.equals("(") ||
				str.equals(")"))
	     return true;
		
		else
			 return false;
	     
	}
	
	boolean isoperator(char ch)
	{
		if(ch=='+'||ch=='-'||ch=='*'||ch=='/'||ch=='('||ch==')')
			 return true;
		 
		 else
			 return false;
	}
	
	 public String addSpace(String eqsn)
	 {
		 String output = "";
		 for(int i=0;i<eqsn.length();i++)
		 {
			 if(isoperator(eqsn.charAt(i)))
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
	//	 System.out.println("OK"+output);
		 return output;
	 }
	 
	
}
