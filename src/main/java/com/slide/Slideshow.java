package com.slide;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Slideshow implements Runnable {

	public static final Color bcol = new Color(192, 192, 192);
	protected Color hiblue = new Color(0, 0, 0);
	private DisplayFullScreen frame;

	private static SSImage[] imageArray;

	private static SSShower showImages;
	private static List<String> imageFiles = new ArrayList<String>();

	private static SSViewer imageViewer;
	// array of supported extensions (use a List if you prefer)
	static final String[] EXTENSIONS = new String[] { "gif", "png", "bmp", "JPG", "jpg", "bmp", "BMP", "gif", "GIF",
			"WBMP", "png", "PNG", "wbmp", "jpeg", "JPEG"
			// and other formats you need
	};

	public static void listFilesForFolder(final File folder) {
		System.out.println("Here");
		File[] files = folder.listFiles(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				List<String> images = Arrays.asList(ImageIO.getReaderFormatNames());
				String extension = "";
				int i = name.lastIndexOf('.');
				if (i > 0) {
					extension = name.substring(i + 1);
				}

				return images.contains(extension.toLowerCase());
			}
		});

		for (File file : files) {
			Slideshow.imageFiles.add(file.getAbsolutePath());
		}
		System.out.println(Slideshow.imageFiles.size());
	}

	public Slideshow(String myFolder) {
		// String myFolder = "C:\\Users\\HDMI\\Downloads\\list";
		System.out.println(myFolder);
		final File folder = new File(myFolder);
		listFilesForFolder(folder);
		System.out.println(imageFiles.size());
		imageArray = new SSImage[imageFiles.size()];
		Image image[] = new Image[imageFiles.size()];
		String delays[] = new String[imageFiles.size()];
		extracted(image, delays);
	}


	private void extracted(Image[] image, String[] delays) {

		try {
			int i = 0;
			for (String s : imageFiles) {
				String delayS[] = s.split("_");
				String delayTimes = delayS[delayS.length - 1];
				String splitDot[] = delayTimes.split("\\.");
				System.out.println(delayTimes);
				String integr = splitDot[0];
				int inum = Integer.valueOf(integr);
				inum = inum * 1000;
				integr = inum + "";
				// image[i] =resizeImage(bIm,);
				delays[i] = integr;
				imageArray[i] = new SSImage(extractedImage(s), Long.parseLong(integr));
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	private BufferedImage extractedImage(String s) throws IOException {
		Image im = ImageIO.read(new File(s));
		float FACTOR = calculateFactor(im);
		BufferedImage bIm = toBufferedImage(im);
		int scaleX = (int) (bIm.getWidth() * FACTOR);
		System.out.println(scaleX);

		int scaleY = (int) (bIm.getHeight() * FACTOR);
		System.out.println(scaleY);
		Image image = bIm.getScaledInstance(scaleX, scaleY, Image.SCALE_SMOOTH);
		BufferedImage buffered = new BufferedImage(scaleX, scaleY, BufferedImage.TYPE_INT_ARGB);
		// buffered.getGraphics().drawImage(image, 0, 0, null);
		Graphics2D g2d = buffered.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		return buffered;
		// return bIm;
	}


	private float calculateFactor(Image im) {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		System.out.println(screenHeight + "   " + screenWidth);
		// g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);

		int imgWidth = im.getWidth(null);
		int imgHeight = im.getHeight(null);
		System.out.println(imgHeight + "   " + " imgHe" + imgWidth);
		float p = (float)screenHeight / imgHeight;
		float q = (float)screenWidth / imgWidth;
		/*
		 * float pFactor = 1; float qFactor = 1; if (imgWidth > screenWidth) { pFactor =
		 * (float)imgHeight / screenHeight; System.out.println("pFactor" + pFactor);
		 * 
		 * } else if (imgHeight >= screenHeight) { qFactor = (float)imgHeight /
		 * screenHeight; System.out.println("qFactor" + qFactor); }
		 * 
		 * screenHeight = (int) (screenHeight * qFactor); screenWidth = (int)
		 * (screenWidth * pFactor);
		 */	
		float m = Math.min(p, q);
		System.out.println(m);
		return m;
	}

	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}

	private BufferedImage resizeImage(BufferedImage originalImage, int width, int height, int type) throws IOException {
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}

	/**
	 * @param image
	 * @param delaus
	 */
	public static Image scaleImage(BufferedImage original, int newWidth, int newHeight) {

		int imgWidth = original.getWidth(null);
		int imgHeight = original.getHeight(null);
		// do nothing if new and old resolutions are same
		if (imgWidth == newWidth && imgHeight == newHeight) {
			return original;
		}

		int[] rawInput = new int[imgHeight * imgWidth];
		// original.getRGB(rawInput, 0, imgWidth, 0, 0, imgWidth, imgHeight);
		int[] rawOutput = new int[newWidth * newHeight];
		// YD compensates for the x loop by subtracting the width back out
		int YD = (imgHeight / newHeight) * imgWidth - imgWidth;
		int YR = imgHeight % newHeight;
		int XD = imgWidth / newWidth;
		int XR = imgWidth % newWidth;
		int outOffset = 0;
		int inOffset = 0;
		for (int y = newHeight, YE = 0; y > 0; y--) {
			for (int x = newWidth, XE = 0; x > 0; x--) {
				rawOutput[outOffset++] = rawInput[inOffset];
				inOffset += XD;
				XE += XR;
				if (XE >= newWidth) {
					XE -= newWidth;
					inOffset++;
				}
			}
			inOffset += YD;
			YE += YR;
			if (YE >= newHeight) {
				YE -= newHeight;
				inOffset += imgWidth;
			}
		}
		return original;

		// Image.createRGBImage(rawOutput, newWidth, newHeight, false);
	}
	@Override
	public void run() {
		frame = new DisplayFullScreen();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				exitProcedure();
			}
		});
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		imageViewer = new SSViewer(width, height);

		frame.add(imageViewer);
		frame.setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setResizable(false); // Disable the Resize Button
		frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		// frame.setLocationByPlatform(true);
		frame.addWindowListener(getWindowAdapter());
		frame.pack();
		frame.setVisible(true);
		showImages = new SSShower(imageArray, imageViewer);
		if (showImages.imageArray.length > 0) {
			new Thread(showImages).start();
		}

	}

	private WindowAdapter getWindowAdapter() {
		return new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {// overrode to show message
				super.windowClosing(we);

				JOptionPane.showMessageDialog(frame, "Cant Exit");
			}

			@Override
			public void windowIconified(WindowEvent we) {
				frame.setState(JFrame.NORMAL);
				JOptionPane.showMessageDialog(frame, "Cant Minimize");
			}
		};
	}

	private void exitProcedure() {
		showImages.setRunning(false);
		frame.dispose();
		System.exit(0);
	}

	public class SSImage {

		private final long delay;

		private final Image image;

		public SSImage(Image image, long delay) {
			this.image = image;
			this.delay = delay;
		}

		public long getDelay() {
			return delay;
		}

		public Image getImage() {
			return image;
		}

	}

	public class SSViewer extends JPanel {

		private static final long serialVersionUID = -7893539139464582702L;

		private Image image;

		public SSViewer(int width, int height) {
			this.setBackground(Color.BLACK);
			this.setPreferredSize(new Dimension(width, height));
		}

		public void setImage(Image image) {
			this.image = image;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (image != null) {
				System.out.println("----------------------------------");
				System.out.println(this.getWidth() + " \'  " + this.getHeight());
				System.out.println(image.getWidth(this) + " \'  " + image.getHeight(this));
				System.out.println((this.getWidth() - image.getWidth(this)) / 2 + " "
						+ (this.getHeight() - image.getHeight(this)) / 2);

				/*
				 * g.drawImage(image, (this.getWidth() - image.getWidth(this)) / 2,
				 * (this.getHeight() - image.getHeight(this)) / 2, this);
				 */
				extracted(g);
				System.out.println("******************************************");
			}
		}

		private void extracted(Graphics g) {
			g.setColor(Color.BLACK);
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int screenHeight = screenSize.height;
			int screenWidth = screenSize.width;
			System.out.println(screenHeight + "" + screenWidth);
			// g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
			int imgWidth = image.getWidth(null);
			int imgHeight = image.getHeight(null);

			double imgAspect = (double) imgHeight / imgWidth;

			int canvasWidth = screenSize.width;
			int canvasHeight = screenSize.height;

			double canvasAspect = (double) canvasHeight / canvasWidth;

			int x1 = 0; // top left X position
			int y1 = 0; // top left Y position
			int x2 = 0; // bottom right X position
			int y2 = 0; // bottom right Y position

			if (imgWidth < canvasWidth && imgHeight < canvasHeight) {
				// the image is smaller than the canvas
				x1 = (canvasWidth - imgWidth) / 2;
				y1 = (canvasHeight - imgHeight) / 2;
				x2 = imgWidth + x1;
				y2 = imgHeight + y1;

			} else {
				if (canvasAspect > imgAspect) {
					y1 = canvasHeight;
					// keep image aspect ratio
					canvasHeight = (int) (canvasWidth * imgAspect);
					y1 = (y1 - canvasHeight) / 2;
				} else {
					x1 = canvasWidth;
					// keep image aspect ratio
					canvasWidth = (int) (canvasHeight / imgAspect);
					x1 = (x1 - canvasWidth) / 2;
				}
				x2 = canvasWidth + x1;
				y2 = canvasHeight + y1;
			}

			g.drawImage(image, x1, y1, x2, y2, 0, 0, imgWidth, imgHeight, null);
		}
	}

	public class DisplayFullScreen extends JFrame {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public DisplayFullScreen() {

			addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent ke) { // handler
					/*
					 * if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) { System.out.println("escaped ?");
					 * DisplayFullScreen.this.dispose(); } else { System.out.println("not escaped");
					 * }
					 */
					DisplayFullScreen.this.dispose();
				}
			});
			// on ESC key close frame
			getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
					.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
			getRootPane().getActionMap().put("Cancel", new AbstractAction() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					System.exit(0);
					// framename.setVisible(false);
				}
			});
		}

	}

	public class SSShower implements Runnable {

		private int counter;

		private volatile boolean running;

		private SSViewer ssviewer;

		private SSImage[] imageArray;

		public SSShower(SSImage[] imageArray, SSViewer ssviewer) {
			this.imageArray = imageArray;
			this.ssviewer = ssviewer;
			this.counter = 0;
			this.running = true;
		}

		@Override
		public void run() {
			while (running) {
				SSImage ssimage = imageArray[counter];
				ssviewer.setImage(ssimage.getImage());
				repaint();
				sleep(ssimage.getDelay());
				counter = ++counter % imageArray.length;
			}
		}

		private void repaint() {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					ssviewer.repaint();
				}
			});
		}

		private void sleep(long delay) {
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {

			}
		}

		public synchronized void setRunning(boolean running) {
			this.running = running;
		}
	}

	public static void main(String[] args) {

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
		SwingUtilities.invokeLater(new Slideshow("C:\\Users\\HDMI\\Downloads\\list\\tt"));
	}
}