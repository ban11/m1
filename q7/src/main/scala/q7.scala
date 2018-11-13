import java.io._
import scala.xml._

object KEGGmlParcer {
/*
  def getDipathFromFile(path: String): List[(String,String)] = {
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
    val edges =  reactionPaths.map(p => (p._1, p._2))
    edges
  }

  def getDipath(path: String): List[(String,String)] = {
    val dir = new File(path)
    val files = dir.listFiles().map(_.toString).toList
    val reactionPaths = files.flatMap(file => getDipathFromFile(file))
    reactionPaths
  }
*/
  def getPathFromFile(path: String): List[(String,String)]  = {
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
    val edges = reactionPaths.map(p => (p._1, p._2))
    edges
  }
  def getPath(path: String): List[(String,String)] = {
    val dir = new File(path)
    val files = dir.listFiles().map(_.toString).toList
    val reactionPaths = files.flatMap(file => getPathFromFile(file))
    reactionPaths
  }
  
  def graphFromPath(path:List[(String,String)]): Graph[String] = {
    val path2 = path ::: path.map(e => (e._2, e._1))
    val graphMap = path2.groupBy(_._1).map{case (k,v) => (k,v.map(e =>e._2))}
    
    val graph = new Graph[String]
    graph.g = graphMap
    graph
  }

  def main(args: Array[String]): Unit = {
    val path = "./eco"
    val reactionPath = getPath(path)
    val gr = graphFromPath(reactionPath)
    gr.intoComponents.foreach(println)
  }
}

