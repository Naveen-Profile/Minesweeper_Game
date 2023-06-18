/*
*  @author  Naveen
*/
import java.io.*;
import java.awt.*;
import java.awt.Dimension;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;
import javax.sound.sampled.*;

public class Minesweeper extends JFrame implements ActionListener, ContainerListener {
private static final long serialVersionUID = 12345678L;
int fw, fh, blockr, blockc, var1, var2, num_of_mine, detectedmine = 0, savedlevel = 1,
savedblockr, savedblockc, savednum_of_mine = 10;
int[] r = {-1, -1, -1, 0, 1, 1, 1, 0};
int[] c = {-1, 0, 1, 1, 1, 0, -1, -1};
JButton[][] blocks;
int[][] countmine;
int[][] colour;
ImageIcon[] ic = new ImageIcon[16];
JPanel panelb = new JPanel();
JPanel panelmt = new JPanel();
JTextField tf_mine, tf_time;
JFrame frame = new JFrame();
    
JLabel timeLabel = new JLabel();
int elapsedTime = 0;
int seconds =0;
int minutes =0;
boolean started = false;
String seconds_string = String.format("%02d", seconds);
String minutes_string = String.format("%02d", minutes);
JButton reset = new JButton("");
Random ranr = new Random();
Random ranc = new Random();
boolean check = true, starttime = false;
Point framelocation;
//ShowTimer class object
ShowTimer st = new ShowTimer();
MouseHandler mh;
Point p;
//main method
public static void main(String args[]) {
    new Minesweeper();
  }

  //constructor
public Minesweeper() {
super("Minesweeper Game");
//location of the board on screen
setLocation(500, 310);
// to set imgaes on cells
setImage();
setPanel(1, 0, 0, 0);
setOptions();

reset.addActionListener(new ActionListener() {

public void actionPerformed(ActionEvent ae) {
try {
st.stop();
st.resetTimer();
setPanel(savedlevel, savedblockr, savedblockc, savednum_of_mine);
} catch (Exception ex) {
setPanel(savedlevel, savedblockr, savedblockc, savednum_of_mine);
}
reset();

}
});
setDefaultCloseOperation(EXIT_ON_CLOSE);
setVisible(true);
}

public void reset() {
check = true;
starttime = false;
for (int i = 0; i < blockr; i++) {
for (int j = 0; j < blockc; j++) {
colour[i][j] = 'w';
}
}
}

/*
 * @param = level , rows, cols , no. of mines
 */
public void setPanel(int level, int setr, int setc, int setm) {
if (level == 1) {
fw = 200;
fh = 300;
blockr = 7;
blockc = 9;
num_of_mine = 10;
} else if (level == 2) {
fw = 320;
fh = 416;
blockr = 13;
blockc = 18;
num_of_mine = 35;
} else if (level == 3) {
fw = 400;
fh = 520;
blockr = 22;
blockc = 25;
num_of_mine = 91;
} 

savedblockr = blockr;
savedblockc = blockc;
savednum_of_mine = num_of_mine;

setSize(fw, fh);
setResizable(false);
detectedmine = num_of_mine;
p = this.getLocation();

blocks = new JButton[blockr][blockc];
countmine = new int[blockr][blockc];
colour = new int[blockr][blockc];
mh = new MouseHandler();

getContentPane().removeAll();
panelb.removeAll();
//number of mines display on board
tf_mine = new JTextField("" + num_of_mine, 3);
tf_mine.setEditable(false);
tf_mine.setFont(new Font("DigtalFont.TTF", Font.BOLD, 25));
tf_mine.setBackground(Color.BLACK);
tf_mine.setForeground(Color.RED);
tf_mine.setBorder(BorderFactory.createLoweredBevelBorder());
// timer display on board
timeLabel.setText(minutes_string+":"+seconds_string);
timeLabel.setBounds(100,100,200,100);
timeLabel.setFont(new Font("DigtalFont.TTF",Font.BOLD,25));
timeLabel.setBorder(BorderFactory.createBevelBorder(1));
timeLabel.setOpaque(true);
timeLabel.setHorizontalAlignment(JTextField.CENTER);
timeLabel.setBackground(Color.BLACK);
timeLabel.setForeground(Color.RED);
//reset functionality on click of smiley
reset.setIcon(ic[11]);
reset.setBorder(BorderFactory.createLoweredBevelBorder());
//top panel
panelmt.removeAll();
panelmt.setLayout(new BorderLayout());
panelmt.add(tf_mine, BorderLayout.WEST);
panelmt.add(reset, BorderLayout.CENTER);
panelmt.add(timeLabel,BorderLayout.EAST);
panelmt.setBackground(Color.black);
panelmt.setBorder(BorderFactory.createLoweredBevelBorder());

panelb.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createLoweredBevelBorder()));
panelb.setPreferredSize(new Dimension(fw, fh));
panelb.setLayout(new GridLayout(0, blockc));
panelb.addContainerListener(this);

