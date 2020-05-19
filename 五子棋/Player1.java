package Îå×ÓÆå;

public class Player1 implements Player{
     
	private int chance=3;
	static public Player1 getPlayer1;
	
	private Player1() { }
	public static Player1 getPlayer() {
		if(getPlayer1 == null) {
			getPlayer1 = new Player1();
		}
		return getPlayer1;
	}
	
	public int judge() {
    	if(chance>0) {
    		return 1;
    	}
    	else {
    		return 0;
    	}
    }
    
    public void reduce() {
    	chance--;
    }
    
    public int getChance() {
    	return chance;
    }
    
    public void restart() {
    	chance=3;
    }
}
