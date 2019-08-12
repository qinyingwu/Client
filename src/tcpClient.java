import java.io.*;
import java.net.*;
import java.sql.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import com.sun.java.swing.plaf.windows.*;
import javax.swing.JFrame;
public class tcpClient {
	private static void createAndShowGUI(){
	     JFrame.setDefaultLookAndFeelDecorated(true);
	     JFrame frame = new JFrame("查询信息");
	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     TestingGUI newContentPane = new TestingGUI();
	     newContentPane.setOpaque(true);
	     frame.setContentPane(newContentPane);
	     frame.pack();
	     frame.setVisible(true);
	     frame.setLocationRelativeTo(null);
	     frame.setSize(360,130);
	  }
public static void main(String[] args) throws IOException{
//建立Socket，服务器在本机的8888端口处进行“侦听”
	javax.swing.SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		   createAndShowGUI();
		}
        });
    }
}