package dummy;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CopyGUI extends JFrame implements ActionListener, ChangeListener {

    private static final long serialVersionUID = 1L;
    private JLabel fromLabel;
    private JLabel toLabel;
    private JTextField fromField;
    private JTextField toField;
    private JButton fromButton;
    private JButton toButton;
    private JProgressBar progressBar;
    private JButton beginButton;
    private File fromFile = new File("C:\\Users\\HDMI\\Downloads\\WhatsApp Image 2020-02-06 at 19.17.48.jpeg");
    private File toFile = new File("C:\\Users\\HDMI\\Downloads\\WhatsApp Image 2020-02-1106 at 19.17.48.jpeg");
    private long fileLength;
    private String fileName;

    public CopyGUI(String fromFileCopy, String twoFileCopy) {
        init(fromFileCopy,twoFileCopy);
    }

    private void init(String fromFileCopy, String twoFileCopy) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(200, 200, 400, 300);
        setResizable(false);
        setTitle("File Copy GUI");

        JPanel panel = new JPanel(null);
        add(panel);

        /*
         * add button and lebel
         */
        fromLabel = new JLabel("From:");
        fromLabel.setBounds(40, 20, 50, 20);

        fromField = new JTextField();
        fromField.setBounds(100, 20, 200, 20);

		/*
		 * fromButton = new JButton("Browse"); fromButton.setBounds(300, 20, 80, 20);
		 * fromButton.addActionListener(this);
		 */
        
        
        toLabel = new JLabel("To: ");
        toLabel.setBounds(40, 40, 50, 20);

        toField = new JTextField();
        toField.setBounds(100, 40, 200, 20);

		/*
		 * toButton = new JButton("Browse"); toButton.setBounds(300, 40, 80, 20);
		 * toButton.addActionListener(this);
		 */
        
        

        panel.add(fromLabel);
        panel.add(fromField);
//        panel.add(fromButton);

        panel.add(toLabel);
        panel.add(toField);
  //      panel.add(toButton);

        /*
         * add progress bar
         */
        progressBar = new JProgressBar();
        progressBar.setBounds(50, 100, 300, 30);
        progressBar.addChangeListener(this);

        panel.add(progressBar);

        beginButton = new JButton("Begin Copy");
        beginButton.setBounds(200, 200, 100, 20);
        beginButton.addActionListener(this);

        panel.add(beginButton);

    }

    public void actionPerformed(ActionEvent e) {
    	fromField.setText(fromFile.getAbsolutePath());
        toField.setText(toFile.getAbsolutePath() + fileName);
        fromFile = new File(fromField.getText());
        toFile = new File(toField.getText());
        if (toFile.exists()) {
            int op = JOptionPane.showConfirmDialog(this, toFile.getName()
                    + " file exist! \n Do your want to cover it?",
                    "Confirm Window", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "Copy Canceled");
                fromField.setText("");
                toField.setText("");
                return;
            }
        }

        /*
         * get file length, set progressBar max value
         */
        fileLength = fromFile.length();
        progressBar.setMaximum((int) fileLength);

        /*
         * copy method add to a new thread when copying, increase progress
         * bar
         */
        Runnable r1 = new Runnable() {

            public void run() {

                try {
                    FileInputStream fis = new FileInputStream(fromFile);
                    FileOutputStream fos = new FileOutputStream(toFile);

                    byte[] buf = new byte[2048];
                    int size = 0;
                    int flag = 0;
                    while ((size = fis.read(buf)) != -1) {
                        fos.write(buf, 0, size);
                        flag += size;
                        progressBar.setValue(flag);
                    }

                    fis.close();
                    fos.close();

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        };

        /*
         * start new thread
         */
        Thread t1 = new Thread(r1);
        t1.start();


        /*
         * choose from file
         */
        if (e.getSource() == fromButton) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(false);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            int op = fileChooser.showOpenDialog(this);
            if (op == JFileChooser.APPROVE_OPTION) {
                fromFile = fileChooser.getSelectedFile();
                fileName = fromFile.getName();
                
            }
        }

        /*
         * choose to file
         */
        if (e.getSource() == toButton) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int op = fileChooser.showOpenDialog(this);
            if (op == JFileChooser.APPROVE_OPTION) {
                toFile = fileChooser.getSelectedFile();
                if (fileName != null) {
                    fileName = fromFile.getName();
                }
            }
        }

        /*
         * begin copy method
         */
        if (e.getSource() == beginButton) {

            /*
             * before copy check from file and to file
             */
            if ("".equals(fromField.getText())) {
                JOptionPane.showMessageDialog(this, "Input from File!");
                return;
            }

            if ("".equals(toField.getText())) {
                JOptionPane.showMessageDialog(this, "Input to File!");
                return;
            }

            /*
             * get copy from file and to file
             */

            /*
             * if to file exist, ask user information
             */
        }
    }

    /*
     * update progress bar value
     */
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == progressBar) {
            if (progressBar.getValue() == progressBar.getMaximum()) {
                JOptionPane.showMessageDialog(this, "Copy Over");
                progressBar.setValue(0);
            }
        }
    }

    /*
     * Main method
     */
    public static void main(String[] args) {
    	String fromFileCopy="";
    	String twoFileCopy="";
    	new CopyGUI(fromFileCopy,twoFileCopy).setVisible(true);
    }
}