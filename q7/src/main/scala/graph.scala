case class Edge(n1: Node, n2:Node) {
  def toTuple = (n1.value, n2.value)
}
case class Node(value: String) {
  override def toString = value
}

abstract class GraphBase (val edges: List[Edge], val nodes: List[Node]) {
  def targets(gr: GraphBase, n :Node): List[Node]
}


class Digraph(edges: List[Edge], nodes: List[Node]) extends GraphBase(edges, nodes) {
  override def target(gr: Digraph,  node :Node): List[Node] = {

  }
}

class Graph(edges: List[Edge], nodes: List[Node]) extends GraphBase(edges, nodes) {
  override def target(gr: Graph, node :Node): List[Node] = {
  }
}

