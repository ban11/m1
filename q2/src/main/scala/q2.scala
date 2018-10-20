object Digest{
  def aminoAcidMatch(aa:Char)(seq:Char):Boolean= seq match {
    case `aa` => true
    case  _   => false
  }

  def seqPatternFilter(pattern:String):List[Char => Boolean] = {
    val f = aminoAcidMatch _
    pattern.toList
           .map(aa => f(aa))
  }

  def main(argv: Array[String]):Unit = {
    val pattern = "AKKAKKPK"
    val slicePosition = 2
    val filter = seqPatternFilter(pattern)
    
    val seq = "AKKAKKKPKAKAKKAKKPK"
  }
}
