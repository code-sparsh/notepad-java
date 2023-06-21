import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Notepad {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Notepad");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        StringBuilder fileName = new StringBuilder("");

        // Create the navigation bar panel
        JPanel navBarPanel = new JPanel(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem openMenuItem = new JMenuItem("Open a file");
        JMenuItem saveAsMenuItem = new JMenuItem("Save as");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        fileMenu.add(openMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.add(exitMenuItem);

        JMenu viewMenu = new JMenu("View");

        JMenuItem zoomInMenuItem = new JMenuItem("Zoom in");
        JMenuItem zoomOutMenuItem = new JMenuItem("Zoom out");

        viewMenu.add(zoomInMenuItem);
        viewMenu.add(zoomOutMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        navBarPanel.add(menuBar, BorderLayout.WEST);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem feedbackItem = new JMenuItem("Send feedback");
        JMenuItem aboutItem = new JMenuItem("About");

        helpMenu.add(feedbackItem);
        helpMenu.add(aboutItem);

        menuBar.add(helpMenu);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton themeToggleBtn = new JButton("Toggle Theme");
        themeToggleBtn.setBackground(Color.WHITE);

        JButton saveButton = new JButton("Save");
        saveButton.setBackground(new Color(76, 175, 80));
        saveButton.setForeground(Color.BLACK);
        saveButton.setFont(new Font("Arial", 0, 15));

        panel.add(saveButton);
        panel.add(themeToggleBtn);

        navBarPanel.add(panel);

        // Create the text area
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Arial", 0, 20));

        frame.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                textArea.requestFocus();
            }
        });

        JScrollPane scrollPane = new JScrollPane(textArea);

        // Create the main panel and set its layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(navBarPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add the main panel to the frame
        frame.add(mainPanel);

        // event listeners
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // no file currently open
                if (fileName.length() == 0) {
                    String f = JOptionPane.showInputDialog(navBarPanel, "Save as new file - Enter the name: ");

                    // input cancelled through GUI
                    if (f == null)
                        return;

                    FileCreator creator = new FileCreator(f);
                    if (creator.getResult() == false) {
                        JOptionPane.showMessageDialog(null,
                                "Error: Could not create a new file on the system. Please try again");
                        return;
                    }

                    fileName.append(f);
                    frame.setTitle(fileName.toString());
                }

                else {
                    FileSaver saver = new FileSaver(fileName.toString());
                    saver.save(textArea.getText());
                }
            }
        });

        // saveAs button click
        saveAsMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                String f = JOptionPane.showInputDialog(navBarPanel, "Save as new file - Enter the name: ");

                // input cancelled through GUI
                if (f == null)
                    return;

                FileCreator creator = new FileCreator(f);
                if (creator.getResult() == false) {
                    JOptionPane.showMessageDialog(null,
                            "Error: Could not create a new file on the system. Please try again");
                    return;
                }

                FileSaver saver = new FileSaver(f);
                saver.save(textArea.getText());

                fileName.setLength(0);
                fileName.append(saver.getFileName().toString());
                frame.setTitle(fileName.toString());
            }
        });

        openMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String f = JOptionPane.showInputDialog(navBarPanel, "Open a file - Enter the name: ");

                // input cancelled through GUI
                if (f == null)
                    return;

                FileOpener opener = new FileOpener(f);

                // couldn't open the file
                if (opener.getResult() == false) {
                    return;
                }

                fileName.setLength(0); // emptying the StringBuilder
                fileName.append(f.toString()); // setting the StringBuilder to the name of file opened

                frame.setTitle(f);
                textArea.setText(opener.getFileContent());
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // exit the application immediately
                System.exit(0);
            }
        });

        feedbackItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "You can reach out to us via following emails -: " + System.lineSeparator()
                                + "- sparshsethi15@gmail.com" + System.lineSeparator() + "- sonalikunwar50@gmail.com");
            }
        });

        aboutItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Notepad v1.0" + System.lineSeparator()
                                + "This is the first release of our Notepad written completely in Java"
                                + System.lineSeparator() + System.lineSeparator() + "Made with love by - "
                                + System.lineSeparator() + System.lineSeparator() + "Sonali" + System.lineSeparator()
                                + "Sparsh Sethi");
            }
        });

        zoomInMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int currentSize = textArea.getFont().getSize();
                textArea.setFont(new Font("Arial", 0, currentSize + 5));
            }
        });

        zoomOutMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int currentSize = textArea.getFont().getSize();
                textArea.setFont(new Font("Arial", 0, currentSize - 5));
            }
        });

        // mouse hover color change
        themeToggleBtn.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
                themeToggleBtn.setBackground(Color.LIGHT_GRAY);
            }

            public void mouseExited(MouseEvent e) {
                themeToggleBtn.setBackground(Color.WHITE);
            }
        });


        saveButton.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
                saveButton.setBackground(new Color( 10, 219, 73
));
            }

            public void mouseExited(MouseEvent e) {
                saveButton.setBackground(new Color(76, 175, 80));
            }
        });




        // dark/light theme
        themeToggleBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (textArea.getBackground().equals(Color.WHITE)) {
                    textArea.setBackground(new Color(43, 43, 51));
                    textArea.setForeground(new Color(251, 251, 254));
                }

                else {
                    textArea.setBackground(Color.WHITE);
                    textArea.setForeground(null);
                }
            }
        });

        frame.setVisible(true);
    }
}
