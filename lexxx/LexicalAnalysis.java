package lexxx;

import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.naming.InitialContext;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import states.InitialState;
import states.StateContext;


public class LexicalAnalysis {
	
	private final static String path= "C:\\Users\\spide\\Desktop\\Tec\\9o semestre\\Compiladores\\Compilador";
	private boolean haveFoundSpecial = false;
	private int whiteSpaceCounter = 0;
	
	private final static List<String> keyWords = new ArrayList<String>(Arrays.asList("principal", "entero", "real", "logico", "si", "mientras", "regresa", "falso", "verdadero"));	
	private final static List<Character> whiteSpaces = Arrays.asList('\t', '\n', '\r', ' ');
	private final static List<Integer> wholeNumbersConstant = new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,8,9));
	private final static List<Character> arithmeticOperator = Arrays.asList('+', '-', '*', '/', '^');
	private final static List<Character> relationalOperator = Arrays.asList('>', '<', '=');
	private final static List<Character> logicalOperator = Arrays.asList('&', '|', '!');
	private final static List<Character> punctuation = Arrays.asList(',', ';','(', ')', '{', '}');
	private final static char asignationOperator = '=';
	private final static char WHITESPACE = ' ';
	
	private StateContext state;
	private List<LexicalError> error;
	
	
	public LexicalAnalysis(){
		state = new StateContext();
		error = new ArrayList<LexicalError>();
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
	
	public void writeFile(File file){
		
	}
	
	
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
					*/
				}
				//System.out.println("Char : " + currChar + " \nState: " + this.state.classType());
			}
		}
		tokens.add(this.state.getInfo());
		
		return tokens;
	}
	
	public List<LexicalError> getErrors(){
		return this.error;
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
		
		System.out.println("Preprocessed text: ");
		for(int i = 0; i < preprocessedText.size(); i++){
			System.out.print(preprocessedText.get(i));
		}
		System.out.println();
		
		automata = sexxxAnalysis.finiteAutomata(preprocessedText);
		

		System.out.println("By tokens:" );
		for(int i = 0; i < automata.size(); i++){
			System.out.println(automata.get(i));
		}
	}
}
