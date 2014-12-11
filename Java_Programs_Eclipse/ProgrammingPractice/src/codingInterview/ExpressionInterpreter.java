package codingInterview;

import java.util.Stack;

public class ExpressionInterpreter {
	
	private Stack<Integer> exprSt = new Stack();
	private Stack<Integer> resultsSt = new Stack();
	
	public int exprInterpret(String expr){
		int result = 0;			
		
		String[] exprArr = expr.split("\\s+");
		
		for(int i = (exprArr.length -1); i >= 1 ; i--){
			
			
			if(exprArr[i].matches(".*\\d+.*")){
				//System.out.println(Integer.parseInt(exprArr[i].replaceAll("[^0-9]", "")));
				this.exprSt.push(Integer.parseInt(exprArr[i].replaceAll("[^0-9]", "")));
				
			}
			if(exprArr[i].matches(".*[-+\\*].*")){
				//System.out.println(exprArr[i].replaceAll("[^-+\\*]", ""));
				evaulateExpr(exprArr[i].replaceAll("[^-+\\*]", ""));
			}
		}
		
		String firstSign = exprArr[0].replaceAll("[^-+\\*]", "");		
		
		if(resultsSt.isEmpty())
			resultsSt = exprSt;
		
		if(firstSign.matches(".*[-+\\*].*")){
			switch(firstSign){
			case "+":
				result = resultsSt.pop();				
				while(!resultsSt.isEmpty()){
					result += resultsSt.pop();
				}
				break;
			
			case "-":
				result = resultsSt.pop();				
				while(!resultsSt.isEmpty()){
					result -= resultsSt.pop();
				}
				break;
				
			case "/":
				result = resultsSt.pop();				
				while(!resultsSt.isEmpty()){
					result /= resultsSt.pop();
				}
				break;
			
			case "*":
				result = resultsSt.pop();				
				while(!resultsSt.isEmpty()){
					result *= resultsSt.pop();
				}
				break;
		
			}
		}
		
		System.out.println(expr + " =");
		return result;
		
	}

	private void evaulateExpr(String expChr) {
		int result = 0;
		switch(expChr){
			case "+":
				result = this.exprSt.pop();				
				while(!this.exprSt.isEmpty()){
					result += this.exprSt.pop();
				}
				this.resultsSt.push(result);
				break;
			
			case "-":
				result = this.exprSt.pop();				
				while(!this.exprSt.isEmpty()){
					result -= this.exprSt.pop();
				}
				this.resultsSt.push(result);
				break;
			
			case "/":
				result = this.exprSt.pop();				
				while(!this.exprSt.isEmpty()){
					result /= this.exprSt.pop();
				}
				this.resultsSt.push(result);
				break;
			
			case "*":
				result = this.exprSt.pop();				
				while(!this.exprSt.isEmpty()){
					result *= this.exprSt.pop();
				}
				this.resultsSt.push(result);
				break;
		
		}
		
		//System.out.println(this.resultsSt);
			
		
	}
}
