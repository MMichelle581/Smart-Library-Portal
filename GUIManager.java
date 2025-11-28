import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

// Book class implementing Comparable for sorting
class Book implements Comparable<Book> {
    private String title;
    private String author;
    private String isbn;
    
    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }
    
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getISBN() { return isbn; }
    
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setISBN(String isbn) { this.isbn = isbn; }
    
    @Override
    public int compareTo(Book other) {
        return this.title.compareTo(other.title);
    }
    
    @Override
    public String toString() {
        return title + " by " + author + " (ISBN: " + isbn + ")";
    }
}

// Abstract User class - demonstrating abstraction
abstract class User {
    protected String name;
    protected String email;
    protected String password;
    
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    
    public abstract String getUserType();
    
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}

// Student class extending User - demonstrating inheritance
class Student extends User {
    private String studentId;
    
    public Student(String name, String email, String password) {
        super(name, email, password);
        this.studentId = "STU" + System.currentTimeMillis();
    }
    
    @Override
    public String getUserType() {
        return "Student";
    }
    
    public String getStudentId() { return studentId; }
}

public class GUIManager {
    
    private HashMap<String, String> subjectsMap;
    private ArrayList<Book> booksList;
    private String currentUser;
    private String selectedPaperPath;
    
    private static final String USERS_FILE = "users.txt";
    private static final String BOOKS_FILE = "books.txt";
    private static final String PYQ_DIR = "PYQ";
    
    public GUIManager() {
        initializeSubjects();
        initializeBooks();
        showLandingPage();
    }
    
    private void initializeSubjects() {
        subjectsMap = new HashMap<>();
        subjectsMap.put("CHEM F111", "Chemistry");
        subjectsMap.put("BIO F111", "Biology");
        subjectsMap.put("CS F213", "OOP");
        subjectsMap.put("BIOT F212", "Microbiology");
        subjectsMap.put("HSS F252", "International Law");
    }
    
