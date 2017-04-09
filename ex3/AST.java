import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class AST
{
	private Node root;
	private int id;

	public AST(Node r)
	{
		this.root = r;
		this.id = 1;
	}
	
	public Node getRoot()
	{
		return this.root;
	}
	public String printAST()
	{
		String str = "";
		return printAST(this.root,str);
	}

	private String printAST(Node r,String s)
	{
		LinkedList<Node> children = r.getCildren();
		if(children == null)
			return s;//this.root.getSymbol()+"_"+this.id;
		
		String currNode = r.toString() + "_"+ this.id;
		for (Node n : children) 
		{
			s+= currNode + "->"+n.getSymbol()+"_"+ ++ this.id+"\n";
			s = printAST(n,s);
		}
		return s;
	}
	
}
