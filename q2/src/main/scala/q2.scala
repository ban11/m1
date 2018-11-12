import scala.annotation.tailrec

object Dijest {
  @tailrec
  def digest(seq: List[Char], pattern: List[Char], ans: List[Int] = Nil): List[Int] = seq match {
    case x::xs if(seq.length >= pattern.length && matching(seq.take(pattern.length), pattern))  => { 
                                               val ans2 = seq.length :: ans
                                               digest(xs,pattern,ans2)
                                             }
    case x::xs if(seq.length >= pattern.length && !matching(seq.take(pattern.length), pattern)) => digest(xs,pattern,ans)
    case _ => ans.reverse
  }
  @tailrec
  def matching(seq: List[Char], pattern: List[Char]): Boolean = seq match {
    case y::ys if( y == pattern.head ) => matching(ys, pattern.tail)
    case y::ys if( y != pattern.head ) => false
    case _                             => true
  }
  def fragment(site: List[Int], seqlen: Int, short:List[Int] = Nil): List[Int] = site match {
    case x::xs => {
                   val short2 = x :: short
                   val site2 = xs.map(_ - x)
                   fragment(site2, seqlen, short2)
                  }
    case _ => {
               val ans = (seqlen - short.head) :: short
               ans.reverse
    }
  }

  def main(args: Array[String]): Unit = {
/*    val rEnzayme = args(0)
    val pattern = rEnzayme match {
      case EcoRI => ("GAATTC", 1)
      case HindIII => ("AAGCTT",1)
      case BamHI => ("GGATC",1)
      case NotI => ("GCGGCCGC",2)
      case _ => ("", 0)
    }
*/    
    val pattern =("AAKAA", 1)
    val seq = "AAKAAAKAAKAAAAKAAAA"
    
    val site = digest(seq.toList, pattern._1.toList)
               .map(seq.length - _ + pattern._2)

    println(site)

    val shortfragment = fragment(site, seq.length)

    println(shortfragment)
  }
}
