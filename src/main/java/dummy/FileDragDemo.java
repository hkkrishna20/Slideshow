package dummy;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

@SuppressWarnings("serial")
public class FileDragDemo extends JPanel {
	private JList list = new JList();
	  @Override
	  public Dimension getPreferredSize() {
	    return new Dimension(600, 600);
	  }

	public FileDragDemo() {
		list.setDragEnabled(true);
		list.setTransferHandler(new FileListTransferHandler(list));
		this.setSize(800, 800);
		add(new JScrollPane(list));
		setLayout(new GridBagLayout());
		
	}

	private static void createAndShowGui() {
		FileDragDemo mainPanel = new FileDragDemo();

		JFrame frame = new JFrame("FileDragDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(mainPanel);
		frame.pack();
		frame.setSize(700, 700);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGui();
			}
		});
	}
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

			DefaultListModel listModel = new DefaultListModel();
			for (Object item : data) {
				File file = (File) item;
				listModel.addElement(file);
			}

			list.setModel(listModel);
			return true;

		} catch (UnsupportedFlavorException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}
}