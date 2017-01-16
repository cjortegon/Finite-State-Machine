package test;

import java.util.Collection;

import model.Edge;
import model.FSM;
import model.Node;
import model.ReduceAlgorithm;

public class Main {

	private static final int A = 0;
	private static final int B = 1;
	private static final int C = 2;
	private static final int D = 3;
	private static final int E = 4;
	private static final int F = 5;
	private static final int G = 6;
	private static final int H = 7;
	private static final int I = 8;
	private static final int J = 9;
	private static final int K = 10;
	private static final int L = 11;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		example0();
//		example1();
//		example2();
//		example3();
//		mergeInputWords();

	}

	public static void example0() {

		FSM fsm = new FSM();

		Node[] nodes = new Node[6];
		nodes[1] = new Node("1");
		nodes[2] = new Node("2");
		nodes[3] = new Node("3");
		nodes[4] = new Node("4");
		nodes[5] = new Node("5");
		nodes[5].acceptor = true;
		for (int i = 1; i < nodes.length; i++)
			fsm.nodes.add(nodes[i]);

		fsm.addEdge(new Edge("a", nodes[1], nodes[2]));fsm.addEdge(new Edge("b", nodes[1], nodes[3]));
		fsm.addEdge(new Edge("a", nodes[2], nodes[2]));fsm.addEdge(new Edge("b", nodes[2], nodes[4]));
		fsm.addEdge(new Edge("a", nodes[3], nodes[2]));fsm.addEdge(new Edge("b", nodes[3], nodes[3]));
		fsm.addEdge(new Edge("a", nodes[4], nodes[2]));fsm.addEdge(new Edge("b", nodes[4], nodes[5]));
		fsm.addEdge(new Edge("a", nodes[5], nodes[2]));fsm.addEdge(new Edge("b", nodes[5], nodes[1]));

		System.out.println("\n *** Example 0 ***\n");
		FSM FSMReducido = fsm.reduce();
		System.out.println("\n *** Result ***\n\nP(final) = "+FSMReducido.partitionString+"\n");
		FSMReducido.printTranslateTable();
		printEdges(FSMReducido.edges, FSMReducido.nodes);

	}

	public static void example1() {

		FSM FSM = new FSM();

		Node[] nodes = new Node[11];
		nodes[A] = new Node("A");
		nodes[B] = new Node("B");
		nodes[C] = new Node("C");
		nodes[D] = new Node("D");
		nodes[E] = new Node("E");
		nodes[F] = new Node("F");
		nodes[G] = new Node("G");
		nodes[H] = new Node("H");
		nodes[I] = new Node("I");
		nodes[J] = new Node("J");
		nodes[K] = new Node("K");
		nodes[I].acceptor = true;
		nodes[K].acceptor = true;

		for (int i = A; i <= K; i++)
			FSM.nodes.add(nodes[i]);

		FSM.addEdge(new Edge("0", nodes[A], nodes[B]));FSM.addEdge(new Edge("1", nodes[A], nodes[A]));

		FSM.addEdge(new Edge("0", nodes[A], nodes[B]));FSM.addEdge(new Edge("1", nodes[A], nodes[A]));
		FSM.addEdge(new Edge("0", nodes[B], nodes[C]));FSM.addEdge(new Edge("1", nodes[B], nodes[D]));
		FSM.addEdge(new Edge("0", nodes[C], nodes[E]));FSM.addEdge(new Edge("1", nodes[C], nodes[C]));
		FSM.addEdge(new Edge("0", nodes[D], nodes[F]));FSM.addEdge(new Edge("1", nodes[D], nodes[B]));
		FSM.addEdge(new Edge("0", nodes[E], nodes[G]));FSM.addEdge(new Edge("1", nodes[E], nodes[E]));
		FSM.addEdge(new Edge("0", nodes[F], nodes[H]));FSM.addEdge(new Edge("1", nodes[F], nodes[F]));
		FSM.addEdge(new Edge("0", nodes[G], nodes[I]));FSM.addEdge(new Edge("1", nodes[G], nodes[G]));
		FSM.addEdge(new Edge("0", nodes[H], nodes[J]));FSM.addEdge(new Edge("1", nodes[H], nodes[H]));
		FSM.addEdge(new Edge("0", nodes[I], nodes[A]));FSM.addEdge(new Edge("1", nodes[I], nodes[K]));
		FSM.addEdge(new Edge("0", nodes[J], nodes[K]));FSM.addEdge(new Edge("1", nodes[J], nodes[J]));
		FSM.addEdge(new Edge("0", nodes[K], nodes[A]));FSM.addEdge(new Edge("1", nodes[K], nodes[K]));

		System.out.println("\n *** Example 1 ***\n");
		FSM FSMReducido = FSM.reduce();
		System.out.println("\n *** Result ***\n\nP(final) = "+FSM.partitionString+"\n");
		FSMReducido.printTranslateTable();

	}

	public static void example2() {

		FSM FSM = new FSM();

		Node[] nodes = new Node[6];
		nodes[A] = new Node("A");
		nodes[B] = new Node("B");
		nodes[C] = new Node("C");
		nodes[D] = new Node("D");
		nodes[E] = new Node("E");
		nodes[F] = new Node("F");
		nodes[F].acceptor = true;

		for (int i = A; i <= F; i++)
			FSM.nodes.add(nodes[i]);

		FSM.addEdge(new Edge("0", nodes[A], nodes[B]));FSM.addEdge(new Edge("1", nodes[A], nodes[A]));

		FSM.addEdge(new Edge("0", nodes[A], nodes[B]));FSM.addEdge(new Edge("1", nodes[A], nodes[A]));
		FSM.addEdge(new Edge("0", nodes[B], nodes[C]));FSM.addEdge(new Edge("1", nodes[B], nodes[B]));
		FSM.addEdge(new Edge("0", nodes[C], nodes[D]));FSM.addEdge(new Edge("1", nodes[C], nodes[C]));
		FSM.addEdge(new Edge("0", nodes[D], nodes[E]));FSM.addEdge(new Edge("1", nodes[D], nodes[D]));
		FSM.addEdge(new Edge("0", nodes[E], nodes[F]));FSM.addEdge(new Edge("1", nodes[E], nodes[E]));
		FSM.addEdge(new Edge("0", nodes[F], nodes[B]));FSM.addEdge(new Edge("1", nodes[F], nodes[F]));

		System.out.println("\n *** Example 2 ***\n");
		FSM FSMReducido = FSM.reduce();
		System.out.println("\n *** Result ***\n\nP(final) = "+FSM.partitionString+"\n");
		FSMReducido.printTranslateTable();

	}
	
	public static void example3() {

		FSM fsm = new FSM();

		Node[] nodes = new Node[12];
		nodes[A] = new Node("A");
		nodes[B] = new Node("B");
		nodes[C] = new Node("C");
		nodes[D] = new Node("D");
		nodes[E] = new Node("E");
		nodes[F] = new Node("F");
		nodes[G] = new Node("G");
		nodes[H] = new Node("H");
		nodes[I] = new Node("I");
		nodes[J] = new Node("J");
		nodes[K] = new Node("K");
		nodes[L] = new Node("L");
		nodes[L].acceptor = true;
		nodes[F].acceptor = true;

		for (int i = A; i <= L; i++)
			fsm.nodes.add(nodes[i]);

		fsm.addEdge(new Edge("0", nodes[A], nodes[B]));fsm.addEdge(new Edge("1", nodes[A], nodes[A]));

		fsm.addEdge(new Edge("0", nodes[A], nodes[B]));fsm.addEdge(new Edge("1", nodes[A], nodes[A]));
		fsm.addEdge(new Edge("0", nodes[B], nodes[C]));fsm.addEdge(new Edge("1", nodes[B], nodes[B]));
		fsm.addEdge(new Edge("0", nodes[C], nodes[D]));fsm.addEdge(new Edge("1", nodes[C], nodes[C]));
		fsm.addEdge(new Edge("0", nodes[D], nodes[E]));fsm.addEdge(new Edge("1", nodes[D], nodes[D]));
		fsm.addEdge(new Edge("0", nodes[E], nodes[F]));fsm.addEdge(new Edge("1", nodes[E], nodes[E]));
		fsm.addEdge(new Edge("0", nodes[F], nodes[B]));fsm.addEdge(new Edge("1", nodes[F], nodes[F]));
		
		fsm.addEdge(new Edge("0", nodes[G], nodes[H]));fsm.addEdge(new Edge("1", nodes[G], nodes[G]));
		fsm.addEdge(new Edge("0", nodes[H], nodes[I]));fsm.addEdge(new Edge("1", nodes[H], nodes[H]));
		fsm.addEdge(new Edge("0", nodes[I], nodes[J]));fsm.addEdge(new Edge("1", nodes[I], nodes[I]));
		fsm.addEdge(new Edge("0", nodes[J], nodes[K]));fsm.addEdge(new Edge("1", nodes[J], nodes[J]));
		fsm.addEdge(new Edge("0", nodes[K], nodes[L]));fsm.addEdge(new Edge("1", nodes[K], nodes[K]));
		fsm.addEdge(new Edge("0", nodes[L], nodes[G]));fsm.addEdge(new Edge("1", nodes[L], nodes[L]));

		System.out.println("\n *** Example 3 ***\n");
		FSM FSMReducido = fsm.reduce();
		System.out.println("\n *** Result ***\n\nP(final) = "+fsm.partitionString+"\n");
		FSMReducido.printTranslateTable();

	}
	
	public static void mergeInputWords() {
		
		FSM fsm = new FSM();

		Node[] nodes = new Node[2];
		nodes[A] = new Node("A");
		nodes[B] = new Node("B");
		
		for (int i = A; i <= B; i++)
			fsm.nodes.add(nodes[i]);
		
		fsm.addEdge(new Edge("0", nodes[A], nodes[B]));fsm.addEdge(new Edge("1", nodes[A], nodes[B]));
		fsm.printTranslateTable();
	}
	
	public static void printEdges(Collection<Edge> edges, Collection<Node> nodes) {
		System.out.println("Edges");
		for (Edge edge : edges)
			System.out.println(edge.tail.name+" -- "+edge.inputWordsString+" -> "+edge.head.name);
		System.out.println("Outputs");
		for (Node node : nodes) {
			for (Edge edge : node.outgoingEdges)
				System.out.println(edge.tail.name+" -- "+edge.inputWordsString+" -> "+edge.head.name);
		}
		System.out.println("Inputs");
		for (Node node : nodes) {
			for (Edge edge : node.incomingEdges)
				System.out.println(edge.head.name+" <- "+edge.inputWordsString+" -- "+edge.tail.name);
		}
	}

}
