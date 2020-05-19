package 五子棋;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Image;

 
//实现对GoBangframe下棋界面的监听接口处理
public class FallChess implements GoBangconfig,MouseListener{
	public FiveChess gf;
    FiveChess go;
	
	Image black=new ImageIcon("C://Users//86139//Desktop//五指棋//black.png").getImage();
	Image white=new ImageIcon("C://Users//86139//Desktop//五指棋//white.png").getImage();
	
	public void setGraphics(FiveChess gf) {
		this.gf=gf;

	}
	
	
	
	//AI联合算法
		public Integer unionWeight(Integer a,Integer b ) {
			//必须要先判断a,b两个数值是不是null
			if((a==null)||(b==null)) return 0;
			//一一:101/202
		    else if((a>=22)&&(a<=25)&&(b>=22)&&(b<=25)) return 60;
			//一二、二一:1011/2022
			else if(((a>=22)&&(a<=25)&&(b>=76)&&(b<=80))||((a>=76)&&(a<=80)&&(b>=22)&&(b<=25))) return 800;
			//一三、三一、二二:10111/20222
			else if(((a>=10)&&(a<=25)&&(b>=1050)&&(b<=1100))||((a>=1050)&&(a<=1100)&&(b>=10)&&(b<=25))||((a>=76)&&(a<=80)&&(b>=76)&&(b<=80)))
				return 3000;
			//眠三连和眠一连。一三、三一
			else if(((a>=22)&&(a<=25)&&(b>=140)&&(b<=150))||((a>=140)&&(a<=150)&&(b>=22)&&(b<=25))) return 3000;
			//二三、三二:110111
			else if(((a>=76)&&(a<=80)&&(b>=1050)&&(b<=1100))||((a>=1050)&&(a<=1100)&&(b>=76)&&(b<=80))) return 3000;
			else return 0;
		}
	
		
	
