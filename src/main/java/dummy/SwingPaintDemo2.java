package dummy;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class SwingPaintDemo2 {
   
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
    }

    private static void createAndShowGUI() {
    
		
    	String myFolder = "C:\\Users\\HDMI\\Downloads\\list";
        System.out.println("Created GUI on EDT? "+
        SwingUtilities.isEventDispatchThread());
    	PicturePanel panel = new PicturePanel(myFolder);
    	
        JFrame f = new JFrame("Swing Paint Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(panel);
        Timer timer = panel.getTimer();
        f.setSize(1360,768); 
        timer.start();
        f.pack();
        f.setVisible(true);
    }
}
