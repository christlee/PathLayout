package org.pathvisio.pathlayout;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.pathvisio.core.preferences.Preference;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.pathvisio.desktop.PvDesktop;
import org.pathvisio.desktop.plugin.Plugin;
import org.pathvisio.pathlayout.layouts.Balloon;
import org.pathvisio.pathlayout.layouts.FruchtRein;
import org.pathvisio.pathlayout.layouts.ISOM;
import org.pathvisio.pathlayout.layouts.KamKaw;
import org.pathvisio.pathlayout.layouts.Prefuse;
import org.pathvisio.pathlayout.layouts.Spring;

public class PlPlugin implements Plugin, BundleActivator {

	private static String PLUGIN_NAME = "PathLayout";
	private static String FRLAYOUT = "FRLayout";
	private static String KKLAYOUT = "KKLayout";
	private static String SPRINGLAYOUT = "SpringLayout";
	private static String BALLAYOUT = "BalloonLayout";
	private static String ISOMLAYOUT = "ISOMLayout";
	private static String PREF = "Preferences";
	private static String PREFUSE = "prefuse";
	
	
	public static enum Action
	{
		FR,
		KK,
		SPRING,
		ISOM,
		BAL,
		PREF,
		PREFUSE;
	}
	
	private selectAction springAction;
	private selectAction frAction;
	private selectAction kkAction;
	private selectAction ISOMAction;
	private selectAction prefAction;
	private selectAction BalAction;
	private selectAction prefuse;
	private PvDesktop desktop;
	private JMenu subMenu;
	private static BundleContext context;
	private JFrame frame;

	@Override
	public void init(PvDesktop desktop) {
		this.desktop = desktop;
		springAction = new selectAction(Action.SPRING);
		frAction = new selectAction(Action.FR);
		kkAction = new selectAction(Action.KK);
		ISOMAction = new selectAction(Action.ISOM);
		BalAction = new selectAction(Action.BAL);
		prefAction = new selectAction(Action.PREF);
		prefuse = new selectAction(Action.PREFUSE);
		JMenuItem prefMenu = new JMenuItem(PREF);
		prefMenu.addActionListener(prefAction);
		prefMenu.add(new JSeparator());
		
		subMenu = new JMenu();
		subMenu.setText(PLUGIN_NAME);
		subMenu.add(springAction);
		subMenu.add(frAction);
		subMenu.add(kkAction);
		subMenu.add(ISOMAction);
		subMenu.add(BalAction);
		subMenu.add(prefuse);
		subMenu.add(prefMenu);
		desktop.registerSubMenu("Plugins", subMenu);
		
	}
	
	private class selectAction extends AbstractAction {
		private Action action;

		selectAction(Action action)
		{
			this.action = action;
			switch(action)
			{
			case FR:
				putValue(NAME,FRLAYOUT);
				break;
			case SPRING:
				putValue(NAME,SPRINGLAYOUT);
				break;
			case KK:
				putValue(NAME,KKLAYOUT);
				break;
			case ISOM:
				putValue(NAME,ISOMLAYOUT);
				break;
			case BAL:
				putValue(NAME,BALLAYOUT);
				break;
			case PREF:
				putValue(NAME,PREF);
				break;
			case PREFUSE:
				putValue(NAME,PREFUSE);
				break;
			}
			
		}
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			switch(action)
			{
			case FR:
				new FruchtRein(desktop.getSwingEngine(),false);
				break;
			case SPRING:
				new Spring(desktop.getSwingEngine(),false);
				break;
			case KK:
				new KamKaw(desktop.getSwingEngine(),false);
				break;
			case ISOM:
				new ISOM(desktop.getSwingEngine(),false);
				break;
			case BAL:
				new Balloon(desktop.getSwingEngine(),false);
				break;
			case PREF:
				preferenceWindow();
				break;
			case PREFUSE:
				new Prefuse(desktop.getSwingEngine(),false);
				break;
			}
		}
		
	}
	
	void preferenceWindow(){
		frame = new JFrame("Build pathway");
	
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(new PrefWindow(frame), BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(desktop.getFrame());
		frame.setVisible(true);
	}
	
	
	public static enum PlPreference implements Preference
	{
		PL_LAYOUT_FR_ATTRACTION("0.5"),
		PL_LAYOUT_FR_REPULSION("1"),
		PL_LAYOUT_SPRING_FORCE("0.33"),
		PL_LAYOUT_SPRING_REPULSION("100"),
		PL_LAYOUT_SPRING_STRETCH("0.7");
		
	
		private final String defaultVal;
		
		PlPreference (String _defaultVal) { defaultVal = _defaultVal; }
		
		@Override
		public String getDefault() { return defaultVal; }
	}

	
	static BundleContext getContext() {
		return context;
	}
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception 
	{
		context.registerService(Plugin.class.getName(), this, null);	
	}

	@Override
	public void stop(BundleContext context) throws Exception 
	{
		done();
	}
	@Override
	public void done() {
		desktop.unregisterSubMenu("Plugins", subMenu);
		
	}
}
