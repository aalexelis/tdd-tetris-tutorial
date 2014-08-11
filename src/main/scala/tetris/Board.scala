package tetris

/**
 * Created by andreas on 8/5/14.
 */

class Board(val rows: Int, val columns: Int) {
  val middle:Int = (columns+1)/2
  val state = scala.collection.mutable.Map[(Int,Int),BlockElement]()

  override def toString: String = {
    (1 to rows).map(r => {
      (1 to columns).map(c => state.get((r, c)).map(_.toString()).getOrElse(".")).mkString("")
    }).mkString("","\n","\n")
  }

  val falling = scala.collection.mutable.Set[BlockElement]()
  def hasFalling: Boolean = falling.nonEmpty

  def drop(block: Block) {
    if (hasFalling) throw new IllegalStateException("already falling")
    else {
      falling ++= block.state.values
      state ++= dropElem(block)
    }
  }

  def dropElem(block: Block): scala.collection.mutable.Map[(Int,Int),BlockElement] =
    block.state.map(be => ((be._1._1,middle+be._1._2),be._2))

  def coords(b: BlockElement):Option[(Int,Int)] = state.filter(_._2 == b).map(_._1).headOption

  def isAtBottomLine(b:BlockElement): Boolean = coords(b).filter(_._1 == rows).isDefined
  def optBlockBellow(b:BlockElement):Option[BlockElement] = coords(b).flatMap(c => state.get((c._1+1,c._2)))
  def isOverFixedBlock(b:BlockElement): Boolean = optBlockBellow(b).map(!falling.contains(_)).isDefined
  def toBeFixedAtNextTick(b:BlockElement): Boolean = isAtBottomLine(b) || isOverFixedBlock(b) || optBlockBellow(b).filter(toBeFixedAtNextTick(_)).isDefined

  def tick() {
    val tr = falling.filter(toBeFixedAtNextTick(_))
    tr.foreach(falling.remove(_))
    falling.foreach(fb => {
      coords(fb).foreach( bc => {
        state remove (bc._1, bc._2); state += (bc._1+1,bc._2)->fb
      })
    })
  }


}

case class BlockElement(ch: Char)

class Block(val repr: String) extends BlockIF {
  def this(ch: Char) = this(ch.toString)
  override def toString() = repr

  var (state, rows, columns):(scala.collection.mutable.Map[(Int,Int),BlockElement], Int, Int) =
    (scala.collection.mutable.Map[(Int,Int),BlockElement]((0,0)->BlockElement(repr.filter(_!='.').head)),1,1)
  var origin:(Int,Int) = ((columns-1)/2,(rows-1)/2)
}

trait BlockIF {
  val rows:Int
  val columns:Int
  val origin:(Int,Int)
  val repr:String
}