	  public void mouseClicked(MouseEvent e) {
		 if(gf.turn!=0) {
		  int x=e.getX();
		  int y=e.getY();
		  //计算棋子要落在棋盘的哪个交叉点上
		  int countx=(x/40)*40+20;
		  int county=(y/40)*40+20;
		  Graphics g=gf.getGraphics();
		  int colu=(countx-20)/40;
		  int ro=(county-20)/40;
		  
		  if(gf.A[colu][ro]!=0) {
			  JOptionPane.showMessageDialog(null,"此处已经有棋子了，请下在其它地方");
		  }
		  else {
	
			  if(gf.ChooseType==0) {
				  
				  
				  //落子
			  if(gf.turn==1) {				  
				  g.drawImage(black, countx-20, county-20, 40,40, null);

				  gf.A[colu][ro]=1;
				  gf.turn++;
			  }
			  else {
				  g.drawImage(white, countx-20, county-20, 40,40, null);

				  gf.A[colu][ro]=2;
				  gf.turn--;
			  }
			  
		  gf.ChessList.add(new Chess(colu,ro));
		  gf.determineOutcome();
		  gf.determinetime();
		  
		  if(gf.turn!=0)  gf.resettime();
		  
		   }
			  
			  //AI
			  
			  else {
			  if(gf.turn==1) {
				  g.drawImage(black, countx-20, county-20, 40,40, null);
				  gf.A[colu][ro]=1;
				  gf.turn++;
				  gf.ChessList.add(new Chess(colu,ro));
				  
				  gf.determineOutcome();			  
				  gf.determinetime();
				  
				  gf.resettime();
			  }else{
				  g.drawImage(white, countx-20, county-20, 40,40, null);
				  gf.A[colu][ro]=2;
				  gf.turn--;
				  gf.ChessList.add(new Chess(colu,ro));
				  
				  gf.determineOutcome();			  
				  gf.determinetime();
				  
				  if(gf.turn!=0)  gf.resettime();
			  }
		
			  for(int i=0;i<gf.A.length;i++) {
				  for(int j=0;j<gf.A[i].length;j++) {
					  //首先判断当前位置是否为空
					  if(gf.A[i][j]==0) {
						  //往左延伸
						  String ConnectType="0";
						  int jmin=Math.max(0, j-4);
						  for(int positionj=j-1;positionj>=jmin;positionj--) {
							  //依次加上前面的棋子
							  ConnectType=ConnectType+gf.A[i][positionj];
						  }
						  //从数组中取出相应的权值，加到权值数组的当前位置中
						  Integer valueleft=gf.map.get(ConnectType);
						  if(valueleft!=null) gf.weightArray[i][j]+=valueleft;

						  //往右延伸
						  ConnectType="0";
						  int jmax=Math.min(15, j+4);
						  for(int positionj=j+1;positionj<=jmax;positionj++) {
							  //依次加上前面的棋子
							  ConnectType=ConnectType+gf.A[i][positionj];
						  }
						  //从数组中取出相应的权值，加到权值数组的当前位置中
						  Integer valueright=gf.map.get(ConnectType);
						  if(valueright!=null) gf.weightArray[i][j]+=valueright;

						  //联合判断，判断行
						  gf.weightArray[i][j]+=unionWeight(valueleft,valueright);

						  //往上延伸
						  ConnectType="0";
						  int imin=Math.max(0, i-4);
						  for(int positioni=i-1;positioni>=imin;positioni--) {
							  //依次加上前面的棋子
							  ConnectType=ConnectType+gf.A[positioni][j];
						  }
						  //从数组中取出相应的权值，加到权值数组的当前位置中
						  Integer valueup=gf.map.get(ConnectType);
						  if(valueup!=null) gf.weightArray[i][j]+=valueup;

						  //往下延伸
						  ConnectType="0";
						  int imax=Math.min(15, i+4);
						  for(int positioni=i+1;positioni<=imax;positioni++) {
							  //依次加上前面的棋子
							  ConnectType=ConnectType+gf.A[positioni][j];
						  }
						  //从数组中取出相应的权值，加到权值数组的当前位置中
						  Integer valuedown=gf.map.get(ConnectType);
						  if(valuedown!=null) gf.weightArray[i][j]+=valuedown;

						  //联合判断，判断列
						  gf.weightArray[i][j]+=unionWeight(valueup,valuedown);

						  //往左上方延伸,i,j,都减去相同的数
						  ConnectType="0";
						  for(int position=-1;position>=-4;position--) {
							  if((i+position>=0)&&(i+position<=15)&&(j+position>=0)&&(j+position<=15))
							  ConnectType=ConnectType+gf.A[i+position][j+position];
						  }
						  //从数组中取出相应的权值，加到权值数组的当前位置
						  Integer valueLeftUp=gf.map.get(ConnectType);
						  if(valueLeftUp!=null) gf.weightArray[i][j]+=valueLeftUp;

						 //往右下方延伸,i,j,都加上相同的数
						  ConnectType="0";
						  for(int position=1;position<=4;position++) {
							  if((i+position>=0)&&(i+position<=15)&&(j+position>=0)&&(j+position<=15))
							  ConnectType=ConnectType+gf.A[i+position][j+position];
						  }
						  //从数组中取出相应的权值，加到权值数组的当前位置
						  Integer valueRightDown=gf.map.get(ConnectType);
						  if(valueRightDown!=null) gf.weightArray[i][j]+=valueRightDown;

						  //联合判断，判断行
						  gf.weightArray[i][j]+=unionWeight(valueLeftUp,valueRightDown);

						  //往左下方延伸,i加,j减
						  ConnectType="0";
						  for(int position=1;position<=4;position++) {
							  if((i+position>=0)&&(i+position<=15)&&(j-position>=0)&&(j-position<=15))
							  ConnectType=ConnectType+gf.A[i+position][j-position];
						  }
						  //从数组中取出相应的权值，加到权值数组的当前位置
						  Integer valueLeftDown=gf.map.get(ConnectType);
						  if(valueLeftDown!=null) gf.weightArray[i][j]+=valueLeftDown;

						  //往右上方延伸,i减,j加
						  ConnectType="0";
						  for(int position=1;position<=4;position++) {
							  if((i-position>=0)&&(i-position<=15)&&(j+position>=0)&&(j+position<=15))
							  ConnectType=ConnectType+gf.A[i-position][j+position];
						  }
						  //从数组中取出相应的权值，加到权值数组的当前位置
						  Integer valueRightUp=gf.map.get(ConnectType);
						  if(valueRightUp!=null) gf.weightArray[i][j]+=valueRightUp;

						  //联合判断，判断行
						  gf.weightArray[i][j]+=unionWeight(valueLeftDown,valueRightUp);
					  }
				  }
			  }

			  //取出最大的权值
			  int AIi=0,AIj=0;
			  int weightmax=0;
			  for(int i=0;i<=go.row;i++) {
				  for(int j=0;j<=go.column;j++) {
					  if(weightmax<gf.weightArray[i][j]) {
						  weightmax=gf.weightArray[i][j];
						  AIi=i;
						  AIj=j;
					  }
				  }
			  }

			  //确定位置，落子
			  //i对应y，j对应x
			  countx=20+AIj*40;
			  county=20+AIi*40;
			  if(gf.turn==2) {
			  g.drawImage(white,countx-size/2, county-size/2, size, size,null);
			  //设置当前位置已经有棋子了，棋子为白子
			  gf.ChessList.add(new Chess(AIi,AIj));
			  gf.A[AIi][AIj]=2;
			  gf.turn--;}
			  else {
				  g.drawImage(black,countx-size/2, county-size/2, size, size,null);
				  //设置当前位置已经有棋子了，棋子为白子
				  gf.ChessList.add(new Chess(AIi,AIj));
				  gf.A[AIi][AIj]=1;
				  gf.turn++;
			  }

			  //落子以后重置权值数组weightArray
			  for(int i=0;i<=go.column;i++) 
				  for(int j=0;j<=go.row;j++)
					  gf.weightArray[i][j]=0;
			  
			  gf.determineOutcome();
			  }
			  
	 }
		    
		  }else {
			  JOptionPane.showMessageDialog(null,"请点击开始新游戏");
		  }
		
 }
	  
	  
	  // Method descriptor #8 (Ljava/awt/event/MouseEvent;)V
	  public void mousePressed(java.awt.event.MouseEvent e) {
		  
	  }
	  
	  // Method descriptor #8 (Ljava/awt/event/MouseEvent;)V
	  public void mouseReleased(java.awt.event.MouseEvent e) {
		  
	  }
	  
	  // Method descriptor #8 (Ljava/awt/event/MouseEvent;)V
	  public void mouseEntered(java.awt.event.MouseEvent e) {
		  
	  }
	  
	  // Method descriptor #8 (Ljava/awt/event/MouseEvent;)V
	  public void mouseExited(java.awt.event.MouseEvent e) {
		  
	  }
}
		 