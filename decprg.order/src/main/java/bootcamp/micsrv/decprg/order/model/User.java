package bootcamp.micsrv.decprg.order.model;

public class User {

    private Long id;

    private String name;

    private String email;

    private String address;

    private boolean active=false;


    public User() {
    }

    public User(String name, String email, String address, boolean active) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.active = active;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
