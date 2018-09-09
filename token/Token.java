package token;

public class Token {
	//El id, tipo, línea, posición
	private int line;
	private int column;
	private String type;
	private String id;
	
	public Token(int line, int column, String type, String id){
		this.line = line;
		this.column = column;
		this.type = type;
		this.id = id;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}

	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

}
