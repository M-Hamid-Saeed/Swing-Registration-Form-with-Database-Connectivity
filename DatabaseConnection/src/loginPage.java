
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Formatter;
import java.util.Scanner;
import java.sql.*;

public class loginPage extends JFrame {
    JLabel l1,l2;
    JTextField username;
    JPasswordField password;
    JButton loginButton,signupButton,exit;
    Formatter file;
    String user;
    String pass;
    String ConnectionURL = "jdbc:sqlserver://DESKTOP-R529PSP;databaseName=loginDatabase;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
    boolean flag=false;
    Statement stmt=null;
    
    public loginPage()  {
        super("github.com/M-Hamid-Saeed");
        
        Font f = new Font("Serif",Font.BOLD,30);
        JPanel heading = new JPanel(new GridLayout(0,1));
        heading.setBackground(new Color(0,0,80));
        
        heading.setBounds(0,0,900,100);
        JLabel name = new JLabel("WELCOME",SwingConstants.CENTER);
        //JLabel follow = new JLabel("\nFollow me on: github.com/M-Hamid-Saeed");
        JLabel followLabel = new JLabel("Follow me on: github.com/M-Hamid-Saeed", SwingConstants.CENTER);
        name.setForeground(Color.WHITE);
        followLabel.setForeground(Color.WHITE);
        //follow.setBounds(200,50,400,50);
        name.setFont(f);
        heading.add(name);
        heading.add(followLabel);
        
        //loginpanel
        JPanel login = new JPanel();
        login.setLayout(null);
        login.setSize(400,350);
        login.setBackground(new Color(0,0,0,60));
        login.setBounds(250, 175, 400, 350);
        username = new JTextField("  Enter Username");
        username.setBounds(50,50,300,50);
        username.setBackground(new Color(220,180,140));
        login.add(username);
        
        password = new JPasswordField();
        password.setBounds(50,150,300,50);
        password.setBackground(new Color(210,180,140));
        login.add(password);
        
        loginButton = new JButton("Login");
        loginButton.setBounds(50,250,100,50);
        loginButton.setBackground(new Color(160,182,45));
        login.add(loginButton);
        
        signupButton = new JButton("Signup");
        signupButton.setBounds(155,250,100,50);
        signupButton.setBackground(new Color(160,182,45));
        login.add(signupButton);
        
        exit= new JButton("Exit");
        exit.setBounds(260,250,100,50);
        exit.setBackground(new Color(160,182,45));
        login.add(exit);
        add(login);
 
        setSize(900,600);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //background
        ImageIcon back_image = new ImageIcon("bg.jpg");
        Image img= back_image.getImage();
        Image temp_img = img.getScaledInstance(900, 600, Image.SCALE_SMOOTH);
        back_image = new ImageIcon(temp_img);
        JLabel background = new JLabel("",back_image,JLabel.CENTER);
        background.add(heading);
        background.setBounds(0,0,900,600);
        add(background);
       // add(exit);
        setVisible(true);
        
        //Anonymous class
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	user = username.getText();
                pass= password.getText().toString();
            	if(isrecordfound(user,pass)) 
            		JOptionPane.showMessageDialog(null, "Login Confirmed");
            	else 
            		JOptionPane.showMessageDialog(null, "USername/Pass incorrect");
            }       
        });
        //########################################
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	user = username.getText();
                pass= password.getText().toString();
            	
            	 String query = String.format("INSERT INTO dbo.records (UserName, Password) VALUES ('%s', '%s')",user,pass);
                 try{  
                     stmt=connectingDB();
                     if(!isrecordfound(user,pass)) {
                     JOptionPane.showMessageDialog(null, "Account created");
                     stmt.execute(query); //execute query
                     }
                     else
                    	 JOptionPane.showMessageDialog(null, "Account Already Exist");
                 } catch (Exception e1) {
					e1.printStackTrace();}

        		}
        
        });
        //##################################################
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//showconfirmdialog will return
            	 // 0=yes, 1=no, 2=cancel
                System.exit(JOptionPane.showConfirmDialog(null, "Are You Sure?"));
            }       
        });
  
    }
    //################################################
    private boolean isrecordfound(String user,String pass) {
    	
    	String queryString = String.format("SELECT 1 FROM dbo.records WHERE UserName='%s' AND Password='%s' ",user,pass);
    	
		try {
			 stmt=connectingDB();
		     ResultSet results = stmt.executeQuery(queryString);
		     if(results.next()) 
		    	 flag=true; 
		     else flag=false;
		     }
		
		catch (Exception ep) {
			    System.out.println("Error " + ep.getMessage()); }			
		return flag; }
    //##########################################################
    
    private Statement connectingDB() throws Exception {
    	Connection con = DriverManager.getConnection(ConnectionURL);
        Statement stmt = con.createStatement();
        System.out.println("COnnected");
    	return stmt;
    }
    public static void main (String args[]) {
    	
		  new loginPage();

	}
}