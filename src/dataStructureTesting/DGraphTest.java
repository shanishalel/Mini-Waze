package dataStructureTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import dataStructure.dGraph;
import dataStructure.Nodes;
import dataStructure.edge_data;
import dataStructure.node_data;
import utils.Point3D;

class DGraphTest {

	@Test
	void testGetNode() {
		dGraph d = new dGraph();
		int j=1;
		for(int i=1;i<10;i++ ) { //the key can't be 0  
			Point3D p = new Point3D(j,j,j);
			Nodes n=new Nodes(i,p);
			d.addNode(n);
			if(d.getNode(i)!=n) {
				fail("Not the same node");
			}
		}
	}
	
	@Test 
	void testGetNodeNoKey() {
		dGraph d = new dGraph();
		int j=1;
		for(int i=1;i<3;i++ ) { //the key can't be 0  
			Point3D p = new Point3D(j,j,j);
			Nodes n=new Nodes(i,p);
			d.addNode(n);
		}
		if ( d.getNode(11) != null ) {
			fail();
		}
	}
	
	@Test
	void buildSameNode() {
		dGraph d = new dGraph();
		int j=1;
		Point3D p = new Point3D(j,j,j);
		Nodes n=new Nodes(1,p);
		d.addNode(n);
		d.addNode(n);
		assertEquals(d.nodeSize(), 1);
	}

	@Test
	void testGetEdge() {
		dGraph d = new dGraph();
		int j=1;
		for(int i=1;i<10;i++ ) {  
			Point3D p = new Point3D(j,j,j);
			Nodes n=new Nodes(i,p);
			d.addNode(n);
		}

		for(int k=1;k<8;k++) { //connected 1->2->3->4->5->6->7->8->9
			d.connect(k, k+1, k);
			if(d.getEdge(k, k+1)!=d.getEdge(k, k+1)) {
				fail("Not the same Edge");
			}

			try {
				d.getEdge(k+1, k);
			}
			catch(Exception e) {

			}
		}

	}
	
	@Test
	void testAddNode() {
		dGraph d = new dGraph();
		int j=1;
		Point3D p = new Point3D(j, j, j);
		for (int i =1 ; i<=1000000 ;i++){
			Nodes n = new Nodes(i , p);
			d.addNode(n);
		}
		if (!(d.nodeSize() == 1000000)) {
			fail();
		}
	}

	@Test
	void testConnect() {
		dGraph d = new dGraph();
		int j=1;
		for (int i =1 ; i<=10 ;i++){
			Point3D p = new Point3D(j, j, j);
			Nodes n = new Nodes(i , p);
			d.addNode(n);
		}
		for(int i =2 ; i <=d.nodeSize() ;i++) {
			d.connect(1, i, i);
			try {
				d.getEdge(1, i);
			}
			catch(Exception e) {
				fail(); //shouldn't throw exception
			}
		}
		if(d.edgeSize()!=9) {
			fail();
		}
	}
	
	@Test 
	void testCoonectSameSrcDest() {
		dGraph d = new dGraph();
		int j=1;
		Point3D p = new Point3D(j, j, j);
		for (int i =1 ; i<=3 ;i++){
			Nodes n = new Nodes(i , p);
			d.addNode(n);
		}
		d.connect(1, 2, 3);
		assertEquals(d.getEdge(1, 2).getWeight(), 3);
		d.connect(1, 2, 4);
		assertEquals(d.getEdge(1, 2).getWeight(), 4);
	}

	@Test
	void testGetV() {
		dGraph d = new dGraph();
		int j=1;
		for (int i =1 ; i<=10 ;i++){
			Point3D p = new Point3D(j, j, j);
			Nodes n = new Nodes(i , p );
			d.addNode(n);
		}
		dGraph g = new dGraph();
		for (int i =1 ; i<=10 ;i++){
			Point3D p = new Point3D(j, j, j);
			Nodes n = new Nodes(i , p );
			g.addNode(n);
		}
		//ask is the m and n should be equals
		Collection<node_data>n=g.getV();
		Collection<node_data>m=d.getV();
		if(!n.equals(g.getV())){
			fail();		
		}
		if(!m.equals(d.getV())){
			fail();
		}


	}

