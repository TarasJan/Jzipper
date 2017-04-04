/**
 * Created by Janek Taras on 4/3/2017.
 */
import java.io.FileWriter;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;

public class Jzip {

    private Frame main;
    private Button button;
    private Button button2;
    private Label label;
    private TextField tf1;
    private TextField tf2;

    public Jzip()
    {

        setupGUI();
        //
    }

    private void setupGUI()
    {
        main = new Frame("Java Zip Unpacker");
        main.setSize(400,400);
        main.setLayout(new GridLayout(3, 2));
        main.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        tf1 = new TextField();
        tf2 = new TextField();
        tf1.setEditable(false);
        tf2.setEditable(false);
        button = new Button("Select");
        button2 = new Button("Unpack");
        button.setActionCommand("Select");
        button2.setActionCommand("Unpack");


        label = new Label("Select a file to unpack");
        main.add(tf1);
        main.add(button);
        main.add(tf2);
        main.add(button2);
        main.add(label);
        main.setVisible(true);
        button.addActionListener(new ButtonClickListener());
        button2.addActionListener(new ButtonClickListener());

    }


    private class ButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if( command.equals( "Select" ))  {
                label.setText("Specify output folder.");
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "ZIP archives", "zip");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(main);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    tf1.setText(chooser.getSelectedFile().getAbsolutePath());
                }
            }
            else if( command.equals( "Unpack" ) )  {
                if(tf1.getText().equals("")) {
                    JOptionPane.showMessageDialog(main,
                            "Select an archive first!!!");
                    return;
                }
                if(tf2.getText().equals(""))
                {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    int returnVal = chooser.showOpenDialog(main);
                    if(returnVal == JFileChooser.APPROVE_OPTION) {
                        tf2.setText(chooser.getSelectedFile().getAbsolutePath());
                    }
                }

                String sourceFile = tf1.getText();
                String destinationFolder = tf2.getText()+"\\";

                try
                {
                    Unzipper.unzip(sourceFile,destinationFolder);
                }
                catch(Exception exe)
                {
                    JOptionPane.showMessageDialog(main,
                            "Extraction failed!!!");
                }
                JOptionPane.showMessageDialog(main,
                        "Extraction successful!!!");
                label.setText("Archive unpacked.");

            }
            else  {
                label.setText("Select a file to unpack");
            }
        }
    }

/* I was trying to create something different at first :)
    private void generateHTML()
    {
        File f = new File("content.html");
        try {
            FileWriter writer = new FileWriter(f);
            writer.close();
        }
        catch(IOException e)
        {
            System.out.println("Unable to generate HTML. Aborting the program.");
            System.exit(1);
        }


    }
*/
    public static void main(String[] args)
    {
        Jzip jzip = new Jzip();

    }
}
