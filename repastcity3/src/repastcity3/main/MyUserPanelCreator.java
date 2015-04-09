package repastcity3.main;

import java.awt.Button;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.environment.RunListener;
import repast.simphony.engine.environment.RunState;
import repast.simphony.ui.RSApplication;
import repast.simphony.userpanel.ui.UserPanelCreator;

/**
 * Custom Controls and Displays Demo Advance Repast Course 2013
 * 
 * User panel creator.
 * 
 * @author jozik
 *
 */
public class MyUserPanelCreator implements UserPanelCreator, RunListener {

	/**
	 * This class eventually gets superseded by the Window Builder WBPanel.
	 * @author jozik
	 *
	 */
/*	class MyPanel extends JPanel{
		
		public JLabel label;
		public Button button;
		
		public MyPanel(){
			label = new JLabel("Hello!");
			add(label);
			button = new Button("Press Me");
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					label.setText("Thanks!");
				}
			});
			add(button);
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
	}*/
	
	WBPanel panel;
	
	@Override
	public JPanel createPanel() {
		RunState.getInstance().getScheduleRegistry().getScheduleRunner().addRunListener(this);
		panel = new WBPanel();
		return panel;
	}

	@Override
	public void stopped() {
		panel.getBtnPressMe().setEnabled(false);		
	}

	@Override
	public void paused() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void started() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restarted() {
		// TODO Auto-generated method stub
		
	}
	
	

}
