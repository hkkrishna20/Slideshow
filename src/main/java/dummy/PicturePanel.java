package dummy;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PicturePanel extends Canvas {
	public static final Color bcol = new Color(192, 192, 192);
	private static List<String> imageFiles = new ArrayList<String>();
	private Timer timer = null;
	private int counter = 0;
	static ImageIcon[] images = new ImageIcon[10];
	private static JLabel label;

	public Dimension getPreferredSize() {
		return new Dimension(1368,768);
	}

	public void paint(Graphics g) {
		String myFolder = "C:\\Users\\HDMI\\Downloads\\list";
		Toolkit t = Toolkit.getDefaultToolkit();
		Image i = t.getImage(myFolder+"\\Picture1.png");
		g.drawImage(i, 120, 100, this);

	}

	public PicturePanel(String myFolder) {

		final File folder = new File(myFolder);
		listFilesForFolder(folder);
		images = new ImageIcon[imageFiles.size()];
		int count = 0;
		for (String s : imageFiles) {
			System.out.println(s);
			images[count] = new ImageIcon(s);
			count++;
		}
		// images = (ImageIcon[]) imageFiles.toArray();
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.getDefaults().put("Button.disabledShadow", bcol);
		}
//
		catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
//
		} catch (ClassNotFoundException classNotFoundException) {
//
		} catch (InstantiationException instantiationException) {
//
		} catch (IllegalAccessException illegalAccessException) {
		}
		label = new JLabel();
		timer = new Timer(1000, new TimerListener());
	}

	public Timer getTimer() {
		return timer;
	}

	public class TimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			if (counter == images.length) {
				System.out.println(counter);
				((Timer) ae.getSource()).stop();
			} else {
				label.setIcon(images[counter]);
//				PicturePanel.addLabel(label);
				counter++;
			}
		}
	}

	public static void listFilesForFolder(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				imageFiles.add(fileEntry.getAbsolutePath());
				System.out.println(fileEntry.getName());
			}
		}
	}

	public static void addLabel(JLabel label2) {
		// TODO Auto-generated method stub
		PicturePanel.addLabel(label2);
	}

}