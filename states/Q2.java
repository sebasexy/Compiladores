package states;

public class Q2 implements State{

	@Override
	public void nextState(StateContext stateContext, char c) {
		if(c >= '0' && c <= '9'){
			stateContext.setState(new Q3(), stateContext.token + c);
		}
		else{
			stateContext.setState(new DeadState(), stateContext.token + c);
		}		
	}

	@Override
	public boolean isValidState() {
		return false;
	}
	
	public String classType(){
		return "Q2";		
	}

}
