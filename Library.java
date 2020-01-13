package Library;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Description: This program can add books, reset books, view books, 
 * borrow and return books, rearrange books. 
 * It contains 1 JFrame, 1 JPanel, 1 text area (with 1 scrollbar), 
 * 3 labels, 3 combo box, and 5 buttons.
 * It also contains a object[] of book storage and a variable to store chosen book
 * @author Jenny Tai
 * @since Oct 26, 2018
 */

public class Library extends JPanel implements ActionListener{

    // declare Swing JFrame, JPanel and other widget objects for UI
    private JLabel label1, label2, label3;
    private JComboBox bookChoice, arrange, setBox;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JPanel panel;
    private JFrame frame;
    private JButton button1, button2, button3, button4;
    // declare storage and variable
    private Book[] books = new Book[Book.getMAX_STORAGE()];
    private int chosen;
    
    // main method
    public static void main(String[] args) {
        new Library();
    }
    
    // constructor
    public Library(){
        //set up frame window
        frame = new JFrame("Your Library");
        frame.setSize(1000,800);
        frame.setLocationRelativeTo(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        
        //set up 3 labels
        label1 = new JLabel("Here is your storage:");
        label1.setBounds(17, 120, 200, 25);
        label1.setFont(new Font("Georgia", Font.BOLD, 16));
        label1.setToolTipText("Max Storage = "+Book.getMAX_STORAGE());
        
        label2 = new JLabel("Here to arrange storage:");
        label2.setBounds(17, 180, 200, 30);
        label2.setFont(new Font("Georgia", Font.BOLD, 15));
        label2.setVisible(false);
        
        label3 = new JLabel("Here to reset attributes:");
        label3.setBounds(17, 240, 200, 30);
        label3.setFont(new Font("Georgia", Font.BOLD, 16));
        label3.setVisible(false);
        
        //set up 3 combo boxes
	bookChoice = new JComboBox();
        bookChoice.addItem("Choose Your Book");
	bookChoice.setBounds(20, 150, 160, 25);  
	bookChoice.addActionListener(this); 
        bookChoice.setToolTipText("Max Storage = "+Book.getMAX_STORAGE());
        
        String[] arrangement = {"By ID Number", "By Suggested", "By isBorrowed", "By Rating", "By Price", "By Age", "By Thickness"};
        arrange = new JComboBox(arrangement);
        arrange.setBounds(20, 210, 160, 25);  
	arrange.addActionListener(this); 
        arrange.setVisible(false);
        
        String[] attributes = {"Reset Attributes", "Rating", "Price", "Published Year", "Pages"};
        setBox = new JComboBox(attributes);
	setBox.setBounds(20, 270, 160, 25);  
	setBox.addActionListener(this); 
        setBox.setVisible(false);
        
        //set up 5 buttons
        button1 = new JButton("Store New Book");
	button2 = new JButton("Borrow");
        button3 = new JButton("Return");
        button4 = new JButton("View");
        button1.addActionListener(this);
	button2.addActionListener(this);
	button3.addActionListener(this);
        button4.addActionListener(this);
        button1.setBounds(25, 380, 150, 30);
	button2.setBounds(25, 480, 150, 30);
	button3.setBounds(25, 530, 150, 30);
        button4.setBounds(25, 580, 150, 30);
        button2.setVisible(false);
        button3.setVisible(false);
        button4.setVisible(false);
        
        // set up text area and scrollpane to display
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBounds(230, 40, 700, 600);
        textArea.setBackground(Color.BLACK);
	textArea.setForeground(Color.WHITE);
	textArea.setFont(new Font("Georgia", Font.BOLD, 14));
        textArea.setTabSize(15);
        textArea.append("\nWelcome to your Library");
        textArea.append("\nPlease add new books and choose them to further explore your library");
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(230, 40, 700, 600);
        
        //set up panel
        panel = new JPanel();
        panel.setLayout(null);
        panel.add(scrollPane);
        panel.add(bookChoice);
        panel.add(arrange);
        panel.add(setBox);
        panel.add(button1);     
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        panel.add(label1);  
        panel.add(label2);
        panel.add(label3);
        panel.setBackground(new Color(210, 190, 240));
        
        // add panel to frame
        frame.setContentPane(panel);
        frame.setVisible(true);
        
    } // end constructor
    
    
    // action perform method
    public void actionPerformed(ActionEvent e) {
        
        // when change the selected book
        if (e.getSource() == bookChoice){
            
            // error traping if didn't actually choose a book
            if(bookChoice.getSelectedItem().equals("Choose Your Book")){
                if (bookChoice.getItemCount()==1)
                    textArea.append("\n\nYour don't yet have any book.");
                else 
                    textArea.append("\n\nPlease choose a book.");
                button2.setVisible(false);
                button3.setVisible(false);
                button4.setVisible(false);
                arrange.setVisible(false);
                setBox.setVisible(false);
                label2.setVisible(false);
                label3.setVisible(false);
                
            // functions to selected book appear    
            } else {
                button2.setVisible(true);
                button3.setVisible(true);
                button4.setVisible(true);
                arrange.setVisible(true);
                setBox.setVisible(true);
                label2.setVisible(true);
                label3.setVisible(true);
                
                // find and record the selected book in the storage
                for (int i = 0; i<Book.getMAX_STORAGE(); i++){
                    if (bookChoice.getSelectedItem().equals(books[i].getTitle())){
                        chosen = i;
                        break;
                    }
                }
                // show processing
                textArea.append("\n\nYour have chosen book - " + books[chosen].getTitle());
            }
        
        // when user change the arrange comboBox, jump to method
        } else if (e.getSource() == arrange){
            arrangeBy(arrange.getSelectedItem().toString());
            
        // when want to change attributes of the selected book
        } else if (e.getSource() == setBox){
            // limit the reset situation
            if (books[chosen].getIsBorrowed()){
                textArea.append("\n\nThe book is borrowed so your can't reset attributes");
            // when change rating    
            } else {
                reset(setBox.getSelectedItem().toString());
                // re selected to default option after reset
                setBox.setSelectedIndex(0); 
            }
        
        // when user click button to make book
        } else if(e.getActionCommand().equals("Store New Book")){
            
            // error trapping from exceeding array range
            if (Book.getNumBook()>=Book.getMAX_STORAGE())
                textArea.append("\n\nYou can't exceed your max storage");
            else {
                // make a new book
                books[Book.getNumBook()] = new Book();
                chosen = Book.getNumBook()-1;
                textArea.append("\n\nYou added book " + books[chosen].getTitle());
                bookChoice.addItem(books[chosen].getTitle());
                // inform user errors are trapped 
                if (books[chosen].getIsRandom())
                    textArea.append("\n\nInvalid Input, other infomations will be generated randomly");
                // get rid of default option as soon as one book is made
                if (bookChoice.getItemAt(0)=="Choose Your Book")
                    bookChoice.removeItemAt(0);
            }
            
        // when user click borrow button, use borrow methods of selected book
        } else if (e.getActionCommand().equals("Borrow")){
            textArea.append(books[chosen].borrow());
            
        // when user click borrow button, use borrow methods of selected book
        } else if (e.getActionCommand().equals("Return")){
            textArea.append(books[chosen].returnBook());

        // when user want to print all attributes
        } else if (e.getActionCommand().equals("View")){
            textArea.append(books[chosen].toString());
        }
        
    } // end actionperformer
    
    
    /**
     * arrange the comboBox by certain rule chose by user
     *  declare variable for arranging: set the first value to be the base, 
     *  compare them with every other value from top to bottom,
     *  swap them if smaller then keep comparing
     *  base value got increase form the first value to the last
     * @param rule the user choice
     */
    public void arrangeBy(String rule){
        // error trapping to not to arrange a single book
        if (bookChoice.getItemCount()>1){
            
            // a and b for place in the objects array
            // i3 for the base object to compare 2 values
            int a=0,b=0,i3;
            // i1 for the general loop taking place
            for(int i1=0;i1<bookChoice.getItemCount()-1;i1++){
                // at the start of a general loop, the base value goes to the next object
                i3=i1;
                // link and record the base object to place in the storage
                for (int i2=0; i2<Book.getMAX_STORAGE(); i2++){
                    if (bookChoice.getItemAt(i3).equals(books[i2].getTitle())){
                        a = i2;
                        break;
                    }
                }
                // j1 be the object the bas is comparing to
                for(int j1=i3+1;j1<bookChoice.getItemCount();j1++){
                    // link and record the comparing object to the place in storage
                    for (int j2=0; j2<Book.getMAX_STORAGE(); j2++){
                        if (bookChoice.getItemAt(j1).equals(books[j2].getTitle())){
                            b = j2;
                            break;
                        }
                    }
                    
                    // choose comparing condition by user choice
                    switch (rule){
                        case "By ID Number":  
                            // swap larger id to the bottom
                            if(books[a].getID_NUMBER()>books[b].getID_NUMBER()){
                                swap(a, b, i3, j1);
                                i3 = j1;
                            }
                            break;
                            
                        case "By Suggested": 
                            // swap smaller score to the bottom
                            if(books[a].getSuggestedScore()<books[b].getSuggestedScore()){
                                swap(a, b, i3, j1);
                                i3 = j1;
                            }
                            break;
                            
                        case "By isBorrowed":  
                            // swap un-borrowed book to the bottom
                            if(books[a].getIsBorrowed()==false && books[b].getIsBorrowed()==true){
                                swap(a, b, i3, j1);
                                i3 = j1;
                            }
                            break;
                            
                        case "By Rating":  
                            // swap smaller rating to the bottom
                            if(books[a].getRating()<books[b].getRating()){
                                swap(a, b, i3, j1);
                                i3 = j1;
                            }
                            break;
                            
                        case "By Price":  
                            // swap higher price to the bottom
                            if(books[a].getPrice()>books[b].getPrice()){
                                swap(a, b, i3, j1);
                                i3 = j1;
                            }
                            break;
                            
                        case "By Age":  
                            // swap higher age to the bottom
                            if(books[a].getAge()>books[b].getAge()){
                                swap(a, b, i3, j1);
                                i3 = j1;
                            }    
                            break;
                            
                        case "By Thickness":  
                            // swap thicker book to the bottom
                            if(books[a].getThickness()>books[b].getThickness()){
                                swap(a, b, i3, j1);
                                i3 = j1;
                            }
                            break;
                    } // end swtich statement
                    
                } // end inside loop for increasing comparing object
            } // end the general loop for increasing base object
        } //end the shortcur for having one book
        
        // output to show process
        textArea.append(" \n\nYou rearranged the storage " +rule);
    } // end arrange method
    
    /**
     * reset certain attributes of chosen book in the array
     * @param attributes the user choice
     */
    private void reset(String attributes) {
        try {
            switch (attributes){
                case "Rating":
                    books[chosen].setRating();
                    textArea.append("\n\nThe rating of "+bookChoice.getSelectedItem()
                            + " has been changed to "+books[chosen].getRating());
                    break;

                case "Price":
                    books[chosen].setPrice();
                    textArea.append("\n\nThe price of "+bookChoice.getSelectedItem()
                            + " has been changed to "+books[chosen].getPrice());
                    break;

                case "Published Year":
                    books[chosen].setPublishYear();
                    textArea.append("\n\nThe year when "+bookChoice.getSelectedItem()
                            + " was published has been changed to "+books[chosen].getPublishYear());
                    break;

                case "Pages":
                    books[chosen].setPages();
                    textArea.append("\n\nThe number of pages of "+bookChoice.getSelectedItem()
                            + " has been changed to "+books[chosen].getPages());
                    break;

                default: 
                    textArea.append("\n\nPlease select an attribute to reset");
            }
        } catch (NumberFormatException | NullPointerException ex){
            textArea.append("\n\nInvalid input");
        }
    }
    
    /**
     * Use for arranging, swap the place of 2 books in the comboBox
     * @param a String for choice a
     * @param b String for choice b
     * @param aCurrentPlace 
     * @param bCurrentPlace 
     */
    public void swap(int a, int b, int aCurrentPlace, int bCurrentPlace){
        System.out.println(a+" "+b);
        bookChoice.insertItemAt(books[b].getTitle(), aCurrentPlace);
        bookChoice.removeItemAt(bCurrentPlace+1);
        bookChoice.insertItemAt(books[a].getTitle(), bCurrentPlace+1);
        bookChoice.removeItem(books[a].getTitle());
    }
} //end class