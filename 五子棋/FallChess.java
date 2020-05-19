package ������;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Image;

 
//ʵ�ֶ�GoBangframe�������ļ����ӿڴ���
public class FallChess implements GoBangconfig,MouseListener{
	public FiveChess gf;
    FiveChess go;
	
	Image black=new ImageIcon("C://Users//86139//Desktop//��ָ��//black.png").getImage();
	Image white=new ImageIcon("C://Users//86139//Desktop//��ָ��//white.png").getImage();
	
	public void setGraphics(FiveChess gf) {
		this.gf=gf;

	}
	
	
	
	//AI�����㷨
		public Integer unionWeight(Integer a,Integer b ) {
			//����Ҫ���ж�a,b������ֵ�ǲ���null
			if((a==null)||(b==null)) return 0;
			//һһ:101/202
		    else if((a>=22)&&(a<=25)&&(b>=22)&&(b<=25)) return 60;
			//һ������һ:1011/2022
			else if(((a>=22)&&(a<=25)&&(b>=76)&&(b<=80))||((a>=76)&&(a<=80)&&(b>=22)&&(b<=25))) return 800;
			//һ������һ������:10111/20222
			else if(((a>=10)&&(a<=25)&&(b>=1050)&&(b<=1100))||((a>=1050)&&(a<=1100)&&(b>=10)&&(b<=25))||((a>=76)&&(a<=80)&&(b>=76)&&(b<=80)))
				return 3000;
			//����������һ����һ������һ
			else if(((a>=22)&&(a<=25)&&(b>=140)&&(b<=150))||((a>=140)&&(a<=150)&&(b>=22)&&(b<=25))) return 3000;
			//����������:110111
			else if(((a>=76)&&(a<=80)&&(b>=1050)&&(b<=1100))||((a>=1050)&&(a<=1100)&&(b>=76)&&(b<=80))) return 3000;
			else return 0;
		}
	
		
	