for (int i = 0; i < blockr; i++) {
for (int j = 0; j < blockc; j++) {
blocks[i][j] = new JButton("");
blocks[i][j].addMouseListener(mh);

panelb.add(blocks[i][j]);

}
}
reset();

panelb.revalidate();
panelb.repaint();

getContentPane().setLayout(new BorderLayout());
getContentPane().addContainerListener(this);
getContentPane().repaint();
getContentPane().add(panelb, BorderLayout.CENTER);
getContentPane().add(panelmt, BorderLayout.NORTH);
setVisible(true);
}
//to set required options on top panel
public void setOptions() {
JMenuBar bar = new JMenuBar();
JMenu game = new JMenu("GAME");
JMenuItem menuitem = new JMenuItem("New Game");
final JCheckBoxMenuItem beginner = new JCheckBoxMenuItem("Beginner");
final JCheckBoxMenuItem intermediate = new JCheckBoxMenuItem("Intermediate");
final JCheckBoxMenuItem advanced = new JCheckBoxMenuItem("Advanced");
final JMenuItem exit = new JMenuItem("Exit");
final JMenu help = new JMenu("HELP");
final JMenuItem helpitem = new JMenuItem("Details");
final JMenuItem instruct = new JMenuItem("Instructions");

ButtonGroup status = new ButtonGroup();
//ActionListners
menuitem.addActionListener(
new ActionListener() {

public void actionPerformed(ActionEvent e) {

setPanel(1, 0, 0, 0);
st.resetTimer();

}
});

beginner.addActionListener(
new ActionListener() {

public void actionPerformed(ActionEvent e) {
panelb.removeAll();
st.resetTimer();
setPanel(1, 0, 0, 0);
panelb.revalidate();
panelb.repaint();
beginner.setSelected(true);
savedlevel = 1;
}
});
intermediate.addActionListener(
new ActionListener() {

public void actionPerformed(ActionEvent e) {
panelb.removeAll();
st.resetTimer();
setPanel(2, 0, 0, 0);
panelb.revalidate();
panelb.repaint();
intermediate.setSelected(true);
savedlevel = 2;
}
});
advanced.addActionListener(
new ActionListener() {

public void actionPerformed(ActionEvent e) {
panelb.removeAll();
st.resetTimer();
setPanel(3, 0, 0, 0);
panelb.revalidate();
panelb.repaint();
advanced.setSelected(true);
savedlevel = 3;
}
});

exit.addActionListener(new ActionListener() {

public void actionPerformed(ActionEvent e) {
System.exit(0);
}
});

helpitem.addActionListener(new ActionListener() {

public void actionPerformed(ActionEvent e) {
JOptionPane.showMessageDialog(null, "Advanced Programming Concepts\n\nName : Venkata Naveen Rudravaram\nImplementation Date : 11/12/2022\n");

}
});

instruct.addActionListener(new ActionListener() {

    public void actionPerformed(ActionEvent e) {
    JOptionPane.showMessageDialog(null, "Minesweeper Game:\n\nWhat is the Object of Minesweeper?\n\n"+
    " In this classic game you are presented with a board of squares. Some squares contain hidden mines, others don\'t.\n If you click on a square containing a mine, you lose. If you manage to click all of the squares without clicking on any mines, you win..\n"+
    "\nHow do you play Minesweeper? What do the numbers mean?\n\n"+
    " Clicking a square which doesn\'t have a mine reveals the number of neighbouring squares containing mines.\n By a process of deduction, elimination and guesswork, this information can be used to work out where all the mines are.\n"+
    "\nWhat are the rules of Minesweeper?\n\n"+
    "1. A square\'s neighbors are the squares above, below, left, right, and all 4 diagonals.\n Squares on the sides of the board or in a corner have fewer neighbors. The board does not wrap around the edges.\n"+
    "2. If you open a square with no neighboring mines, all its neighbors will automatically open.\n  This can cause a large area to open up quickly.\n"+
    "3. To remove a marker from a square, point at it and right-click again.\n"+
    "4. The first square you open is never a mine.\n"+
    "5. If you mark a mine incorrectly, you will have to correct the mistake before you can win.Incorrect mine marking doesn\'t kill you,\n  but it can lead to mistakes which do."+
    "   You don\'t have to mark all the mines to win, you just need to open all non-mine squares!\n"+
    "6. Click the yellow face on the top panel to start a new game.\n"+
    
    "\nStatus Information:\n\n"+
    
    "1. The upper left display contains the number of mines left to find. The number will update as you mark squares.\n"+
    "2. The upper right display contains a timer.\n\n"+
    
    "       Beginner - 1 minute and have 10 mines\n"+
    "       Intermediate - 3 minutes and have 35 mines\n"+
    "       Advanced - 10 minutes and have 91 mines\n\n"+
    
    "3. If runs out of time, the mines gets exploded with a blasting sound.\n\n"+" Don't let the boredom hit you so hard  -  Just Play !!\n\n");
    }
    });

setJMenuBar(bar);

status.add(beginner);
status.add(intermediate);
status.add(advanced);

game.add(menuitem);
game.addSeparator();
game.add(beginner);
game.add(intermediate);
game.add(advanced);

game.addSeparator();
game.add(exit);
help.add(helpitem);
help.add(instruct);

bar.add(game);
bar.add(help);

}

