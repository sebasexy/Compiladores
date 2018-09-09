package states;

public class StateContext {
	
	State state;
	String token;
	
	public StateContext(){
		this.state = new InitialState();
	}
	
	public void setState(State state, String token){
		this.state = state;
		this.token = token;
	}
	
	public void nextState(char c){
		this.state.nextState(this, c);
	}
	
	public boolean isValidState(){
		return this.state.isValidState();
	}
	
	public String classType(){
		return this.state.classType();
	}
	
	public String getInfo(){
		return this.token;
	}
	
}
