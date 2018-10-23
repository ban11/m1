package com.github.ban11.q3

import scala.collection.JavaConversions._
import java.io.File
import org.biojava.nbio.core.sequence.DNASequence

object GenomeCounter{

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

  def getFasta (f: File):Map[String,Map[String,DNASequence]] ={ 
    val basename = f.getName()
    Map(basename.substring(0,basename.lastIndexOf('.')) -> ExFastaReaderHelper.readFastaAmDNASequence(f).toMap)
  }
  
  def f(files: List[File]):Unit = {
    
  }

      

  def main(args:Array[String]): Unit = {
    val dirGenome = "."///bio/db/fasta/genome/"
    val genomeFile = """.*\.genome$"""

    val fileList = getListofFile(dirGenome,genomeFile)
    
    val genome = fileList.view
                         .map{ f => getFasta(f).mapValues(m => m.mapValues(g => g.size).values.sum)}
                         .toList match {
                           case Nil => Map(""->0)
                           case x::xs => xs.foldLeft(x){
                             (a,b) => a ++ b
                           }
                         }

                           
    
    println(genome)

    val dirGene ="/bio/db/fasta/genes/"
    val geneFile = """.*\.nuc$""" 

    val geneList =getListofFile(dirGene,geneFile)
    println(geneList.length)








  }
}

