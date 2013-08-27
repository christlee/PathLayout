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
import edu.uci.ics.jung.algorithms.layout.KKLayout;

public class KamKaw extends LayoutAbstract{

	KKLayout<String,String> l; 
	Transformer<String,Point2D> in;
	
	
	KamKaw(SwingEngine swingEngine){
		super.swingEngine = swingEngine;
		super.pwy = swingEngine.getEngine().getActivePathway();
		
		createDSMultigraph();
		l = new KKLayout<String,String>( g );
		setDimension(l);

		l.setAdjustForGravity(false);
		l.setExchangeVertices(false);
		l.setLengthFactor(0.6);
		l.setDisconnectedDistanceMultiplier(1);
		
		
		l.initialize();
		while(!l.done()){
			l.step();
		}
		
		drawNodes((AbstractLayout<String,String>) l);
		//re-draw the lines
		drawLines();
	}

}
