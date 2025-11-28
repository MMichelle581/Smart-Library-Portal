import java.awt.Desktop;
import java.io.*;
import javax.swing.*;

public class DownloadPaper {
    
    /**
     * Downloads a paper file to the user's Downloads folder as PDF and opens it
     * @param paperPath Path to the paper file to download
     */
    public static void downloadPaperAsPDF(String paperPath) {
        try {
            File sourceFile = new File(paperPath);
            String userHome = System.getProperty("user.home");
            File downloadsDir = new File(userHome, "Downloads");
            
            // Create Downloads folder if it doesn't exist
            if (!downloadsDir.exists()) {
                downloadsDir.mkdirs();
            }
            
            // Generate PDF filename
            String fileName = sourceFile.getName();
            String baseName = fileName.contains(".") ? 
                fileName.substring(0, fileName.lastIndexOf(".")) : fileName;
            String pdfFileName = baseName + ".pdf";
            File destinationFile = new File(downloadsDir, pdfFileName);
            
            // Handle duplicate filenames
            int counter = 1;
            while (destinationFile.exists()) {
                pdfFileName = baseName + "_" + counter + ".pdf";
                destinationFile = new File(downloadsDir, pdfFileName);
                counter++;
            }
            
            // Make final for use in lambda
            final File finalDestinationFile = destinationFile;
            
            // Read source file and create PDF
            StringBuilder content = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(sourceFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }
            
            // Write PDF with proper format
            try (FileOutputStream fos = new FileOutputStream(finalDestinationFile)) {
                // Simple PDF header
                String pdfContent = createSimplePDF(baseName, content.toString());
                fos.write(pdfContent.getBytes());
            }
            
            JOptionPane.showMessageDialog(null, 
                "Paper downloaded as PDF successfully!\n\nLocation: " + finalDestinationFile.getAbsolutePath(),
                "Download Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Open the downloaded PDF file
            if (Desktop.isDesktopSupported()) {
                new Thread(() -> {
                    try {
                        Thread.sleep(500); // Give time for dialog to close
                        Desktop.getDesktop().open(finalDestinationFile);
                    } catch (IOException | InterruptedException ex) {
                        JOptionPane.showMessageDialog(null, 
                            "PDF created but could not open automatically.\nLocation: " + finalDestinationFile.getAbsolutePath(),
                            "Info",
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                }).start();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, 
                "Error downloading paper: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Creates a simple PDF format from text content
     */
    private static String createSimplePDF(String title, String content) {
        StringBuilder pdf = new StringBuilder();
        
        // PDF Header
        pdf.append("%PDF-1.4\n");
        
        // Catalog object
        pdf.append("1 0 obj\n");
        pdf.append("<< /Type /Catalog /Pages 2 0 R >>\n");
        pdf.append("endobj\n");
        
        // Pages object
        pdf.append("2 0 obj\n");
        pdf.append("<< /Type /Pages /Kids [3 0 R] /Count 1 >>\n");
        pdf.append("endobj\n");
        
        // Page object
        pdf.append("3 0 obj\n");
        pdf.append("<< /Type /Page /Parent 2 0 R /MediaBox [0 0 612 792] /Contents 4 0 R /Resources << /Font << /F1 5 0 R >> >> >>\n");
        pdf.append("endobj\n");
        
        // Content stream
        pdf.append("4 0 obj\n");
        String contentStream = "BT /F1 12 Tf 50 750 Td (" + escapePDFText(title) + ") Tj 0 -20 Td ";
        String[] lines = content.split("\n");
        for (String line : lines) {
            if (line.length() > 100) {
                line = line.substring(0, 100) + "...";
            }
            contentStream += "(" + escapePDFText(line) + ") Tj 0 -15 Td ";
        }
        contentStream += "ET";
        
        pdf.append("<< /Length ").append(contentStream.length()).append(" >>\n");
        pdf.append("stream\n");
        pdf.append(contentStream).append("\n");
        pdf.append("endstream\n");
        pdf.append("endobj\n");
        
        // Font object
        pdf.append("5 0 obj\n");
        pdf.append("<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>\n");
        pdf.append("endobj\n");
        
        // xref table
        pdf.append("xref\n");
        pdf.append("0 6\n");
        pdf.append("0000000000 65535 f\n");
        pdf.append("0000000009 00000 n\n");
        pdf.append("0000000058 00000 n\n");
        pdf.append("0000000115 00000 n\n");
        pdf.append("0000000279 00000 n\n");
        pdf.append("0000000581 00000 n\n");
        
        // Trailer
        pdf.append("trailer\n");
        pdf.append("<< /Size 6 /Root 1 0 R >>\n");
        pdf.append("startxref\n");
        pdf.append("0\n");
        pdf.append("%%EOF\n");
        
        return pdf.toString();
    }
    
    /**
     * Escapes special characters for PDF text
     */
    private static String escapePDFText(String text) {
        if (text == null) return "";
        return text.replace("\\", "\\\\")
                   .replace("(", "\\(")
                   .replace(")", "\\)")
                   .replace("\n", " ")
                   .replace("\r", " ");
    }
}
