package 五子棋;

import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
 
//实现对JPanel的监听接口处理
public class ButtonListener implements GoBangconfig,ActionListener{
	public FiveChess gf;
	public JComboBox box;
	
	public ButtonListener(FiveChess gf,JComboBox box) {
		this.gf=gf;//获取左半部分的画
		this.box=box;
	}
	
	//当界面发生操作时进行处理
	public void actionPerformed(ActionEvent e) {
		//获取当前被点击按钮的内容，判断是不是"开始新游戏"这个按钮
		if(e.getActionCommand().equals("开始新游戏")) {

			String s=JOptionPane.showInputDialog("选黑棋请输入:1；选白棋请输入:2");
	        if(!s.equals("1")&&!s.equals("2")) {
	       	 s=JOptionPane.showInputDialog("请重新选择。选黑棋请输入:1；选白棋请输入:2");
	        }
			gf.sb=Integer.parseInt(s);
			//如果是开始新游戏的按钮，再为左半部分设置监听方法
            gf.turn=gf.sb;
            
            gf.time1[gf.num]=new Thread(gf.time);
            gf.time1[gf.num].start();
				
		}
		else if(e.getActionCommand().equals("悔棋")) {
			
			gf.regret();
			gf.resettime();
			
		}
		else if(e.getActionCommand().equals("认输")){
			
				gf.giveUp();

			}
		
		//AI
		else if(box.getSelectedItem().equals("人机对战")) {
				 gf.ChooseType=1;
				 gf.turn=0;
			}
			else if(box.getSelectedItem().equals("人人对战")){
				 gf.ChooseType=0;
				 gf.turn=0;
			}
		
		
		
		}

}

