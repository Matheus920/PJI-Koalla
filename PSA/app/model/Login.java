package app.model;

public class Login {
    private long id;
    private String email;
    private String senha;
    private int privilegio;

    public Login(long id, String email, String senha, int privilegio) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.privilegio = privilegio;
    }

    public Login(String email, String senha, int privilegio) {
        this.email = email;
        this.senha = senha;
        this.privilegio = privilegio;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(int privilegio) {
        this.privilegio = privilegio;
    }
}
