package dtu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class LoginController {
    //Just establish the user as a singleton
    private boolean loggedInFlag;

    private HashSet<String> users =  new HashSet<>();
    private final String usersDatabase = "./db/users.csv";
    private String text;


    public void loadUsers() {
        try (Scanner scanner = new Scanner(new File(usersDatabase))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                processLine(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("User database file not found: " + e.getMessage());
        }
    }

    public HashSet<String> getUsers() {
        return users;
    }


    private void processLine(String line) {
        line = line.replace("\"", "");
        String[] parts = line.split(",");        
        if (parts.length >= 2) {
            String username = parts[0].trim();
            //String password = parts[1].trim(); //Add this if we need passwords for any reason.
            users.add(username);
        }
    }

    public boolean loggedIn() {
        //What is this?
        // no clue. ask anton.
        loggedInFlag = false;
        return loggedInFlag;
    }

    public boolean isUserLoggedIn(String username) {
        //Navngivning af metoden her stinker, der menes "Findes brugeren der forsøges at logges ind på"
        //Renamed to 'isUserLoggedIn' -M
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

    

    public boolean createUser(String username) throws Exception {
        username = username.trim();
        if (users.contains(username)) {
            throw new Exception("User already exists.");
        } else if (username.isEmpty()) {
            throw new Exception("Username is blank.");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(usersDatabase, true))) {
            bw.write(username);
            bw.newLine();
            users.add(username);
            return true;
        } catch (IOException e) {
            System.out.println("Error creating new user.");
            return false;
        }
    }

    private void validateUsersfile() {
        //When is this ever used? M: This would have been used on launch in order to ensure the user file is intact.
        File file = new File(usersDatabase);
        if (!file.exists()) {
            try {
                file.createNewFile(); // Create file if not exists
            } catch (IOException e) {
                System.out.println("Unable to create new userdata file.");
                e.printStackTrace();
            }
        }
    }
    

    public boolean validate(String username) {
        return users.contains(username.trim());
    }






}
