import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import com.sun.java.swing.plaf.windows.*;

public class TestingGUI extends JPanel implements Runnable {
	private JTextArea infoShow, numberInput;
	private JButton connectBtn, senderBtn;
	private JLabel numberShow;
	private SocketAddress socketAddr;
	private Socket socket;
	private Thread thread;
	private DataInputStream ins;
	private DataOutputStream outs;

	private static final String CONNECT = "���ӷ�����";
	private static final String SENDER = "����";
	private static final String NUMBERSHOW = "����ѧ�ţ�";
	final int portNum = 8080;
	final String serverIPAddress = "localhost";

	public TestingGUI() {
		connectBtn = new JButton(CONNECT);
		senderBtn = new JButton(SENDER);
		numberShow = new JLabel(NUMBERSHOW);
		BtnListener objButtonHandler = new BtnListener();
		numberInput = new JTextArea();
		numberInput.setLineWrap(true);
		infoShow = new JTextArea(20,2);
		infoShow.setLineWrap(true);
		JScrollPane numberInputpane = new JScrollPane(numberInput);
		JScrollPane infoShowpane = new JScrollPane(infoShow);

		JPanel upSplitPane = new JPanel();
		upSplitPane.add(connectBtn);
		upSplitPane.add(numberShow);
		upSplitPane.add(numberInputpane);
		upSplitPane.add(senderBtn);
		connectBtn.addActionListener(objButtonHandler);
		senderBtn.addActionListener(objButtonHandler);

		JSplitPane bigSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upSplitPane, infoShowpane);

		add(bigSplitPane);
		setVisible(true);
	}

	@Override
	public void run() {
		String s = null;
		while (true) {
			try {
				s = ins.readUTF();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			if (s.equals("���ӶϿ�")) { // ���������������е���Ϣ�С�����������ʱ���ر�socket����
				try {
					socket.close();
					infoShow.setText("");
					infoShow.append(s);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
			} else{
				infoShow.setText("");
				infoShow.append(s);
			}
		}
	}

	public void sendMessage() {
		if (socket.isConnected()) {
			String s = numberInput.getText();
			if (s != null) {
				try {
					outs.writeUTF(s);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 
		} else {
			infoShow.setText("");
			infoShow.append("\n...δ����....");
		}
		numberInput.setText("");
	}

	public void linkToServer(String ipAddr, int pNum) {
		try {
			socketAddr = new InetSocketAddress(ipAddr, pNum);
			socket = new Socket();
			socket.connect(socketAddr, 1000); 
			if (socket.isConnected()) {
				ins = new DataInputStream(socket.getInputStream());
				outs = new DataOutputStream(socket.getOutputStream());
				infoShow.setText("");
				infoShow.setText("���ӳɹ�");
			}

		} catch (UnknownHostException e) {
			// e.printStackTrace();
			infoShow.setText("");
			infoShow.setText("�Ҳ����������...\n���ܵĴ�����Ϣ��\n" + e.getMessage());
			//return false;
		} catch (IOException e) {
			// e.printStackTrace();
			infoShow.setText("");
			infoShow.setText("�Ҳ����������...\n���ܵĴ�����Ϣ��\n" + e.getMessage());
			//return false;
		}

		if (thread == null) { // �����߳�
			thread = new Thread(this);
			thread.setPriority(Thread.MIN_PRIORITY);
			thread.start(); // �����߳�
		} 
	}

	class BtnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == senderBtn) {
				sendMessage();
			}

			// ��������˵İ�ť����ʵ��
			if (e.getSource() == connectBtn) {
				linkToServer(serverIPAddress, portNum);
			}
		}
	}

}
