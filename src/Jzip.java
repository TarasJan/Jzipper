/**
 * Created by Janek Taras on 4/3/2017.
 */


import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Jzip implements WindowListener {

    private JFrame main;
    private JButton button;
    private JButton button2;
    private JLabel label;
    private JLabel headerLabel;
    private JTextField tf1;
    private JTextField tf2;


    private File[] files ;

    private JComboBox<String> combo;

    private boolean writeMode;

    public Jzip()
    {
        files = null;
        writeMode = false;
        setupGUI();

    }


    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent){
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    private void setupGUI()
    {
        main = new JFrame("Java Zip Unpacker");
        main.setLayout(new BoxLayout(main.getContentPane(),3));
        main.addWindowListener(this);
        tf1 = new JTextField();
        tf2 = new JTextField();
        tf1.setEditable(false);
        tf2.setEditable(false);

        String[] options = {"Unzip","Zip"};
        combo = new JComboBox<String>(options);
        combo.setSelectedIndex(0);
        combo.addActionListener(new ComboBoxListener());

        button = new JButton("Select");
        button2 = new JButton("Unpack");
        button.setActionCommand("Select");
        button2.setActionCommand("Unpack");


        label = new JLabel("Select a file to unpack");
        headerLabel = new JLabel("Zip/Unzip mode selection");

        main.add(headerLabel);
        main.add(combo);
        main.add(tf1);
        main.add(button);
        main.add(tf2);
        main.add(button2);
        main.add(label);
        main.setVisible(true);
        button.addActionListener(new ButtonClickListener());
        button2.addActionListener(new ButtonClickListener());
        main.pack();

    }


    private class ComboBoxListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox cb = (JComboBox)e.getSource();
            String state = (String) cb.getSelectedItem();
            if(state.equals("Zip"))
            {
                button2.setText("Compress");
                writeMode = true;
            }
            else
            {
                button2.setText("Unpack");
                writeMode = false;
            }
        }
    }

    private class ButtonClickListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {

            Object source = e.getSource();
            if( source == button) {
                label.setText("Specify output folder.");
                JFileChooser chooser = new JFileChooser();
                if (!writeMode) {
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(
                            "ZIP archives", "zip");
                    chooser.setFileFilter(filter);
                    int returnVal = chooser.showOpenDialog(main);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        tf1.setText(chooser.getSelectedFile().getAbsolutePath());

                    }
                }
                // wybor kilku plikow do archiwom
                else {
                    chooser = new JFileChooser();
                    chooser.setMultiSelectionEnabled(true);
                    int returnVal = chooser.showOpenDialog(null);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        System.out.println(chooser.getSelectedFiles());
                         files = chooser.getSelectedFiles();
                        tf1.setText(files.toString());
                    }

                }

                main.pack();
            }
            else if( source == button2 )  {
                if(tf1.getText().equals("")) {
                    JOptionPane.showMessageDialog(main,
                            "Select a file first!!!");
                    return;
                }
                if(tf2.getText().equals(""))
                {
                    if(!writeMode) {
                        JFileChooser chooser = new JFileChooser();
                        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        int returnVal = chooser.showOpenDialog(main);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            tf2.setText(chooser.getSelectedFile().getAbsolutePath());
                        }
                    }
                    else
                    {
                        JFileChooser chooser = new JFileChooser();
                        chooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
                        int returnVal = chooser.showSaveDialog(main);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            tf2.setText(chooser.getSelectedFile().getAbsolutePath());
                        }

                    }
                    main.pack();

                }

                try
                {

                    if(!writeMode) {
                        String sourceFile = tf1.getText();
                        String destinationFolder = tf2.getText() + "\\";
                        Zipper.unzip(sourceFile,destinationFolder);
                    }
                    else
                    {
                        String destinationFile = tf2.getText();
                        System.out.println("Writing reached");
                        System.out.println(destinationFile);
                        //if(files.equals(null))System.out.println("GÓWNO");
                        Zipper.zip(files,destinationFile);
                    }



                }
                catch(Exception exe)
                {
                    JOptionPane.showMessageDialog(main,
                            "Operation failed!!!");
                    exe.printStackTrace();
                }
                JOptionPane.showMessageDialog(main,
                        "Operation successful!!!");
                label.setText("Operation successful!!!");

            }
            else  {
                label.setText("Select a file to unpack");
            }
        }


    }


    public static void main(String[] args)
    {
        File[] f = {new File("src\\Jzip.java")};
        Zipper.zip(f,"Lol.zip");
        Jzip jzip = new Jzip();

    }
}
