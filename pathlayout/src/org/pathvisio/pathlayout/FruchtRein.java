package org.pathvisio.pathlayout;

import java.awt.Dimension;
import java.awt.geom.Point2D;

import org.apache.commons.collections15.Transformer;
import org.pathvisio.core.model.ObjectType;
import org.pathvisio.core.model.PathwayElement;
import org.pathvisio.core.preferences.PreferenceManager;
import org.pathvisio.gui.SwingEngine;
import org.pathvisio.pathlayout.PlPlugin.PlPreference;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;

public class FruchtRein extends LayoutAbstract{

	FRLayout<String,String> l; 
	Transformer<String,Point2D> in;
	
	
	FruchtRein(SwingEngine swingEngine){
		super.swingEngine = swingEngine;
		super.pwy = swingEngine.getEngine().getActivePathway();
		
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
