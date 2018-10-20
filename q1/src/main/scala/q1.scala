object Transformer {
  implicit class aminoAcid(aa: Char) {
    def toGeneticCode: List[String] = {
      aa match {
        case 'A' => List("GCU","GCC","GCA","GCG")
        case 'R' => List("CGU","CGC","CGA","CGG","AGA","AGG")
        case 'N' => List("AAU","AAC")
        case 'D' => List("GAU","GAC")
        case 'C' => List("UGU","UGC")
        case 'Q' => List("CAA","CAG")
        case 'E' => List("GAA","GAG")
        case 'G' => List("GGU","GGC","GGA","GGG")
        case 'H' => List("CAU","CAC")
        case 'I' => List("AUU","AUC","AUA")
        case 'L' => List("UUA","UUG","CUU","CUC","CUA","CUG")
        case 'K' => List("AAA","AAG")
        case 'M' => List("AUG")
        case 'F' => List("UUU","UUC")
        case 'P' => List("CCU","CCC","CCA","CCG")
        case 'S' => List("UCU","UCC","UCA","UCG","AGU","AGC")
        case 'T' => List("ACU","ACC","ACA","ACG")
        case 'W' => List("UGG")
        case 'Y' => List("UAU","UAC")
        case 'V' => List("GUU","GUC","GUA","GUG")
        case  _  => List("XXX")
      }
    }
  }
  
  implicit class aminoAcidChain(aac:String) {
    def trans2GeneticCode: List[String] = aac.toList match {
      case Nil => List("")
      case x::xs => xs.foldLeft(x.toGeneticCode){
        (x,y) => x.flatMap(e => y.toGeneticCode.map(g => e + g ))
      }
    }
  }
  
  def main(args: Array[String]): Unit = {
    val aac = "KK" 
    val lst = aac.trans2GeneticCode
    lst.foreach(println)
  }
}
