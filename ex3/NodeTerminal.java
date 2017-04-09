public class NodeTerminal extends Node{

	public NodeTerminal(String sym)
	{
		this.symbol = sym;
	}
	@Override
	public boolean isTerminal() {return true;}

	@Override
	public String toString() {return this.symbol;}

}