	@Test
	void testGetE() {
		dGraph d = new dGraph();
		int j=1;
		for (int i =1 ; i<10 ;i++){
			Point3D p = new Point3D(j, j, j);
			Nodes n = new Nodes(i , p );
			d.addNode(n);
		}
		for(int i =1 ; i <d.nodeSize() ;i++) {
			d.connect(1, i, i);
		}
		dGraph g = new dGraph();
		for (int i =1 ; i<10 ;i++){
			Point3D p = new Point3D(j, j, j);
			Nodes n = new Nodes(i , p );
			g.addNode(n);
		}
		//connect key 1 to all the edge
		for(int i =1 ; i <g.nodeSize() ;i++) {
			g.connect(1, i, i);
		}
		//ask if n should be equals to m 

		Collection<edge_data>n=g.getE(1);
		Collection<edge_data>m=d.getE(1);
		if(!n.equals(g.getE(1))){
			fail();		
		}
		if(!m.equals(d.getE(1))){
			fail();
		}

	}

	@Test
	void testRemoveNode() {
		dGraph d = new dGraph();
		int j=1;
		for (int i =1 ; i<10 ;i++){
			Point3D p = new Point3D(j, j, j);
			Nodes n = new Nodes(i , p );
			d.addNode(n);
		}
		for(int i =1 ; i <d.nodeSize() ;i++) {
			d.connect(1, i, i);
		}
		d.removeNode(1);
		try {
			d.getNode(1);
		}catch(Exception e){
			//should catch an exception
		}
		int catchs=0;
		for(int i =1 ; i <d.nodeSize() ;i++) {
			try {
				d.getEdge(1, i);
			}catch(Exception e) {
				catchs++;//should catch an exception 
			}
		}
		if(catchs!=(d.nodeSize()-1)) {
			fail();
		}
		
	}

	@Test
	void testRemoveEdge() {
		dGraph d = new dGraph();
		int j=1;
		for (int i =0 ; i<10 ;i++){
			Point3D p = new Point3D(j, j, j);
			Nodes n = new Nodes(i , p);
			d.addNode(n);
		}
		for(int i =0 ; i <d.nodeSize() ;i++) {
			if ( d.nodeSize() > 1+i) {
			d.connect(i, i+1, i);
			}
		}
		d.removeEdge(1, 2);
		if (d.edgeSize() != 8 ) {
			fail();
		}
		d.removeEdge(1, 3);
		if (d.edgeSize() != 8 ) {
			fail();
		}
	}
	
	@Test
	void testRemoveNodeEdge() {
		dGraph d = new dGraph();
		int j=1;
		Point3D p = new Point3D(j, j, j);
		for (int i =1 ; i<=6 ;i++){
			Nodes n = new Nodes(i , p);
			d.addNode(n);
			}
		d.connect(1, 2, 1);
		d.connect(2, 3, 7);
		d.connect(3, 4, 2);
		d.connect(5, 4, 5);
		d.connect(2, 5, 1);
		d.connect(4, 6 ,7);
		assertEquals(d.edgeSize(), 6);
		d.removeNode(2);
		assertEquals(d.edgeSize(), 3);
		
	}

	@Test
	void testNodeSize() {
		dGraph d = new dGraph();
		int j=1;
		for (int i =1 ; i<10 ;i++){
			Point3D p = new Point3D(j, j, j);
			Nodes n = new Nodes(i , p );
			d.addNode(n);
		}
		if (d.nodeSize() != 9) {
			fail();
		}
	}

	@Test
	void testEdgeSize() {
		dGraph d = new dGraph();
		int j=1;
		for (int i =1 ; i<10 ;i++){
			Point3D p = new Point3D(j, j, j);
			Nodes n = new Nodes(i , p);
			d.addNode(n);
		}
		for(int i =2 ; i <=d.nodeSize() ;i++) {
			d.connect(1, i, i);
		}
		//1->2, 1->3, 1->4, 1->5, 1->6, 1->7, 1->8, 1->9
		if(d.edgeSize()!=8) {
			fail();
		}
	}

	@Test
	void testGetMC() {
		dGraph d = new dGraph();
		int j=1;
		Point3D p = new Point3D(j, j, j);
		for (int i = 0; i < 6; i++) {
			Nodes n = new Nodes(i , p);
			d.addNode(n);
		}
		assertEquals(d.getMC(), 6);
		d.connect(0, 1, 1);
		d.connect(2, 0, 1);
		d.connect(1, 2, 1);
		d.connect(2, 3, 7);
		d.connect(3, 4, 2);
		d.connect(5, 4, 5);
		d.connect(2, 5, 1);
		d.connect(4, 5 ,7);
		assertEquals(d.getMC(), 14);
		d.removeEdge(4, 5);
		d.removeNode(0);
		assertEquals(d.getMC(), 16);
	}

}
