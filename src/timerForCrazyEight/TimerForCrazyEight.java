package timerForCrazyEight;

import java.awt.Button;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

public class TimerForCrazyEight extends Frame implements ActionListener{

	// field
	private final static int WIDTH  = 500;
	private final static int HEIGHT = 500;
	private Label l = new Label("30");
	private Label taskL = new Label("Task track 0");
	private Button bt = new Button("タスクトラック");
	private Button btR = new Button("レストトラック");
	private int count = 0;
	private Timer timer = null;
	public int taskTrack = 1; // max = 8

	//constructor
	public TimerForCrazyEight() {
		//終了ボタンを有効にする
		addWindowListener(
			new WindowAdapter() {
				public void windowClosing(WindowEvent evt) {
					System.exit(0);
				}
			}
		);

		//タイトルを設定
		setTitle("Timer for Crazy Eight!");

		setLayout(new GridLayout(4,1));
		l.setAlignment(Label.CENTER);
		l.setFont(new Font("Arial", Font.PLAIN, 96));
		taskL.setFont(new Font("Arial", Font.PLAIN,48));
		add(taskL);
		add(l);
		add(bt);
		add(btR);
		bt.addActionListener(this);
		btR.addActionListener(this);
	}

	//main method
	public static void main(String[] args) {
		TimerForCrazyEight fm = new TimerForCrazyEight();
		fm.setSize(WIDTH, HEIGHT);
		fm.setVisible(true);
	}

	// method
	public void actionPerformed(ActionEvent ev){
		if(ev.getSource() == bt){
			count = 30;
			taskL.setText("Track iteration is "+ Integer.toString( (taskTrack+1)/2 ));
			if (timer == null) {
				timer = new java.util.Timer(false);
				timer.schedule(new MyTimeTask(), 1000l, 1000l);
			}
		}
		if(ev.getSource() == btR){
			count = 10;
			taskL.setText("Rest!");
			if (timer == null) {
				timer = new java.util.Timer(false);
				timer.schedule(new MyTimeTask(), 1000l, 1000l);
			}
		}
	}

	private class MyTimeTask extends TimerTask {
		int count_timer = count;

		@Override
		public void run() {
			if (count_timer > 0) {
				count_timer --;
				l.setText(Integer.toString(count_timer));
			} else {
				timer.cancel();
				timer = null;
				taskTrack++;
			}
		}
	}  // end of MyTimeTask

}//  end of class
