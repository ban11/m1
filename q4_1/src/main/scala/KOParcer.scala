import java.io.{BufferedReader, FileReader}


object KOParcer {
  class LineIterator(in: BufferedReader) extends Iterator[String] {
    private var nextLine: String = null

    def hasNext = {
      if (nextLine == null)
        nextLine = in.readLine
      nextLine != null
    }

    def next: String = {
      if (hasNext) {
        val line = nextLine
        nextLine = null
        line
      }
      else
        Iterator.empty.next
    }
  }

  def open[U](fileName: String)(body: Iterator[String] => U): Unit = {
    val in = new BufferedReader(new FileReader(fileName))
    try
      body(new LineIterator(in))
    finally
      in.close
  }
  def main(args: Array[String]): Unit = {
    open(args(0)) { f =>
      for(line <- f){
        val lineElement = line.split(" {1,}")
        val element = lineElement.head match {
          case e if e == "ENTRY" => lineElement(1)+","
          case e if e == "DEFINITION" =>  """\[EC:[0-9.\- ]+\]""".r.findFirstIn(line) match {
            case Some(x) => x + "\n"
            case None => "\n"
          }
          case _ => ""
        }
        print(element)

      }
    }
  }
}

