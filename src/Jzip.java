/**
 * Created by Janek Taras on 4/3/2017.
 */


import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Jzip implements WindowListener {

    private JFrame main;
    private JButton button;
    private JButton button2;
    private JLabel label;
    private JTextField tf1;
    private JTextField tf2;

    public Jzip()
    {

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
        button = new JButton("Select");
        button2 = new JButton("Unpack");
        button.setActionCommand("Select");
        button2.setActionCommand("Unpack");


        label = new JLabel("Select a file to unpack");
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


    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if( source == button)  {
                label.setText("Specify output folder.");
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "ZIP archives", "zip");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(main);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    tf1.setText(chooser.getSelectedFile().getAbsolutePath());
                    main.pack();
                }
            }
            else if( source == button2 )  {
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
                        main.pack();
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


    public static void main(String[] args)
    {
        Jzip jzip = new Jzip();

    }
}
