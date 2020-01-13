package Library;

import javax.swing.JOptionPane;

/**
 * @since Oct 26 2018
 * @author Jenny Tai
 * UML, class and GUI
 */
public class Book {
    
    //attributes
    private static final int MAX_STORAGE = 10;
    private static int numBook = 0;
    private static int availableNumBook = 0;
    private static boolean isRandom = false;
    private int rating = (int) (Math.random() * 11);
    private double price = Math.round(Math.random() * 10000)/100.00;
    private int publishYear = (int) (Math.random() * 2019);
    private int pages = (int) (Math.random() * 1000);
    private boolean isBorrowed = false;
    private final int ID_NUMBER;
    private String title;
    
    // constructor
    public Book(){
        
        numBook++;
        availableNumBook++;
        ID_NUMBER = numBook;
        
        try{
            title = JOptionPane.showInputDialog("What is the title of the book?");
            while (title.equals(""))
                title = JOptionPane.showInputDialog("Please enter a title");
            this.setRating();
            this.setPrice();
            this.setPublishYear();
            this.setPages();
            
        } catch (NumberFormatException | NullPointerException ex){
            isRandom = true;
        }
    }
    
    // functions
    public String borrow(){
        availableNumBook--;
        if (!isBorrowed){
            isBorrowed = true;
            return ("\n\n"+title+" is borrowed.");
        } else
            return ("\n\n"+title+" is already borrowed.");
    }
    public String returnBook(){
        availableNumBook++;
        if (isBorrowed){
            isBorrowed = false;
            return ("\n\n"+title+" is returned.");
        } else
            return ("\n\n"+title+" is not even borrowed.");
    }
    
    public String toString() {
        return "\n\nStoage: " + numBook + "/" + MAX_STORAGE 
                + "\nAvailable rate: " + availableNumBook + "/" + numBook
                + "\nTitle: " + title
                + "\nID: " + ID_NUMBER
                + "\nSuggested Score: " + this.getSuggestedScore()
                + "\nRating: " + rating
                + "\nPrice: $ " + price
                + "\nPublished year: " + publishYear
                + "\nAge: " + this.getAge()
                + "\nPages: " + pages
                + "\nThickness: " + this.getThickness() + " cm"
                + "\nBorrowed: " + isBorrowed ;
    }
    
    // accessor methods, class and inctance methods
    public static int getMAX_STORAGE(){return MAX_STORAGE;}
    public static int getNumBook(){return numBook;}
    public static int getAvailableNumBook(){return availableNumBook;}
    
    public int getRating(){return rating;}
    public double getPrice(){return price;}
    public boolean getIsBorrowed(){return isBorrowed;}
    public int getPublishYear(){return publishYear;}
    public int getPages(){return pages;}
    public int getID_NUMBER(){return ID_NUMBER;}
    public boolean getIsRandom(){return isRandom;}
    public String getTitle(){return title;}
    
    public int getAge(){return 2018-publishYear;}
    public double getThickness(){return Math.round(pages*0.0097*100)/100.00;}
    public double getSuggestedScore(){return Math.round(rating*rating/price*1000)/100.00;}
    
    // Mutator methods
    public void setRating(){
        rating = Integer.parseInt(JOptionPane.showInputDialog("What is the rating out of 10 in integer?"));
        while (rating<0||rating>10)
            rating = Integer.parseInt(JOptionPane.showInputDialog("Please enter integer 0-10 for rating"));
    }
    
    public void setPrice(){
        price = Double.parseDouble(JOptionPane.showInputDialog("How much is the book?"));
        while (price<0)
            price = Double.parseDouble(JOptionPane.showInputDialog("Please enter positive number for price"));
        price = Math.round(price*100)/100.00;
    }
    
    public void setPublishYear(){
        publishYear = Integer.parseInt(JOptionPane.showInputDialog("When did it publish?"));
        while (publishYear>2018||publishYear<-2000)
            publishYear = Integer.parseInt(JOptionPane.showInputDialog("Please enter a possible year in the past"));
    }
    
    public void setPages(){
        pages = Integer.parseInt(JOptionPane.showInputDialog("How many pages are there in this book?"));
        while (pages<1)
            pages = Integer.parseInt(JOptionPane.showInputDialog("Please enter a positive integer"));
    }
    
}