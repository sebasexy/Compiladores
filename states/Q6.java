package states;

public class Q6 implements State{

	@Override
	public void nextState(StateContext stateContext, char c) {
		if(c == '='){
			stateContext.setState(new Q6(), stateContext.token + c);
		}else{
			stateContext.setState(new InitialState(), "");
			stateContext.nextState(c);
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
