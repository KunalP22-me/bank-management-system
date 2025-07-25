import java.io.*;
import java.util.*;

public class BankServer {
    private HashMap<String, Accountholder> Accdetails = new HashMap<>();
    private final String FILE = "Accdetails.txt";

    public BankServer() {
        loadFromFile();
    }

    public boolean register(String username, String password) {
        if (Accdetails.containsKey(username)) return false;
        Accdetails.put(username, new Accountholder(username, password));
        saveToFile();
        return true;
    }

    public Accountholder login(String username, String password) {
        Accountholder user = Accdetails.get(username);
        if (user != null && user.checkPassword(password)) {
            user.setLastLogin(new java.util.Date().toString()); // <-- insert here
            saveToFile(); // save updated info
            return user;
        }
        return null;
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE))) {
            for (Accountholder user : Accdetails.values()) {
                writer.println(user.toFileString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        File file = new File(FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Accountholder user = Accountholder.fromFileString(line);
                Accdetails.put(user.getUsername(), user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateData() {
        saveToFile();
    }
}
