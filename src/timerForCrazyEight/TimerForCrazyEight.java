package timerForCrazyEight;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class TimerForCrazyEight extends Frame implements ActionListener{

	// field
	private final static int WIDTH  = 500;
	private final static int HEIGHT = 500;
	private Label l = new Label("30");
	private Label taskL = new Label("Timer for Crazy Eight!");
	private Button bt = new Button("Start!");
	private Button btS = new Button("Stop");
	private int count_timer = 0;
	private int iteration = 0;
	private int track = 0;
	private int TASK_TIME = 30;
	private int REST_TIME = 10;
	private Timer timer = null;

	private String soundFile = "./src/timerForCrazyEight/bip.wav";
	private Clip clip;

	private int MAX_track = 8;

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

		setTitle("Timer for Crazy Eight!");
		setLayout(new GridLayout(4,1));
		l.setAlignment(Label.CENTER);
		l.setFont(new Font("Arial", Font.PLAIN, 96));
		taskL.setFont(new Font("Arial", Font.PLAIN,48));
		add(taskL);
		add(l);
		bt.setFont(new Font("Arial", Font.BOLD, 24));
		btS.setFont(new Font("Arial", Font.BOLD, 24));
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
				iteration = 0;
				track = 0;
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
				if(count_timer < 6){
					int fontNum = 96+(50-10*count_timer);
					l.setFont(new Font("Arial", Font.BOLD, fontNum));
					l.setForeground(Color.RED);
					clip = createClip(new File(soundFile));
					clip.start();
				}
				btS.setEnabled(true);
			} else {
				timer.cancel();
				timer = new java.util.Timer(false);
				timer.schedule(new MyTimeTask(), 1000l, 1000l);
				l.setFont(new Font("Arial", Font.PLAIN, 96));
				l.setForeground(Color.BLACK);
				if(track >= MAX_track) {
					l.setText("Finish!");
					taskL.setText("Finish!");
					timer.cancel();
					timer = null;
					btS.setEnabled(false);
				}
				if(iteration%2 == 0){
					taskL.setText("Track iteration is "+ Integer.toString( track+1 ));
					count_timer = TASK_TIME;
					iteration++;
					track++;
				} else {
					if (track < MAX_track) {
						taskL.setText("Rest!");
					}
					count_timer = REST_TIME;
					iteration++;
				}

			}
		}
	}  // end of MyTimeTask

	public static Clip createClip(File path) {
		//指定されたURLのオーディオ入力ストリームを取得
		try (AudioInputStream ais = AudioSystem.getAudioInputStream(path)){

			//ファイルの形式取得
			AudioFormat af = ais.getFormat();

			//単一のオーディオ形式を含む指定した情報からデータラインの情報オブジェクトを構築
			DataLine.Info dataLine = new DataLine.Info(Clip.class,af);

			//指定された Line.Info オブジェクトの記述に一致するラインを取得
			Clip c = (Clip)AudioSystem.getLine(dataLine);

			//再生準備完了
			c.open(ais);

			return c;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}


}//  end of class
