import java.io.File

import org.biojava.nbio.core.sequence.{DNASequence, ProteinSequence}
import org.biojava.nbio.core.sequence.compound.AmbiguityDNACompoundSet

object genomeCounter{
  implicit def str2DNA(seq: String) = new DNASequence(seq,AmbiguityDNACompoundSet.getDNACompoundSet)

  def getListofFile(dir: String,filePattern: scala.util.matching.Regex): List[File] ={
    val d = new File(dir)
    if (d.exists) {
      val files = d.listFiles
                   .filter(_.isFile)
                   .filter(_ match {
                                    case `filePattern` => true
                                    case _ => false
                                   })
                   .toList
      files
    }else{
      List[File]()
    }
  }

      

  def main(args:Array[String]): Unit = {
    val dirGenome = "/bio/db/fasta/genome"
    val genomeFile = """.*\.genome$""".r
    
    val genomeList = getListofFile(dirGenome,genomeFile)
    println(genomeList.length)

    val dirGene ="/bio/db/fasta/gene"
    val geneFile = """.*\.nuc$""".r

    val geneList =getListofFile(dirGene,geneFile)
    println(geneList.length)

  }
}

