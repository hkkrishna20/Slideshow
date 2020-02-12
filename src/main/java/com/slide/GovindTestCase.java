package com.slide;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicArrowButton;

public class GovindTestCase extends JFrame implements ActionListener, DropTargetListener {
	static String textFieldDirectory = "C:\\Users\\Public\\Pictures";

	static {

		try (InputStream input = new FileInputStream("C:\\Users\\Public\\Pictures\\config.properties")) {

			Properties prop = new Properties();

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			System.out.println(prop.getProperty("path"));
			textFieldDirectory = prop.getProperty("path");
		} catch (IOException ex) {
			try (InputStream input = GovindTestCase.class.getClassLoader().getResourceAsStream("config.properties")) {

				Properties prop = new Properties();

				if (input == null) {
					System.out.println("Sorry, unable to find config.properties");
				}

				// load a properties file from class path, inside static method
				prop.load(input);

				System.out.println(prop.getProperty("path"));

				ex.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	public static final Color bcol = new Color(192, 192, 192);
	protected Color hiblue = new Color(0, 0, 0);
	private static final long serialVersionUID = 1L;
	private File fromFile = new File("C:\\Users\\HDMI\\Downloads\\WhatsApp Image 2020-02-06 at 19.17.48.jpeg");
	private File toFile = new File("C:\\Users\\HDMI\\Downloads\\WhatsApp Image 2020-02-1106 at 19.17.48.jpeg");
	private long fileLength;
	private String fileName;
	private JProgressBar progressBar;
	static JTextField userInput;
	static JTextField defaultTime;
	static String timeS = "10";

	private final JTextArea txtContent = new JTextArea(10, 100);
	JFileChooser chooser;
	String choosertitle;
	DropTarget dt;
	static JFrame jf = null;
	JTextArea ta = new JTextArea();
	String[] folder1;
	public static TextArea tobeCopied = new TextArea(30, 50);
	public static TextArea slideShowDir = new TextArea(30, 50);
	Map<String, String> folderMap = new HashMap<String, String>();
	Map<String, String> intitalDir = new HashMap<String, String>();
	Map<String, HashMap<String, String>> choices = new HashMap<String, HashMap<String, String>>();
	static Map<String, List<String>> listOfFiles = new TreeMap<String, List<String>>();
	static Map<String, CopyImage> listofCopyImages = new TreeMap<String, CopyImage>();
	static Map<String, String> listOfSeconds = new TreeMap<String, String>();

	public GovindTestCase() {

		super("ATM");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		setVisible(false);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0); // calling the method is a must
			}
		});
	}

	private void showGui() {
		// create the jframe
		JFrame jFrame = new JFrame();
		jFrame.setResizable(false);
		jf = jFrame;
		jFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0); // calling the method is a must
			}
		});
		JPanel top = new JPanel(new FlowLayout());
		JLabel label = new JLabel("Interval");
		JButton jButton = new JButton("Change");
		jButton.setSize(75, 75);
		jButton.setBounds(20, 30, 50, 30);
		JButton jButtonSlideShow = new JButton("SlideShow");

		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(620, 580);
		jFrame.getContentPane().setBackground(Color.WHITE);
		defaultTime = new JTextField(timeS, 10); // accepts upto 10 characters
		userInput = new JTextField(textFieldDirectory);
		userInput.setEditable(false);

