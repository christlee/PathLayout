package org.pathvisio.pathlayout.layouts;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.pathvisio.core.model.ObjectType;
import org.pathvisio.core.model.Pathway;
import org.pathvisio.core.model.PathwayElement;
import org.pathvisio.gui.SwingEngine;

import prefuse.Visualization;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.data.Node;
import prefuse.data.tuple.TableNode;
import prefuse.util.force.DragForce;
import prefuse.util.force.ForceItem;
import prefuse.util.force.ForceSimulator;
import prefuse.util.force.NBodyForce;
import prefuse.util.force.SpringForce;
import prefuse.visual.VisualItem;

public class Prefuse extends LayoutAbstract{
	
	public int numIterations = 100;
	public float defaultSpringCoefficient = 1e-4f;
	public float defaultSpringLength = 100.0f;
	public double defaultNodeMass = 3.0;
	public boolean isDeterministic;

	public Prefuse(SwingEngine swingEngine, boolean selection){
		super(swingEngine,selection);
		ForceDirectedLayout l = new ForceDirectedLayout("Layout");
		ForceSimulator f = new ForceSimulator();
		f.addForce(new NBodyForce());
		f.addForce(new SpringForce());
		f.addForce(new DragForce());
		Map<String,ForceItem> nodes = new HashMap<String,ForceItem>();
		for (PathwayElement pe: pwyNodes){
			ForceItem item = new ForceItem();
			item.location[0] = (float) pe.getMCenterX();
			item.location[1] = (float) pe.getMCenterY();
			nodes.put(pe.getGraphId(),item);
			f.addItem(item);
		}
		
		for (PathwayElement pe: pwyLines){
			f.addSpring(nodes.get(pe.getStartGraphRef()), nodes.get(pe.getEndGraphRef()), defaultSpringCoefficient, defaultSpringLength);
		}
		
		l.setForceSimulator(f);
		long timestep = 1000L;
		for (int i=0;i<numIterations; i++){
			timestep *= (1.0 - i/(double)numIterations);
			long step = timestep+50;
			f.runSimulator(step);
		}
		Map<String,Point2D> points = new HashMap<String,Point2D>();
		for (Entry<String,ForceItem> e : nodes.entrySet()){
			points.put(e.getKey(), new Point2D.Float(e.getValue().location[0], e.getValue().location[1]));
		}
		setLocations(points);
		drawLines();
	}

}
