package dummy;

import javax.swing.JOptionPane; 

public class Average {

    public static void main (String [] args){

        String test1, test2, test3, avg;

		  test1= JOptionPane.showInputDialog(null,
	                "Please enter new quantity", "12");

        test2= JOptionPane.showInputDialog("Please input mark for test 2: ");

        test3= JOptionPane.showInputDialog("Please input mark for test 3: ");
        System.out.println(test1+test2+test3);

    }

}