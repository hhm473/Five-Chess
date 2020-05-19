package 五子棋;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Dimension;

import static 五子棋.GoBangconfig.x;
import static 五子棋.GoBangconfig.y;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
 
public class FiveChess extends JPanel implements GoBangconfig{
	
	public Graphics g;//定义一支画笔
    public ArrayList<Chess>ChessList=new ArrayList<Chess>();
	public int turn=0;//判断当前轮到谁了，1表示黑方，2表示白方
	public int num=0;
	public Player p; 
	//public Player p1=new Player(1);
	//public Player p2=new Player(2);
	public Stime time=new Stime();
	public Thread[] time1=new Thread[100];
    public int sb=0;

	public int[][] A=new int [column+1][row+1];//定义一个二维数组来储存棋盘的落子情况
	
	
	
	public int ChooseType=0;//0表示人人对战，1表示人机对战，默认人人对战
	public static HashMap<String,Integer> map = new HashMap<String,Integer>();//设置不同落子情况和相应权值的数组
	static {

		//被堵住
		map.put("01", 25);//眠1连
		map.put("02", 22);//眠1连
		map.put("001", 17);//眠1连
		map.put("002", 12);//眠1连
		map.put("0001", 17);//眠1连
		map.put("0002", 12);//眠1连

		map.put("0102",25);//眠1连，15
		map.put("0201",22);//眠1连，10
		map.put("0012",15);//眠1连，15
		map.put("0021",10);//眠1连，10
		map.put("01002",25);//眠1连，15
		map.put("02001",22);//眠1连，10
		map.put("00102",17);//眠1连，15
		map.put("00201",12);//眠1连，10
		map.put("00012",15);//眠1连，15
		map.put("00021",10);//眠1连，10

		map.put("01000",25);//活1连，15
		map.put("02000",22);//活1连，10
		map.put("00100",19);//活1连，15
		map.put("00200",14);//活1连，10
		map.put("00010",17);//活1连，15
		map.put("00020",12);//活1连，10
		map.put("00001",15);//活1连，15
		map.put("00002",10);//活1连，10

		//被墙堵住
		map.put("0101",65);//眠2连，40
		map.put("0202",60);//眠2连，30
		map.put("0110",80);//眠2连，40
		map.put("0220",76);//眠2连，30
		map.put("011",80);//眠2连，40
		map.put("022",76);//眠2连，30
		map.put("0011",65);//眠2连，40
		map.put("0022",60);//眠2连，30

		map.put("01012",65);//眠2连，40
		map.put("02021",60);//眠2连，30
		map.put("01102",80);//眠2连，40
		map.put("02201",76);//眠2连，30
		map.put("01120",80);//眠2连，40
		map.put("02210",76);//眠2连，30
		map.put("00112",65);//眠2连，40
		map.put("00221",60);//眠2连，30

		map.put("01100",80);//活2连，40
		map.put("02200",76);//活2连，30
		map.put("01010",75);//活2连，40
		map.put("02020",70);//活2连，30
		map.put("00110",75);//活2连，40
		map.put("00220",70);//活2连，30
		map.put("00011",75);//活2连，40
		map.put("00022",70);//活2连，30

		//被堵住
		map.put("0111",150);//眠3连，100
		map.put("0222",140);//眠3连，80

		map.put("01112",150);//眠3连，100
		map.put("02221",140);//眠3连，80

		map.put("01110", 1100);//活3连
		map.put("02220", 1050);//活3连
		map.put("01101",1000);//活3连，130
		map.put("02202",800);//活3连，110
		map.put("01011",1000);//活3连，130
		map.put("02022",800);//活3连，110

		map.put("01111",3000);//4连，300
		map.put("02222",3500);//4连，280
	}
	public int[][] weightArray=new int[column+1][row+1];//定义一个二维数组，保存各个点的权值
	
	
	
	
	
