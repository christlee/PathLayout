package org.pathvisio.pathlayout;

import org.pathvisio.core.preferences.PreferenceManager;
import org.pathvisio.gui.SwingEngine;
import org.pathvisio.pathlayout.PlPlugin.PlPreference;
import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;

import java.awt.Dimension;
import java.awt.geom.Point2D;


public class Spring extends LayoutAbstract{

	SpringLayout<String,String> l;
	Transformer<String,Point2D> in;
	
	
	Spring(SwingEngine swingEngine){
		super.swingEngine = swingEngine;
		super.pwy = swingEngine.getEngine().getActivePathway();
		createDSMultigraph();
		l = new SpringLayout<String,String>( g );
		Dimension d = new Dimension(800,600);
		l.setSize(d);
		
		System.out.println("forceMultiplier: " + l.getForceMultiplier());
		System.out.println("RepulsionRange: " + l.getRepulsionRange());
		System.out.println("Stretch : " + l.getStretch());
		
		double fm = Double.parseDouble(PreferenceManager.getCurrent().get(PlPreference.PL_LAYOUT_SPRING_FORCE));
		int rep = Integer.parseInt(PreferenceManager.getCurrent().get(PlPreference.PL_LAYOUT_SPRING_REPULSION));
		double s = Double.parseDouble(PreferenceManager.getCurrent().get(PlPreference.PL_LAYOUT_SPRING_STRETCH));
		l.setForceMultiplier(fm);
		l.setRepulsionRange(rep);
		l.setStretch(s);
		
		
		l.initialize();
		for(int i=0;i<10000;i++){
			l.step();
		}
		
		drawNodes((AbstractLayout<String,String>) l);
		//re-draw the lines
		drawLines();
	}
	
	
}
