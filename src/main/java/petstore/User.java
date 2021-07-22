package petstore;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class User {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private int userStatus;

    public User() {
        this.id = new Random().nextLong();
        this.username = RandomStringUtils.randomAlphabetic(10);
        this.firstName = RandomStringUtils.randomAlphabetic(10);
        this.lastName = RandomStringUtils.randomAlphabetic(10);
        this.email = RandomStringUtils.randomAlphabetic(5) + "@test.tt";
        this.password = RandomStringUtils.randomAlphanumeric(5);
        this.phone = "+7" + RandomStringUtils.randomNumeric(10);
        this.userStatus = new Random().nextInt();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public int getUserStatus() {
        return userStatus;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", userStatus=" + userStatus +
                '}';
    }
}
