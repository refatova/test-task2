package hello_module.server;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Saniye on 24.09.16.
 */

@Entity
@Table(name = "Account")
public class Account  implements Serializable {

    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "user_login")
    private String login;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_name")
    private String name;

    @Column(name = "sault")
    private String sault;

public Account(){}
    public Account(String login, String password) {

        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