// accepts upto 10 characters
		JPanel green2 = new JPanel();

		jButton.addActionListener((e) -> {
			try {
				submitAction();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		// Listen for changes in the text

		userInput.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				warn();
			}

			public void removeUpdate(DocumentEvent e) {
				warn();
			}

			public void insertUpdate(DocumentEvent e) {
				warn();
			}

			public void warn() {
				/*
				 * if (Integer.parseInt(userInput.getText())<=0){
				 * JOptionPane.showMessageDialog(null,
				 * "Error: Please enter number bigger than 0", "Error Message",
				 * JOptionPane.ERROR_MESSAGE); }
				 */
				if (!(userInput.getText().isEmpty())) {
					textFieldDirectory = userInput.getText();
					System.out.println("" + textFieldDirectory);
				} else {
					textFieldDirectory = System.getProperty("user.home");
					System.out.println(textFieldDirectory);
				}

			}
		});

		//
		defaultTime.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				warn();
			}

			public void removeUpdate(DocumentEvent e) {
				warn();
			}

			public void insertUpdate(DocumentEvent e) {
				warn();
			}

			public void warn() {
				/*
				 * if (Integer.parseInt(userInput.getText())<=0){
				 * JOptionPane.showMessageDialog(null,
				 * "Error: Please enter number bigger than 0", "Error Message",
				 * JOptionPane.ERROR_MESSAGE); }
				 */
				if (!(defaultTime.getText().isEmpty())) {
					timeS = defaultTime.getText();
					System.out.println("" + defaultTime.getText());

				} else {
					timeS = "1";
					System.out.println(timeS);
				}

			}
		});
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHWEST;

		c.gridx = 0;// set the x location of the grid for the next component
		c.gridy = 0;// set the y location of the grid for the next component

		top.add(label, c);
		c.gridx = 1;// set the x location of the grid for the next component
		c.gridy = 0;// change the y location

		top.add(defaultTime, c);

		c.gridx = 3;
		c.gridy = 0;// change the y location

		c.gridx = 2;
		c.gridy = 0;
		// change the y location
		/*
		 * label = new JLabel("3. Target Folder");
		 * 
		 * top.add(label, c); // Components Added using Flow Layout
		 */ c.gridx = 4;
		c.gridy = 0;// change the y location

		top.add(userInput, c);

		// create the right panel
		/*
		 * JPanel middle = new JPanel(); middle.setBackground(Color.orange);
		 */

		// create the bottom panel

//		JPanel green = new JPanel();
//		green.setBackground(Color.green);
//		String data1 = "";

		/*
		 * JPanel end = new JPanel(); end.setBackground(Color.CYAN); folder1 =
		 * choices.keySet().toArray(new String[choices.size()]);
		 */
		/*
		 * JComboBox cmiddleb = new JComboBox(folder1); cmiddleb.setBounds(50, 100, 90,
		 * 20); middle.add(cmiddleb); cmiddleb.setSelectedItem(null);
		 */
		/*
		 * JComboBox<String> cendb = new JComboBox<>(new DefaultComboBoxModel<>());
		 * cendb.setBounds(50, 100, 90, 20); end.add(cendb);
		 * cendb.setSelectedItem(null);
		 */
		/*
		 * cmiddleb.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { String value =
		 * (String) cmiddleb.getSelectedItem(); // List<String> secondValues = ;
		 * HashMap<String, String> val = choices.get(value); List<String> list = new
		 * ArrayList<String>(); for (Map.Entry<String, String> entry : val.entrySet()) {
		 * String key = entry.getKey(); String values = entry.getValue();
		 * list.add(values); // do stuff } thisIsAStringArray = new String[list.size()];
		 * int count = 0; for (String s : list) { thisIsAStringArray[count++] = s; }
		 * 
		 * DefaultComboBoxModel model = (DefaultComboBoxModel) cendb.getModel();
		 * model.removeAllElements(); for (String s : list) { model.addElement(s); } //
		 * System.out.println("Here"); } });
		 */

		/*
		 * MyItemListener actionListener = new MyItemListener();
		 * cmiddleb.addItemListener(actionListener);
		 * 
		 * MySubItemListener subListener = new MySubItemListener();
		 * cendb.addItemListener(subListener);
		 */
		/*
		 * JButton jButtonCopy = new JButton("6. Start Copy");
		 * jButtonCopy.addActionListener((e) -> { try {
		 * 
		 * 
		 * } catch (IOException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } });
		 */
		jButtonSlideShow.addActionListener((e) -> {
			Thread thread = new Thread() {
				public void run() {
					System.out.println("Thread Running");
					System.out.println("textFieldDirectory" + textFieldDirectory);
					startSlideShow(textFieldDirectory);

				}
			};

			thread.start();

		});

		FileDragDemo fileDragDemo = new FileDragDemo();

		c.gridx = 6;// set the x location of the grid for the next component
		c.gridy = 0;// set the y location of the grid for the next component
		JLabel lc = new JLabel("Drop images here");
		green2.add(lc);
		green2.add(new BasicArrowButton(BasicArrowButton.SOUTH), BorderLayout.SOUTH);
		green2.add(fileDragDemo, c);
		// ta.setBackground(Color.white);
		// green2.add(ta);
		// dt = new DropTarget(green2, this);

		/*
		 * green2.setDropTarget(new DropTarget() {
		 *//**
			* 
			*//*
				 * // List<String> lis= new ArrayList<String>();
				 * 
				 * @SuppressWarnings("unchecked") public synchronized void
				 * 
				 * drop(DropTargetDropEvent evt) { try {
				 * evt.acceptDrop(DnDConstants.ACTION_COPY); List droppedFiles = (List)
				 * evt.getTr
				 * 
				 * ansferable().getTransferData(DataFlavor.javaFileListFlavor); if
				 * (droppedFiles.size() > 1) { JOptionPane.showMessageDialog(green2,
				 * "Sorry...can't handle more than one files together."); } else { File
				 * droppedFile = (File) droppedFiles.get(0); // Component lis;
				 * lis.add(droppedFile.getAbsolutePath()); listString[0] =
				 * droppedFile.getAbsolutePath(); } } catch (Exception ex) {
				 * ex.printStackTrace(); } } });
				 */
		// sourcePath = listString[0];
		// green2.add(jButtonCopy);
		top.add(jButton, c);
		c.gridx = 1;// set the x location of the grid for the next component
		c.gridy = 7;// set the y location of the grid for the next component
		top.add(jButtonSlideShow, c);
		// create the split panes
		c.gridx = 3;// set the x location of the grid for the next component
		c.gridy = 6;// set the y location of the grid for the next component

		// top.add(lc, c);
		//// green.add(tobeCopied);
		// green.add(slideShowDir);
		tobeCopied.setEditable(false);
		slideShowDir.setEditable(false);

		File folder = new File(textFieldDirectory);

		String fileList = search(folder);
		slideShowDir.setText(fileList);

		ResizableSplitPane horizontalSplitright =

				new ResizableSplitPane(JSplitPane.VERTICAL_SPLIT, top, green2, jFrame);

		/*
		 * ResizableSplitPane splitright =
		 * 
		 * new ResizableSplitPane(JSplitPane.VERTICAL_SPLIT, horizontalSplitright,
		 * green, jFrame);
		 */
		/*
		 * ResizableSplitPane topSplit = new
		 * ResizableSplitPane(JSplitPane.HORIZONTAL_SPLIT, top, , jFrame);
		 */
		// ResizableSplitPane verticalSplit= new
		// ResizableSplitPane(JSplitPane.HORIZONTAL_SPLIT, , topSplit , jFrame);

