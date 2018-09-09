package states;

public class Q5 implements State{

	@Override
	public void nextState(StateContext stateContext, char c) {
		
		stateContext.setState(new DeadState(), stateContext.token + c);
		
		/*
		if(c == ' '){
			//System.out.println("Reading "  + c + " sending to initial state");
			stateContext.setState(new InitialState(), "");
		}else{
			stateContext.setState(new DeadState(), stateContext.token + c);
		}
		*/		
		
	}

	@Override
	public boolean isValidState() {
		return true;
	}
	
	public String classType(){
		return "Q5";
	}
}
