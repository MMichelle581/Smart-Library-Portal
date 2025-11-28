# SmartLibraryPortal

## Java Desktop Application for BITS Pilani Hyderabad Campus

### Project Structure

```
/app/
├── SmartLibraryPortal.java       # Main entry point
├── GUIManager.java                # All GUI windows (Swing)
├── AIQuestionGenerator.java       # AI question generation
├── books.txt                      # Books data
├── users.txt                      # User credentials (auto-created)
├── PYQ/                          # Previous Year Questions
│   ├── CHEM_F111/
│   ├── BIO_F111/
│   ├── CS_F213/
│   ├── BIOT_F212/
│   └── HSS_F252/
└── (Similar Paper Finder removed in this build)
```

### Subjects Available

1. Chemistry - CHEM F111
2. Biology - BIO F111
3. OOP - CS F213
4. Microbiology - BIOT F212
5. International Law - HSS F252

### How to Run

1. **Compile all Java files:**
   ```bash
   javac SmartLibraryPortal.java GUIManager.java AIQuestionGenerator.java
   ```

2. **Run the application:**
   ```bash
   java SmartLibraryPortal
   ```

### Setup Instructions

1. Create the PYQ folder structure:
   - Create folders: PYQ/CHEM_F111, PYQ/BIO_F111, PYQ/CS_F213, PYQ/BIOT_F212, PYQ/HSS_F252
   - Add 3 paper files in each folder (paper1.txt, paper2.txt, paper3.txt or PDFs)

2. Similar paper finder: This feature was removed from the current build; no similarPapers/ folder required.

### Features

- **User Authentication**: Signup and Login with validation
- **Books Section**: View and browse e-books (sorted alphabetically)
- **PYQ Browser**: Browse previous year question papers by subject
- **AI Question Generator**: Generate 35 similar questions from any paper
-- (Removed) Similar Paper Finder: this feature has been removed from the current build

### Java Concepts Used

- **OOP**: Inheritance, Encapsulation, Abstraction, Polymorphism
- **Collections**: ArrayList, LinkedList, HashMap
- **Interfaces**: Comparable
- **File Handling**: FileReader, FileWriter, BufferedReader
- **String Processing**: StringTokenizer, StringBuffer
- **GUI**: Java Swing (JFrame, JPanel, JButton, etc.)
- **Exception Handling**: try-catch blocks
- **Control Structures**: Nested loops, switch-case, if-else

### Password Requirements

- Minimum 6 characters
- At least 1 symbol (@, #, $, %, !)
- At least 2 digits

### Notes

- The application uses file-based storage (users.txt)
- Books are automatically sorted alphabetically
- All GUI windows are managed in GUIManager.java
- Simple and clean code structure for easy understanding
