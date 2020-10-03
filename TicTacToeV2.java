import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.Random;

public class TicTacToeV2 extends JFrame implements ActionListener 
{
  /*Instance Variables*/
  int[][] winning = new int[][] 
  {
    {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, //horizontal wins
    {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, //vertical wins
    {0, 4, 8}, {2, 4, 6}             //diagonal wins
  };
  JButton button[] = new JButton[9];//array of the button
  int count = 0;//variable to count the numver of times the button are clicked in a round
  int p1Wins = 0;//to count the number of times the first player wins
  int p2Wins = 0;//to count the number of times the second player wins
  int round = 1;//to count the number of rounds played
  int play;
  
  PrintStream print;
  //elements for the menu
  JMenuBar menubar = new JMenuBar();
  //elements in the file drop down
  JMenu file = new JMenu("File");
  JMenuItem reset = new JMenuItem("Reset");
  JMenuItem exit = new JMenuItem("Exit");
  //elements in the help drop down
  JMenu help = new JMenu("Help");
  JMenuItem instruct = new JMenuItem("Instructions");
  JMenuItem about = new JMenuItem("About");
  //the names of the 2 players
  String player1;
  String player2;
  //panel for the button array
  JPanel grid = new JPanel();
  //panel to display the results
  JPanel result = new JPanel();
  JLabel p1Win, p2Win;
  
  String letter = "";//according to the turn the letter changes
  ImageIcon icon;//according to the turn the icon changes
  ImageIcon nikhil = new ImageIcon("nikhil.gif");
  boolean win = false;//boolean to check if the game has been won
  boolean go = true;//boolean to check for the correct file input
  //to position the GUI in the center of the screen
  Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
  int x = (screen.width / 2) - (150); // Center horizontally.
  int y = (screen.height / 2) - (175); // Center vertically.
  //elements for the timer
  JLabel timer = new JLabel("Time elapsed:");
  Stopwatch stop = new Stopwatch();
  JPanel time = new JPanel();
  
  public TicTacToeV2()
  {
    this.setTitle("Tic Tac Toe");//set the title of the game
    //enter the file name
    String output = JOptionPane.showInputDialog("Enter the file to which the scores are to be written to:");
    //if the file name is empty the prgm will exit
    if (output==null)
    {
      System.exit(0);
    }
    try
    {
      //to check if the output file has been printstreamed without a problem
      while(go)
      {
        if(output.substring(output.length()-4).equals(".txt"))
        {
          print = new PrintStream (new File(output));
          go = false;
        }
        else
        {
          JOptionPane.showMessageDialog(this,"Enter a proper file name", "Tic Tac Toe",JOptionPane.ERROR_MESSAGE);
          output = null;
          output = JOptionPane.showInputDialog("Enter the file to which the scores are to be written to:");
          if (output==null)
          {
            System.exit(0);
          }
          go = true;
        }
      }
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog(this,"Enter a proper file name", "Tic Tac Toe",JOptionPane.ERROR_MESSAGE);
    }
    
    /*Create Window*/
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    
    //create the menu
    this.setJMenuBar(menubar);
    menubar.add(file);
    menubar.add(help);
    file.add(reset);
    file.add(exit);
    help.add(instruct);
    help.add(about);
    reset.setActionCommand("reset");
    exit.setActionCommand("exit");
    instruct.setActionCommand("instruct");
    about.setActionCommand("about");
    //add ActionListeners fot the menu functions
    reset.addActionListener(this);
    exit.addActionListener(this);
    instruct.addActionListener(this);
    about.addActionListener(this);
    play = JOptionPane.showConfirmDialog(this, "Is it 1 player or no?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    if(play == JOptionPane.YES_OPTION)   
    {
      do
      {
        player1 = JOptionPane.showInputDialog("Enter the name of player 1:");
      }
      while(player1==null);
      player2 = "Computer";
    }
    else 
    {
      //get the player names
      do
      {
        player1 = JOptionPane.showInputDialog("Enter the name of player 1:");
      }
      while(player1==null);
      do
      {
        player2 = JOptionPane.showInputDialog("Enter the name of player 2:");
      }
      while(player2==null);
    }
    
    //display the number of times each won
    p1Win = new JLabel(player1+": "+p1Wins);
    p2Win = new JLabel(player2+": "+p2Wins);
    result.add(p1Win);
    result.add(p2Win);
    /*Add Buttons To The Grid*/
    grid.setLayout(new GridLayout(3,3));
    for(int i=0; i<9; i++)
    {
      button[i] = new JButton();
      grid.add(button[i]);
      button[i].addActionListener(this);
    }
    //add the elements of the timer to the time panel
    time.add(timer);
    time.add(stop);
    
    getContentPane().setLayout( new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS) );//set the layout to boxlayout
    //add the elements to the frame
    getContentPane().add(grid);
    getContentPane().add(result);
    getContentPane().add(time);
    JOptionPane.showMessageDialog(this, player1+" starts the game.");
    //set the size of the button array grid
    grid.setPreferredSize(new Dimension(300,300));
    this.setLocation(x,y);//position the frame in the center of the screen
    this.setResizable(false);
    stop.start("Start");
  }
  
  /*
   When an object is clicked, perform an action.
   @param a action event object
   */
  public void actionPerformed(ActionEvent a) 
  {
    //Action Commands for the menu
    if(a.getActionCommand().equals("reset"))
    {
      reset();
    }
    else if (a.getActionCommand().equals("exit"))
    {
      System.exit(0);
    }
    else if (a.getActionCommand().equals("instruct"))
    {
      dialog d = new dialog();//show the instructions dialog
      d.addWindowListener(new WindowAdapter() 
                            {
        public void windowOpened(WindowEvent e)
        {
          stop.start("Stop");//to stop the timer
        }
        public void windowClosed(WindowEvent e)
        {
          stop.start("Start");//to start the timer when the dialog is closed
        }
        public void windowClosing(WindowEvent e)
        {
          stop.start("Start");//to start the timer when the dialog is closed
        }
      });
      
      
    }
    else if (a.getActionCommand().equals("about"))
    {
      stop.start("Stop");
      JOptionPane.showMessageDialog(this,"Programmed by Nikhil Joy!","Tic Tac Toe",JOptionPane.INFORMATION_MESSAGE,nikhil);
      stop.start("Start");
    }
    else
    {
      if(play == JOptionPane.YES_OPTION)
      {
        
        /*Write the letter to the button and deactivate it*/
        for(int i = 0; i< 9; i++)
        {
          if(a.getSource() == button[i])
          {
            button[i].setText("X");
            button[i].setIcon(new ImageIcon("x.gif"));
            button[i].setEnabled(false);
          }
        }
        count++;
        System.out.println(count);
        AI();
      }
      else
      {
        count++;//to count the turns
        /*Calculate whose turn it is*/
        if (round % 2 == 1)//to check which round it is to determine the first player in each round
        {
          if(count % 2 == 0)//if it is true player1 starts the game. player1 is assigned X and player2 is assigned O.
          {
            letter = "O";
            icon = new ImageIcon("o.gif");
          } 
          else 
          {
            letter = "X";
            icon = new ImageIcon("x.gif");
          }
        }
        else
        {
          if(count % 2 == 1)//player2 starts the game
          {
            letter = "O";
            icon = new ImageIcon("o.gif");
          } 
          else 
          {
            letter = "X";
            icon = new ImageIcon("x.gif");
          }
        }
        /*Write the letter to the button and deactivate it*/
        JButton pressedButton = (JButton)a.getSource(); 
        pressedButton.setText(letter);
        pressedButton.setIcon(icon);
        pressedButton.setEnabled(false);
        
        checkwin();
      }
      checkwin();
    }
  }
  public void AI()
  {
    count++;
    System.out.println(count);
    if(button[0].getText().equals("O") && button[1].getText().equals("O") && button[2].getText().equals(""))
    {
      button[2].setText("O");
      button[2].setIcon(new ImageIcon("o.gif"));
      button[2].setEnabled(false);
    }
    else if(count==1)
    {
      RandomMove();
    }
    else if(button[3].getText().equals("O") && button[4].getText().equals("O") && button[5].getText().equals(""))
    {
      button[5].setText("O");
      button[5].setIcon(new ImageIcon("o.gif"));
      button[5].setEnabled(false);
    } 
    else if(button[6].getText().equals("O") && button[7].getText().equals("O") && button[8].getText().equals(""))
    {
      button[8].setText("O");
      button[8].setIcon(new ImageIcon("o.gif"));
      button[8].setEnabled(false);                
    } 
    
    else if(button[1].getText().equals("O") && button[2].getText().equals("O") && button[0].getText().equals(""))
    {
      button[0].setText("O");
      button[0].setIcon(new ImageIcon("o.gif"));
      button[0].setEnabled(false);                
    } 
    else if(button[4].getText().equals("O") && button[5].getText().equals("O") && button[3].getText().equals(""))
    {
      button[3].setText("O");
      button[3].setIcon(new ImageIcon("o.gif"));
      button[3].setEnabled(false);                
    } 
    else if(button[7].getText().equals("O") && button[8].getText().equals("O") && button[6].getText().equals(""))
    {
      button[6].setText("O");
      button[6].setIcon(new ImageIcon("o.gif"));
      button[6].setEnabled(false);                
    }
    
    else if(button[0].getText().equals("O") && button[2].getText().equals("O") && button[1].getText().equals(""))
    {
      button[1].setText("O");
      button[1].setIcon(new ImageIcon("o.gif"));
      button[1].setEnabled(false);                
    } 
    else if(button[3].getText().equals("O") && button[5].getText().equals("O") && button[4].getText().equals(""))
    {
      button[4].setText("O");
      button[4].setIcon(new ImageIcon("o.gif"));
      button[4].setEnabled(false);                
    } 
    else if(button[6].getText().equals("O") && button[8].getText().equals("O") && button[7].getText().equals(""))
    {
      button[7].setText("O");
      button[7].setIcon(new ImageIcon("o.gif"));
      button[7].setEnabled(false);                
    }
    
    else if(button[0].getText().equals("O") && button[3].getText().equals("O") && button[6].getText().equals(""))
    {
      button[6].setText("O");
      button[6].setIcon(new ImageIcon("o.gif"));
      button[6].setEnabled(false);                
    } 
    else if(button[1].getText().equals("O") && button[4].getText().equals("O") && button[7].getText().equals(""))
    {
      button[7].setText("O");
      button[7].setIcon(new ImageIcon("o.gif"));
      button[7].setEnabled(false);                
    } 
    else if(button[2].getText().equals("O") && button[5].getText().equals("O") && button[8].getText().equals(""))
    {
      button[8].setText("O");
      button[8].setIcon(new ImageIcon("o.gif"));
      button[8].setEnabled(false);                
    }
    
    else if(button[3].getText().equals("O") && button[6].getText().equals("O") && button[0].getText().equals(""))
    {
      button[0].setText("O");
      button[0].setIcon(new ImageIcon("o.gif"));
      button[0].setEnabled(false);                
    } 
    else if(button[4].getText().equals("O") && button[7].getText().equals("O") && button[1].getText().equals(""))
    {
      button[1].setText("O");
      button[1].setIcon(new ImageIcon("o.gif"));
      button[1].setEnabled(false);                
    } 
    else if(button[5].getText().equals("O") && button[8].getText().equals("O") && button[2].getText().equals(""))
    {
      button[2].setText("O");
      button[2].setIcon(new ImageIcon("o.gif"));
      button[2].setEnabled(false);                
    }
    
    else if(button[0].getText().equals("O") && button[6].getText().equals("O") && button[3].getText().equals(""))
    {
      button[3].setText("O");
      button[3].setIcon(new ImageIcon("o.gif"));
      button[3].setEnabled(false);                
    }
    else if(button[1].getText().equals("O") && button[7].getText().equals("O") && button[4].getText().equals(""))
    {
      button[4].setText("O");
      button[4].setIcon(new ImageIcon("o.gif"));
      button[4].setEnabled(false);                
    } 
    else if(button[2].getText().equals("O") && button[8].getText().equals("O") && button[5].getText().equals(""))
    {
      button[5].setText("O");
      button[5].setIcon(new ImageIcon("o.gif"));
      button[5].setEnabled(false);                
    }
    
    else if(button[0].getText().equals("O") && button[4].getText().equals("O") && button[8].getText().equals(""))
    {
      button[8].setText("O");
      button[8].setIcon(new ImageIcon("o.gif"));
      button[8].setEnabled(false);                
    } 
    else if(button[4].getText().equals("O") && button[8].getText().equals("O") && button[0].getText().equals(""))
    {
      button[0].setText("O");
      button[0].setIcon(new ImageIcon("o.gif"));
      button[0].setEnabled(false);                
    } 
    else if(button[0].getText().equals("O") && button[8].getText().equals("O") && button[4].getText().equals(""))
    {
      button[4].setText("O");
      button[4].setIcon(new ImageIcon("o.gif"));
      button[4].setEnabled(false);                
    }
    
    else if(button[2].getText().equals("O") && button[4].getText().equals("O") && button[6].getText().equals(""))
    {
      button[6].setText("O");
      button[6].setIcon(new ImageIcon("o.gif"));
      button[6].setEnabled(false);                
    } 
    else if(button[6].getText().equals("O") && button[4].getText().equals("O") && button[2].getText().equals(""))
    {
      button[2].setText("O");
      button[2].setIcon(new ImageIcon("o.gif"));
      button[2].setEnabled(false);                
    } 
    else if(button[6].getText().equals("O") && button[2].getText().equals("O") && button[4].getText().equals(""))
    {
      button[4].setText("O");
      button[4].setIcon(new ImageIcon("o.gif"));
      button[4].setEnabled(false);                
    }
    
    else if(button[0].getText().equals("X") && button[1].getText().equals("X") && button[2].getText().equals(""))
    {
      button[2].setText("O");
      button[2].setIcon(new ImageIcon("o.gif"));
      button[2].setEnabled(false);
    } 
    else if(button[3].getText().equals("X") && button[4].getText().equals("X") && button[5].getText().equals(""))
    {
      button[5].setText("O");
      button[5].setIcon(new ImageIcon("o.gif"));
      button[5].setEnabled(false);                
    } 
    else if(button[6].getText().equals("X") && button[7].getText().equals("X") && button[8].getText().equals(""))
    {
      button[8].setText("O");
      button[8].setIcon(new ImageIcon("o.gif"));
      button[8].setEnabled(false);                
    } 
    
    else if(button[1].getText().equals("X") && button[2].getText().equals("X") && button[0].getText().equals(""))
    {
      button[0].setText("O");
      button[0].setIcon(new ImageIcon("o.gif"));
      button[0].setEnabled(false);                
    } 
    else if(button[4].getText().equals("X") && button[5].getText().equals("X") && button[3].getText().equals(""))
    {
      button[3].setText("O");
      button[3].setIcon(new ImageIcon("o.gif"));
      button[3].setEnabled(false);                
    } 
    else if(button[7].getText().equals("X") && button[8].getText().equals("X") && button[6].getText().equals(""))
    {
      button[6].setText("O");
      button[6].setIcon(new ImageIcon("o.gif"));
      button[6].setEnabled(false);                
    }
    
    else if(button[0].getText().equals("X") && button[2].getText().equals("X") && button[1].getText().equals(""))
    {
      button[1].setText("O");
      button[1].setIcon(new ImageIcon("o.gif"));
      button[1].setEnabled(false);                
    } 
    else if(button[3].getText().equals("X") && button[5].getText().equals("X") && button[4].getText().equals(""))
    {
      button[4].setText("O");
      button[4].setIcon(new ImageIcon("o.gif"));
      button[4].setEnabled(false);                
    } 
    else if(button[6].getText().equals("X") && button[8].getText().equals("X") && button[7].getText().equals(""))
    {
      button[7].setText("O");
      button[7].setIcon(new ImageIcon("o.gif"));
      button[7].setEnabled(false);                
    }
    
    else if(button[1].getText().equals("X") && button[4].getText().equals("X") && button[7].getText().equals(""))
    {
      button[7].setText("O");
      button[7].setIcon(new ImageIcon("o.gif"));
      button[7].setEnabled(false);                
    } 
    else if(button[2].getText().equals("X") && button[5].getText().equals("X") && button[8].getText().equals(""))
    {
      button[8].setText("O");
      button[8].setIcon(new ImageIcon("o.gif"));
      button[8].setEnabled(false);                
    } 
    else if(button[0].getText().equals("X") && button[3].getText().equals("X") && button[6].getText().equals(""))
    {
      button[6].setText("O");
      button[6].setIcon(new ImageIcon("o.gif"));
      button[6].setEnabled(false);                
    }
    
    else if(button[4].getText().equals("X") && button[7].getText().equals("X") && button[1].getText().equals(""))
    {
      button[1].setText("O");
      button[1].setIcon(new ImageIcon("o.gif"));
      button[1].setEnabled(false);                
    } 
    else if(button[5].getText().equals("X") && button[8].getText().equals("X") && button[2].getText().equals(""))
    {
      button[2].setText("O");
      button[2].setIcon(new ImageIcon("o.gif"));
      button[2].setEnabled(false);                
    } 
    else if(button[3].getText().equals("X") && button[6].getText().equals("X") && button[0].getText().equals(""))
    {
      button[0].setText("O");
      button[0].setIcon(new ImageIcon("o.gif"));
      button[0].setEnabled(false);                
    }
    
    else if(button[1].getText().equals("X") && button[7].getText().equals("X") && button[4].getText().equals(""))
    {
      button[4].setText("O");
      button[4].setIcon(new ImageIcon("o.gif"));
      button[4].setEnabled(false);                
    } 
    else if(button[2].getText().equals("X") && button[8].getText().equals("X") && button[5].getText().equals(""))
    {
      button[5].setText("O");
      button[5].setIcon(new ImageIcon("o.gif"));
      button[5].setEnabled(false);                
    } 
    else if(button[0].getText().equals("X") && button[6].getText().equals("X") && button[3].getText().equals(""))
    {
      button[3].setText("O");
      button[3].setIcon(new ImageIcon("o.gif"));
      button[3].setEnabled(false);                
    }
    
    else if(button[0].getText().equals("X") && button[4].getText().equals("X") && button[8].getText().equals(""))
    {
      button[8].setText("O");
      button[8].setIcon(new ImageIcon("o.gif"));
      button[8].setEnabled(false);                
    } 
    else if(button[4].getText().equals("X") && button[8].getText().equals("X") && button[0].getText().equals("")){
      
      button[0].setText("O");
      button[0].setIcon(new ImageIcon("o.gif"));
      button[0].setEnabled(false);                
    } 
    else if(button[0].getText().equals("X") && button[8].getText().equals("X") && button[4].getText().equals(""))
    {
      button[4].setText("O");
      button[4].setIcon(new ImageIcon("o.gif"));
      button[4].setEnabled(false);                
    }
    
    else if(button[2].getText().equals("X") && button[4].getText().equals("X") && button[6].getText().equals(""))
    {
      button[6].setText("O");
      button[6].setIcon(new ImageIcon("o.gif"));
      button[6].setEnabled(false);                
    } 
    else if(button[6].getText().equals("X") && button[4].getText().equals("X") && button[2].getText().equals(""))
    {
      button[2].setText("O");
      button[2].setIcon(new ImageIcon("o.gif"));
      button[2].setEnabled(false);                
    } 
    else if(button[6].getText().equals("X") && button[2].getText().equals("X") && button[4].getText().equals(""))
    {
      button[4].setText("O");
      button[4].setIcon(new ImageIcon("o.gif"));
      button[4].setEnabled(false);                
    }
    
    else if(button[0].getText().equals("X") && button[4].getText().equals("O") && button[8].getText().equals("X") && button[5].getText().equals("")) 
    {
      button[5].setText("O");
      button[5].setIcon(new ImageIcon("o.gif"));
      button[5].setEnabled(false);            
    }    
    
    else if(button[2].getText().equals("X") && button[4].getText().equals("O") && button[6].getText().equals("X") && button[3].getText().equals("")) 
    {
      button[3].setText("O");
      button[3].setIcon(new ImageIcon("o.gif"));
      button[3].setEnabled(false);            
    }
    
    else if(button[4].getText().equals(""))
    {
      button[4].setText("O");
      button[4].setIcon(new ImageIcon("o.gif"));
      button[4].setEnabled(false);                
    }
    
    else if(button[0].getText().equals(""))
    {
      button[0].setText("O");
      button[0].setIcon(new ImageIcon("o.gif"));
      button[0].setEnabled(false);                
    }
    else if (button[2].getText().equals(""))
      {
      button[2].setText("O");
      button[2].setIcon(new ImageIcon("o.gif"));
      button[2].setEnabled(false);                
    }
    
    else 
    {
      if(count > 9)
        checkwin();
      else
        RandomMove();
      checkwin();
    }
    
    checkwin();
    
  }
  
  public void RandomMove()
  {
    System.out.println("random");
    Random x = new Random();
    int y = 1 + x.nextInt(8);
    if(button[y].getText().equals("O") || button[y].getText().equals("X") )
    {
      RandomMove();
    } 
    else 
    {
      button[y].setText("O");
      button[y].setIcon(new ImageIcon("o.gif"));
      button[y].setEnabled(false);
    }
  }
  public void checkwin()
  {
    
    /*Determine who won*/
    for(int i=0; i<=7; i++)
    {
      if( button[winning[i][0]].getText().equals(button[winning[i][1]].getText()) && 
         button[winning[i][1]].getText().equals(button[winning[i][2]].getText()) && 
         button[winning[i][0]].getText() != "")
      {
        win = true;
        letter = button[winning[i][0]].getText();
        for(int j = 0; j<3 ; j++)
        {
          button[winning[i][j]].setEnabled(true);
          button[winning[i][j]].setBackground(Color.green);
          button[winning[i][j]].setText("");
        }
      }
    }
    
    /*Show a dialog when game is over*/
    if(win == true)
    {
      stop.start("Stop");//stops the timer
      if(letter.equals("X"))
      {
        JOptionPane.showMessageDialog(this, player1 + " wins the game!");
      }
      else
      {
        JOptionPane.showMessageDialog(this, player2 + " wins the game!");
      }   
      playAgainDialog();
    }
    else if(count >= 9 && win == false)
    {
      stop.start("Stop");//stops the timer
      JOptionPane.showMessageDialog(this, "The game was tie!");
      playAgainDialog();   
    }
  }
  public void playAgainDialog() 
  {
    //displays who won the round
    if(letter.equals("X") && win == true)  
    {
      p1Wins++;
      p1Win.setText(player1+": "+p1Wins);
    }
    else if (letter.equals("O") && win == true)                   
    {
      p2Wins++;
      p2Win.setText(player2+": "+p2Wins);
    }
    //writes to the file the results of the round
    print.println("Round "+ round); 
    print.println("Score "+player1+": "+p1Wins+" "+player2+": "+p2Wins);
    print.println("Time Taken: "+stop.timetaken());
    round++;
    //to see if the player wants to play again
    int response = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    
    if(response == JOptionPane.YES_OPTION)   
    {
      reset();
    }
    else 
    {
      System.exit(0);
    }
    
  }
  
  public void reset() 
  {
    //to set the grid to the original form
    for(int i=0; i<9; i++)
    {
      button[i].setText("");
      button[i].setBackground(null);
      button[i].setIcon(null);
      button[i].setEnabled(true);
    }
    win = false;
    count = 0;
    stop.reset();//to reset the timer
    stop.start("Stop");
    //determine which player starts the game according to the round
    if (round%2 == 1)
    {
      JOptionPane.showMessageDialog(this, player1+" starts the game.");
    }
    else
    {
      JOptionPane.showMessageDialog(this, player2+" starts the game.");
      AI();
    }
    stop.start("Start");
  }
  
  
  public static void main(String[] args)
  {
    TicTacToeV2 starter = new TicTacToeV2();
    starter.setSize(300,350);
    starter.setVisible(true);
  }
}