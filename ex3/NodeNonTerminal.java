import java.util.LinkedList;


public class NodeNonTerminal extends Node{


	private LinkedList<Node> rules;

	public NodeNonTerminal(String sym)
	{
		this.symbol = sym;
		rules = new LinkedList<>();
	}
	public void setNonTerminalRule(Node n)
	{
		rules.add(n);
	}
	@Override
	public boolean isTerminal() {return false;}

	@Override
	public String toString() {return this.symbol;}


}
