package tetris

/**
 * Created by andreas on 8/5/14.
 */

class Board(val rows: Int, val columns: Int) {
  val state = scala.collection.mutable.Map[(Int,Int),Block]()

  override def toString: String = {
    (1 to rows).map(r => {
      (1 to columns).map(c => state.get((r, c)).map(_.toString()).getOrElse(".")).mkString("")
    }).mkString("","\n","\n")
  }

  val falling = scala.collection.mutable.Set[Block]()
  def hasFalling: Boolean = falling.nonEmpty

  def drop(block: Block) {
    if (hasFalling) throw new IllegalStateException("already falling")
    else {
      falling += block
      state += (1,(columns+1)/2) -> block
    }
  }

  def coords(b: Block):Option[(Int,Int)] = state.filter(_._2 == b).map(_._1).headOption

  def isAtBottomLine(b:Block): Boolean = coords(b).filter(_._1 == rows).isDefined
  def optBlockBellow(b:Block):Option[Block] = coords(b).flatMap(c => state.get((c._1+1,c._2)))
  def isOverFixedBlock(b:Block): Boolean = optBlockBellow(b).map(!falling.contains(_)).isDefined
  def toBeFixedAtNextTick(b:Block): Boolean = isAtBottomLine(b) || isOverFixedBlock(b) || optBlockBellow(b).filter(toBeFixedAtNextTick(_)).isDefined

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

class Block(repr: String) extends SimpleBlock(repr.head){
  def this(ch: Char) = this(ch.toString)
  override def toString() = repr
}

abstract class BlockIF

class SimpleBlock(charRepr: Char)
