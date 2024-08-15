package org.example.courier;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Courier {

    private String login;
    private String password;
    private String firstName;
    private Integer id;

    public Courier(Integer id) {
        this.id = id;
    }

    public Courier(String login) {
        this.login = login;
    }

    public Courier(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

}
