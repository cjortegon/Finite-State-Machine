package model;

import java.util.ArrayList;

public class Partition {

	private int partitionId;
	private ArrayList<Node> list;
	
	public Partition(int partitionId) {
		this.partitionId = partitionId;
		this.list = new ArrayList<Node>();
	}

	public void add(Node node) {
		list.add(node);
	}
	
	public void setIdToNodes() {
		for (Node node : list)
			node.groupPointer = partitionId;
	}

	public ArrayList<Node> getList() {
		return list;
	}
	
	public Node getRepresentativeNode() {
		if(list.size() == 0)
			return null;
		else {
			Node representative = list.get(0);
			for (int i = 1; i < list.size(); i++) {
				representative.addAllConnectionsFrom(list.get(i));
				list.get(i).incomingEdges.clear();
				list.get(i).outgoingEdges.clear();
			}
			return representative;
		}
	}
	
	@Override
	public String toString() {
		String toString = "{";
		for (int i = 0; i < list.size(); i++) {
			if(i != 0)
				toString += ",";
			toString += list.get(i).name;
		}
		toString += "}";
		return toString;
	}
	
}
