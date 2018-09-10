package lexxx;

import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.naming.InitialContext;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import states.InitialState;
import states.*;
import states.StateContext;
import token.Token;


public class LexicalAnalysis {
	
	private final static String path= "C:\\Users\\spide\\Desktop\\Tec\\9o semestre\\Compiladores\\Compilador";
	private final static String symbolTable = "symbols.txt";
	private final static String errorFile = "error.txt";
	
	private boolean haveFoundSpecial = false;
	private int whiteSpaceCounter = 0;
	
	private final static List<String> keyWords = new ArrayList<String>(Arrays.asList("principal", "entero", "real", "logico", "si", "mientras", "regresa", "falso", "verdadero"));	
	private final static List<Character> whiteSpaces = Arrays.asList('\t', '\n', '\r', ' ');
	private final static List<Character> arithmeticOperator = Arrays.asList('+', '-', '*', '/', '^');
	private final static List<Character> relationalOperator = Arrays.asList('>', '<', '=');
	private final static List<Character> logicalOperator = Arrays.asList('&', '|', '!');
	private final static List<Character> punctuation = Arrays.asList(',', ';','(', ')', '{', '}');
	
	private StateContext state;
	private List<LexicalError> error;
	private List<Token> tokenTable;
	
	
	public LexicalAnalysis(){
		state = new StateContext();
		error = new ArrayList<LexicalError>();
		tokenTable = new ArrayList<Token>();
	}
	
	public File readFile(){
		
		JFileChooser fileChooser = new JFileChooser(path);
		File file = null;
		int returnVal = fileChooser.showOpenDialog(new JFrame());
		if(returnVal == JFileChooser.APPROVE_OPTION){
			file = fileChooser.getSelectedFile();
		}
		return file;
	}
	
