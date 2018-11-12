class Graph[T] {
  type Node = T
  type GraphMap = Map[Node,List[Node]]
  var g:GraphMap = Map()
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
}
