import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

public class dialog extends JDialog
{
  String file = "";
  JPanel pan = new JPanel();
  JTextArea area = new JTextArea();
  //to set the dialog in the center of the screen
  Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
  int x = (screen.width / 2) - (175); // Center horizontally.
  int y = (screen.height / 2) - (75); // Center vertically.
  
  public dialog()
  {
    this.setTitle("Instructions");
    try
    {
      Scanner fileStream = new Scanner(new File("instructions.txt"));//to read the instructions from the text file
      while(fileStream.hasNextLine())
      {
        file += fileStream.nextLine().concat("\n");//all the text is stored in this string
      }
    }
    catch(IOException e)
    {
    }
    area.setText(file);//to display the contents of instructions
    pan.add(area);//to add the text area to the panel
    this.add(pan);//add the panel to the dialog
    this.setSize(350, 150);
    this.setLocation(x,y);//set the dialog in the center of the screen
    this.setVisible(true);
    this.setResizable(false);
    this.setLayout(new FlowLayout());
    area.setEditable(false);
  }
  
}
