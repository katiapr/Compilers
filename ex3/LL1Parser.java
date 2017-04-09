import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;




public class LL1Parser {

	private Hashtable<String, Map<String,LinkedList<Node>>> LL1table;
	private NodeTerminal dollar;
	private Stack<Node> stack;
	private Node[] terminals;
	private Node[] nonTerminals;
	private static BufferedReader rd;
	private int id;

	public LL1Parser(String configFileName)
	{
		try{
			this.id = 1;
			LL1table = new Hashtable<String,Map<String,LinkedList<Node>>>();

			//read from config-file
			Properties prop = new Properties();
			InputStream in = new FileInputStream(configFileName);
			prop.load(in);
			//init terminals
			String strTerminals = prop.getProperty("terminals");
			String[] arrTerminals = strTerminals.split(",");
			initTerminals(arrTerminals);
			//init non-terminals
			initNonTerminals(configFileName);
			//init Stack
			this.dollar = new NodeTerminal("$");
			//init table
			initLL1Table(configFileName);
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			System.exit(1);
		}
	}

	private void initLL1Table(String configName) {
		//TODO
		if(terminals == null || nonTerminals == null)
			return;

		Properties prop = new Properties();
		InputStream in = null;
		try {
			in = new FileInputStream(configName);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int k = 0;
		String strRules = "";
		String[] rules = null;
		HashMap <String,LinkedList<Node>> tableCol = null;// new HashMap <String,LinkedList<Node>>();
		for(int i = 0; i< nonTerminals.length; i++)
		{
			tableCol  = new HashMap <String,LinkedList<Node>>();
			strRules = prop.getProperty(nonTerminals[i].getSymbol());
			rules = strRules.split(",");
			k = 0;
			for(int j = 0; j< rules.length; j++)
			{
				if(k < terminals.length)
				{
					LinkedList<Node> value = createValue(rules[j]);
					tableCol.put(terminals[k].getSymbol(), value);
					k++;
				}
			}
			LL1table.put(nonTerminals[i].getSymbol(),tableCol);
		}

	}

	private LinkedList<Node> createValue(String string) {
		String[] rule = string.split(";");
		boolean isTerm,isEps;
		String sym = "";
		LinkedList<Node> list = new LinkedList<Node>();
		for(int i = 0; i< rule.length; i++)
		{
			isTerm = false;
			isEps = false;
			for (TokenInfoEnum e : TokenInfoEnum.values())
			{

				if(e.name().equals(rule[i]))
				{
					isTerm = true;
					sym = e.name();
				}
				else if(e.toString().compareToIgnoreCase("eps") == 0){
					isEps = true;
				}
			}
			if(isTerm)
			{
				list.add(new NodeTerminal(sym));
			}
			else if(isEps)
			{
				list.add(new NodeEpsilon());
			}
			else//grammar
			{
				list.add(new NodeNonTerminal(rule[i]));
			}
		}
		return list;
	}

	private void initNonTerminals(String configFileName) 
	{
		BufferedReader br = null;
		String currLine;
		String strNonTerminals = "";
		String[] arrNonTerminals = null;
		try{
			br = new BufferedReader(new FileReader(configFileName));

			while((currLine = br.readLine()) != null)
			{
				if(currLine.isEmpty())
					continue;
				if(currLine.substring(0, 9).equals("terminals"))//the first line of config-file
					continue;
				if(currLine.charAt(0) == '#')//remarks
					continue;
				else
				{
					String rows[] = currLine.split("=");
					strNonTerminals+=rows[0] +",";
				}
			}
			arrNonTerminals = strNonTerminals.split(",");
			nonTerminals = new Node[arrNonTerminals.length];
			for(int i = 0; i < arrNonTerminals.length; i++)
				nonTerminals[i] = new NodeNonTerminal(arrNonTerminals[i]);

		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}finally{
			try{
				if(br!= null)
					br.close();
			}catch(IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	private void initTerminals(String[] arrTerminals)
	{
		terminals = new Node[arrTerminals.length];
		int i = 0;
		while(i< arrTerminals.length)
		{
			terminals[i] = new NodeTerminal(arrTerminals[i]);
			i++;
		}
//		Node eps  = new NodeEpsilon();
//		terminals[i] = eps.getEpsilon();

	}

	//TODO - > build the three insert curr
	@SuppressWarnings("unchecked")
	public String yyLL1parser(Stack<TokenInfoEnum> input)
	{	
		String tree = "";
		stack = new Stack<Node>();
		Node currVariable = null;
		Map <String,LinkedList<Node>> currMap; //= LL1table.get("F");
		Map <String,LinkedList<Node>> tempMap;// = new HashMap <String,LinkedList<Node>>();
		LinkedList<Node> tempGrammar;
		int i = 0;
		LinkedList<Node> currGrammar = null;//check Linked List
		AST astTree = null;

		//init stack stack <- <E,$>
		stack.push(this.dollar); //special sing
		if(this.nonTerminals[0] == null)
		{
			System.out.println("There is no Non-Terminals");
			System.exit(1);
		}

		stack.push(this.nonTerminals[0]);//first variable


		currVariable = stack.peek();
		astTree = new AST(currVariable);//root! 
		while(!stack.isEmpty())
		{
			currVariable = stack.pop();
			tempMap = new HashMap <String,LinkedList<Node>>();	
			tempGrammar = new LinkedList<Node>();
			if(i == input.size())
			{
				if(currVariable.getSymbol().equals("$") && stack.size() == 0)//ACCEPT
					return tree;
				
				else if(currVariable instanceof NodeEpsilon)
					continue;
				else stack.pop();

				
			}
			//1) if stack = <A,rest> A is not a terminal 
			//		x = TAB[A,NEXT]
			//		if is empty -> ERROR
			
			if(currVariable instanceof NodeNonTerminal)
			{


				if(i<input.size())
				{
					currMap = LL1table.get(currVariable.getSymbol());
					tempMap = currMap;//currMap;
					currGrammar = currMap.get(input.get(i).toString());
					tempGrammar = (LinkedList<Node>) currGrammar.clone();

					tempMap.put(input.get(i).toString(), tempGrammar);
					LL1table.put(currVariable.toString(), tempMap);
				}
				else if (i >= input.size())
				{
					currMap = LL1table.get(currVariable.getSymbol());
					tempMap = currMap;//currMap;
					currGrammar = currMap.get(TokenInfoEnum.EOF.toString());
					tempGrammar = (LinkedList<Node>) currGrammar.clone();
					tempMap.put(TokenInfoEnum.EOF.toString(), tempGrammar);
					LL1table.put(currVariable.toString(), tempMap);
				}

				if( currGrammar == null|| currGrammar.isEmpty()|| currGrammar.getFirst().getSymbol().equals(""))//there is no grammar in the table
					return null;
				else 
				{
					currVariable.setFather(currVariable);
					String tempFather = currVariable.getSymbol()+"_"+this.id + "->";
					while(currGrammar.size() > 0)
					{

						Node n = currGrammar.removeLast();
						if(n.getSymbol().compareToIgnoreCase("eps") == 0)
						{
							NodeEpsilon epsilon = new NodeEpsilon();
							epsilon.setSymbol("eps");
							stack.push(epsilon);
						}
						else stack.push(n);
						
						tree+= tempFather + n.getSymbol()+"_"+ ++this.id+"\n";
						currVariable.setChildCurrent(n);
					}

				}
			}
			//2.if stack = <t,rest> t is a terminal
			//if(t == next)
			//stack = <rest>
			//next++
			else if (currVariable instanceof NodeTerminal)
			{
				if(i < input.size())
				{
					if(currVariable.getSymbol().equals(input.get(i).toString()) )
					{
						i++;
					}
				}
				else{
					System.out.println("ERROR");
					return null;//ERROR
				}

			}

		}
		return null;
	}



}

