package states;

import java.util.Arrays;
import java.util.List;

public class DeadState implements State {
	
	private final static List<Character> punctuation = Arrays.asList(',', ';','(', ')', '{', '}');
	private final static List<Character> arithmeticOperator = Arrays.asList('+', '-', '*', '/', '^');
	private final static List<Character> relationalOperator = Arrays.asList('>', '<', '=');
	private final static List<Character> logicalOperator = Arrays.asList('&', '|', '!');
	
	private final static char WHITESPACE = ' ';
	@Override
	public void nextState(StateContext stateContext, char c) {
		if(c == WHITESPACE){
			stateContext.setState(new InitialState(), "");
		}
		else if(punctuation.contains(c) ||
				arithmeticOperator.contains(c) ||
				relationalOperator.contains(c) || 
				logicalOperator.contains(c)){
			stateContext.setState(new InitialState(), "");
			stateContext.nextState(c);
			
		}else{
			stateContext.setState(new DeadState(), stateContext.token + c);
		}
		
	}

	@Override
	public boolean isValidState() {
		return false;
	}
	
	public String classType(){
		return "DEADSTATE";		
	}
	
}
