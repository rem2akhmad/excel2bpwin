/*
 * Created on 06.11.2005
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.blackhorse.ui;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.blackhorse.converting.sibsac.SibSACModelManager;
import com.blackhorse.modeling.idef0.IDEF0MixtureLayout;
import com.blackhorse.modeling.sibsac.IDEF0SibSACFullModelBuilder;
/**
 * @author ra
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GUIMain extends JFrame {

	private javax.swing.JPanel jContentPane = null;
	private JButton btOpenSource = null;
	private JTextPane jTextPane = null;
	private JTextField jTextField = null;
	private JLabel jLabel = null;
	private JTextField jTextField1 = null;
	private JButton btOpenReceiver = null;
	private JLabel jLabel1 = null;
	private JButton jButton = null;
	JTextField jTextField2 = null;
	private JLabel jLabel3 = null;
	JTextField jTextField3 = null;
	private JLabel jLabel2 = null;
	JComboBox jComboBox = null;
	private JLabel jLabel4 = null;
	private JTextField jTextField4 = null;
	private JLabel jLabel5 = null;
	private JCheckBox jCheckBox = null;
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtOpenSource() {
		if (btOpenSource == null) {
			btOpenSource = new JButton();
			btOpenSource.setLocation(425, 35);
			btOpenSource.setSize(50, 22);
			btOpenSource.setName("");
			btOpenSource.setText("...");
			btOpenSource.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
				    JFileChooser fc = new JFileChooser();
				    if (fc.showOpenDialog(GUIMain.this) == JFileChooser.APPROVE_OPTION) {
				        jTextField.setText(fc.getSelectedFile().toString());    
				    }
				}
			});
		}
		return btOpenSource;
	}
	/**
	 * This method initializes jTextPane	
	 * 	
	 * @return javax.swing.JTextPane	
	 */    
	private JTextPane getJTextPane() {
		if (jTextPane == null) {
			jTextPane.setBounds(56, 68, 10, 21);
		}
		return jTextPane;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setLocation(15, 35);
			jTextField.setSize(392, 22);
		}
		return jTextField;
	}
	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setLocation(15, 85);
			jTextField1.setSize(392, 22);
		}
		return jTextField1;
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtOpenReceiver() {
		if (btOpenReceiver == null) {
			btOpenReceiver = new JButton();
			btOpenReceiver.setLocation(425, 85);
			btOpenReceiver.setSize(50, 22);
			btOpenReceiver.setText("...");
			btOpenReceiver.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
				    JFileChooser fc = new JFileChooser();
				    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				    if (fc.showDialog(GUIMain.this, null) == JFileChooser.APPROVE_OPTION) {
				        jTextField1.setText(fc.getSelectedFile().toString());    
				    }

				}
			});
		}
		return btOpenReceiver;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("Пуск");
			jButton.setLocation(15, 279);
			jButton.setSize(84, 22);
			jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
				    String fileSource = jTextField.getText().trim();
				    String dirOut = jTextField1.getText().trim();
				    if (fileSource.length() > 0 && dirOut.length() > 0) {
				        SibSACModelManager manager = new SibSACModelManager();
				        // TODO добавть проверку instanceof для mb
				        // TODO провести рефакторинг этого кода
				        IDEF0SibSACFullModelBuilder mb = (IDEF0SibSACFullModelBuilder) manager.getSibBuilder();
				        mb.setAuthor(jTextField2.getText().trim());
				        mb.setProjectName(jTextField3.getText().trim());
				        mb.setStatus((String)jComboBox.getSelectedItem());
				        mb.setProjectNameByModel(jCheckBox.isSelected());
				        // получение разместителя
				        IDEF0MixtureLayout ml =  (IDEF0MixtureLayout) manager.getLayouter();
				        ml.setCNumberPrefix(jTextField4.getText().trim());
				        
					    try {
                            manager.readModels(fileSource);                            
                            int i = 0;
                            int cnt = manager.modelsCount();
                            while (manager.hasNextModel()) {                                
                                manager.writeNextModel(dirOut);
                                i++;
                            }
                        } catch (IOException e1) {
                            System.out.println(e1);
                        }
				    }
				}
			});
		}
		return jButton;
	}
	/**
	 * This method initializes jTextField2	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setSize(392, 22);
			jTextField2.setLocation(15, 135);
		}
		return jTextField2;
	}
	/**
	 * This method initializes jTextField3	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setSize(392, 22);
			jTextField3.setLocation(15, 185);
		}
		return jTextField3;
	}
	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(273, 235, 137, 22);
			jComboBox.setMaximumRowCount(4);
			jComboBox.setName("");
			jComboBox.addItem(new String("WORKING"));
			jComboBox.addItem(new String("DRAFT"));
			jComboBox.addItem(new String("RECOMMENDED"));
			jComboBox.addItem(new String("PUBLICATION"));
		}
		
		return jComboBox;
	}
	/**
	 * This method initializes jTextField4	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setSize(182, 22);
			jTextField4.setLocation(15, 235);
			jTextField4.setText("F");
		}
		return jTextField4;
	}
	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */    
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(165, 164, 243, 21);
			jCheckBox.setText("из модели");
			jCheckBox.setName("");
			jCheckBox.setSelected(false);
			
		}
		return jCheckBox;
	}
               public static void main(String[] args) {
   
		GUIMain application = new GUIMain();
		application.show();
 }

	/**
	 * This is the default constructor
	 */
	public GUIMain() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setResizable(false);
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		this.setSize(495, 335);
		this.setContentPane(getJContentPane());
		this.setTitle("excel2bpwin");
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel2 = new JLabel();
			jLabel3 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setText("Файл-источник");
			jLabel.setLocation(15, 13);
			jLabel.setSize(392, 22);
			jLabel1.setText("Папка-приемник");
			jLabel1.setLocation(15, 63);
			jLabel1.setSize(392, 22);
			jLabel3.setText("Автор");
			jLabel3.setSize(85, 22);
			jLabel3.setLocation(15, 113);
			jLabel2.setText("Название проекта");
			jLabel2.setSize(131, 22);
			jLabel2.setLocation(15, 163);
			jLabel4.setBounds(273, 213, 137, 22);
			jLabel4.setText("Статус");
			jLabel5.setText("Префикс для CNumber");
			jLabel5.setSize(141, 22);
			jLabel5.setLocation(15, 213);
			jContentPane.add(getBtOpenSource(), null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(getBtOpenReceiver(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJTextField2(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getJTextField3(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJComboBox(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getJTextField4(), null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getJCheckBox(), null);
		}
		return jContentPane;
	}
}  //  @jve:decl-index=0:visual-constraint="17,13"
