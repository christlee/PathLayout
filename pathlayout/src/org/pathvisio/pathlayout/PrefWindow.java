package org.pathvisio.pathlayout;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.pathvisio.core.preferences.PreferenceManager;
import org.pathvisio.desktop.PvDesktop;
import org.pathvisio.pathlayout.PlPlugin.PlPreference;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class PrefWindow extends JPanel implements ActionListener{

	private JTextField frAtt;
	private JTextField frRep;
	private JTextField springRep;
	private JTextField springForce;
	private JTextField springStretch;
	private JButton ok;
	private JButton cancel;
	private JFrame frame;
	private static String OK = "ok";
	private static String CANCEL = "cancel";
	
	PrefWindow(JFrame frame){
		this.frame = frame;
		FormLayout frLayout = new FormLayout(
				"4dlu, pref, 4dlu, pref, 4dlu",
				"4dlu, pref, 4dlu, pref, 4dlu");
		FormLayout springLayout = new FormLayout(
				"4dlu, pref, 4dlu, pref, 4dlu",
				"4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu");
		
		
		CellConstraints cc = new CellConstraints();
		Border etch = BorderFactory.createEtchedBorder();
		
		JPanel frPanel = new JPanel();
		JPanel springPanel = new JPanel();
		
		frPanel.setBorder(BorderFactory.createTitledBorder(etch,"FRLayout:"));
		frPanel.setLayout(frLayout);
		
		frPanel.add(new JLabel("Attraction Multiplier:"), cc.xy(2,2));
		frPanel.add(new JLabel("Repulsion Multiplier:"), cc.xy(2, 4));
		
		frAtt = new JTextField();
		frRep = new JTextField();
		frAtt.setText(PreferenceManager.getCurrent().get(PlPreference.PL_LAYOUT_FR_ATTRACTION));
		frRep.setText(PreferenceManager.getCurrent().get(PlPreference.PL_LAYOUT_FR_REPULSION));
		
		frPanel.add(frAtt,cc.xy(4, 2));
		frPanel.add(frRep,cc.xy(4, 4));
		
		springPanel.setBorder(BorderFactory.createTitledBorder(etch,"FRLayout:"));
		springPanel.setLayout(springLayout);
		
		springPanel.add(new JLabel("Repulsion Range:"), cc.xy(2, 2));
		springPanel.add(new JLabel("Force Multiplier:"), cc.xy(2, 4));
		springPanel.add(new JLabel("Stretch:"), cc.xy(2, 6));
		
		springRep = new JTextField();
		springForce = new JTextField();
		springStretch = new JTextField();
		springRep.setText(PreferenceManager.getCurrent().get(PlPreference.PL_LAYOUT_SPRING_REPULSION));
		springForce.setText(PreferenceManager.getCurrent().get(PlPreference.PL_LAYOUT_SPRING_FORCE));
		springStretch.setText(PreferenceManager.getCurrent().get(PlPreference.PL_LAYOUT_SPRING_STRETCH));
		
		springPanel.add(springRep, cc.xy(4,2));
		springPanel.add(springForce,cc.xy(4, 4));
		springPanel.add(springStretch, cc.xy(4, 6));
		
		
		ok = new JButton(OK);
		ok.addActionListener(this);
		ok.setActionCommand(OK);
		
		cancel = new JButton(CANCEL);
		cancel.addActionListener(this);
		cancel.setActionCommand(CANCEL);
		
		Box okCancel = Box.createHorizontalBox();
		okCancel.add(cancel);
		okCancel.add(ok);
		
		Box total = Box.createVerticalBox();
		total.add(frPanel);
		total.add(springPanel);
		total.add(okCancel);
		add(total, BorderLayout.CENTER);
	}
	
	double checkInput(String input){
		try {
			return Double.parseDouble(input);
		} catch (NumberFormatException e) {
		
	    return -1;
		}
	}
	
	public void showMessageDialog(String message) 
	{
		JOptionPane.showMessageDialog(this, message, message, JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand()=="ok"){
			List<String> input = new ArrayList<String>();
			input.add(frAtt.getText());
			input.add(frRep.getText());
			input.add(springRep.getText());
			input.add(springForce.getText());
			input.add(springStretch.getText());
			for (String s : input){
				double check = checkInput(s);
				if (check<=0){
					showMessageDialog("Incorrect input!");
					return;
				}
			}
			
			PreferenceManager.getCurrent().set(PlPreference.PL_LAYOUT_FR_ATTRACTION, frAtt.getText());
			PreferenceManager.getCurrent().set(PlPreference.PL_LAYOUT_FR_REPULSION, frRep.getText());
			PreferenceManager.getCurrent().set(PlPreference.PL_LAYOUT_SPRING_REPULSION, springRep.getText());
			PreferenceManager.getCurrent().set(PlPreference.PL_LAYOUT_SPRING_FORCE, springForce.getText());
			PreferenceManager.getCurrent().set(PlPreference.PL_LAYOUT_SPRING_STRETCH, springStretch.getText());
			frame.dispose();
		}
		else if (e.getActionCommand()=="cancel"){
			frame.dispose();
		}
	}
	
	
}
