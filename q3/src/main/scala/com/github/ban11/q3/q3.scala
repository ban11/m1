package com.github.ban11.q3

import java.io.File

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
  
  def getFastaFiles(files: List[File]) = {
    files.map( f =>(
                    {val basename = f.getName()
                      basename.substring(0,basename.lastIndexOf('.'))},
                      ExFastaReaderHelper.readFastaAmDNASequence(f)
                  )
              ).toMap
  }

      

  def main(args:Array[String]): Unit = {
    val dirGenome = "."///bio/db/fasta/genome/"
    val genomeFile = """.*\.genome$"""

    val genomeList = getFastaFiles(getListofFile(dirGenome,genomeFile))

      
    println(genomeList)
    
    

    val dirGene ="/bio/db/fasta/genes/"
    val geneFile = """.*\.nuc$""" 

    val geneList =getListofFile(dirGene,geneFile)
    println(geneList.length)








  }
}

