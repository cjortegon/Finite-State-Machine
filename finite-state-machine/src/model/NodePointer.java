package model;

public class NodePointer implements Comparable {

	public Node node;
	public String pointer;

	public NodePointer(Node node, String pointer) {
		super();
		this.node = node;
		this.pointer = pointer;
	}

	@Override
	public int compareTo(Object o) {
		NodePointer other = (NodePointer)o;
		return pointer.compareTo(other.pointer);
	}

	@Override
	public String toString() {
		return node.name + " -> " + pointer;
	}
}
