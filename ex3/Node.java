import java.util.LinkedList;


public abstract class Node {

	protected String symbol;
	protected Node father;
	private LinkedList<Node> childList;
	
	public abstract boolean isTerminal();
	public String getSymbol(){return this.symbol;}
	public LinkedList<Node> getCildren(){return this.childList;}
	public void setChildCurrent(Node child)
	{
		this.childList = new LinkedList<Node>();
		this.childList.push(child);
	}
	public boolean equals(Node other){return (other.getSymbol().equals(this.getSymbol()));}
	public abstract String toString();
	public  void setFather(Node n) { this.father = n;}
	public void setSymbol(String s){this.symbol = s;}
	
}
