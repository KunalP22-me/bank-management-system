import java.util.ArrayList;
import java.util.List;

public class Accountholder {
    private String username;
    private String password;
    private List<AccountManage> accounts = new ArrayList<>();

    private String lastLogin = "";
    public String getLastLogin() { return lastLogin; }
    public void setLastLogin(String time) { lastLogin = time; }

    public Accountholder(String username, String password) {
        this.username = username;
        this.password = password;
        accounts.add(new AccountManage("Main"));  // Default accounts
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public List<AccountManage> getAccounts() {
        return accounts;
    }

    public AccountManage getAccount(String name) {
        for (AccountManage acc : accounts) {
            if (acc.getName().equals(name)) return acc;
        }
        return null;
    }

    public void addAccount(String name) {
        accounts.add(new AccountManage(name));
    }

    public boolean deleteAccount(String name) {
        if (name.equals("Main")) return false; // Prevent deleting default accounts
        accounts.removeIf(acc -> acc.getName().equals(name));
        return true;
    }

    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(username).append(",").append(password);
        for (AccountManage acc : accounts) {
            sb.append(";").append(acc.toFileString());
        }
        return sb.toString();
    }

    public static Accountholder fromFileString(String line) {
        String[] parts = line.split(",");
        Accountholder user = new Accountholder(parts[0], parts[1].split(";")[0]);
        String[] accs = line.substring(line.indexOf(';') + 1).split(";");
        user.accounts.clear(); // remove default
        for (String acc : accs) {
            user.accounts.add(AccountManage.fromFileString(acc));
        }
        return user;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
    
}
