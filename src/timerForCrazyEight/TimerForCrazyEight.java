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
	private Button btS = new Button("ストップ");
	public int count_timer = 0;
	public int iteration = 0;
	public int track = 0;
	public Timer timer = null;

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

		setTitle("Timer for Crazy Eight!");		//タイトルを設定
		setLayout(new GridLayout(4,1));
		l.setAlignment(Label.CENTER);
		l.setFont(new Font("Arial", Font.PLAIN, 96));
		taskL.setFont(new Font("Arial", Font.PLAIN,48));
		add(taskL);
		add(l);
		add(bt);
		add(btS);
		btS.setEnabled(false);
		bt.addActionListener(this);
		btS.addActionListener(this);
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
				timer = new java.util.Timer(false);
				timer.schedule(new MyTimeTask(), 1000l, 1000l);
		}

		if(ev.getSource() == btS){
			timer.cancel();
			timer = null;
			btS.setEnabled(false);
		}
	}

	private class MyTimeTask extends TimerTask {

		@Override
		public void run() {
			if (count_timer > 0) {
				count_timer --;
				l.setText(Integer.toString(count_timer));
				btS.setEnabled(true);
			} else {
				timer.cancel();
				timer = new java.util.Timer(false);
				timer.schedule(new MyTimeTask(), 1000l, 1000l);
				if(iteration%2 == 0){
					taskL.setText("Track iteration is "+ Integer.toString( track+1 ));
					count_timer = 31;
					iteration++;
					track++;
				} else {
					taskL.setText("Rest!");
					count_timer = 11;
					iteration++;
				}

			}
		}
	}  // end of MyTimeTask

}//  end of class
