package com.slide;

import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.TransferHandler;

@SuppressWarnings("serial")
public class FileDragDemo extends JPanel {
	private JList list = new JList();
	@Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 800);
    }
	public FileDragDemo() {
		list.setDragEnabled(true);
		list.setTransferHandler(new FileListTransferHandler(list));
		getPreferredSize();
		add(new JScrollPane(list));
	}

	/*
	 * private static void createAndShowGui() { FileDragDemo mainPanel = new
	 * FileDragDemo();
	 * 
	 * JFrame frame = new JFrame("FileDragDemo");
	 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 * frame.getContentPane().add(mainPanel); frame.pack();
	 * frame.setLocationByPlatform(true); frame.setVisible(true); }
	 * 
	 * public static void main(String[] args) { SwingUtilities.invokeLater(new
	 * Runnable() { public void run() { createAndShowGui(); } }); }
	 */
}

@SuppressWarnings("serial")
class FileListTransferHandler extends TransferHandler {
	private JList list;
	  
		public FileListTransferHandler(JList list) {
			list.setFixedCellWidth(600);
		      list.setFixedCellHeight(80);
			this.list = list;
		}
	public int getSourceActions(JComponent c) {
		return COPY_OR_MOVE;
	}

	public boolean canImport(TransferSupport ts) {
		return ts.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
	}

	public boolean importData(TransferSupport ts) {
		try {
			@SuppressWarnings("rawtypes")
			List data = (List) ts.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
			if (data.size() < 1) {
				return false;
			}
			JFrame parent = new JFrame();
			String response = "";
			response = JOptionPane.showInputDialog(null, "Please enter new quantity", GovindTestCase.timeS);
			System.out.println(response);
			if ((response != null) && (response.length() > 0)) {
				extractedFromFiles(data, response);
				return true;
			}
			else {
				return false;
			}
			

		} catch (UnsupportedFlavorException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	private void extractedFromFiles(List data, String response) throws IOException {
		DefaultListModel listModel = new DefaultListModel();
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		System.out.println(timeStamp);
		List<String> listofFiles = new ArrayList<String>();
		for (Object item : data) {
			System.out.println(data.size());
			File file = (File) item;
			listofFiles.add(file.getAbsolutePath());
			listModel.addElement(file);
		}
		if (GovindTestCase.listOfFiles.get(timeStamp) == null) {
			GovindTestCase.listOfFiles.put(timeStamp, listofFiles);
			GovindTestCase.listOfSeconds.put(timeStamp, response);
			CopyImage c = new CopyImage();
			c.setFiles(listofFiles);
			c.setSec(response);
			c.setTmestmp(timeStamp);
			GovindTestCase.listofCopyImages.put(timeStamp, c);
		}
		System.out.println(GovindTestCase.listOfSeconds.size());
		String fileList = "";
		for (Entry<String, List<String>> entry : GovindTestCase.listOfFiles.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			fileList = fileList + "\n \t:";
			for (String l : entry.getValue()) {

				fileList = fileList + "\n" + l;
			}
		}
		GovindTestCase.copyAction();
		GovindTestCase.tobeCopied.setText(fileList);
		//list.setModel(listModel);
	}
}