package org.pathvisio.pathlayout.layouts;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections15.Transformer;
import org.pathvisio.core.model.ObjectType;
import org.pathvisio.core.model.PathwayElement;
import org.pathvisio.core.preferences.PreferenceManager;
import org.pathvisio.gui.SwingEngine;
import org.pathvisio.pathlayout.PlPlugin.PlPreference;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.BalloonLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.graph.event.GraphEvent.Vertex;

public class Balloon extends JungAbstract{

	BalloonLayout<String,String> l; 
	Transformer<String,Point2D> in;
	
	public Balloon(SwingEngine swingEngine, boolean selection){
		super(swingEngine,selection);
		
		
		createDSMultigraph();
		Forest<String,String> forest = new DelegateForest<String,String>();
		List<PathwayElement> elements = pwy.getDataObjects();
//		List<PathwayElement> lines = new ArrayList<PathwayElement>();
//		for (PathwayElement element : elements){
//			if (element.getObjectType().equals(ObjectType.LINE)){
//				lines.add(element);
//			}
//		}
//		List<PathwayElement> newLines = new ArrayList<PathwayElement>();
//		newLines.addAll(lines);
//		PathwayElement firstline;
//		while(!newLines.isEmpty()){
//			firstline = newLines.get(0);
//			String startgr = firstline.getStartGraphRef();
//			String endgr = firstline.getEndGraphRef();
//			forest.addVertex(startgr);
//			forest.addEdge(firstline.getGraphId(),startgr,endgr);
//			newLines.remove(firstline);
//		
//			for (int i = 1; i<lines.size();i++){
//				PathwayElement line = lines.get(i);
//				if (forest.containsVertex(line.getStartGraphRef()) ||
//						forest.containsVertex(line.getEndGraphRef())){
//					forest.addEdge(line.getGraphId(),line.getStartGraphRef(),line.getEndGraphRef());
//					newLines.remove(line);
//				}
//			}
//		}
		
		
		for (PathwayElement element : elements){
			if (element.getObjectType().equals(ObjectType.LINE)){
				if (!forest.containsVertex(element.getStartGraphRef())){
					forest.addVertex(element.getStartGraphRef());
				}
				if (!forest.containsEdge(element.getEndGraphRef())){
					forest.addVertex(element.getEndGraphRef());
				}
				forest.addEdge(element.getGraphId(), element.getStartGraphRef(),element.getEndGraphRef());
			}
		}
		BalloonLayout.DEFAULT_DISTX=200;
		BalloonLayout.DEFAULT_DISTY=200;
		l = new BalloonLayout<String,String>(forest);
		setDimension((Layout<String,String>) l);
		
		
		for (PathwayElement element : elements){
			if (element.getObjectType().equals(ObjectType.DATANODE)){
				Point2D p = new Point2D.Double(element.getMCenterX(),element.getMCenterY());
				l.setLocation(element.getGraphId(), p);
			}
		}
//		Transformer<String,Point2D> init = new Transformer<String,Point2D>(){
//		    public Point2D transform(String vertex){
//		    	PathwayElement node = pwy.getElementById(String);
//		    	Point2D point = new Point2D.Double(node.getMCenterX(),node.getMCenterY());
//		    	return point;
//	 	    }
//	 	};
//	 	l.setGraph(forest);
//		l.setInitializer(init);
		l.initialize();
	 	
		for (String v : l.getGraph().getVertices()){
			Point2D center = l.transform(v);
			
			double x = center.getX();
			double y = center.getY();
			PathwayElement e = pwy.getElementById(v);
			x = x + e.getMWidth()/2*5;
			y = y + e.getMHeight()/2*5;
			e.setMCenterX(x);
			e.setMCenterY(y);
		}
		//re-draw the lines
		drawLines();
	}

}

	
