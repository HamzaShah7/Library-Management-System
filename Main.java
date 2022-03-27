import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Date;

public class Main {
    public static void main(String args[]) throws SQLException {
        Scanner sc = new Scanner (System.in);
        int flag = 0;
        String name = null;
        Connection connection = DriverManager.getConnection ("jdbc:mysql://localhost:3306/librarydb","root","Mnismhn7");
        Statement statement = connection.createStatement ();

        int option = 7;
        //String input="M";
        while (option!=0){
            if (flag==0){
                System.out.println ("Welcome to Library Management System.\n");
                System.out.println ("\t\t\tLogin\n");
                System.out.print ("Enter your name:");
                name = sc.next ();
                System.out.print ("Enter your password:");
                String password = sc.next ();
                try {
                    ResultSet resultSet = statement.executeQuery ("SELECT * FROM librarydb.librarian");
                    while (resultSet.next ()){
                        String n = resultSet.getString ("name");
                        String p = resultSet.getString ("password");
                        if (n.equalsIgnoreCase (name) && p.equals (password)){
                            flag = 1;
                            System.out.println ("\nHi! "+ name);
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace ();
                }
            }
            if (flag == 1){
                System.out.println ("\n\nEnter 0 to quit.");
                System.out.println ("Enter 1 to Show all books");
                System.out.println ("Enter 2 to Add new book");
                System.out.println ("Enter 3 to Issue a book");
                System.out.println ("Enter 4 to See issued books record");
                System.out.println ("Enter 5 to Delete a issued book record");

                option = sc.nextInt();
                switch (option) {
                    case 1:
                        ResultSet resultSet = statement.executeQuery ("SELECT * FROM librarydb.books");
                        while (resultSet.next ()){
                            String BookTitle = resultSet.getString ("BookTitle");
                            System.out.println (BookTitle);
                        }
                        break;
                    case 2:
                        System.out.println ("Enter ISBN:");
                        String ISBN = sc.next();
                        System.out.println ("Enter Book Title:");
                        String BookTitle = sc.next();
                        BookTitle += sc.nextLine ();
                        System.out.println("Enter Author:");
                        String Author = sc.nextLine ();
                        //Author += sc.nextLine ();
                        System.out.println ("Enter Availability:");
                        String Availability = sc.nextLine ();
                        //Availability += sc.nextLine ();
                        System.out.println ("Enter Quantity:");
                        int Quantity = sc.nextInt ();
                        Books book = new Books (ISBN,BookTitle,Author,Availability,Quantity);
                        book.storeBook (connection);
                        System.out.println ("You successfully added a book.");
                        break;
                    case 3:
                        System.out.println ("Enter StdID:");
                        int S_ID = sc.nextInt ();
                        String S_ISBN = null;
                        String S_Name = null;
                        System.out.println ("Enter Book Title:");
                        String STitle = sc.next();
                        STitle += sc.nextLine ();
                        resultSet = statement.executeQuery ("SELECT * FROM librarydb.books");
                        while (resultSet.next ()){
                            String B_Title = resultSet.getString ("BookTitle");
                            if (B_Title.equalsIgnoreCase (STitle)){
                                S_ISBN = resultSet.getString ("ISBN");
                            }
                        }
                        resultSet = statement.executeQuery ("SELECT * FROM librarydb.students");
                        while (resultSet.next ()){
                            int Std_ID = resultSet.getInt ("StdID");
                            if (S_ID==Std_ID){
                                S_Name = resultSet.getString ("Name");
                            }
                        }
                        Date date = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
                        String IssueDate = formatter.format(date);
                        Calendar c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.DATE, 10);
                        Date DatePlus = c.getTime();
                        String ReturnDate = formatter.format (DatePlus);
                        resultSet = statement.executeQuery ("SELECT * FROM librarydb.books");
                        while (resultSet.next ()){
                            String a = resultSet.getString ("BookTitle");
                            int Quan = resultSet.getInt ("Quantity");
                            if (a.equalsIgnoreCase (STitle)){
                                if (Quan>0){
                                    Quan = Quan - 1;
                                    String sql = "UPDATE books SET Quantity = "+"'"+Quan+"' WHERE BookTitle ="+"'"+STitle+"'"+";";
                                    statement.executeUpdate (sql);
                                    String sql2 = "INSERT INTO issuedbooks (ISBN, Std_ID, Std_Name, Issued_Date, Return_Date) VALUES ("+"'"+S_ISBN+"'"+", "+"'"+S_ID+"'"+", "+"'"+S_Name+"'"+", "+"'"+IssueDate+"'"+", "+"'"+ReturnDate+"'"+")";
                                    statement.executeUpdate (sql2);
                                    System.out.println ("\nYou successfully issue the book with Return Date: "+ReturnDate);
                                    break;
                                }
                                else {
                                    String Avai = "No";
                                    String sql3 = "UPDATE books SET Availability = "+"'"+Avai+"' WHERE BookTitle ="+"'"+STitle+"'"+";";
                                    statement.executeUpdate (sql3);
                                    System.out.println ("This book ("+STitle+") is not available yet.");
                                    break;
                                }
                            }
                        }
                        break;
                    case 4:
                        resultSet = statement.executeQuery ("SELECT * FROM librarydb.issuedbooks;");
                        while (resultSet.next ()){
                            System.out.println ("Issue ID is: "+resultSet.getInt ("Issue_ID"));
                            System.out.println ("Book ISBN is: "+resultSet.getString ("ISBN"));
                            System.out.println ("Student ID is: "+resultSet.getInt ("Std_ID"));
                            System.out.println ("Student Name is: "+resultSet.getString ("Std_Name"));
                            System.out.println ("Issue Date: "+resultSet.getString ("Issued_Date"));
                            System.out.println ("Return Date: "+resultSet.getString ("Return_Date"));
                            System.out.println ("");
                        }
                        break;
                    case 5:
                        System.out.println ("Enter Book ISBN: ");
                        String B_ISBN = sc.next ();
                        System.out.println ("Enter your ID: ");
                        String Std_ID = sc.next ();
                        statement.executeUpdate ("delete from issuedbooks where ISBN='"+B_ISBN+"' and Std_ID="+Std_ID+";");
                        resultSet = statement.executeQuery ("SELECT * FROM librarydb.books");
                        while (resultSet.next ()){
                            String a = resultSet.getString ("ISBN");
                            int Quan = resultSet.getInt ("Quantity");
                            if (a.equals (B_ISBN)) {
                                if (Quan > 0) {
                                    Quan = Quan + 1;
                                    String sql = "UPDATE books SET Quantity = " + "'" + Quan + "' WHERE ISBN =" + "'" + B_ISBN + "'" + ";";
                                    statement.executeUpdate (sql);
                                    System.out.println ("\nYou successfully returned a book.");
                                    break;
                                }
                            }
                        }
                }
            }
            else {
                System.out.println ("You entered wrong credentials. Please Try Again.");
            }
        }
    }
}
