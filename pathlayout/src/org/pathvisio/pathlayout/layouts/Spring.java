package org.pathvisio.pathlayout.layouts;

import org.pathvisio.core.preferences.PreferenceManager;
import org.pathvisio.gui.SwingEngine;
import org.pathvisio.pathlayout.LayoutManager.PlPreference;
import org.apache.commons.collections15.Transformer;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import java.awt.geom.Point2D;


public class Spring extends JungAbstract{

	SpringLayout<String,String> l;
	Transformer<String,Point2D> in;
	
	
	public Spring(SwingEngine swingEngine, boolean selection){
		super(swingEngine,selection);
		createDSMultigraph();
		l = new SpringLayout<String,String>( g );
		setDimension(l);
		double fm = Double.parseDouble(PreferenceManager.getCurrent().get(PlPreference.PL_LAYOUT_SPRING_FORCE));
		int rep = Integer.parseInt(PreferenceManager.getCurrent().get(PlPreference.PL_LAYOUT_SPRING_REPULSION));
		double s = Double.parseDouble(PreferenceManager.getCurrent().get(PlPreference.PL_LAYOUT_SPRING_STRETCH));
		l.setForceMultiplier(fm);
		l.setRepulsionRange(rep);
		l.setStretch(s);
		
		
		l.initialize();
		for(int i=0;i<1000;i++){
			l.step();
		}
		
		drawNodes((AbstractLayout<String,String>) l);
		//re-draw the lines
		drawLines();
	}
	
	
}
