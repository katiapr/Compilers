//Katia Prigon 322088154

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Stack;


public class LL1Main {

	/**
	 * @param args
	 */

	// TODO Auto-generated method stub
	static BufferedWriter wr;
	static Stack<TokenInfoEnum> input;
	public int nodeID;
	public static void main(String[] args)
	{
		try{
			int i = 0;
			TokenInfo token = null;
			TokenScanner scanner = null;
			AST asTree = null;
			input = new Stack<TokenInfoEnum>();
			if(args.length == 2)
			{
				String outputFileName = args[1].substring(0, args[1].indexOf('.')) + ".ptree";
				scanner = new TokenScanner(args[1]);

				while((token = scanner.yylex()) != null)
				{
					if(token.getTypeToken() != TokenInfoEnum.WHITE)
					{
						input.add(token.getTypeToken());
					}
				}
				LL1Parser ll1 = new LL1Parser(args[0]);//init the data
				wr = new BufferedWriter(new FileWriter(outputFileName));//streamer
				String tree = ll1.yyLL1parser(input);
				WriteToFile(tree);

			}
			wr.close();
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			System.exit(1);
		}
	}

	public static void WriteToFile(String tree)
	{
		try {
			String filecont = "digraph G{\n"+tree +"\n}";
			wr.write(filecont);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
