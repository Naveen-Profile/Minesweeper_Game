/*
*  @author Naveen
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//parent class
public class Cell{
    public JButton button;
    public int value;
    public int id;
    public boolean notChecked;

}
//inherited child class
class Cell2 extends Cell implements ActionListener{
    public Board board;
    public Cell2(Board board){
        super.button = new JButton();
        super.button.addActionListener(this);
        super.button.setPreferredSize(new Dimension(20,20));
        super.button.setMargin(new Insets(0,0,0,0));
        this.board = board;
        super.notChecked = true;
        }   
public JButton getButton() {
return super.button;
}
public int getValue() {
return super.value;
}
public int getId() {
return super.id;
}
//@param = id
public void setId(int id) {
this.id = id;
}
//@param = value
public void setValue(int value) {
this.value = value;
}
public void displayValue(){
if(super.value==-1){
    super.button.setText("\u2600");
    super.button.setBackground(Color.RED);
}else if(super.value!=0){
    super.button.setText(String.valueOf(super.value));
}
}
public void checkCell(){
    super.button.setEnabled(false);
displayValue();
super.notChecked = false;
if(super.value == 0) board.scanForEmptyCells();
if(super.value == -1) board.fail();
}
public void incrementValue(){
    super.value++;
}
public boolean isNotChecked(){
return super.notChecked;
}
public boolean isEmpty(){
return isNotChecked() && super.value==0;
}
public void reveal(){
displayValue();
super.button.setEnabled(false);
}
//Override
public void actionPerformed(ActionEvent e) {
checkCell();
}
}
