class Graph[T] {
  type Node = T
  type GraphMap = Map[Node,List[Node]]
  var g:GraphMap = Map()
  def nodes: List[Node] = g.keys.toList
  def DFS(start: Node): List[Node] = {

    def DFS0(v: Node, visited: List[Node]): List[Node] = {
      if (visited.contains(v))
        visited
      else {
        val neighbours: List[Node] = g(v).filterNot(visited.contains)
        neighbours.foldLeft(v :: visited)((b,a) => DFS0(a,b))
      }
    }
    DFS0(start, List()).reverse
  }

  def intoComponents: List[List[Node]] = {
     
    def intoComponents0(nodes: List[Node],ans:List[List[Node]]):List[List[Node]] = {
      val gr = DFS(nodes.head)
      val cgr = nodes.filterNot(gr.contains)
      val ans2 = gr :: ans
      cgr match {
        case Nil => ans2
        case x => intoComponents0(x, ans2)
      }
    }
    
    val nodes = this.nodes
    intoComponents0(nodes,List()).reverse
  }

}
