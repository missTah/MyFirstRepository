package repastcity3.main;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.JTextField;

import HelloKML.export;
import repast.simphony.context.Context;
import repast.simphony.engine.environment.GUIRegistry;
import repast.simphony.engine.environment.RunState;
import repast.simphony.essentials.RepastEssentials;
import repast.simphony.util.ContextUtils;
import repast.simphony.visualization.IDisplay;
import repastcity3.agent.AgentFactory;

import java.awt.BorderLayout;

/**
 * Custom Controls and Displays Demo Advance Repast Course 2013
 * 
 * Window builder panel.
 * 
 * @author jozik
 *
 */
public class WBPanel extends JPanel {
	private JLabel lblHello;
	private JTextField txtTextPanel;
	private JButton btnPressMe;

	/**
	 * Create the panel.
	 */
	public WBPanel() {
		
		btnPressMe = new JButton("Export into KML");
		btnPressMe.setBounds(5, 50, 200, 25);
		btnPressMe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblHello.setText("Thanks!");
				Context context = RepastEssentials.FindContext("UserPanelProject");
				/*Iterable<MyAgent> iter = context.getObjects(MyAgent.class);
				
				for (MyAgent ma : iter){
					ma.setHappyLevel(ma.getHappyLevel() + 1);
				}*/
				
				/*Deserialise d=new Deserialise(AgentFactory.CompteurStudent);
				try {
					d.GoDeserialise();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				
				
				GUIRegistry guiRegistry = RunState.getInstance().getGUIRegistry();
				for (IDisplay display : guiRegistry.getDisplays()){
					display.update();
					display.render();
				}
			}
		});
		setLayout(null);
		
		lblHello = new JLabel("Hello!");
		lblHello.setBounds(0, 0, 134, 16);
		//add(lblHello);
		add(btnPressMe);
		
		txtTextPanel = new JTextField();
		txtTextPanel.setBounds(67, 164, 134, 28);
		txtTextPanel.setText("text panel");
		//add(txtTextPanel);
		txtTextPanel.setColumns(10);

		addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorRemoved(AncestorEvent event) {
			}
			
			@Override
			public void ancestorMoved(AncestorEvent event) {
			}
			
			@Override
			public void ancestorAdded(AncestorEvent event) {
				Container ancestor = event.getAncestor();
				ancestor.revalidate();
				ancestor.repaint();
			}
		});
	}

	protected JLabel getLblHello() {
		return lblHello;
	}
	public JButton getBtnPressMe() {
		return btnPressMe;
	}
}
