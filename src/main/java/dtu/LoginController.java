package dtu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class LoginController {

    private boolean loggedInFlag;
    private HashSet<String> users =  new HashSet<>();
    private final String usersDatabase = "./db/users.csv";
    private String text;


    public void loadUsers() {
        //Reads in the csv file containing users into the users hashset
        try (Scanner scanner = new Scanner(new File(usersDatabase))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                processLine(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("User database file not found: " + e.getMessage());
        }
    }

    private void processLine(String line) {
        line = line.replace("\"", "");
        String[] parts = line.split(",");        
        if (parts.length >= 2) {
            String username = parts[0].trim();
            //String password = parts[1].trim(); //This is a leftover from when we could have had passwords.
            users.add(username); //
        }
    }

    public HashSet<String> getUsers() {
        return users;
    }

    //Is logged in check for testing
    public boolean loggedIn() {
        loggedInFlag = false;
        return loggedInFlag;
    }

    //Is this user logged in
    public boolean isUserLoggedIn(String username) {
        if (users.contains(username)) {
            return true;
        }
        setText("Not Valid Credentials. Try Again.");
        return false;
    }

    public void setText(String string) {
        text = string;
    }

    public String getText() {
        return text;
    }

    public boolean validate(String username) {
        return users.contains(username.trim());
    }






}