    private void initializeBooks() {
        booksList = new ArrayList<>();
        
        try {
            File booksFile = new File(BOOKS_FILE);
            if (booksFile.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(booksFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split("\\|");
                        if (parts.length == 3) {
                            booksList.add(new Book(parts[0], parts[1], parts[2]));
                        }
                    }
                }
            } else {
                booksList.add(new Book("Introduction to Algorithms", "Thomas H. Cormen", "978-0262033848"));
                booksList.add(new Book("Clean Code", "Robert C. Martin", "978-0132350884"));
                booksList.add(new Book("Design Patterns", "Erich Gamma", "978-0201633612"));
                booksList.add(new Book("The Pragmatic Programmer", "Andrew Hunt", "978-0135957059"));
                booksList.add(new Book("Java: The Complete Reference", "Herbert Schildt", "978-1260440232"));
            }
            
            Collections.sort(booksList);
            
        } catch (IOException e) {
            System.err.println("Error loading books: " + e.getMessage());
        }
    }
    
    private void showLandingPage() {
        JFrame frame = new JFrame("SmartLibraryPortal");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        JLabel titleLabel = new JLabel("SmartLibraryPortal");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("BITS Pilani Hyderabad Campus");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Signup");
        
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        loginButton.setMaximumSize(new Dimension(200, 40));
        signupButton.setMaximumSize(new Dimension(200, 40));
        
        JLabel infoLabel = new JLabel("Choose Login or Signup to continue");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(loginButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(signupButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(infoLabel);
        
        loginButton.addActionListener(e -> {
            frame.dispose();
            showLoginPage();
        });
        
        signupButton.addActionListener(e -> {
            frame.dispose();
            showSignupPage();
        });
        
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    
    private void showSignupPage() {
        JFrame frame = new JFrame("Signup - SmartLibraryPortal");
        frame.setSize(450, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(new JLabel("Full Name:"), gbc);
        
        gbc.gridx = 1;
        JTextField nameField = new JTextField(20);
        panel.add(nameField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        JTextField emailField = new JTextField(20);
        panel.add(emailField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JButton submitButton = new JButton("Submit");
        panel.add(submitButton, gbc);
        
        gbc.gridy = 5;
        JButton backButton = new JButton("Back to Landing Page");
        panel.add(backButton, gbc);
        
        submitButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(frame, "Email must contain @", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (password.length() < 6) {
                JOptionPane.showMessageDialog(frame, "Password must be at least 6 characters", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.matches(".*[@#$%!].*")) {
                JOptionPane.showMessageDialog(frame, "Password must contain at least 1 symbol (@, #, $, %, !)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int digitCount = 0;
            for (int i = 0; i < password.length(); i++) {
                if (Character.isDigit(password.charAt(i))) {
                    digitCount++;
                }
            }
            if (digitCount < 2) {
                JOptionPane.showMessageDialog(frame, "Password must contain at least 2 digits", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (FileWriter writer = new FileWriter(USERS_FILE, true)) {
                writer.write(email + "|" + password + "|" + name + "\n");
                JOptionPane.showMessageDialog(frame, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                showLoginPage();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error saving user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        backButton.addActionListener(e -> {
            frame.dispose();
            showLandingPage();
        });
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    
    private void showLoginPage() {
        JFrame frame = new JFrame("Login - SmartLibraryPortal");
        frame.setSize(450, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        JTextField emailField = new JTextField(20);
        panel.add(emailField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton submitButton = new JButton("Submit");
        panel.add(submitButton, gbc);
        
        gbc.gridy = 4;
        JButton backButton = new JButton("Back to Landing Page");
        panel.add(backButton, gbc);
        
        submitButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(frame, "Email must contain @", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                File usersFile = new File(USERS_FILE);
                if (!usersFile.exists()) {
                    JOptionPane.showMessageDialog(frame, "Invalid Email or Password", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                try (BufferedReader reader = new BufferedReader(new FileReader(usersFile))) {
                    String line;
                    boolean found = false;
                    String userName = "";
                    
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split("\\|");
                        if (parts.length == 3) {
                            if (parts[0].equals(email) && parts[1].equals(password)) {
                                found = true;
                                userName = parts[2];
                                break;
                            }
                        }
                    }
                    
                    if (found) {
                        currentUser = userName;
                        frame.dispose();
                        showHomeDashboard();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid Email or Password", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error reading users: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        backButton.addActionListener(e -> {
            frame.dispose();
            showLandingPage();
        });
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    
    private void showHomeDashboard() {
        JFrame frame = new JFrame("Home - SmartLibraryPortal");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(70, 130, 180));
        JLabel welcomeLabel = new JLabel("Welcome to SmartLibraryPortal, " + currentUser + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.WHITE);
        topPanel.add(welcomeLabel);
        
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JButton homeButton = new JButton("Home");
        JButton aboutButton = new JButton("About");
        JButton booksButton = new JButton("Books");
        JButton questionPapersButton = new JButton("Question Papers");
        JButton logoutButton = new JButton("Logout");
        
        Dimension buttonSize = new Dimension(250, 50);
        homeButton.setPreferredSize(buttonSize);
        aboutButton.setPreferredSize(buttonSize);
        booksButton.setPreferredSize(buttonSize);
        questionPapersButton.setPreferredSize(buttonSize);
        logoutButton.setPreferredSize(buttonSize);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(homeButton, gbc);
        
        gbc.gridy = 1;
        centerPanel.add(aboutButton, gbc);
        
        gbc.gridy = 2;
        centerPanel.add(booksButton, gbc);
        
        gbc.gridy = 3;
        centerPanel.add(questionPapersButton, gbc);
        
        gbc.gridy = 4;
        centerPanel.add(logoutButton, gbc);
        
        homeButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "You are already on the Home page!", "Info", JOptionPane.INFORMATION_MESSAGE);
        });
        
        aboutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, 
                "SmartLibraryPortal\nVersion 1.0\nBITS Pilani Hyderabad Campus\n\nA comprehensive library management system", 
                "About", JOptionPane.INFORMATION_MESSAGE);
        });
        
        booksButton.addActionListener(e -> {
            frame.dispose();
            showBooksSection();
        });
        
        questionPapersButton.addActionListener(e -> {
            frame.dispose();
            showQuestionPapersSection();
        });
        
        logoutButton.addActionListener(e -> {
            frame.dispose();
            showLandingPage();
        });
        
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    
    private void showBooksSection() {
        JFrame frame = new JFrame("Books - SmartLibraryPortal");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("E-Books Collection");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Book book : booksList) {
            listModel.addElement(book.toString());
        }
        
        JList<String> bookList = new JList<>(listModel);
        bookList.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(bookList);
        
        JButton backButton = new JButton("Back to Home");
        backButton.addActionListener(e -> {
            frame.dispose();
            showHomeDashboard();
        });
        
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(backButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    
    private void showQuestionPapersSection() {
        JFrame frame = new JFrame("Question Papers - SmartLibraryPortal");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Question Papers");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        
        JButton pyqButton = new JButton("PYQ (Previous Year Questions)");
        pyqButton.setPreferredSize(new Dimension(300, 60));
        pyqButton.setFont(new Font("Arial", Font.BOLD, 16));
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(pyqButton, gbc);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton backButton = new JButton("Back to Home");
        bottomPanel.add(backButton, BorderLayout.CENTER);
        
        pyqButton.addActionListener(e -> {
            frame.dispose();
            showPYQSelection();
        });
        
        backButton.addActionListener(e -> {
            frame.dispose();
            showHomeDashboard();
        });
        
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    
    private void showPYQSelection() {
        JFrame frame = new JFrame("PYQ Selection - SmartLibraryPortal");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel titleLabel = new JLabel("Select Subject");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(new JLabel("Subject Code:"), gbc);
        
        gbc.gridx = 1;
        JTextField codeField = new JTextField(20);
        panel.add(codeField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Subject Name:"), gbc);
        
        gbc.gridx = 1;
        JTextField nameField = new JTextField(20);
        panel.add(nameField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JLabel infoLabel = new JLabel("<html><i>Available: CHEM F111, BIO F111, CS F213, BIOT F212, HSS F252</i></html>");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        panel.add(infoLabel, gbc);
        
        gbc.gridy = 4;
        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(150, 35));
        panel.add(okButton, gbc);
        
        gbc.gridy = 5;
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 35));
        panel.add(backButton, gbc);
        
        okButton.addActionListener(e -> {
            String code = codeField.getText().trim().toUpperCase();
            String name = nameField.getText().trim();
            
            if (code.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter both Subject Code and Name", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (subjectsMap.containsKey(code)) {
                String expectedName = subjectsMap.get(code);
                if (expectedName.equalsIgnoreCase(name)) {
                    frame.dispose();
                    showPaperList(code, name);
                } else {
                    JOptionPane.showMessageDialog(frame, 
                        "Subject name does not match! Expected: " + expectedName, 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, 
                    "Invalid Subject Code! Please check available subjects.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        backButton.addActionListener(e -> {
            frame.dispose();
            showQuestionPapersSection();
        });
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    
    private void showPaperList(String subjectCode, String subjectName) {
        JFrame frame = new JFrame("Papers - " + subjectName);
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel(subjectName + " (" + subjectCode + ") - Papers");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        String folderName = subjectCode.replace(" ", "_");
        File paperFolder = new File(PYQ_DIR + File.separator + folderName);
        
        if (!paperFolder.exists()) {
            JLabel noFilesLabel = new JLabel("No papers found. Please create folder: " + PYQ_DIR + "/" + folderName);
            gbc.gridx = 0;
            gbc.gridy = 0;
            centerPanel.add(noFilesLabel, gbc);
        } else {
            File[] files = paperFolder.listFiles();
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length && i < 3; i++) {
                    File file = files[i];
                    if (file.isFile()) {
                        JButton paperButton = new JButton("Paper " + (i + 1) + ": " + file.getName());
                        paperButton.setPreferredSize(new Dimension(400, 40));
                        
                        final String paperPath = file.getAbsolutePath();
                        paperButton.addActionListener(evt -> {
                            selectedPaperPath = paperPath;
                            openPaper(paperPath);
                        });
                        
                        gbc.gridx = 0;
                        gbc.gridy = i;
                        centerPanel.add(paperButton, gbc);
                    }
                }
            } else {
                JLabel noFilesLabel = new JLabel("No papers found in folder: " + folderName);
                gbc.gridx = 0;
                gbc.gridy = 0;
                centerPanel.add(noFilesLabel, gbc);
            }
        }
        
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton downloadButton = new JButton("Download Paper as PDF");
        leftPanel.add(downloadButton);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton aiGeneratorButton = new JButton("AI Question Generator");
        rightPanel.add(aiGeneratorButton);

        JPanel centerBottomPanel = new JPanel();
        JButton backButton = new JButton("Back");
        centerBottomPanel.add(backButton);

        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(centerBottomPanel, BorderLayout.CENTER);
        bottomPanel.add(rightPanel, BorderLayout.EAST);

        aiGeneratorButton.addActionListener(e -> {
            if (selectedPaperPath == null || selectedPaperPath.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please select a paper first!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                showAIGenerator(selectedPaperPath);
            }
        });

        downloadButton.addActionListener(e -> {
            if (selectedPaperPath == null || selectedPaperPath.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please select a paper first!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                DownloadPaper.downloadPaperAsPDF(selectedPaperPath);
            }
        });
        
        backButton.addActionListener(e -> {
            frame.dispose();
            showPYQSelection();
        });
        
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    
    private void openPaper(String paperPath) {
        try {
            File paper = new File(paperPath);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(paper);
            } else {
                JOptionPane.showMessageDialog(null, "Paper path: " + paperPath, "Paper Location", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error opening paper: " + ex.getMessage() + "\nPath: " + paperPath, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showAIGenerator(String paperPath) {
        JFrame frame = new JFrame("AI Question Generator");
        frame.setSize(700, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("AI Generated Questions");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        JTextArea questionsArea = new JTextArea();
        questionsArea.setEditable(false);
        questionsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        questionsArea.setLineWrap(true);
        questionsArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(questionsArea);
        
        AIQuestionGenerator generator = new AIQuestionGenerator();
        ArrayList<String> questions = generator.generateQuestions(paperPath);
        
        StringBuilder displayText = new StringBuilder();
        displayText.append("Generated 35 Questions from: ").append(new File(paperPath).getName()).append("\n\n");
        for (String question : questions) {
            displayText.append(question).append("\n");
        }
        questionsArea.setText(displayText.toString());
        
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> frame.dispose());
        
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(closeButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    
}
