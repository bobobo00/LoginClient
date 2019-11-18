package TCP;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class LonginClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("--------Client-----------");
		Socket client=new Socket("localhost",8888);//服务器的IP地址和端口
		Send s=new Send(client);
		Recive r=new Recive(client);
		s.send();
		r.print();
		s.release();
		r.release();
		client.close();
		
	}
	
	static class Send{
		private Socket client;
		private ObjectOutputStream  dos;
		private BufferedReader bas;
		User user=new User();
		public Send(Socket client) {
			super();
			this.client = client;
			try {
				dos=new ObjectOutputStream(client.getOutputStream());
				bas=new BufferedReader(new InputStreamReader(System.in));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void send() {
			System.out.println("请输入用户名：");
			try {
				user.setName(bas.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("请输入用户密码：");
			try {
				user.setPassword(bas.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				dos.writeObject(user);
				dos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		private void release() {
			try {
				dos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				bas.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	static class Recive{
		private Socket client;
		private BufferedReader dis;
		public Recive(Socket client) {
			super();
			this.client = client;
			try {
				dis=new BufferedReader(new InputStreamReader(client.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void print() throws IOException {
			String res=dis.readLine();
			System.out.println(res);
		}
		private void release() {
			try {
				dis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
