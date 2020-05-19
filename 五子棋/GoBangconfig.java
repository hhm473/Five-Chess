package 五子棋;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

//定义GoBangconfig接口
//定义与棋盘数据相关的接口，保存棋盘的起点，格子大小，行数列数等信息
public interface GoBangconfig {
	int x=20,y=20,size=40,row=15,column=15;
	int UIWIDTH=800;
	int UIHIGHTH=740;
	Image beij=new ImageIcon("C://Users//86139//Desktop//五指棋//背景1.jpg").getImage();
	Image black=new ImageIcon("C://Users//86139//Desktop//五指棋//black.png").getImage();
	Image white=new ImageIcon("C://Users//86139//Desktop//五指棋//white.png").getImage();
	ImageIcon USERPICTURE = new ImageIcon("C:\\Users\\86139\\Desktop\\五指棋\\头像.jpg");
	Dimension dim1=new Dimension(150,600);//设置右半部分的大小
	Dimension dim3=new Dimension(550,0);//设置左半部分的大小
	Dimension dim2=new Dimension(140,50);//设置右边按钮组件的大小
	Dimension dim4=new Dimension(120,120);
	
}
