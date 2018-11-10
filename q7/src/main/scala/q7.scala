import java.io._
import scala.xml._

abstract class GraphBase[T] {
  case class Edge(n1:Node, n2: Node) {
    def toTuple= (n1.value, n2.value)
  }
  case class Node(value:T) {
  }
}

class Graph[T] extends GraphBase[T] {
}




object KEGGmlParcer {

  def getDigraphFromFile(path: String): List[(String,String)]  = {
    val xmlFile = XML.load(path)
    val reactions = xmlFile \ "reaction"
    val reactionPaths = 
      reactions.foldLeft(Nil: List[(NodeSeq,NodeSeq)]){
        (x,y) => {
           if((y \ "@type").toString == "irreversible") (y \ "substrate", y \ "product") :: x
           else if ((y \ "@type").toString == "reversible") (y \ "substrate", y \ "product") :: (y \ "product", y \ "substrate") :: x
           else x 
           }
        }
          .flatMap(tp =>  for(s <- tp._1;p <- tp._2) yield (s,p))
          .map(tp => ((tp._1 \ "@name").toString.dropWhile{ _ != ':'}.tail, 
                      (tp._2 \ "@name").toString.dropWhile{ _ != ':'}.tail))
    reactionPaths
  }

  def getDigraph(path: String): List[(String,String)] = {
    val dir = new File(path)
    val files = dir.listFiles().map(_.toString).toList
    val reactionPaths = files.flatMap(file => getDigraphFromFile(file))
    reactionPaths 
  }

  def getGraphFromFile(path: String): List[(String,String)]  = {
    val xmlFile = XML.load(path)
    val reactions = xmlFile \ "reaction"
    val reactionPaths = 
      reactions.foldLeft(Nil: List[(NodeSeq,NodeSeq)]){
        (x,y) => {
           (y \ "substrate", y \ "product") :: x
           }
        }
          .flatMap(tp =>  for(s <- tp._1;p <- tp._2) yield (s,p))
          .map(tp => ((tp._1 \ "@name").toString.dropWhile{ _ != ':'}.tail, 
                      (tp._2 \ "@name").toString.dropWhile{ _ != ':'}.tail))
    reactionPaths
  }
  def getGraph(path: String): List[(String,String)] = {
    val dir = new File(path)
    val files = dir.listFiles().map(_.toString).toList
    val reactionPaths = files.flatMap(file => getGraphFromFile(file))
    reactionPaths 
  }

  def main(args: Array[String]): Unit = {
    val path = "./eco"
    val reactionGraph = getGraph(path)
    println(reactionGraph.length)
  }
}

