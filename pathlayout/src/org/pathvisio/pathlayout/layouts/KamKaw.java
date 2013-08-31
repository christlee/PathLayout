package org.pathvisio.pathlayout.layouts;

import java.awt.geom.Point2D;
import org.apache.commons.collections15.Transformer;
import org.pathvisio.gui.SwingEngine;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;

public class KamKaw extends JungAbstract{

	KKLayout<String,String> l; 
	Transformer<String,Point2D> in;
	
	
	public KamKaw(SwingEngine swingEngine, boolean selection){
		super(swingEngine,selection);
		
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
