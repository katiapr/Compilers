import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


/*
 * Author:Katia Prigon
 * ID:322088154
 * -Lexical-analyzer-
 */
public class ParserMain {


	public static void main(String[] args)
	{
		int numOfLine = 0;//,numOfToken = 0;
		File file = new File(args[0]);
		TokenInfo tokenInfo = new TokenInfo();
		ParserMain p = new ParserMain();
		try {

			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				numOfLine++;
				
				System.out.println(numOfLine+" "+line);
				p.ManageLine(line,numOfLine);
				//				tokenInfo.yylex(line);
			}
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void ManageLine(String line,int numOfLine)
	{
		System.out.println("Manage Line");
		//String word = "";
		for (int i = 0; i < line.length(); i++) 
		{
			System.out.println(line.charAt(i));
		}
	}
}
