package states;

public class Q5 implements State{

	@Override
	public void nextState(StateContext stateContext, char c) {
		stateContext.setState(new InitialState(), Character.toString(c));
		stateContext.nextState(c);
	}

	@Override
	public boolean isValidState() {
		return true;
	}
	
	public String classType(){
		return "Q5";
	}
}