//		ResizableSplitPane verticalSplit2 = new ResizableSplitPane(JSplitPane.VERTICAL_SPLIT, verticalSplit, ,
		// jFrame);
		// jFrame.getContentPane().setLayout(null);
		jFrame.getContentPane().add(horizontalSplitright);
		// show the gui
		jFrame.setVisible(true);
	}

	public static String search(final File folder) {

		String srch = "";
		for (final File f : folder.listFiles()) {

			if (f.isDirectory()) {
				srch = srch + "\n" + search(f);
			}

			if (f.isFile()) {

				srch = srch + "\n" + f.getName();
			}

		}
		return srch;
	}

	private void submitAction() throws IOException {

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File(textFieldDirectory));
		chooser.setDialogTitle("choosertitle");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
			System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
			textFieldDirectory = chooser.getSelectedFile().getAbsolutePath();
			userInput.setText(textFieldDirectory);
		} else {
			System.out.println("No Selection ");
		}
		// Properties props = new Properties();
		System.out.println("Inside Submit Action" + textFieldDirectory);// do whatever you want with the variable, I
																		// just printed it to the
		/*
		 * FileOutputStream out = new
		 * FileOutputStream("src/main/resources/main.properties");
		 * props.setProperty("folder", textFieldDirectory); props.store(out, null);
		 * out.close(); // console
		 */

	}

	private static void selectDirectory() throws IOException {
		// TODO Auto-generated method stub
		// Properties props = new Properties();
		JFileChooser fileopen = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("c files", "c");
		fileopen.addChoosableFileFilter(filter);
		fileopen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int ret = fileopen.showDialog(null, "Open file");

		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = fileopen.getSelectedFile();
			textFieldDirectory = file.getAbsolutePath();
			System.out.println(textFieldDirectory);
			/*
			 * FileOutputStream out = new
			 * FileOutputStream("src/main/resources/main.properties");
			 * props.setProperty("folder", textFieldDirectory); props.store(out, null);
			 * out.close();
			 */
		}
	}

	static void copyAction() throws IOException {
		// TODO Auto-generated method stub
		final Path path = Paths.get(textFieldDirectory);
		if (Files.notExists(path)) {
			Files.createFile(Files.createDirectories(path)).toFile();
		}
		File dir = new File(textFieldDirectory);
		if (!dir.exists())
			dir.mkdirs();
		/*
		 * String source = null; String dest = null; FileChannel sourceChannel = null;
		 * FileChannel destChannel = null; try {
		 * 
		 * 
		 * sourceChannel = new FileInputStream(source).getChannel(); destChannel = new
		 * FileOutputStream(dest).getChannel(); destChannel.transferFrom(sourceChannel,
		 * 0, sourceChannel.size()); } finally { sourceChannel.close();
		 * destChannel.close(); }
		 */
		loopOverFiles();

		listOfFiles = new HashMap<String, List<String>>();
		listOfSeconds = new HashMap<String, String>();
		listofCopyImages = new TreeMap<String, CopyImage>();
		GovindTestCase.tobeCopied.setText("");
	}

	/**
	 * 
	 */
	private static void loopOverFiles() {
		ParallelTasks tasks = new ParallelTasks();
		for (Entry<String, CopyImage> entry : listofCopyImages.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			CopyImage filess = entry.getValue();
			String timeStamp = filess.getTmestmp();
			String sec = filess.getSec();
			extracted(tasks, filess, timeStamp, sec);

		}
	}

	private static String fileExtension(String name) {
		if (name.lastIndexOf(".") != -1 && name.lastIndexOf(".") != 0)
			return name.substring(name.lastIndexOf(".") + 1);
		else
			return "txt";
	}

	static String stripExtension(String str) {
		// Handle null case specially.

		if (str == null)
			return null;

		// Get position of last '.'.

		int pos = str.lastIndexOf(".");

		// If there wasn't any '.' just return the string as is.

		if (pos == -1)
			return str;

		// Otherwise return the string, up to the dot.

		return str.substring(0, pos);
	}

	private static void extracted(ParallelTasks tasks, CopyImage filess, String timeStamp, String sec) {
		try {
			List<String> lsit = filess.getFiles();
			for (String lis : lsit) {
				Path p = Paths.get(lis);
				String file = p.getFileName().toString();

				String fileExt = fileExtension(file);
				file = stripExtension(file);
				if (!fileExt.isEmpty()) {
					System.out.println(file);
					tasks.add(new CopyFileTask(lis,
							textFieldDirectory + "\\" + timeStamp + "_" + file + "_" + sec + "." + fileExt));

				}
			}
			tasks.go();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Thread to copy files from one place to another
	public void CopyFileFromTo(String file, String fromDirectoryName, String destinationDirectoryName)
			throws IOException {
		// TODO: Establish when the thread has ended, no callbacks for
		// CopyFileToDirectory? :(
		// Copy from file to directory
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Path src = Paths.get(fromDirectoryName + "\\" + file);
				Path dest = Paths.get(destinationDirectoryName);
				try {
					Files.copy(src, dest);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t.start();
	}

	public String getContent(String path) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
		String sCurrentLine, result = "";
		while ((sCurrentLine = br.readLine()) != null) {
			result += sCurrentLine + "\n";
		}
		return result;
	}

	public void actionPerformed(ActionEvent e) {
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("C:\\"));
		chooser.setDialogTitle(choosertitle);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		//
		// disable the "All files" option.
		//
		chooser.setAcceptAllFileFilterUsed(false);
		//
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
			System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
		} else {
			System.out.println("No Selection ");
		}
	}

	public void dragEnter(DropTargetDragEvent dtde) {
		// System.out.println("Drag Enter");
	}

	public void dragExit(DropTargetEvent dte) {
		// System.out.println("Drag Exit");
	}

	public void dragOver(DropTargetDragEvent dtde) {
		// System.out.println("Drag Over");
	}

	public void dropActionChanged(DropTargetDragEvent dtde) {
		// System.out.println("Drop Action Changed");
	}

	public void drop(DropTargetDropEvent dtde) {
		System.out.println("Here");
		try {
			// Ok, get the dropped object and try to figure out what it is
			Transferable tr = dtde.getTransferable();
			DataFlavor[] flavors = tr.getTransferDataFlavors();
			for (int i = 0; i < flavors.length; i++) {
				System.out.println("Possible flavor: " + flavors[i].getMimeType());
				// Check for file lists specifically
				if (flavors[i].isFlavorJavaFileListType()) {
					// Great! Accept copy drops...
					dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
					ta.setText("Successful file list drop.\n\n");

					// And add the list of file names to our text area
					java.util.List list = (java.util.List) tr.getTransferData(flavors[i]);
					for (int j = 0; j < list.size(); j++) {
						ta.append(list.get(j) + "\n");
					}

					// If we made it this far, everything worked.
					dtde.dropComplete(true);
					return;
				}
				// Ok, is it another Java object?
				else if (flavors[i].isFlavorSerializedObjectType()) {
					dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
					ta.setText("Successful text drop.\n\n");
					Object o = tr.getTransferData(flavors[i]);
					ta.append("Object: " + o);
					dtde.dropComplete(true);
					return;
				}
				// How about an input stream?
				else if (flavors[i].isRepresentationClassInputStream()) {
					dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
					ta.setText("Successful text drop.\n\n");
					ta.read(new InputStreamReader((InputStream) tr.getTransferData(flavors[i])),
							"from system clipboard");
					dtde.dropComplete(true);
					return;
				}
			}
			// Hmm, the user must not have dropped a file list
			System.out.println("Drop failed: " + dtde);
			dtde.rejectDrop();
		} catch (Exception e) {
			e.printStackTrace();
			dtde.rejectDrop();
		}
	}

	public void dropIntoTextArea(DropTargetDropEvent dtde) {

		System.out.println("Here");
		try {
			// Ok, get the dropped object and try to figure out what it is
			Transferable tr = dtde.getTransferable();
			DataFlavor[] flavors = tr.getTransferDataFlavors();
			for (int i = 0; i < flavors.length; i++) {
				// System.out.println("Possible flavor: "
				// + flavors[i].getMimeType());
				// Check for file lists specifically
				if (flavors[i].isFlavorJavaFileListType()) {
					// Great! Accept copy drops...
					dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);

					// And add the list of file names to our text area
					java.util.List list = (java.util.List) tr.getTransferData(flavors[i]);
					for (int j = 0; j < list.size(); j++) {
						txtContent.append(getContent(list.get(j).toString()) + "\n");
					}

					// If we made it this far, everything worked.
					dtde.dropComplete(true);
					return;
				}
				// Ok, is it another Java object?
				else if (flavors[i].isFlavorSerializedObjectType()) {
					dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);

					Object o = tr.getTransferData(flavors[i]);
					txtContent.append(String.valueOf(o));
					dtde.dropComplete(true);
					return;
				}
				// How about an input stream?
				else if (flavors[i].isRepresentationClassInputStream()) {
					dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);

					txtContent.read(new InputStreamReader((InputStream) tr.getTransferData(flavors[i])),
							"from system clipboard");
					dtde.dropComplete(true);
					return;
				}
				// How about plain text?
				else if (flavors[i].isFlavorTextType()) {
					dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
					InputStreamReader stream = (InputStreamReader) tr.getTransferData(flavors[i]);
					BufferedReader in = new BufferedReader(stream);
					String line = null;
					while ((line = in.readLine()) != null) {
						txtContent.append(line + "\n");
					}
					dtde.dropComplete(true);
					return;
				}
			}
			// Hmm, the user must not have dropped a file list
			System.out.println("Drop failed: " + dtde);
			dtde.rejectDrop();
		} catch (Exception e) {
			e.printStackTrace();
			dtde.rejectDrop();
		}
	}

	class MyItemListener implements ItemListener {
		// This method is called only if a new item has been selected.
		public void itemStateChanged(ItemEvent evt) {
			JComboBox cb = (JComboBox) evt.getSource();
			Object item = evt.getItem();
			if (evt.getStateChange() == ItemEvent.SELECTED) {
				// System.out.println(item.toString());
				System.out.println(item);
				HashMap<String, String> val = choices.get(item.toString());
				List<String> list = new ArrayList<String>();
				for (Map.Entry<String, String> entry : val.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					list.add(value);
					// do stuff
				}
//				thisIsAStringArray = new String[list.size()];
				int count = 0;
				for (String s : list) {
					// thisIsAStringArray[count++] = s;
				}
				// Item was just selected
			} else if (evt.getStateChange() == ItemEvent.DESELECTED) {
				// Item is no longer selected
				// System.out.println("Not Selected" + item.toString());
			}
		}
	}

	class MySubItemListener implements ItemListener {
		// This method is called only if a new item has been selected.
		public void itemStateChanged(ItemEvent evt) {
			JComboBox cb = (JComboBox) evt.getSource();

			Object item = evt.getItem();

			if (evt.getStateChange() == ItemEvent.SELECTED) {
				// System.out.println(item.toString());
				// Item was just selected
			} else if (evt.getStateChange() == ItemEvent.DESELECTED) {
				// Item is no longer selected
				// System.out.println("Not Selected" + item.toString());
			}
		}
	}

	private void startSlideShow(String folderName) {
		// TODO Auto-generated method stub
		new Thread() {
			@Override
			public void run() {

				SwingUtilities.invokeLater(new Slideshow(folderName));
			}
		}.start();

	}

	public Dimension getPreferredSize() {
		return new Dimension(200, 200);
	}

	public static void main(String[] args) throws IOException {
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

		GovindTestCase tc = new GovindTestCase();
		if (textFieldDirectory.trim().isEmpty() || (textFieldDirectory.trim().length() == 0)) {
			selectDirectory();
		}
		tc.showGui();
	}
}

