import java.util.LinkedList;


public class NodeEpsilon extends Node{

	
	public NodeEpsilon()
	{
	}
	@Override
	public boolean isTerminal() {
		return false;
	}
	public boolean isEpsilon() {
		return true;
	}
	@Override
	public String toString() {return this.symbol;}

}
