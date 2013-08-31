package org.pathvisio.pathlayout.layouts;

import java.awt.geom.Point2D;
import org.apache.commons.collections15.Transformer;
import org.pathvisio.core.preferences.PreferenceManager;
import org.pathvisio.gui.SwingEngine;
import org.pathvisio.pathlayout.LayoutManager.PlPreference;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;

public class FruchtRein extends JungAbstract{

	FRLayout<String,String> l; 
	Transformer<String,Point2D> in;
	
	
	public FruchtRein(SwingEngine swingEngine, boolean selection){
		super(swingEngine,selection);
		
		createDSMultigraph();
		l = new FRLayout<String,String>( g );
		setDimension(l);
		
		double att = Double.parseDouble(PreferenceManager.getCurrent().get(PlPreference.PL_LAYOUT_FR_ATTRACTION));
		double rep = Double.parseDouble(PreferenceManager.getCurrent().get(PlPreference.PL_LAYOUT_FR_REPULSION));
		l.setAttractionMultiplier(att);
		l.setRepulsionMultiplier(rep);
		
		l.initialize();
		for(int i=0;i<1000;i++){
			l.step();
		}
		
		drawNodes((AbstractLayout<String,String>) l);
		//re-draw the lines
		drawLines();
	}
}
