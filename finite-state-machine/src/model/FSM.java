package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

public class FSM {

	public ArrayList<Node> nodes;
	public HashSet<Edge> edges;
	public String partitionString;
	public File file;
	public String tmpName;
	public boolean modified;

	public FSM() {
		modified = false;
		this.nodes = new ArrayList<Node>();
		this.edges = new HashSet<Edge>();
	}

	public void addEdge(Edge edge) {
		modified = true;
		for (Edge otherEdge : edges) {
			if(otherEdge.tail == edge.tail && otherEdge.head == edge.head) {
				otherEdge.mergeInputWords(edge.inputWordsArray);
				return;
			}
		}
		edges.add(edge);
		edge.tail.outgoingEdges.add(edge);
		edge.head.incomingEdges.add(edge);
	}

	public void remove(Node nodo) {
		modified = true;
		Iterator<Edge> it = edges.iterator();
		while(it.hasNext()) {
			Edge a = it.next();
			if(a.head.equals(nodo) || a.tail.equals(nodo))
				it.remove();
		}
		nodes.remove(nodo);
	}

	public FSM reduce() {

		FSM reduced = ReduceAlgorithm.reduce(this);
		this.partitionString = ReduceAlgorithm.partitionString;

		return reduced;

	}

	private void setConsecutiveNumberToNodes() {
		for (int i = 0; i < nodes.size(); i++)
			nodes.get(i).consecutive = i;
	}

	private Object[] getDictionary() {

		// Getting the dictionary
		HashSet<String> dictionary = new HashSet<String>();
		for (Edge edge : edges) {
			for (String word : edge.inputWordsArray)
				dictionary.add(word);
		}
		Object[] dic = dictionary.toArray();
		Arrays.sort(dic);

		// Printing the result
		System.out.print("Transition table\n   ");
		for (Object string : dic)
			System.out.print(" "+string);
		System.out.println();

		return dic;
	}

	private Node[][] getTransitions(Object[] dictionary) {

		// Getting the transition table
		Node[][] transitions = new Node[nodes.size()][dictionary.length];
		for (Edge edge : edges) {
			for (String word : edge.inputWordsArray)
				transitions[edge.tail.consecutive][Arrays.binarySearch(dictionary, word)] = edge.head;

		}

		// Printing the result
		for (int i = 0; i < transitions.length; i++) {
			System.out.print(nodes.get(i).name + " |");
			for (int j = 0; j < transitions[i].length; j++)
				System.out.print(" " + (transitions[i][j] != null ? transitions[i][j].name : " "));
			System.out.println();
		}

		return transitions;
	}

	public void printTranslateTable() {

		// Setting a consecutive to the nodes
		setConsecutiveNumberToNodes();

		// Getting the dictionary
		Object[] dictionary = getDictionary();

		// Building transition's table
		Node[][] transitions = getTransitions(dictionary);

	}

	@Override
	public Object clone() {

		FSM fsm = new FSM();
		setConsecutiveNumberToNodes();
		for (Node node : nodes)
			fsm.nodes.add((Node) node.clone());

		for (Edge edge : edges)
			fsm.addEdge(new Edge(edge.inputWordsString, fsm.nodes.get(edge.tail.consecutive), fsm.nodes.get(edge.head.consecutive)));

		return fsm;
	}

	@Override
	public String toString() {
		if(file != null)
			return file.getName();
		else
			return tmpName;
	}

}