	public List<String> preprocess(File file){
		
		List<String> text_preprocessed = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		try(InputStream in = new FileInputStream(file)){
			
			Reader reader = new InputStreamReader(in);
			int c;
			while((c = reader.read()) != -1){
				if(!whiteSpaces.contains((char)c)){
					if(haveFoundSpecial){
						haveFoundSpecial = false;
						if(whiteSpaceCounter>0)
							builder.append(" ");
					}
					builder.append(Character.toString((char)c));
					whiteSpaceCounter++;
				}
				else{
					haveFoundSpecial = true;
					if((char)c == '\n'){
						text_preprocessed.add(builder.toString());
						builder = new StringBuilder();
					}
					
				}
			}
			text_preprocessed.add(builder.toString());
	
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		return text_preprocessed;
	}	
	
	public void writeSymbolTable(List<Token> token){
		try {
			PrintWriter writer = new PrintWriter(path+symbolTable, "UTF-8");
			System.out.println("Writing on: " + path+symbolTable);
			for(int i = 0; i < token.size(); i++){
				if(keyWords.contains(token.get(i).getId())){
					writer.println(token.get(i).getId() + "\t" + "PALABRA RESERVADA" +  "\t" + token.get(i).getLine() + "\t" + token.get(i).getColumn());
				}else{
					writer.println(token.get(i).getId() + "\t" + token.get(i).getType() + "\t" + token.get(i).getLine() + "\t" + token.get(i).getColumn());
				}				
			}
			writer.close();
		} catch (IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public void writeErrors(List<LexicalError> error){
		try{
			PrintWriter writer = new PrintWriter(path+errorFile, "UTF-8");
			System.out.println("Writing on: " + path+errorFile);
			if(error.size() > 0){
				for(int i = 0; i < error.size(); i++){
					writer.println(error.get(i).getErrorLine() + "\t" + error.get(i).getErrorCharacter() + "\t" + error.get(i).getErrorString());
				}
			}else{
				writer.println("No LeXXXical errors on your Code");
			}
			
			writer.close();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public List<String> finiteAutomata(List<String> characters){
		List<String> tokens = new ArrayList<String>();
		String currString;
		char tmpChar;
		char currChar;
		
		for(int i = 0; i < characters.size(); i++){
			currString = characters.get(i);
			for(int j = 0; j < currString.length(); j++){
				currChar = currString.charAt(j);
				
				if(isntSpecialCharacter(currChar)){
					this.state.nextState(currChar);
				}
				
				else{
					if(currChar == ' '){
						if(this.state.isValidState()){
							tokens.add(this.state.getInfo());
							tokenTable.add(new Token(i,j,this.state.classType(), this.state.getInfo()));
						}else{
							if(this.state.getInfo() != "" && this.state.getInfo() != null){
								System.out.println(this.state.getInfo());
								error.add(new LexicalError(i, j, this.state.getInfo()));
							}
						}
					}
					else if(currChar == '='){
						if(this.state.isValidState()){
							this.state.setState(new Q6(), "=");
							tokens.add(this.state.getInfo());
							tokenTable.add(new Token(i,j,this.state.classType(), this.state.getInfo()));
						}else{
							if(this.state.getInfo() != "" && this.state.getInfo() != null){
								System.out.println(this.state.getInfo());
								error.add(new LexicalError(i, j, this.state.getInfo()));
							}
						}
						if(j+1 < currString.length()){
							if((tmpChar = currString.charAt(j+1)) == '='){
								this.state.setState(new Q6(), "==");
								tokens.add("==");
								tokenTable.add(new Token(i,j, this.state.classType(), "=="));
								j = j+2;
							}else{
								tokens.add(Character.toString(currChar));
								tokenTable.add(new Token(i,j, "OPERADOR", Character.toString(currChar)));
							}
						}
						this.state.setState(new InitialState(), "");
					}
					else{
						if(this.state.isValidState()){
							tokens.add(this.state.getInfo());
							tokenTable.add(new Token(i,j,this.state.classType(), this.state.getInfo()));
						}else{
							if(this.state.getInfo() != "" && this.state.getInfo() != null){
								System.out.println(this.state.getInfo());
								error.add(new LexicalError(i, j-1, this.state.getInfo()));
							}
						}
						if(arithmeticOperator.contains(currChar) || relationalOperator.contains(currChar) || logicalOperator.contains(currChar)){
							this.state.setState(new Q6(), Character.toString(currChar));
						}
						if(punctuation.contains(currChar)){
							this.state.setState(new Q5(), Character.toString(currChar));
						}
						tokens.add(Character.toString(currChar));
						tokenTable.add(new Token(i,j+1,this.state.classType(), Character.toString(currChar)));
					}
					this.state.setState(new InitialState(), "");
				}
			}
		}
		return tokens;
	}
	
	/*
	public List<String> finiteAutomata(List<String> characters){
		List<String> tokens = new ArrayList<String>();
		String currString;
		char currChar;
		
		for(int i = 0; i < characters.size(); i++){
			currString = characters.get(i);
			for(int j = 0; j < currString.length(); j++){
				currChar = currString.charAt(j);
				
				if(isntSpecialCharacter(currChar)){
					this.state.nextState(currChar);
				}
				else{	
					if(currChar == ' '){
						if(this.state.isValidState()){
							tokens.add(this.state.getInfo());
						}else{
							error.add(new LexicalError(i, j, this.state.getInfo()));
						}
						this.state.setState(new InitialState(), "");
					}
					else{
						if(this.state.isValidState()){
							tokens.add(this.state.getInfo());
						}else{
							error.add(new LexicalError(i, j, this.state.getInfo()));
						}						
						tokens.add(Character.toString(currChar));
						this.state.setState(new InitialState(), "");
					}					
					/*
					if(this.state.isValidState()){
						tokens.add(this.state.getInfo());
					}
					else{
						error.add(new LexicalError(i, j, this.state.getInfo()));
					}
					if(currChar == )
					this.state.nextState(currChar);
				}
				//System.out.println("Char : " + currChar + " \nState: " + this.state.classType());
			}
		}
		tokens.add(this.state.getInfo());
		
		return tokens;
	}
	
	*/
	public List<LexicalError> getErrors(){
		return this.error;
	}
	public List<Token> getTokens(){
		return this.tokenTable;
	}
	
	/*
	public List<String> finiteAutomata(List<String> characters){
		List<String> tokens = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		String currString;
		char currChar;
		for(int i = 0; i < characters.size(); i++){
			currString = characters.get(i);
			for(int j = 0; j < currString.length(); j++){
				currChar = currString.charAt(j);
				if(isntSpecialCharacter(currChar)){
					builder.append(Character.toString(currChar));
				}else{
					if(builder.length()>0){
						tokens.add(builder.toString());
						if(currChar != WHITESPACE){
							tokens.add(Character.toString(currChar));
						}
						builder = new StringBuilder();
					}
				}
			}
		}
		return tokens;
	}
	*/
	
	private boolean isntSpecialCharacter(char c){
		return !whiteSpaces.contains(c) && !arithmeticOperator.contains(c) && !relationalOperator.contains(c) && !logicalOperator.contains(c) && !punctuation.contains(c);
	}
	
	public static void main(String[] args) {
		
		File fileToRead;
		List<String> preprocessedText;
		List<String> automata;
		LexicalAnalysis sexxxAnalysis = new LexicalAnalysis();
		List<LexicalError> errors = sexxxAnalysis.getErrors();
		
		fileToRead = sexxxAnalysis.readFile();
		preprocessedText = sexxxAnalysis.preprocess(fileToRead);
		automata = sexxxAnalysis.finiteAutomata(preprocessedText);
		
		System.out.println("Preprocessed text: ");
		for(int i = 0; i < preprocessedText.size(); i++){
			System.out.print(preprocessedText.get(i));
		}
		
		/*
		System.out.println("Tokens: ");
		for(int i = 0; i < automata.size(); i++){
			System.out.println(automata.get(i));
		}
		*/

		sexxxAnalysis.writeSymbolTable(sexxxAnalysis.getTokens());
		sexxxAnalysis.writeErrors(sexxxAnalysis.getErrors());
	}
}
