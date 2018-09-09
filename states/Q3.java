package states;

import java.util.Arrays;
import java.util.List;

public class Q3 implements State{

	private final static List<Character> punctuation = Arrays.asList(',', ';','(', ')', '{', '}');
	private final static List<Character> arithmeticOperator = Arrays.asList('+', '-', '*', '/', '^');
	private final static List<Character> relationalOperator = Arrays.asList('>', '<', '=');
	
	@Override
	public void nextState(StateContext stateContext, char c) {
		if(c >= '0' && c <= '9'){
			stateContext.setState(new Q3(), stateContext.token + c);
		}
		/*else if(punctuation.contains(c)){
			stateContext.setState(new Q5(), Character.toString(c));
		}
		else if(arithmeticOperator.contains(c) || relationalOperator.contains(c)){
			stateContext.setState(new Q6(), Character.toString(c));
		}
		else if(c == ' '){
			//System.out.println("Reading "  + c + " sending to inital state");
			stateContext.setState(new InitialState(), "");
		}
		*/
		else{
			stateContext.setState(new DeadState(), stateContext.token + c);
		}
	}

	@Override
	public boolean isValidState() {
		return true;
	}
	
	public String classType(){
		return "Q3";		
	}
}
