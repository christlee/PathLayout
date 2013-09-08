PathLayout
==========

seperate package for layout managment in PathVisio

Currently the following layout algorithms are supported:

Balloon:
  A Layout implementation that assigns positions to vertices using associations with nested circles. (source: jung)
Fruchterman-Reingold:
  The Fruchterman-Rheingold algorithm. (source: jung)
ISOM:
  Meyer’s “Self-Organizing Map” layout. (source: jung)
Kamada-Kawai:
  The Kamada-Kawai algorithm for node layout. (source: jung)
Spring:
  A simple force-directed spring-embedder. (source: jung)
Prefuse:
  Force-Directed layout algorithm of the Prefuse software package. (source: prefuse)
 

build the jar file, or download the jar file at: https://docs.google.com/file/d/0BwqNKFmHlwYkUmhPM0UzU3Vxakk/edit. 
copy the following jar files, depending on the source, from the lib folder of PathLayout to the lib folder of your own project:
jung:
- jung-api-2.0.1.jar
- jung-graph-impl-2.0.1.jar
- jung-algorithms-2.0.1.jar
- collections-generic-4.01.jar
prefuse:
- prefuse.jar
Add the downloaded jars to your lib folder
Use the layout enumerator from the LayoutManager class to select and instantiate your layouts
