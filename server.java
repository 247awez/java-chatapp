
import java.net.*;
import java.io.*;

import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.*;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class server  extends JFrame {

	Socket socket;
	ServerSocket server;
	BufferedReader br;
	PrintWriter out;
	private JLabel heading = new JLabel("server area");
	private JTextArea messegeArea = new JTextArea();
	private JTextField messsegeInput = new JTextField();
	private Font font = new Font("Roboto", Font.ITALIC, 20);
	
	public server() {
try {System.out.println("Server is ready");
System.out.println("waiting...");
	server = new ServerSocket(7777);
	socket = server.accept();
	br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	out = new PrintWriter(socket.getOutputStream());
	CreateGUI();
	handlEvents();
	startReading();
	// startWriting();
	
}catch (Exception e) {
	System.out.println("error in server");
}

}
	private void handlEvents() {
		messsegeInput.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == 10) {
					String contentToSend = messsegeInput.getText();
					out.println(contentToSend);
					messegeArea.append("me: " + contentToSend + "\n");
					
					out.flush();
					messsegeInput.setText("");
					messsegeInput.requestFocus();
				}
			
			}

		});

	}
	private void CreateGUI() {
		this.setTitle("server messeger");
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		heading.setFont(font);
		messegeArea.setFont(font);
		messsegeInput.setFont(font);
		this.setLayout(new BorderLayout());
		
		this.add(heading, BorderLayout.NORTH);
		this.add(messegeArea, BorderLayout.CENTER);
		this.add(messsegeInput, BorderLayout.SOUTH);
		heading.setHorizontalAlignment(SwingConstants.CENTER);
		heading.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		messegeArea.setEditable(false);
		heading.setIcon(new ImageIcon("c++.jpg"));
		JScrollPane jScrollPane= new JScrollPane(messegeArea);
		this.add(jScrollPane);
		heading.setHorizontalTextPosition(SwingConstants.CENTER);
		heading.setVerticalTextPosition(SwingConstants.BOTTOM);
		this.setVisible(true);
	}
	public void startReading() {
		Runnable r1=()->{
			System.out.println("Reader Started");
			try {
				String msg;
			
				while (true) {
				msg = br.readLine();
				if(msg.equals("exit")) {
					System.out.println("client has terminated the chat");
					JOptionPane.showMessageDialog(this, "server terminated the chat");
					messsegeInput.setEnabled(false);
					socket.close();
					
					break;
				}
				// System.out.println("server: "+msg);
				messegeArea.append("server: " + msg + "\n");
			
				
			System.out.println("client: "+msg);
			}
		}catch (IOException e) {
			System.out.println("terminated chat");
		}
		};
		new Thread(r1).start();
		
	}
	// public void startWriting() {
	// 	Runnable r2=()->{
    //         try {
    //             System.out.println("writer has started");

    //             while(true) {
			
	// 				BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
	// 				String content = br1.readLine();
                   
	// 		out.println(content);
	// 				out.flush();
    //                 if(content.equals("exit")){
	// 					socket.close();
    //                     break;
    //                 }
    //             }	
	// 			} catch (Exception e) {
	// 				System.out.println("error at write");
				
	// 		}
			
	// 	};
	// 	new Thread(r2).start();
	// }
	

	
	public static void main(String Args[]) {
		new server();

}
	
}
