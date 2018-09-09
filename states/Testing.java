package states;

public class Testing {

	
	public static void main(String[] args) {
		
		StateContext state = new StateContext();
		Character[] tokens = {'a', '=', '(', '(', '1', 'e', '+'};
		System.out.println(state.classType());
		for(int i = 0; i < tokens.length; i++){
			state.nextState(tokens[i]);
			System.out.println(state.classType());
		}
		System.out.println(state.isValidState());
	}
}
