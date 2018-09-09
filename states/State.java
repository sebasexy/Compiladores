package states;

public interface State {
	
	public void nextState(StateContext stateContext, char c);
	public boolean isValidState();
	public String classType();
}
