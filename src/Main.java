import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.io.*;

/*
**************************************** controls ****************************************
ctrl + n - new
ctrl + o - open
ctrl + p - print
esc - escape

JMenuItem newDoc = new JMenuItem("New");
newDoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
// VK_N works for key name like o, p, n, so on
// ActionEvent is what you are using i.e ctrl or something else
// ctrl + N, will call this object newDoc as we added shortcut

**************************************** functions ****************************************
**/

public class Main extends JFrame implements ActionListener {

    JTextArea jTextArea;
    JScrollPane jScrollPane;
    String copiedText;

    Main(){
        setBounds(0,40,1336,730);
        JMenuBar jMenuBar = new JMenuBar();

        // *************************************************************************************************************
        JMenu file = new JMenu("File");
        JMenuItem newDoc = new JMenuItem("New");
        newDoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK)); // ctrl + N, will call this object newDoc as we added shortcut
        newDoc.addActionListener(this);

        JMenuItem open = new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        open.addActionListener(this);

        JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        save.addActionListener(this);

        JMenuItem print = new JMenuItem("Print");
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        print.addActionListener(this);

        JMenuItem exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        exit.addActionListener(this);

        file.add(newDoc);
        file.add(open);
        file.add(save);
        file.add(print);
        file.add(exit);

        // *************************************************************************************************************
        JMenu edit = new JMenu("Edit");
        JMenuItem copy = new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        copy.addActionListener(this);

        JMenuItem cut = new JMenuItem("Cut");
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        cut.addActionListener(this);

        JMenuItem paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        paste.addActionListener(this);

        JMenuItem selectAll = new JMenuItem("Select All");
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        selectAll.addActionListener(this);

        edit.add(copy);
        edit.add(cut);
        edit.add(paste);
        edit.add(selectAll);

        // *************************************************************************************************************
        JMenu help = new JMenu("Help");
        JMenuItem about = new JMenuItem("About Notepad");
        help.add(about);
        about.addActionListener(this);

        // *************************************************************************************************************
        jMenuBar.add(file);
        jMenuBar.add(edit);
        jMenuBar.add(help);

        // *************************************************************************************************************
        jTextArea = new JTextArea();
        jTextArea.setFont(new Font("Consolas", Font.PLAIN, 16));
        jTextArea.setLineWrap(true); jTextArea.setWrapStyleWord(true); // to go to next line automatically

        jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setBorder(BorderFactory.createEmptyBorder()); // to remove border

        // *************************************************************************************************************
        add(jMenuBar);
        setJMenuBar(jMenuBar);
        add(jScrollPane, BorderLayout.CENTER);

    }

    public static void main(String[] args) {
        new Main().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "New":
                jTextArea.setText("");
                break;

            case "Open":
                JFileChooser jFileChooserOpenFile = new JFileChooser();
                jFileChooserOpenFile.setApproveButtonText("open"); // you can change text
                jFileChooserOpenFile.setAcceptAllFileFilterUsed(false); // to not to choose all file filters i.e .txt, .png, etc
                FileNameExtensionFilter fileNameExtensionFilter =
                        new FileNameExtensionFilter("only .txt files","txt");
                jFileChooserOpenFile.addChoosableFileFilter(fileNameExtensionFilter);
                int oi = jFileChooserOpenFile.showOpenDialog(this);
                if (oi == JFileChooser.APPROVE_OPTION){
                    File file = jFileChooserOpenFile.getSelectedFile();
                    try{
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                        jTextArea.read(bufferedReader,null);
                        bufferedReader.close();
                    }catch (Exception exception){exception.printStackTrace();}
                }
                break;

            case "Save":
                JFileChooser jFileChooserSaveData = new JFileChooser();
                jFileChooserSaveData.setApproveButtonText("save"); // you can change text
                int si = jFileChooserSaveData.showOpenDialog(this);
                if (si == JFileChooser.APPROVE_OPTION){
                    File file = new File(jFileChooserSaveData.getSelectedFile() + ".txt");
                    try{
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                        jTextArea.write(bufferedWriter);
                        bufferedWriter.close();
                    }catch (Exception exception){exception.printStackTrace();}
                }
                break;

            case "Print":
                try{
                    jTextArea.print();
                } catch (PrinterException ex) {ex.printStackTrace();}
                break;

            case "Exit":
                System.exit(0);
                break;

            case "Copy":
                copiedText = jTextArea.getSelectedText();
                break;

            case "Cut":
                copiedText = jTextArea.getSelectedText();
                jTextArea.replaceRange("", jTextArea.getSelectionStart(), jTextArea.getSelectionEnd());
                break;

            case "Paste":
                jTextArea.insert(copiedText, jTextArea.getCaretPosition()); // to paste at correct position
                break;

            case "Select All":
                jTextArea.selectAll();
                break;

            case "About Notepad":
                new AboutNotepad().setVisible(true);
                break;

        }
    }
}

class AboutNotepad extends JFrame implements ActionListener {
    AboutNotepad(){
        setBounds(341,96,700,600);
        setBackground(Color.white);
        ImageIcon imageIconWindows =
                new ImageIcon(ClassLoader.getSystemResource("windows.png"));
        Image imageWindows = imageIconWindows.getImage().getScaledInstance(500,80,Image.SCALE_DEFAULT);
        ImageIcon imageIconWindows1 = new ImageIcon(imageWindows);
        JLabel jLabelWindows = new JLabel(imageIconWindows1); // bcz it doesn't take image in label directly
        jLabelWindows.setBounds(80,50,500,80);
        add(jLabelWindows);

        ImageIcon imageIconNotepad =
                new ImageIcon(ClassLoader.getSystemResource("about.png"));
        Image imageNotepad = imageIconNotepad.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT);
        ImageIcon imageIconNotepad1 = new ImageIcon(imageNotepad);
        JLabel jLabelNotepad = new JLabel(imageIconNotepad1); // bcz it doesn't take image in label directly
        jLabelNotepad.setBounds(50,180,100,100);
        add(jLabelNotepad);

        JLabel jLabelAbout = new JLabel("<html>Code For Java Swing<br>Notepad version: 1.0.0<br>All rights reserved,<br>notepad is word processing program,<br>allows changes</html"); // bcz it doesn't take image in label directly
        jLabelAbout.setFont(new Font("Consolas", Font.PLAIN,16));
        jLabelAbout.setBounds(150,220,650,200);
        add(jLabelAbout);

        JButton jButton = new JButton("ok");
        jButton.setBounds(585,520,80,25);
        jButton.addActionListener(this);
        add(jButton);

        setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }
}
