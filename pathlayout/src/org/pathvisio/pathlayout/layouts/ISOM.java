package org.pathvisio.pathlayout.layouts;

import java.awt.Dimension;
import java.awt.geom.Point2D;

import org.apache.commons.collections15.Transformer;
import org.pathvisio.core.model.ObjectType;
import org.pathvisio.core.model.PathwayElement;
import org.pathvisio.core.preferences.PreferenceManager;
import org.pathvisio.gui.SwingEngine;
import org.pathvisio.pathlayout.PlPlugin.PlPreference;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;

public class ISOM extends JungAbstract{

	ISOMLayout<String,String> l; 
	Transformer<String,Point2D> in;
	
	
	public ISOM(SwingEngine swingEngine, boolean selection){
		super(swingEngine,selection);
		
		createDSMultigraph();
		l = new ISOMLayout<String,String>( g );
		setDimension(l);
		l.initialize();
		while(!l.done()){
			l.step();
		}
		
		drawNodes((AbstractLayout<String,String>) l);
		//re-draw the lines
		drawLines();
	}

}
