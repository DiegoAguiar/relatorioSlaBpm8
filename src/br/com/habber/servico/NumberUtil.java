package br.com.habber.servico;

public class NumberUtil {

	public static boolean isNumeric(String str){  
	   try{  
	      Double.parseDouble(str);  
	      return true;  
	   }catch(NumberFormatException e){  
	      return false;  
	   }  
	}
}
