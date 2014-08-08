package tetris

/**
 * Created by andreas on 8/5/14.
 */

class Board(val rows: Int, val columns: Int) {
  val state = scala.collection.mutable.Map[(Int,Int),Block]()
  val falling = scala.collection.mutable.Set[Block]()

  override def toString: String = {
    (1 to rows).map(r => {
      (1 to columns).map(c => state.get((r, c)).map(_.toString()).getOrElse(".")).mkString("")
    }).mkString("","\n","\n")
  }

  def hasFalling: Boolean = falling.isEmpty

  def drop(block: Block) {
    if (hasFalling) throw new IllegalStateException("already falling")
    else {
      falling.add(block)
      state += (0,(columns+1)/2) -> block
    }
  }

  def tick() {
    (rows to 1).map(r => {
      (1 to columns).map(c => {
        state.get(r,c) match {
          case Some(b) if falling.contains(b) => {
            if (r == rows) falling.remove(b)
            else state.get(r+1,c) match {
              case Some(bb) if falling.contains(bb) => throw new IllegalStateException("falling bellow should have already been moved")
              case Some(bb) if !falling.contains(bb) => falling.remove(b)
              case None => {state.remove(r,c); state += (r+1,c)->b }
            }
          }
          case _ =>
        }
      })
    })
  }


}

case class Block(blocktype: Char) {
  override def toString() = blocktype.toString
}
