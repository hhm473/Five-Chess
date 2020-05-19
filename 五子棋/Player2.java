package Îå×ÓÆå;

public class Player2 implements Player{
    
	private int chance=3;
	static public Player2 getPlayer2;
	
	private Player2() { }
	public static Player2 getPlayer() {
		if(getPlayer2 == null) {
			getPlayer2 = new Player2();
		}
		return getPlayer2;
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
