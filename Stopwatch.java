
/*
 * Stopwatch.java
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;

public class Stopwatch extends JPanel 
{
  
  JTextField elapsedTextField = new JTextField();
  
  JButton startButton = new JButton();
  
  Timer myTimer;
  
  long startTime; // time when Start clicked
  long stopTime; // time when Stop clicked
  long stoppedTime; // amount of time timer is stopped
  boolean reset = true; // true if new timing
  
  public Stopwatch() 
  {
    
    this.setLayout(new GridBagLayout());
    
    
    // add text fields
    GridBagConstraints gridConstraints = new GridBagConstraints();
    elapsedTextField.setText("00:00:00.0");
    elapsedTextField.setEditable(false);
    elapsedTextField.setBackground(Color.WHITE);
    elapsedTextField.setForeground(Color.BLUE);
    elapsedTextField.setFont(new Font("Arial", Font.BOLD, 24));
    elapsedTextField.setColumns(8);
    elapsedTextField.setHorizontalAlignment(SwingConstants.CENTER);
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 0;
    gridConstraints.gridwidth = 3;
    this.add(elapsedTextField, gridConstraints);
    
    
    // create timer
    myTimer = new Timer(100, new ActionListener()
                          {
      public void actionPerformed(ActionEvent e)
      {
        myTimerActionPerformed(e);
      }
    });
  }
  
  public void start(String a)
  {
    
    // Starting or restarting timer?
    if (a.equals("Start"))
    {
      // Reset text on Start/Stop 
      a = "Stop";
      // Start timer and get starting time
      if (reset)
      {
        reset = false;
        startTime = System.currentTimeMillis();
        stoppedTime = 0;
      }
      else
      {
        stoppedTime += System.currentTimeMillis() - stopTime;
      }
      myTimer.start();
    }
    else
    {
      // Stop timer
      stopTime = System.currentTimeMillis();
      myTimer.stop();
      // Disable Start/Stop button, enable Reset button
      a = "Start";
    }
    
  }
  
  
  public void reset()
  {
    // Reset displays to zero
    reset = true;
    elapsedTextField.setText("00:00:00.0");
    
  }
  public String timetaken()
  {
    return elapsedTextField.getText();
  }
  
  
  private void myTimerActionPerformed(ActionEvent e)
  {
    long currentTime;
    // Determine elapsed and total times
    currentTime = System.currentTimeMillis();
    // Display times
    elapsedTextField.setText(HMS(currentTime - startTime - stoppedTime));
    
  }
  
  private String HMS(long tms)
  {
    int h;
    int m;
    double s;
    double t;
    t = tms / 1000.0;
    // Break time down into hours, minutes, and seconds
    h = (int) (t / 3600);
    m = (int) ((t - h * 3600) / 60);
    s = t - h * 3600 - m * 60;
    // Format time as string
    return(new DecimalFormat("00").format(h) + ":" + new DecimalFormat("00").format(m) + ":" + new DecimalFormat("00.0").format(s));
  }
  
}
