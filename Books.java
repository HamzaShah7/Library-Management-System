import java.sql.*;

public class Books {
    private String ISBN;
    private String BookTitle;
    private String Author;
    private String Availability;
    private int quantity;

    public Books(String ISBN, String BookTitle, String Author, String Availability, int quantity){
        this.ISBN = ISBN;
        this.BookTitle = BookTitle;
        this.Author = Author;
        this.Availability = Availability;
        this.quantity = quantity;
    }

    public String getBookTitle () {
        return BookTitle;
    }

    public String getAvailability () {
        return Availability;
    }

    public String getAuthor () {
        return Author;
    }

    public String getISBN () {
        return ISBN;
    }

    public void setBookTitle (String BookTitle) {
        BookTitle = BookTitle;
    }

    public void setAuthor (String author) {
        Author = author;
    }

    public void setAvailability (String availability) {
        Availability = availability;
    }

    public void setISBN (String ISBN) {
        this.ISBN = ISBN;
    }

    public int getQuantity () {
        return quantity;
    }

    public void setQuantity (int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString () {
        return "Books{" +
                "ISBN='" + getISBN () + '\'' +
                ", BookTitle='" + getBookTitle () + '\'' +
                ", Author='" + getAuthor () + '\'' +
                ", Availability='" + getAvailability () + '\'' +
                ", quantity=" + getQuantity () +
                '}';
    }

    public void storeBook(Connection c){
        try {
            Statement statement = c.createStatement ();
            String sql = "INSERT INTO books VALUES ("+"'"+getISBN ()+"'"+", "+"'"+getBookTitle ()+"'"+", "+"'"+getAuthor ()+"'"+", "+"'"+getAvailability ()+"'"+", "+"'"+getQuantity ()+"'"+")";
            statement.executeUpdate (sql);
        } catch (SQLException e) {
            e.printStackTrace ();
        }
    }
}