	//主函数入口
	public static void main(String args[]) {
		FiveChess gf=new FiveChess();//初始化一个五子棋界面的对象

		gf.startI();//调用方法进行界面的初始化
	}
	
	
	public void startI() {
		//初始化一个界面,并设置标题大小等属性
		JFrame jf=new JFrame();
		
		jf.setTitle("五子棋");
		jf.setSize(UIWIDTH,UIHIGHTH);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(3);
		
		jf.setLayout(new BorderLayout());//设置顶级容器JFrame为框架布局
		

		//实现左边的界面，把GoBangframe的对象添加到框架布局的中间部分

		this.setBackground(Color.lightGray);//设置下棋界面的颜色
		//这里的话直接把左边的画板添加上去，指明是在框架布局的中间版块

		jf.add(this,BorderLayout.CENTER);//添加到框架布局的中间部分
		
		//实现右边的JPanel容器界面
		JPanel jp=new JPanel();
		jp.setPreferredSize(dim1);//设置JPanel的大小
		jp.setBackground(Color.lightGray);//设置右边的界面颜色为灰色
		jf.add(jp,BorderLayout.EAST);//添加到框架布局的东边部分
		jp.setLayout(new FlowLayout());//设置JPanel为流式布局
		
		
		String[] userMessage={"pic","昵称：yy","性别：男","等级：新手"};
		JLabel[] user =new JLabel[4];
		//设置背景图片的大小
		USERPICTURE.setImage(USERPICTURE.getImage().getScaledInstance(dim4.height, dim4.width,Image.SCALE_DEFAULT ));
		user[0]=new JLabel(USERPICTURE);
		user[0].setPreferredSize(dim4);
		jp.add(user[0]);

		for(int i=1;i<4;i++){
			user[i]=new JLabel(userMessage[i]);
			user[i].setPreferredSize(dim2);
			/*
			 *利用setFont来设置TextField文本框输入信息的字体大小
			 *textx1.setFont(new Font(Font.DIALOG,Font.PLAIN,30));
			 */	
			user[i].setFont(new Font(Font.MONOSPACED,Font.ITALIC,20));
			jp.add(user[i]);
		}
		
		//接下来我们需要把按钮等组件依次加到那个JPanel上面
		//设置按钮数组
		String[] butname= {"开始新游戏","悔棋","认输"};
		JButton[] button=new JButton[3];

		//依次把三个按钮组件加上去
		for(int i=0;i<butname.length;i++) {
			
			button[i]=new JButton(butname[i]);
			button[i].setPreferredSize(dim2);
			jp.add(button[i]);
		}
		
		//设置选项按钮
				String[] boxname= {"人人对战","人机对战"};
				JComboBox box=new JComboBox(boxname);
				jp.add(box);

				
		//按钮监控类
		FallChess fl=new FallChess();
		fl.setGraphics(this);//获取画笔对象
		this.addMouseListener(fl);
		ButtonListener butListen=new ButtonListener(this,box);
		
		//对每一个按钮都添加状态事件的监听处理机制
		for(int i=0;i<butname.length;i++) {
			button[i].addActionListener(butListen);//添加发生操作的监听方法
		}
		
		
		box.addActionListener(butListen);
		
		jf.setVisible(true);
		

	}
	
	
	//重写重绘方法,这里重写的是第一个大的JPanel的方法
	public void paint(Graphics g) {
		super.paint(g);
		
		//重绘出棋盘
		g.drawImage(beij, 0, 0, 720, 720, null);

		g.setColor(Color.black);
		for(int i=0;i<=row;i++) {
			g.drawLine(x, y+size*i, x+size*column, y+size*i);
		}
		for(int j=0;j<=column;j++) {
			g.drawLine(x+size*j, y, x+size*j, y+size*row);
		}
		
		Font font=new Font("黑体",Font.ITALIC,20); 
		g.setFont(font);
		 g.drawString("剩余时间：", 480, 650);
		 g.drawString("下棋方：", 330, 650);
		 g.drawString("玩家1：", 20, 645);
		 g.drawString("玩家2：", 20, 680);
		 g.drawImage(black, 80, 623, 35,35, null);
		 g.drawImage(white, 80, 660, 35,35, null);
	     if(turn==1) { g.drawImage(black, 400, 625, 35,35, null);}
	        else {g.drawImage(white, 400, 625, 35,35, null);}		 
		 g.drawString("剩余悔棋次数：", 150, 650);
         g.drawString(String.valueOf(this.setPlayer(1).getChance())+"次", 280, 650);
         g.drawString(String.valueOf(this.setPlayer(2).getChance())+"次", 280, 680);
         g.setColor(Color.RED);
         g.drawString(String.valueOf(time.gettime())+"s", 580, 650);
         repaint();
         

		//重绘出棋子
		for(int i=0;i<=row;i++) {
			for(int j=0;j<=column;j++) {
				if(A[i][j]==1) {
					int countx=size*i+20;
					int county=size*j+20;
					g.drawImage(black, countx-20, county-20, 40,40, null);

				}
				else if(A[i][j]==2) {
					int countx=size*i+20;
					int county=size*j+20;
					g.drawImage(white, countx-20, county-20, 40,40, null);

				}
			}
		}
		

	}
	
