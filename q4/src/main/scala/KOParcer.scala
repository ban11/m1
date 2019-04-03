iport scala.util.parsing.combinator._
import java.io.FileReader

class KO extends JavaTokenParsers {
  def ko = repsep(entry,"""///""")
  def entry = koID~
              name~
              definition~
              pathWays~
              opt(module)~
              opt(disease)~
              brites~
              dbLinks~
              genes~
              opt(references)

  def koID:Parser[String] = "ENTRY"~>id<~"KO"
  def id:Parser[String] = """[a-zA-Z]+[0-9]+""".r
  def name = "NAME"~>str
  def definition = "DEFINITION" ~>str~ecID
  def ecID = "[EC:"~>"""([0-9]+\.)+[0-9]+""".r<~"]"
  def pathWays = "PATHWAY"~>rep(id~str)
  def module = "MODULE"~>rep(id~str)
  def disease = "DISEASE"~>rep(id~str)
  def brites = "BRITE"~>rep(briteItems)
  def briteItems =(
      briteOthology
    | briteModules
    | briteEnzyme
    | briteExsome
  )
  def briteOthology = briteTitle~rep(othologyChild)
  def othologyChild:Parser[Any]= (
      briteLeaf  
    | othologyIndex~othologyChild
  )
  def othologyIndex = """[0-9]+""".r~>str
  def briteModules = briteTitle~rep(moduleChild)
  def moduleChild:Parser[Any] =(
      briteLeaf
    | moduleIndex~moduleChild
  )
  def moduleIndex = str
  def briteEnzyme = briteTitle~rep(enzymeChild)
  def enzymeChild:Parser[Any] =(
      briteLeaf
    | enzymeIndex~enzymeChild
  )
  def enzymeIndex = """[0-9.]+""".r~str
  def briteExsome = briteTitle~rep(exsomeChild)
  def exsomeChild:Parser[Any] =(
      briteLeaf
    | exsomeIndex~exsomeChild
  )
  def exsomeIndex = str
  def briteTitle = str~briteId
  def briteId = "[BR:"~>str<~"]"
  def briteLeaf = rep(id~str)
  def dbLinks = "DBLINK"~>rep(str~":"~id)
  def genes = "GENES"~>rep(organism~":"~ str)
  def organism = """[A-Z}+""".r
  def references = rep(reference)
  def reference = "REFERENCE"~>pmID~authors~title~journal~sequence
  def pmID = "PMID:"~>"""[0-9]+""".r
  def authors = "AUTHORS"~>repsep(str,",")
  def title= "TITLE"~>str
  def journal = str
  def sequence = "SEQUENCE"~>"{"~>gene<~"}"
  def gene = str~":"~id
  def str = """([a-zA-Z0-9.,_:;()/+-]+ )*[a-zA-Z0-9.,_:;()/+-]+""".r 

  
}

object KOParser extends KO {
  def main(args: Array[String]):Unit ={
    val f = new FileReader(args(0))
    println(parseAll(ko,f))
  }
}