	  public void mouseClicked(MouseEvent e) {
		 if(gf.turn!=0) {
		  int x=e.getX();
		  int y=e.getY();
		  //��������Ҫ�������̵��ĸ��������
		  int countx=(x/40)*40+20;
		  int county=(y/40)*40+20;
		  Graphics g=gf.getGraphics();
		  int colu=(countx-20)/40;
		  int ro=(county-20)/40;
		  
		  if(gf.A[colu][ro]!=0) {
			  JOptionPane.showMessageDialog(null,"�˴��Ѿ��������ˣ������������ط�");
		  }
		  else {
	
			  if(gf.ChooseType==0) {
				  
				  
				  //����
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
					  //�����жϵ�ǰλ���Ƿ�Ϊ��
					  if(gf.A[i][j]==0) {
						  //��������
						  String ConnectType="0";
						  int jmin=Math.max(0, j-4);
						  for(int positionj=j-1;positionj>=jmin;positionj--) {
							  //���μ���ǰ�������
							  ConnectType=ConnectType+gf.A[i][positionj];
						  }
						  //��������ȡ����Ӧ��Ȩֵ���ӵ�Ȩֵ����ĵ�ǰλ����
						  Integer valueleft=gf.map.get(ConnectType);
						  if(valueleft!=null) gf.weightArray[i][j]+=valueleft;

						  //��������
						  ConnectType="0";
						  int jmax=Math.min(15, j+4);
						  for(int positionj=j+1;positionj<=jmax;positionj++) {
							  //���μ���ǰ�������
							  ConnectType=ConnectType+gf.A[i][positionj];
						  }
						  //��������ȡ����Ӧ��Ȩֵ���ӵ�Ȩֵ����ĵ�ǰλ����
						  Integer valueright=gf.map.get(ConnectType);
						  if(valueright!=null) gf.weightArray[i][j]+=valueright;

						  //�����жϣ��ж���
						  gf.weightArray[i][j]+=unionWeight(valueleft,valueright);

						  //��������
						  ConnectType="0";
						  int imin=Math.max(0, i-4);
						  for(int positioni=i-1;positioni>=imin;positioni--) {
							  //���μ���ǰ�������
							  ConnectType=ConnectType+gf.A[positioni][j];
						  }
						  //��������ȡ����Ӧ��Ȩֵ���ӵ�Ȩֵ����ĵ�ǰλ����
						  Integer valueup=gf.map.get(ConnectType);
						  if(valueup!=null) gf.weightArray[i][j]+=valueup;

						  //��������
						  ConnectType="0";
						  int imax=Math.min(15, i+4);
						  for(int positioni=i+1;positioni<=imax;positioni++) {
							  //���μ���ǰ�������
							  ConnectType=ConnectType+gf.A[positioni][j];
						  }
						  //��������ȡ����Ӧ��Ȩֵ���ӵ�Ȩֵ����ĵ�ǰλ����
						  Integer valuedown=gf.map.get(ConnectType);
						  if(valuedown!=null) gf.weightArray[i][j]+=valuedown;

						  //�����жϣ��ж���
						  gf.weightArray[i][j]+=unionWeight(valueup,valuedown);

						  //�����Ϸ�����,i,j,����ȥ��ͬ����
						  ConnectType="0";
						  for(int position=-1;position>=-4;position--) {
							  if((i+position>=0)&&(i+position<=15)&&(j+position>=0)&&(j+position<=15))
							  ConnectType=ConnectType+gf.A[i+position][j+position];
						  }
						  //��������ȡ����Ӧ��Ȩֵ���ӵ�Ȩֵ����ĵ�ǰλ��
						  Integer valueLeftUp=gf.map.get(ConnectType);
						  if(valueLeftUp!=null) gf.weightArray[i][j]+=valueLeftUp;

						 //�����·�����,i,j,��������ͬ����
						  ConnectType="0";
						  for(int position=1;position<=4;position++) {
							  if((i+position>=0)&&(i+position<=15)&&(j+position>=0)&&(j+position<=15))
							  ConnectType=ConnectType+gf.A[i+position][j+position];
						  }
						  //��������ȡ����Ӧ��Ȩֵ���ӵ�Ȩֵ����ĵ�ǰλ��
						  Integer valueRightDown=gf.map.get(ConnectType);
						  if(valueRightDown!=null) gf.weightArray[i][j]+=valueRightDown;

						  //�����жϣ��ж���
						  gf.weightArray[i][j]+=unionWeight(valueLeftUp,valueRightDown);

						  //�����·�����,i��,j��
						  ConnectType="0";
						  for(int position=1;position<=4;position++) {
							  if((i+position>=0)&&(i+position<=15)&&(j-position>=0)&&(j-position<=15))
							  ConnectType=ConnectType+gf.A[i+position][j-position];
						  }
						  //��������ȡ����Ӧ��Ȩֵ���ӵ�Ȩֵ����ĵ�ǰλ��
						  Integer valueLeftDown=gf.map.get(ConnectType);
						  if(valueLeftDown!=null) gf.weightArray[i][j]+=valueLeftDown;

						  //�����Ϸ�����,i��,j��
						  ConnectType="0";
						  for(int position=1;position<=4;position++) {
							  if((i-position>=0)&&(i-position<=15)&&(j+position>=0)&&(j+position<=15))
							  ConnectType=ConnectType+gf.A[i-position][j+position];
						  }
						  //��������ȡ����Ӧ��Ȩֵ���ӵ�Ȩֵ����ĵ�ǰλ��
						  Integer valueRightUp=gf.map.get(ConnectType);
						  if(valueRightUp!=null) gf.weightArray[i][j]+=valueRightUp;

						  //�����жϣ��ж���
						  gf.weightArray[i][j]+=unionWeight(valueLeftDown,valueRightUp);
					  }
				  }
			  }

			  //ȡ������Ȩֵ
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

			  //ȷ��λ�ã�����
			  //i��Ӧy��j��Ӧx
			  countx=20+AIj*40;
			  county=20+AIi*40;
			  if(gf.turn==2) {
			  g.drawImage(white,countx-size/2, county-size/2, size, size,null);
			  //���õ�ǰλ���Ѿ��������ˣ�����Ϊ����
			  gf.ChessList.add(new Chess(AIi,AIj));
			  gf.A[AIi][AIj]=2;
			  gf.turn--;}
			  else {
				  g.drawImage(black,countx-size/2, county-size/2, size, size,null);
				  //���õ�ǰλ���Ѿ��������ˣ�����Ϊ����
				  gf.ChessList.add(new Chess(AIi,AIj));
				  gf.A[AIi][AIj]=1;
				  gf.turn++;
			  }

			  //�����Ժ�����Ȩֵ����weightArray
			  for(int i=0;i<=go.column;i++) 
				  for(int j=0;j<=go.row;j++)
					  gf.weightArray[i][j]=0;
			  
			  gf.determineOutcome();
			  }
			  
	 }
		    
		  }else {
			  JOptionPane.showMessageDialog(null,"������ʼ����Ϸ");
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
		 