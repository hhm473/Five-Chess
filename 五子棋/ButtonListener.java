package ������;

import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
 
//ʵ�ֶ�JPanel�ļ����ӿڴ���
public class ButtonListener implements GoBangconfig,ActionListener{
	public FiveChess gf;
	public JComboBox box;
	
	public ButtonListener(FiveChess gf,JComboBox box) {
		this.gf=gf;//��ȡ��벿�ֵĻ�
		this.box=box;
	}
	
	//�����淢������ʱ���д���
	public void actionPerformed(ActionEvent e) {
		//��ȡ��ǰ�������ť�����ݣ��ж��ǲ���"��ʼ����Ϸ"�����ť
		if(e.getActionCommand().equals("��ʼ����Ϸ")) {

			String s=JOptionPane.showInputDialog("ѡ����������:1��ѡ����������:2");
	        if(!s.equals("1")&&!s.equals("2")) {
	       	 s=JOptionPane.showInputDialog("������ѡ��ѡ����������:1��ѡ����������:2");
	        }
			gf.sb=Integer.parseInt(s);
			//����ǿ�ʼ����Ϸ�İ�ť����Ϊ��벿�����ü�������
            gf.turn=gf.sb;
            
            gf.time1[gf.num]=new Thread(gf.time);
            gf.time1[gf.num].start();
				
		}
		else if(e.getActionCommand().equals("����")) {
			
			gf.regret();
			gf.resettime();
			
		}
		else if(e.getActionCommand().equals("����")){
			
				gf.giveUp();

			}
		
		//AI
		else if(box.getSelectedItem().equals("�˻���ս")) {
				 gf.ChooseType=1;
				 gf.turn=0;
			}
			else if(box.getSelectedItem().equals("���˶�ս")){
				 gf.ChooseType=0;
				 gf.turn=0;
			}
		
		
		
		}

}

