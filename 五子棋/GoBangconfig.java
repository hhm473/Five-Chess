package ������;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

//����GoBangconfig�ӿ�
//����������������صĽӿڣ��������̵���㣬���Ӵ�С��������������Ϣ
public interface GoBangconfig {
	int x=20,y=20,size=40,row=15,column=15;
	int UIWIDTH=800;
	int UIHIGHTH=740;
	Image beij=new ImageIcon("C://Users//86139//Desktop//��ָ��//����1.jpg").getImage();
	Image black=new ImageIcon("C://Users//86139//Desktop//��ָ��//black.png").getImage();
	Image white=new ImageIcon("C://Users//86139//Desktop//��ָ��//white.png").getImage();
	ImageIcon USERPICTURE = new ImageIcon("C:\\Users\\86139\\Desktop\\��ָ��\\ͷ��.jpg");
	Dimension dim1=new Dimension(150,600);//�����Ұ벿�ֵĴ�С
	Dimension dim3=new Dimension(550,0);//������벿�ֵĴ�С
	Dimension dim2=new Dimension(140,50);//�����ұ߰�ť����Ĵ�С
	Dimension dim4=new Dimension(120,120);
	
}