public void componentAdded(ContainerEvent ce) {
}

public void componentRemoved(ContainerEvent ce) {
}

public void actionPerformed(ActionEvent ae) {
}

class MouseHandler extends MouseAdapter {

public void mouseClicked(MouseEvent me) {
if (check == true) {
for (int i = 0; i < blockr; i++) {
for (int j = 0; j < blockc; j++) {
if (me.getSource() == blocks[i][j]) {
var1 = i;
var2 = j;
i = blockr;
break;
}
}
}

setMine();
calculation();
check = false;

}

showValue(me);
winner();

if (starttime == false) {
st.start();
starttime = true;
}

}
}
//winning case function
public void winner() {
int q = 0;
for (int k = 0; k < blockr; k++) {
for (int l = 0; l < blockc; l++) {
if (colour[k][l] == 'w') {
q = 1;
}
}
}
if (q == 0) {
for (int k = 0; k < blockr; k++) {
for (int l = 0; l < blockc; l++) {
blocks[k][l].removeMouseListener(mh);
}
}
st.stop();
//message box 
JOptionPane.showMessageDialog(this, "You Win !!\nClick on smiley face to start a New Game...");
}
}
//to read bomb sound wav file from the system
 public void bomb(){
    try{
    File s = new File("explode.wav");
    AudioInputStream audioIn = AudioSystem.getAudioInputStream(s);
    // Get a sound clip resource.
    Clip clip = AudioSystem.getClip();
    // Open audio clip and load samples from the audio input stream.
    clip.open(audioIn);
    clip.start();
    }
 catch (UnsupportedAudioFileException e) {
    e.printStackTrace();
 } catch (IOException e) {
    e.printStackTrace();
 } catch (LineUnavailableException e) {
    e.printStackTrace();
 }
} 
//function to know when the bombs should explode
  public void explode(){
    for (int k = 0; k < blockr; k++) {
        for (int l = 0; l < blockc; l++) {
        //if a cell has a mine    
        if (countmine[k][l] == -1) {
            //if a cell has a flag, let it be
            if(blocks[k][l].getIcon()==ic[10]){
                blocks[k][l].removeMouseListener(mh); 
            }
            // else blast
            else{
                blocks[k][l].setIcon(ic[14]);
                bomb();
                blocks[k][l].removeMouseListener(mh); 
            }           
        }
        //mark the wrong cell with a X mark
        else{
            if(blocks[k][l].getIcon()==ic[10]){
                blocks[k][l].setIcon(ic[13]);
                blocks[k][l].removeMouseListener(mh); 
            }
        }
        blocks[k][l].removeMouseListener(mh);
        } 
    }
}
 

public void showValue(MouseEvent e) {

for (int i = 0; i < blockr; i++) {
for (int j = 0; j < blockc; j++) {

if (e.getSource() == blocks[i][j]) {
if (e.isMetaDown() == false) {

if (blocks[i][j].getIcon() == ic[10]) {
    if (detectedmine < num_of_mine) {
    detectedmine++;
    }
    tf_mine.setText("" + detectedmine);
}


if (countmine[i][j] == -1) {
for (int k = 0; k < blockr; k++) {
for (int l = 0; l < blockc; l++) {
//if a cell has a bomb
if (countmine[k][l] == -1) {
    if(blocks[k][l].getIcon()==ic[10]){
        blocks[k][l].removeMouseListener(mh); 
    }
    else{
        blocks[i][j].setIcon(ic[15]); //clicked cell should be highlighted red mine image
        blocks[k][l].setIcon(ic[9]); // normal revealed mines image
        blocks[k][l].removeMouseListener(mh);
    }
}
//wrong cell
else{
    if(blocks[k][l].getIcon()==ic[10]){
        blocks[k][l].setIcon(ic[13]); //change wrong cell to X mark
        blocks[k][l].removeMouseListener(mh); 
    }
}
blocks[k][l].removeMouseListener(mh);

}
}
st.stop();
reset.setIcon(ic[12]);
//message box
JOptionPane.showMessageDialog(null, "You Lost !\nClick on smiley face to start a New Game...");
} else if (countmine[i][j] == 0) {
revealed(i, j);
} else {
blocks[i][j].setIcon(ic[countmine[i][j]]);
colour[i][j] = 'b';
break;
}
} else {
if (detectedmine != 0 && (blocks[i][j].getIcon() == null)) {
detectedmine--;
blocks[i][j].setIcon(ic[10]);
tf_mine.setText("" + detectedmine);
}
else if(detectedmine!=0 && (blocks[i][j].getIcon()==ic[10])){
    detectedmine++;
    blocks[i][j].setIcon(null);
    tf_mine.setText(""+detectedmine);
}
}
}
}
}
}