/*
 * private void readDirectoryIntital() { Properties prop = new Properties();
 * InputStream input = null; try {
 * 
 * prop.load(GovindTestCase.class.getResourceAsStream("/main.properties"));
 * Enumeration<?> e = prop.propertyNames(); while (e.hasMoreElements()) { String
 * key = (String) e.nextElement(); String value = prop.getProperty(key);
 * intitalDir.put(key, value); } if (intitalDir.entrySet().size() > 0) { for
 * (Map.Entry m : intitalDir.entrySet()) { System.out.println(m.getKey() + " " +
 * m.getValue()); textFieldDirectory = "" + (String) m.getValue(); } }
 * 
 * } catch (IOException ex) { ex.printStackTrace(); } finally { if (input !=
 * null) { try { input.close(); } catch (IOException e) { e.printStackTrace(); }
 * } }
 * 
 * }
 */
/*
 * private void readAllChoices() {
 * 
 * Properties prop = new Properties(); InputStream input = null; try {
 * 
 * // String filename = "config.properties";
 * prop.load(GovindTestCase.class.getResourceAsStream("/folder.properties"));
 * 
 * input = getClass().getClassLoader().getResourceAsStream(filename); if (input
 * == null) { System.out.println("Sorry, unable to find " + filename); return; }
 * 
 * // prop.load(input);
 * 
 * Enumeration<?> e = prop.propertyNames(); while (e.hasMoreElements()) { String
 * key = (String) e.nextElement(); String value = prop.getProperty(key);
 * folderMap.put(key, value); // System.out.println("Key : " + key +
 * ", Value : " + value); } for (Map.Entry m : folderMap.entrySet()) { //
 * System.out.println(m.getKey() + " " + m.getValue()); List<String> lists =
 * Arrays.asList(m.getValue().toString().split(",")); //
 * System.out.println(lists);
 * 
 * Map<String, String> map = new HashMap<String, String>(); for (int i = 0; i <
 * lists.size(); i++) { map.put(i + "", "" + lists.get(i)); }
 * choices.put(m.getKey().toString(), (HashMap<String, String>) map); } } catch
 * (IOException ex) { ex.printStackTrace(); } finally { if (input != null) { try
 * { input.close(); } catch (IOException e) { e.printStackTrace(); } } }
 * 
 * }
 */
/*
 * public boolean moveFile(String sourcePath, String targetPath) {
 * 
 * boolean flag = true;
 * 
 * try {
 * 
 * Files.move(Paths.get(sourcePath), Paths.get(targetPath),
 * StandardCopyOption.REPLACE_EXISTING); // Files.move(Paths.get("/foo.txt"),
 * Paths.get("bar.txt"), // StandardCopyOption.REPLACE_EXISTING); } catch
 * (Exception e) { flag = false; e.printStackTrace(); }
 * 
 * return flag; }
 * 
 */