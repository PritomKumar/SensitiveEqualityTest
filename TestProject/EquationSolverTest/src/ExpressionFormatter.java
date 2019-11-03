
import java.util.HashMap;
import java.util.Map;
import java.util.*;


public class ExpressionFormatter {
    
	 public String input,rightExp;
     Map<Character, String> variable = new HashMap<Character, String>();
     Map<Character, Integer> variableList = new HashMap<Character, Integer>();
     
  public ExpressionFormatter(String str) {
        
      input="";
      
      for(int i=0;i<str.length();i++)
      {
    	  if(str.charAt(i)!=' ')
    		  input+=str.charAt(i);
      }
      
       System.out.println("AAAA");
      findVariable();
  }
  
  private void findVariable()
  {
      int id=0;
      while(id<input.length() && input.indexOf("let", id)!=-1)
      {
          id=input.indexOf("let",id);
          
          char ch = input.charAt(id+3);
          id+=5;
          
          String value="";
          //System.out.println(input.charAt(id));
          while(id<input.length() && input.charAt(id)>='0' && input.charAt(id)<='9')
          {
              //System.out.println("ok");
              value+=input.charAt(id++);
          }
          variable.put(ch,value);
          //System.out.println(value);
          //input = remove(id,id+7);
          
         // System.out.println(id);
      }
      
    //  System.out.println(variable.get('x'));
  //    modifyString();
      setVariableValue();
  }
  
  private void setVariableValue()
  {
     String str = modifyString();
     String rightEqsn="";
     
     for(int i=0;i<str.length();i++)
     {
         if((str.charAt(i)>='a' && str.charAt(i)<='z') || (str.charAt(i)>='A' && str.charAt(i)<='Z'))
         {
             if(variable.get(str.charAt(i))!=null)
             {
                 rightEqsn+=variable.get(str.charAt(i));
             }
            
         }
          else
              rightEqsn+=str.charAt(i);   
     }
     
     this.rightExp=rightEqsn;
     //this.getRightExp(r);
     //System.out.println(str);
     //System.out.println("ok "+rightEqsn);
  }
  
  public String getRightExp()
  {
	  
	  return rightExp;
  }
  public String modifyString()
  {
      String str="";
      
      for(int i=0;i<input.length();i++)
      {
          // System.out.println(input.charAt(i));
          if(input.charAt(i) =='(' && input.charAt(i+1) =='l' && input.indexOf("let", i)!=-1)
          {
              i=input.indexOf(")", i);
              
           //    System.out.println("Index: "+i);
          }
          else
          {
              str+=input.charAt(i);
          }
         // System.out.println(str);
      }
     
    // System.out.println("Rifat");
     return str;
  }
  
  public String remove(int start, int end)
  {
      String str="";
      
      for(int i=0;i<start;i++)
          str+=input.charAt(i);
      
      for(int i=end+1;i<input.length();i++)
          str+=input.charAt(i);
      
      return str;
  }
      
      
}
	
	

