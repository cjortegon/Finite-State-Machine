package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class ReduceAlgorithm {

	public static String partitionString;

	public static FSM reduce(FSM fsm) {

		// Setting a consecutive for each node
		setConsecutiveNumberToNodes(fsm.nodes);

		// Cloning the nodes
		ArrayList<Node> nodes = new ArrayList<Node>();
		for (int i = 0; i < fsm.nodes.size(); i++)
			nodes.add((Node) fsm.nodes.get(i).clone());

		// Cloning the edges
		HashSet<Edge> edges = new HashSet<Edge>();
		for (Edge edge : fsm.edges) {
			Edge edgeClone = edge.clone(nodes.get(edge.tail.consecutive), nodes.get(edge.head.consecutive));
			edges.add(edgeClone);
			nodes.get(edge.tail.consecutive).outgoingEdges.add(edgeClone);
			nodes.get(edge.head.consecutive).incomingEdges.add(edgeClone);
		}

		// Getting the dictionary
		Object[] dictionary = getDictionary(edges);

		// Building transion's table
		Node[][] transitions = getTransitions(nodes, edges, dictionary);

		// Starting partitions with first lettes
		int groupId = 0;
		LinkedList<Partition> partitions = new LinkedList<Partition>();
		Partition otherStates = new Partition(groupId++);
		Partition finalStates = new Partition(groupId++);

		for (Node node : nodes) {
			if(node.acceptor)
				finalStates.add(node);
			else
				otherStates.add(node);
		}

		// Adding initial partitions
		partitions.add(otherStates);
		partitions.add(finalStates);

		// Setting ids for partition to each node
		for (Partition partition : partitions)
			partition.setIdToNodes();

		// Print partitions
		printPartitions(partitions);

		// Reducing the FSM
		boolean changes = true;
		while(changes) {

			// Starting without changes
			changes = false;

			// Creating a temporal list with partitions
			LinkedList<Partition> newGroups = new LinkedList<Partition>();

			for (int g = 0; g < partitions.size();) {
				ArrayList<Node> list = partitions.get(g).getList();

				// When the partition has elements
				if(list.size() > 0) {

					// If partition size is one the algorithm finishes here
					if(list.size() == 1) {
						g ++;
					}

					else {

						// Starting the list of correspondence
						ArrayList<NodePointer> nodePointerList = new ArrayList<NodePointer>();
						for (Node node : list) {
							String pointer = generatePointerElement(node, transitions[node.consecutive]);
							nodePointerList.add(new NodePointer(node, pointer));
						}

						// Sorting list before using it
						Collections.sort(nodePointerList);

						// According to this list the clasification is made
						int lastIndicator = 0;
						for (int i = 1; i < nodePointerList.size(); i++) {
							if(!nodePointerList.get(i).pointer.equals(nodePointerList.get(i-1).pointer)) {
								newGroups.add(startNewPartition(groupId++, nodePointerList, lastIndicator, i-1));
								lastIndicator = i;
								changes = true;
							}
						}

						// Adding the last partition
						newGroups.add(startNewPartition(groupId++, nodePointerList, lastIndicator, nodePointerList.size()-1));

						// Removint the old partion
						partitions.remove(g);
					}

				}
				// If partition is empty it must be removed
				else {
					partitions.remove(g);
				}
			}

			// Renaming the ids for the nodes in each partition
			for (Partition partition : newGroups)
				partition.setIdToNodes();

			// Adding the new groups
			if(newGroups.size() > 0)
				partitions.addAll(newGroups);

			// Print partitions
			printPartitions(partitions);
		}

		// Starting the reduced FSM
		return getFSMFromPartitions(partitions);
	}

	private static void setConsecutiveNumberToNodes(ArrayList<Node> nodes) {
		for (int i = 0; i < nodes.size(); i++)
			nodes.get(i).consecutive = i;
	}

	private static Object[] getDictionary(Collection<Edge> edges) {

		// Getting the dictionary
		HashSet<String> dictionary = new HashSet<String>();
		for (Edge edge : edges) {
			for (String word : edge.inputWordsArray)
				dictionary.add(word);
		}
		Object[] dic = dictionary.toArray();
		Arrays.sort(dic);

		// Printing the result
		// Imprimiendo el resultado
		System.out.print("Transition's table\n   ");
		for (Object string : dic)
			System.out.print(" "+string);
		System.out.println();

		return dic;
	}

	private static Node[][] getTransitions(List<Node> nodes, Collection<Edge> edges, Object[] dictionary) {

		// Getting the transition's table
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

	private static String generatePointerElement(Node node, Node[] transitions) {
		String pointingElement = "";
		for (Node goTo : transitions) {
			if(goTo == null)
				pointingElement += ".";
			else
				pointingElement += goTo.groupPointer;
		}
		return pointingElement;
	}

	private static void printPartitions(LinkedList<Partition> partitions) {
		partitionString = "{";
		for (Partition partition : partitions) {
			partitionString += partition;
		}
		partitionString += "}";
		System.out.println(partitionString);
	}

	private static Partition startNewPartition(int groupId, ArrayList<NodePointer> list,int from, int until) {
		Partition group = new Partition(groupId);
		for (int i = from; i <= until; i++)
			group.add(list.get(i).node);
		return group;
	}

	private static FSM getFSMFromPartitions(LinkedList<Partition> partitions) {

		// Building the new FSM
		FSM fsm = new FSM();
		for (Partition partition : partitions) {
			Node representative = partition.getRepresentativeNode();
			fsm.nodes.add(representative);
			fsm.edges.addAll(representative.outgoingEdges);
		}

		// Adding the edges to the new FSM
		for (Node node : fsm.nodes)
			fsm.edges.addAll(node.outgoingEdges);

		// Sorting the nodes
		Collections.sort(fsm.nodes);

		return fsm;
	}

}
