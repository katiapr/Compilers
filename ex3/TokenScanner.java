/*
 * Author:Katia Prigon
 * Author:Lee Biton
 * ID:322088154
 * ID: 304883903
 * -Lexical-analyzer-
 */

import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.File;
public class TokenScanner {

	CharReader charReader;
	private char currChar;
	public TokenScanner(String fileName)
	{
		File file = new File(fileName);
		charReader = new CharReader(file);
		currChar = charReader.getChar();
		
	}

	public TokenInfo yylex()
	{
		int numOfLine = charReader.getNumOfLine();
		switch(currChar)
		{
		case '{':
			currChar = charReader.getChar(); //before return, we get next char
			return new TokenInfo(TokenInfoEnum.LC, null, numOfLine); //LP is left parenthesis
		case '}':
			currChar = charReader.getChar();
			return new TokenInfo(TokenInfoEnum.RC, null, numOfLine);
		case '(':
			currChar = charReader.getChar();
			return new TokenInfo(TokenInfoEnum.LP, null, numOfLine);
		case ')':
			currChar = charReader.getChar();
			return new TokenInfo(TokenInfoEnum.RP, null, numOfLine);
		case '='://==
			currChar = charReader.getChar();
			if(currChar == '=')
			{
				currChar = charReader.getChar();
				return new TokenInfo(TokenInfoEnum.REL, "==", numOfLine);
			}
			else return new TokenInfo(TokenInfoEnum.ASSIGN, null, numOfLine);

		case '>'://>=
			currChar = charReader.getChar();
			if(currChar == '=')
			{
				currChar = charReader.getChar();
				return new TokenInfo(TokenInfoEnum.REL, ">=", numOfLine);
			}

			else return new TokenInfo(TokenInfoEnum.REL, ">", numOfLine);
		case '<'://<=
			currChar = charReader.getChar();
			if(currChar == '=')
			{
				currChar = charReader.getChar();
				return new TokenInfo(TokenInfoEnum.REL, "<=", numOfLine);
			}

			else return new TokenInfo(TokenInfoEnum.REL, "<", numOfLine);

		case '\n':
			currChar = charReader.getChar();
			return new TokenInfo(TokenInfoEnum.WHITE, null, numOfLine);

		case '+':
			currChar = charReader.getChar();
			return new TokenInfo(TokenInfoEnum.PLUS, null, numOfLine);

		case '-':
			currChar = charReader.getChar();
			return new TokenInfo(TokenInfoEnum.MINUS, null, numOfLine);

		case '*':
			currChar = charReader.getChar();
			return new TokenInfo(TokenInfoEnum.MULT, null, numOfLine);

		case '/'://DIV + // + /**/
			currChar = charReader.getChar();
			if(currChar == '/')
			{
				currChar = charReader.getChar();
				while(true)
				{
					currChar = charReader.getChar();
					if(currChar == '\n')
						break;
				}
				return new TokenInfo(TokenInfoEnum.COMMNT, null, numOfLine);
			}
			if(currChar == '*')
			{
				currChar = charReader.getChar();
				while(true)
				{
					if(currChar == '*')
					{
						currChar = charReader.getChar();
						if(currChar == '/')
						{
							currChar = charReader.getChar();
							return new TokenInfo(TokenInfoEnum.COMMNT, null, numOfLine);
						}
					}
					currChar = charReader.getChar();
				}
			}
			else return new TokenInfo(TokenInfoEnum.DIV, null, numOfLine);
		case '!':
			currChar = charReader.getChar();
			if(currChar == '=')
			{
				currChar = charReader.getChar();
				return new TokenInfo(TokenInfoEnum.REL, "!=", numOfLine);
			}
		case ';':
			currChar = charReader.getChar();
			return new TokenInfo(TokenInfoEnum.SC, null, numOfLine);
		case '0':
			currChar = charReader.getChar();
			return new TokenInfo(TokenInfoEnum.NUM, "0", numOfLine);
		case '&'://&&
			currChar = charReader.getChar();
			if(currChar == '&')
			{
				currChar = charReader.getChar();
				return new TokenInfo(TokenInfoEnum.AND, null, numOfLine);
			}
		case '|':// ||
			currChar = charReader.getChar();
			if(currChar == '|')
			{
				currChar = charReader.getChar();
				return new TokenInfo(TokenInfoEnum.OR, null, numOfLine);
			}
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////
		//if we are here, then its not a sign or operator. it must either be an identifier or reserved word.
		//(*) WHITE
		if(currChar == ' ' || currChar == '\t')
		{
			while(currChar == ' ' || currChar == '\t')
				currChar = charReader.getChar();

			return new TokenInfo(TokenInfoEnum.WHITE, null, numOfLine);
		}
		//(*) NUM
		if(currChar >= '1' && currChar <= '9')
		{
			
			StringBuilder strbld = new StringBuilder();
			strbld.append(currChar);
			currChar = charReader.getChar();
			while(currChar >= '0' && currChar <= '9')
			{
				strbld.append(currChar);
				currChar = charReader.getChar();
			}
			return new TokenInfo(TokenInfoEnum.NUM, strbld.toString(), numOfLine);
		}
		//(*) a-z
		if(currChar >='a' && currChar <= 'z')
		{
			StringBuilder strbld = new StringBuilder();
			while(currChar >='a' && currChar <= 'z' || 
					currChar >='A' && currChar <= 'Z' ||
					currChar >='0' && currChar <= '9')
			{
				strbld.append(currChar);
				currChar = charReader.getChar();
			}
			return new TokenInfo(TokenInfoEnum.ID, strbld.toString(), numOfLine);
		}
		//A-Z
		if(currChar >='A' && currChar <= 'Z')
		{
			StringBuilder strbld = new StringBuilder();
			while(currChar >='a' && currChar <= 'z' ||
					currChar >='A' && currChar <= 'Z'||
					currChar >='0' && currChar <= '9')
			{
				strbld.append(currChar);
				currChar = charReader.getChar();
			}
			return new TokenInfo(TokenInfoEnum.FID, strbld.toString(), numOfLine);
		}
		return null;
	}
}

class CharReader{

	private int lineNum;
	private int  index;
	private String line;
	private LineNumberReader lnr;
	private FileReader fr;


	public CharReader(File f)
	{
		try {
			fr= new FileReader(f);
			lnr= new LineNumberReader(fr);
			line=lnr.readLine();
			lineNum=lnr.getLineNumber();
			index=0;
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public char getChar()
	{
		if(index < line.length())
			return line.charAt(index++);
		if( index == line.length())
		{
			index++;
			return '\n';
		}
		try{
			line = lnr.readLine();
			lineNum++;

		}catch(Exception e){
			e.printStackTrace();
		}
		index = 0;
		if(line != null && !line.equals(""))
			return line.charAt(index++);
		else if(line != null)
			return '\n';

		return (char)-1;//EOF
	}
	public int getNumOfLine()
	{
		return this.lineNum;
	}
}
enum TokenInfoEnum {

	INT, FUNC, MAIN, IF, THEN, ELSE, NUM, ID, FID, ASSIGN, 
	PLUS, MINUS, MULT, DIV, AND, OR, REL, SC, LP, RP, LC,
	RC, COMMNT, WHITE, EOF
}

