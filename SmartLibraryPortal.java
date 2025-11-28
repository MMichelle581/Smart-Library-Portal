// Main Entry Point for SmartLibraryPortal
import javax.swing.*;

public class SmartLibraryPortal {
    
    public static void main(String[] args) {
        try {
            // Set look and feel for better UI
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Launch the GUI Manager
            SwingUtilities.invokeLater(() -> new GUIManager());
            
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.err.println("Error launching application: " + e.getMessage());
            JOptionPane.showMessageDialog(null, 
                "Error launching application: " + e.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
