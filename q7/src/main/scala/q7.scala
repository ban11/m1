import java.io._
import scala.xml._

object KEGGmlParcer {

  def getDipathFromFile(path: String): List[Edge] = {
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
          .map(tp => (Node((tp._1 \ "@name").toString.dropWhile{ _ != ':'}.tail), 
                      Node((tp._2 \ "@name").toString.dropWhile{ _ != ':'}.tail)))
     val edges =  reactionPaths.map(p => Edge(p._1, p._2))
    edges
  }

  def getDipath(path: String): List[Edge] = {
    val dir = new File(path)
    val files = dir.listFiles().map(_.toString).toList
    val reactionPaths = files.flatMap(file => getDipathFromFile(file))
    reactionPaths
  }

  def getPathFromFile(path: String): List[Edge]  = {
    val xmlFile = XML.load(path)
    val reactions = xmlFile \ "reaction"
    val reactionPaths = 
      reactions.foldLeft(Nil: List[(NodeSeq,NodeSeq)]){
        (x,y) => {
           (y \ "substrate", y \ "product") :: x
           }
        }
          .flatMap(tp =>  for(s <- tp._1;p <- tp._2) yield (s,p))
          .map(tp => (Node((tp._1 \ "@name").toString.dropWhile{ _ != ':'}.tail), 
                      Node((tp._2 \ "@name").toString.dropWhile{ _ != ':'}.tail)))
    val edges = reactionPaths.map(p => Edge(p._1, p._2))
    edges
  }
  def getPath(path: String): List[Edge] = {
    val dir = new File(path)
    val files = dir.listFiles().map(_.toString).toList
    val reactionPaths = files.flatMap(file => getPathFromFile(file))
    reactionPaths
  }

  def main(args: Array[String]): Unit = {
    val path = "./eco"
    val reactionPath = getPath(path)
    println(reactionPath.length)
  }
}

