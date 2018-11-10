import scala.xml._

object KEGGmlParcer {


  def main(args: Array[String]): Unit = {
    val path = "eco/eco00010.xml" 
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
    println(reactionPaths)
     
  }
}

