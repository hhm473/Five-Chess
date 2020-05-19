package ������;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Dimension;

import static ������.GoBangconfig.x;
import static ������.GoBangconfig.y;

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
	
	public Graphics g;//����һ֧����
    public ArrayList<Chess>ChessList=new ArrayList<Chess>();
	public int turn=0;//�жϵ�ǰ�ֵ�˭�ˣ�1��ʾ�ڷ���2��ʾ�׷�
	public int num=0;
	public Player p; 
	//public Player p1=new Player(1);
	//public Player p2=new Player(2);
	public Stime time=new Stime();
	public Thread[] time1=new Thread[100];
    public int sb=0;

	public int[][] A=new int [column+1][row+1];//����һ����ά�������������̵��������
	
	
	
	public int ChooseType=0;//0��ʾ���˶�ս��1��ʾ�˻���ս��Ĭ�����˶�ս
	public static HashMap<String,Integer> map = new HashMap<String,Integer>();//���ò�ͬ�����������ӦȨֵ������
	static {

		//����ס
		map.put("01", 25);//��1��
		map.put("02", 22);//��1��
		map.put("001", 17);//��1��
		map.put("002", 12);//��1��
		map.put("0001", 17);//��1��
		map.put("0002", 12);//��1��

		map.put("0102",25);//��1����15
		map.put("0201",22);//��1����10
		map.put("0012",15);//��1����15
		map.put("0021",10);//��1����10
		map.put("01002",25);//��1����15
		map.put("02001",22);//��1����10
		map.put("00102",17);//��1����15
		map.put("00201",12);//��1����10
		map.put("00012",15);//��1����15
		map.put("00021",10);//��1����10

		map.put("01000",25);//��1����15
		map.put("02000",22);//��1����10
		map.put("00100",19);//��1����15
		map.put("00200",14);//��1����10
		map.put("00010",17);//��1����15
		map.put("00020",12);//��1����10
		map.put("00001",15);//��1����15
		map.put("00002",10);//��1����10

		//��ǽ��ס
		map.put("0101",65);//��2����40
		map.put("0202",60);//��2����30
		map.put("0110",80);//��2����40
		map.put("0220",76);//��2����30
		map.put("011",80);//��2����40
		map.put("022",76);//��2����30
		map.put("0011",65);//��2����40
		map.put("0022",60);//��2����30

		map.put("01012",65);//��2����40
		map.put("02021",60);//��2����30
		map.put("01102",80);//��2����40
		map.put("02201",76);//��2����30
		map.put("01120",80);//��2����40
		map.put("02210",76);//��2����30
		map.put("00112",65);//��2����40
		map.put("00221",60);//��2����30

		map.put("01100",80);//��2����40
		map.put("02200",76);//��2����30
		map.put("01010",75);//��2����40
		map.put("02020",70);//��2����30
		map.put("00110",75);//��2����40
		map.put("00220",70);//��2����30
		map.put("00011",75);//��2����40
		map.put("00022",70);//��2����30

		//����ס
		map.put("0111",150);//��3����100
		map.put("0222",140);//��3����80

		map.put("01112",150);//��3����100
		map.put("02221",140);//��3����80

		map.put("01110", 1100);//��3��
		map.put("02220", 1050);//��3��
		map.put("01101",1000);//��3����130
		map.put("02202",800);//��3����110
		map.put("01011",1000);//��3����130
		map.put("02022",800);//��3����110

		map.put("01111",3000);//4����300
		map.put("02222",3500);//4����280
	}
	public int[][] weightArray=new int[column+1][row+1];//����һ����ά���飬����������Ȩֵ
	
	
	
	
	
	//���������
	public static void main(String args[]) {
		FiveChess gf=new FiveChess();//��ʼ��һ�����������Ķ���

		gf.startI();//���÷������н���ĳ�ʼ��
	}
	
	
	public void startI() {
		//��ʼ��һ������,�����ñ����С������
		JFrame jf=new JFrame();
		
		jf.setTitle("������");
		jf.setSize(UIWIDTH,UIHIGHTH);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(3);
		
		jf.setLayout(new BorderLayout());//���ö�������JFrameΪ��ܲ���
		

		//ʵ����ߵĽ��棬��GoBangframe�Ķ�����ӵ���ܲ��ֵ��м䲿��

		this.setBackground(Color.lightGray);//��������������ɫ
		//����Ļ�ֱ�Ӱ���ߵĻ��������ȥ��ָ�����ڿ�ܲ��ֵ��м���

		jf.add(this,BorderLayout.CENTER);//��ӵ���ܲ��ֵ��м䲿��
		
		//ʵ���ұߵ�JPanel��������
		JPanel jp=new JPanel();
		jp.setPreferredSize(dim1);//����JPanel�Ĵ�С
		jp.setBackground(Color.lightGray);//�����ұߵĽ�����ɫΪ��ɫ
		jf.add(jp,BorderLayout.EAST);//��ӵ���ܲ��ֵĶ��߲���
		jp.setLayout(new FlowLayout());//����JPanelΪ��ʽ����
		
		
		String[] userMessage={"pic","�ǳƣ�yy","�Ա���","�ȼ�������"};
		JLabel[] user =new JLabel[4];
		//���ñ���ͼƬ�Ĵ�С
		USERPICTURE.setImage(USERPICTURE.getImage().getScaledInstance(dim4.height, dim4.width,Image.SCALE_DEFAULT ));
		user[0]=new JLabel(USERPICTURE);
		user[0].setPreferredSize(dim4);
		jp.add(user[0]);

		for(int i=1;i<4;i++){
			user[i]=new JLabel(userMessage[i]);
			user[i].setPreferredSize(dim2);
			/*
			 *����setFont������TextField�ı���������Ϣ�������С
			 *textx1.setFont(new Font(Font.DIALOG,Font.PLAIN,30));
			 */	
			user[i].setFont(new Font(Font.MONOSPACED,Font.ITALIC,20));
			jp.add(user[i]);
		}
		
		//������������Ҫ�Ѱ�ť��������μӵ��Ǹ�JPanel����
		//���ð�ť����
		String[] butname= {"��ʼ����Ϸ","����","����"};
		JButton[] button=new JButton[3];

		//���ΰ�������ť�������ȥ
		for(int i=0;i<butname.length;i++) {
			
			button[i]=new JButton(butname[i]);
			button[i].setPreferredSize(dim2);
			jp.add(button[i]);
		}
		
		//����ѡ�ť
				String[] boxname= {"���˶�ս","�˻���ս"};
				JComboBox box=new JComboBox(boxname);
				jp.add(box);

				
		//��ť�����
		FallChess fl=new FallChess();
		fl.setGraphics(this);//��ȡ���ʶ���
		this.addMouseListener(fl);
		ButtonListener butListen=new ButtonListener(this,box);
		
		//��ÿһ����ť�����״̬�¼��ļ����������
		for(int i=0;i<butname.length;i++) {
			button[i].addActionListener(butListen);//��ӷ��������ļ�������
		}
		
		
		box.addActionListener(butListen);
		
		jf.setVisible(true);
		

	}
	
	
	//��д�ػ淽��,������д���ǵ�һ�����JPanel�ķ���
	public void paint(Graphics g) {
		super.paint(g);
		
		//�ػ������
		g.drawImage(beij, 0, 0, 720, 720, null);

		g.setColor(Color.black);
		for(int i=0;i<=row;i++) {
			g.drawLine(x, y+size*i, x+size*column, y+size*i);
		}
		for(int j=0;j<=column;j++) {
			g.drawLine(x+size*j, y, x+size*j, y+size*row);
		}
		
		Font font=new Font("����",Font.ITALIC,20); 
		g.setFont(font);
		 g.drawString("ʣ��ʱ�䣺", 480, 650);
		 g.drawString("���巽��", 330, 650);
		 g.drawString("���1��", 20, 645);
		 g.drawString("���2��", 20, 680);
		 g.drawImage(black, 80, 623, 35,35, null);
		 g.drawImage(white, 80, 660, 35,35, null);
	     if(turn==1) { g.drawImage(black, 400, 625, 35,35, null);}
	        else {g.drawImage(white, 400, 625, 35,35, null);}		 
		 g.drawString("ʣ����������", 150, 650);
         g.drawString(String.valueOf(this.setPlayer(1).getChance())+"��", 280, 650);
         g.drawString(String.valueOf(this.setPlayer(2).getChance())+"��", 280, 680);
         g.setColor(Color.RED);
         g.drawString(String.valueOf(time.gettime())+"s", 580, 650);
         repaint();
         

		//�ػ������
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
				            //б���� 
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
						  JOptionPane.showMessageDialog(null,"��ϲ���巽ʤ��!");restart();}
					  if(A[i][j]==2) {
						  JOptionPane.showMessageDialog(null,"��ϲ���巽ʤ��!");restart();}
					  
				  }
				
				  //��������
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
						  JOptionPane.showMessageDialog(null,"��ϲ���巽ʤ��!");restart();}
					  if(A[i][j]==2) {
						  JOptionPane.showMessageDialog(null,"��ϲ���巽ʤ��!");restart();}
					  
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
		//�����߳�
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
						JOptionPane.showMessageDialog(null,"��ϲ���巽ʤ��!");
						restart();
					}
						
				}
				if(turn==2) {
					if(setPlayer(turn-1).getChance()>0)
						this.setPlayer(turn-1).reduce();
						else {
							JOptionPane.showMessageDialog(null,"��ϲ���巽ʤ��!");
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
				JOptionPane.showMessageDialog(null,"���Ѿ�û�л��������!");
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
					JOptionPane.showMessageDialog(null,"���Ѿ�û�л��������!");
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
			JOptionPane.showMessageDialog(null,"���ܻ���!");
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
			JOptionPane.showMessageDialog(null,"��ϲ���巽ʤ��!");
			restart();
			}
		else {
			JOptionPane.showMessageDialog(null,"��ϲ���巽ʤ��!");
		restart();
		
		}
		
	}
	
	public Player setPlayer(int turn) {
		
		if(turn==1) return Player1.getPlayer();
		else return Player2.getPlayer();
		
	}
	
}

