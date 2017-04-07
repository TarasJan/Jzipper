/**
 * Created by Janek Taras on 4/3/2017.
 */


import java.awt.*;
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


    private JMenuBar menuBar;
    private JMenuItem about;

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
        main = new JFrame("Jzip");
        main.setSize(new Dimension(200,200));
        main.setResizable(false);
        main.setLayout(new BoxLayout(main.getContentPane(),3));
        main.addWindowListener(this);


        menuBar = new JMenuBar();
        about = new JMenuItem("About");
        about.addActionListener( new aboutItemListener());
        menuBar.add(about);



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


        label = new JLabel("Select a file to unpack",SwingConstants.CENTER);
        headerLabel = new JLabel("Zip/Unzip mode selection",SwingConstants.CENTER);

        main.add(menuBar);

        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        combo.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);


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


    }


    private class aboutItemListener implements  ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            JOptionPane.showMessageDialog(main,"Jzip - simple zip file manager by Jan Taras\nFor royalty free use.");
        }
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
                tf1.setText("");
                tf2.setText("");
                writeMode = true;
            }
            else
            {
                button2.setText("Unpack");
                tf1.setText("");
                tf2.setText("");
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
                    else
                    {
                        JOptionPane.showMessageDialog(main,"No file for unpacking selected!");
                        return;
                    }
                }
                // wybor kilku plikow do archiwom
                else {
                    chooser = new JFileChooser();
                    chooser.setMultiSelectionEnabled(true);
                    //na razie nie mozna
                    //chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                    int returnVal = chooser.showOpenDialog(null);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {

                         files = chooser.getSelectedFiles();
                        tf1.setText(files.toString());
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(main,"No files for packaging selected!");
                        return;
                    }

                }


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
                        else
                        {
                            JOptionPane.showMessageDialog(main,"No destination folder selected!");
                            return;
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
                        else
                        {
                            JOptionPane.showMessageDialog(main,"No destination file selected!");
                            return;
                        }

                    }


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

        Jzip jzip = new Jzip();

    }
}