public void calculation() {
int row, column;

for (int i = 0; i < blockr; i++) {
for (int j = 0; j < blockc; j++) {
int value = 0;
int R, C;
row = i;
column = j;
if (countmine[row][column] != -1) {
for (int k = 0; k < 8; k++) {
R = row + r[k];
C = column + c[k];

if (R >= 0 && C >= 0 && R < blockr && C < blockc) {
if (countmine[R][C] == -1) {
value++;
}

}

}
countmine[row][column] = value;
}
}
}
}
/*
 * @param = rows and cols
 */
public void revealed(int row, int col) {

int R, C;
colour[row][col] = 'b';
//revealed cells
blocks[row][col].setBackground(Color.gray);

blocks[row][col].setIcon(ic[countmine[row][col]]);
for (int i = 0; i < 8; i++) {
R = row + r[i];
C = col + c[i];
if (R >= 0 && R < blockr && C >= 0 && C < blockc && colour[R][C] == 'w') {
if (countmine[R][C] == 0) {
revealed(R, C);
} else {
blocks[R][C].setIcon(ic[countmine[R][C]]);
colour[R][C] = 'b';

}
}

}
}

public void setMine() {
int row = 0, col = 0;
Boolean[][] flag = new Boolean[blockr][blockc];

for (int i = 0; i < blockr; i++) {
for (int j = 0; j < blockc; j++) {
flag[i][j] = true;
countmine[i][j] = 0;
}
}

flag[var1][var2] = false;
colour[var1][var2] = 'b';

for (int i = 0; i < num_of_mine; i++) {
row = ranr.nextInt(blockr);
col = ranc.nextInt(blockc);

if (flag[row][col] == true) {

countmine[row][col] = -1;
colour[row][col] = 'b';
flag[row][col] = false;
} else {
i--;
}
}
}
// to set images in cells
public void setImage() {
String name;

for (int i = 0; i <= 8; i++) {
name = i + ".PNG";
ic[i] = new ImageIcon(name);
}
ic[9] = new ImageIcon("9.PNG");//mine
ic[10] = new ImageIcon("10.PNG");//flag
ic[11] = new ImageIcon("11.PNG");//newgame smiley
ic[12] = new ImageIcon("12.PNG");//out dull
ic[13] = new ImageIcon("13.PNG");//cross sign
ic[14] = new ImageIcon("14.GIF");//explode gif
ic[15]= new ImageIcon("15.PNG");//bomb click
}
//Timer class
public class ShowTimer {
    Timer t = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
         
         elapsedTime=elapsedTime+1000;
         minutes = (elapsedTime/60000) % 60;
         seconds = (elapsedTime/1000) % 60;
         // to display timer in 00:00 format
         seconds_string = String.format("%02d", seconds);
         minutes_string = String.format("%02d", minutes);
         timeLabel.setText(minutes_string+":"+seconds_string);
         if(minutes==1 && num_of_mine==10){
           reset.setIcon(ic[12]);
           t.stop();
           explode();
           JOptionPane.showMessageDialog(null, "Time Out !\nClick on New Game to play again...\n\nTime allowed : 1 Minute");
           
         }
         if(minutes==3 && num_of_mine==35){
           reset.setIcon(ic[12]);
           t.stop();
           explode();
           JOptionPane.showMessageDialog(null, "Time Out !\nClick on New Game to play again...\n\nTime Allowed : 3 Minutes");
         }
         if(minutes==10 && num_of_mine==91){
           reset.setIcon(ic[12]);
           t.stop();
           explode();
           JOptionPane.showMessageDialog(null, "Time Out !\nClick on New Game to play again...\n\nTime Allowed : 10 Minutes");
         }
        }
        
       });
    
    void start() {
        t.start();
    }
    
    void stop() {
        t.stop();
    
    }
    void resetTimer() {
     t.stop();
     elapsedTime=0;
     seconds =0;
     minutes=0;
     seconds_string = String.format("%02d", seconds);
     minutes_string = String.format("%02d", minutes);
     timeLabel.setText(minutes_string+":"+seconds_string);
    }
   
   }
}


