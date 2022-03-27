public class Librarian {
    private int id;
    private String name;
    private String password;

    private Librarian(int id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getName () {
        return name;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }
}