	public void determineOutcome() {
		  for(int i=4;i<=11;i++) {
			  for(int j=4;j<=11;j++) {
				            //斜方向 
				  int m=1;
				  for(int h=1;h<=4;h++) {
				  if(A[i][j]!=A[i-h][j-h]) m=0;					  
				  }
				  int o=1;
				  for(int h=1;h<=4;h++) {
				  if(A[i][j]!=A[i+h][j-h]) o=0;					  
				  }
				  int p=1;
				  for(int h=1;h<=4;h++) {
				  if(A[i][j]!=A[i-h][j+h]) p=0;					  
				  }
				  int q=1;
				  for(int h=1;h<=4;h++) {
				  if(A[i][j]!=A[i+h][j+h]) q=0;					  
				  }
				  if(q==1||p==1||o==1||m==1) {
					  if(A[i][j]==1) {
						  JOptionPane.showMessageDialog(null,"恭喜黑棋方胜利!");restart();}
					  if(A[i][j]==2) {
						  JOptionPane.showMessageDialog(null,"恭喜白棋方胜利!");restart();}
					  
				  }
				
				  //横竖方向
				   m=1;
				  for(int h=1;h<=4;h++) {
				  if(A[i][j]!=A[i][j-h]) m=0;					  
				  }
				   o=1;
				  for(int h=1;h<=4;h++) {
				  if(A[i][j]!=A[i-h][j]) o=0;					  
				  }
				   p=1;
				  for(int h=1;h<=4;h++) {
				  if(A[i][j]!=A[i][j+h]) p=0;					  
				  }
				   q=1;
				  for(int h=1;h<=4;h++) {
				  if(A[i][j]!=A[i+h][j]) q=0;					  
				  }
				  if(q==1||p==1||o==1||m==1) {
					  if(A[i][j]==1) {
						  JOptionPane.showMessageDialog(null,"恭喜黑棋方胜利!");restart();}
					  if(A[i][j]==2) {
						  JOptionPane.showMessageDialog(null,"恭喜白棋方胜利!");restart();}
					  
				  }
		  }		  
	  }
	}
	
	public void restart() {
		for(int i=0;i<16;i++) {
			for(int j=0;j<16;j++) {
				this.A[i][j]=0;
			}
		}
		this.repaint();
        turn=0;
		ChessList.clear();
		//结束线程
		 time1[num].stop();		 
		 time.reset();
		 num++;
		 this.setPlayer(1).restart();
		 this.setPlayer(2).restart();
	}

	public void determinetime() {

		  if(time.gettime()==0) {
			  
				if(turn==1) {
					if(setPlayer(turn+1).getChance()>0)
					this.setPlayer(turn+1).reduce();
					else {
						JOptionPane.showMessageDialog(null,"恭喜黑棋方胜利!");
						restart();
					}
						
				}
				if(turn==2) {
					if(setPlayer(turn-1).getChance()>0)
						this.setPlayer(turn-1).reduce();
						else {
							JOptionPane.showMessageDialog(null,"恭喜白棋方胜利!");
							restart();
						}
				}
				
			}
		
	}
	
	public void regret() {
		if(ChessList.size()>0) {

			if(ChooseType==0) {
			if(turn==1) {
				if(this.setPlayer(2).judge()>0) {
			Chess chess=(Chess)ChessList.get(ChessList.size()-1);
			ChessList.remove(ChessList.size()-1);
			
			A[chess.x][chess.y]=0;
				turn++;
				this.setPlayer(2).reduce();
				

			}else {
				JOptionPane.showMessageDialog(null,"您已经没有悔棋次数了!");
			}
		}
				
			else if(turn==2) {
					if(this.setPlayer(turn).judge()>0) {
				Chess chess=(Chess)ChessList.get(ChessList.size()-1);
				ChessList.remove(ChessList.size()-1);
				
				A[chess.x][chess.y]=0;
					turn--;
					this.setPlayer(turn).reduce();

				}else {
					JOptionPane.showMessageDialog(null,"您已经没有悔棋次数了!");
				}
			}
		}
			else {
				Chess chess=(Chess)ChessList.get(ChessList.size()-1);
				ChessList.remove(ChessList.size()-1);					
				A[chess.x][chess.y]=0;
				
				Chess chess1=(Chess)ChessList.get(ChessList.size()-1);
				ChessList.remove(ChessList.size()-1);	
				A[chess1.x][chess1.y]=0;
				this.setPlayer(turn).reduce();
			}
			repaint();

		}else {
			JOptionPane.showMessageDialog(null,"不能悔棋!");
		}
	
	}
	
	public void resettime() {
		
		 time1[num].stop();		 
		 time.reset();
		 num++;
		 time1[num]=new Thread(time);
		 time1[num].start();
	}

	public void giveUp() {

		if(turn==1) { 
			JOptionPane.showMessageDialog(null,"恭喜白棋方胜利!");
			restart();
			}
		else {
			JOptionPane.showMessageDialog(null,"恭喜黑棋方胜利!");
		restart();
		
		}
		
	}
	
	public Player setPlayer(int turn) {
		
		if(turn==1) return Player1.getPlayer();
		else return Player2.getPlayer();
		
	}
	
}

