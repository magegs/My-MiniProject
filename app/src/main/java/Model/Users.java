package Model;

public class Users extends com.example.demo.Users {
    private  String name,email,password,phoneno,refkey;

    public Users() {
    }

    public Users(String name, String password, String email, String phoneno, String name1, String email1, String password1, String phoneno1, String refkey) {
        super(name, password, email, phoneno);
        this.name = name1;
        this.email = email1;
        this.password = password1;
        this.phoneno = phoneno1;
        this.refkey = refkey;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPhoneno() {
        return phoneno;
    }

    @Override
    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getRefkey() {
        return refkey;
    }

    public void setRefkey(String refkey) {
        this.refkey = refkey;
    }
}
