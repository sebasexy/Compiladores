package states;

public class Q6 implements State{

	@Override
	public void nextState(StateContext stateContext, char c) {
		//System.out.println("My tokens in Q6() : " + stateContext.token);
		if(c == '='){
			stateContext.setState(new Q6(), stateContext.token + c);
		}else if(c == ' '){
			stateContext.setState(new InitialState(), "");
		}
		else{
			stateContext.setState(new DeadState(), stateContext.token + c);
		}	
	}

	@Override
	public boolean isValidState() {
		return true;
	}
	
	public String classType(){
		return "Q6";		
	}
}
