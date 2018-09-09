package lexxx;

public class LexicalError {
	
	private int errorLine;
	private int errorCharacter;
	private String errorString;
	
	public LexicalError(int errorLine, int errorCharacter, String errorString){
		this.errorLine = errorLine;
		this.errorCharacter = errorCharacter;
		this.errorString = errorString;
	}
	
	public int getErrorLine(){
		return this.errorLine;
	}
	
	public int getErrorCharacter(){
		return this.errorCharacter;
	}
	
	public String getErrorString(){
		return this.errorString;
	}
	
	public void printErrors(){
		System.out.println("Line: " + this.getErrorLine() + " Char: " + this.getErrorCharacter() + " String: " + this.getErrorString());
	}

}
