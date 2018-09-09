package lexxx;

public class LexicalError {
	
	private short errorLine;
	private short errorCharacter;
	
	public LexicalError(short errorLine, short errorCharacter){
		this.errorLine = errorLine;
		this.errorCharacter = errorCharacter;
	}
	
	public short getErrorLine(){
		return this.errorLine;
	}
	
	public short getErrorCharacter(){
		return this.errorCharacter;
	}

}
