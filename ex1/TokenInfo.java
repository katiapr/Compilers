
public class TokenInfo {

	private String typeToken;
	private String dataToken;
	private int lineToken;

	

//	public TokenInfo(String type,int line,String data)
//	{
//		this.typeToken = type;
//		this.lineToken = line;
//		this.dataToken = data;
//	}

	public String getTypeToken(){return this.typeToken;}
	public void setTypeToken(String typeTok){this.typeToken = typeTok;}

	public String getDataToken(){return this.dataToken;}
	public void setDataToken(String dataTok){this.dataToken = dataTok;}

	public int getLineToken(){return this.lineToken;}
	public void setLineToken(int line){this.lineToken = line;}

	public String toString()
	{
		String s = "";

		s+= getTypeToken()!= null ? getTypeToken() : ""+ ";"+getDataToken()!= null? getDataToken():"" + ";" +getLineToken() + "\n";
		return s;		
	}

	public TokenInfo yylex(String line)
	{
		
		return null;
	}


}
