public class TokenInfo {

	private TokenInfoEnum typeToken;
	private String dataToken;
	private int lineToken;

	

	public TokenInfo(TokenInfoEnum type,String data,int line)
	{
		this.typeToken = type;
		this.lineToken = line;
		this.dataToken = data;
	}

	
	public TokenInfoEnum getTypeToken(){return this.typeToken;}
	public void setTypeToken(TokenInfoEnum typeTok){this.typeToken = typeTok;}

	public String getDataToken(){return this.dataToken;}
	public void setDataToken(String dataTok){this.dataToken = dataTok;}

	public int getLineToken(){return this.lineToken;}
	public void setLineToken(int line){this.lineToken = line;}

	public String toString()
	{
		String s = "";

		s+= getTypeToken()!= null ? getTypeToken().toString() : "";
		s+=";";
		s+= getDataToken()!= null? getDataToken():"";
		s+=";";
		s+= getLineToken();
		
		return s;		
	}
}

