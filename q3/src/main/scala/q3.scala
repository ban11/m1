import java.io.File

import org.biojava.nbio.core.sequence.DNASequence
import org.biojava.nbio.core.sequence.io.FastaReaderHelper
object genomeCounter{

  def getListofFile(dir: String,filePattern: String): List[File] ={
    val d = new File(dir)
    if (d.exists) {
      val files = d.listFiles
                   .filter(_.isFile)
                   .filter(_.toString.matches(filePattern)) 
                   .toList
      files
    }else{
      println("none")
      List[File]()
    }
  }
  
  def getFastaFiles(files: List[File]) = {
  }
      

  def main(args:Array[String]): Unit = {
    val dirGenome = "/bio/db/fasta/genome/"
    val genomeFile = """.*\.genome$"""

    val genomeList = getListofFile(dirGenome,genomeFile)
                    .map( f => FastaReaderHelper.readFastaDNASequence(f))
      
    println(genomeList)
    
    

    val dirGene ="/bio/db/fasta/genes/"
    val geneFile = """.*\.nuc$""" 

    val geneList =getListofFile(dirGene,geneFile)
    println(geneList.length)








  }
}

