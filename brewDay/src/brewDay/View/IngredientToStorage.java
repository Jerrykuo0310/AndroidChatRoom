package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import brewDay.Brew;
import brewDay.Database;
import brewDay.Recipe;
import brewDay.RecipeIngredient;
import brewDay.StorageIngredient;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import javax.swing.JTextField;

public class IngredientToStorage extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JButton btnBack;
	private JLabel lblNewLabel;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IngredientToStorage frame = new IngredientToStorage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public IngredientToStorage() throws SQLException {
		
		setTitle("Storage Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 484);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(54, 53, 345, 183);
		contentPane.add(scrollPane);
		
		Vector<String> columnName = new Vector<String>();
		Vector<Vector<Object>> dataVector = new
		Vector<Vector<Object>>();
		columnName.add("name");
		columnName.add("amount");
		columnName.add("unit");
		
		ResultSet rs= StorageIngredient.AllStorageIngredient();
		
		while(rs.next()){
		Vector<Object> vec = new Vector<Object>();//single for big Vector
		for(int i=2;i<=4;i++){
		vec.add(rs.getObject(i));
		}
		dataVector.add(vec);
		}
		
		table = new JTable(dataVector, columnName);
		scrollPane.add(table.getTableHeader());
		scrollPane.add(table);	
		scrollPane.setViewportView(table);
		
		JButton button = new JButton("Back");
		button.setForeground(new Color(30, 144, 255));
		button.setFont(new Font("Tahoma", Font.PLAIN, 13));
		button.setBounds(12, 12, 68, 29);
		contentPane.add(button);
		
		button.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        	dispose();

        	JFrame home;
			home = new MainIngredient();

     	home.setLocation(100,50);
     	home.setSize(600, 500);
     	home.setVisible(true);
        	}

        	});
	
		
		textField = new JTextField();
		textField.setBounds(200, 274, 130, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblIngredientName = new JLabel("<html>Ingredient name:</html>");
		lblIngredientName.setBounds(92, 271, 121, 33);
		contentPane.add(lblIngredientName);
		
		JLabel lblingredeintAmount = new JLabel("<html>Ingredeint amount:</html>");
		lblingredeintAmount.setBounds(92, 297, 121, 33);
		contentPane.add(lblingredeintAmount);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(210, 300, 120, 26);
		contentPane.add(textField_1);
		textField_1.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();				
				if((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)|| (keyChar == '.')){
					
				}else{
					e.consume();
				}
			}
		});
		JButton btnSubmit = new JButton("Add");
		btnSubmit.setForeground(new Color(50, 205, 50));
		btnSubmit.setBackground(new Color(255, 255, 255));
		btnSubmit.setBounds(159, 423, 117, 29);
		contentPane.add(btnSubmit);
		
		btnSubmit.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		int mark1 = 0;
				int mark2 =0;
				int mark3=0;
				int mark4=1;
        		
				if (textField.getText().trim().equals("")) {
					String messege = "You must input name!";
					JFrame win = new PromptWindow(messege);
					win.setLocation(500, 80);
					win.setSize(400, 200);
					win.setVisible(true);
					mark1 = 1;
				}
				else if (textField_1.getText().trim().equals("")) {
					String messege = "You must input quantity!";
					JFrame win = new PromptWindow(messege);
					win.setLocation(500, 80);
					win.setSize(400, 200);
					win.setVisible(true);
					mark2 = 1;
				}
				else if(textField.getText().trim().equals("water")||textField.getText().trim().equals("malts")||textField.getText().trim().equals("hops")||textField.getText().trim().equals("yeasts")||textField.getText().trim().equals("sugars")||textField.getText().trim().equals("additives"))
				{
					mark4 = 0;
				}
				else if(mark4==1) {
					String messege = "Ingredient not exit!";
					JFrame win = new PromptWindow(messege);
					win.setLocation(500, 80);
					win.setSize(400, 200);
					win.setVisible(true);
					mark3 = 1;
				}
					if(mark1 ==0 && mark2 == 0&&mark3==0&&mark4==0) {
					String name = textField.getText();
					String quan = textField_1.getText();
					float quantity = Float.parseFloat(quan);
					String unit;
					if(textField.getText().trim().equals("water")) {
						unit = "L";
					}
					else {
						unit = "g";
					}
					StorageIngredient stin = new StorageIngredient(name,quantity,unit);
				try {
					stin.addIngredient(name, quantity, unit);
					dispose();
					JFrame main;
					main = new MainIngredient();
					
		        	main.setLocation(100,50);
		        	main.setSize(600, 500);
		        	main.setVisible(true);
					String messege="Success.";
					JFrame win = new PromptWindow(messege);
					win.setLocation(500, 80);
					win.setSize(400, 200);
					win.setVisible(true);
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
        	}

        	});
	
		
		JLabel lblNewLabel_1 = new JLabel("<html>Fill in the blank for the ingredient that you want to add</html>");
		lblNewLabel_1.setBounds(64, 243, 335, 29);
		contentPane.add(lblNewLabel_1);
		
		JLabel label = new JLabel("<html>When you add ingredient, the system will automatically add units. \n\t\tL for water, \n\t\tg for malts, \n\t\tg for hops, \n\t\tg for yeasts, \n\t\tg for sugars, \n\t\tg for additives</html>");
		label.setFont(new Font("Dialog", Font.BOLD, 14));
		label.setBackground(Color.GREEN);
		label.setBounds(64, 328, 303, 83);
		contentPane.add(label);
		
	}
}
