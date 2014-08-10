package tetris

/**
 * Created by andreas on 8/8/14.
 */
abstract class PieceBase(repr: String) {
  def toString():String
  def rotateLeft():PieceBase
  def rotateRight():PieceBase

}

class Piece(repr: String) extends PieceBase(repr) {
  private var (state, rows, columns):(scala.collection.mutable.Map[(Int,Int),Block], Int, Int) = fromString(repr)
  def this(ps: scala.collection.mutable.Map[(Int,Int),Block], pr: Int, pc: Int) {
    this("")
    state = ps
    rows = pr
    columns = pc
  }

  def fromString(repr:String):(scala.collection.mutable.Map[(Int,Int),Block], Int, Int) = {
    if (Option(repr).isEmpty) throw new IllegalStateException("empty representation string")
    val state = scala.collection.mutable.Map[(Int,Int),Block]()
    val rowarr = repr.split("\n")
    val rows = rowarr.length
    var columns = 0;
    (1 to rows).map( r => {
      if ((columns > 0) && (rowarr(r-1).length != columns)) throw new IllegalStateException("malformed representation string")
      if (columns == 0) columns = rowarr(r-1).length
      (1 to columns).map(c => {
        val ch = rowarr(r-1).charAt(c-1)
        if (ch != '.') state += (r,c)-> Block(ch)
      })
    })
    (state, rows, columns)
  }

  override def toString: String = toString(state, rows, columns)

  def toString(state: scala.collection.mutable.Map[(Int,Int),Block], rows: Int, columns: Int): String = {
    (1 to rows).map(r => {
      (1 to columns).map(c => state.get((r, c)).map(_.toString()).getOrElse(".")).mkString("")
    }).mkString("","\n","\n")
  }

  def rotateLeft: Piece = {
    val newstate = scala.collection.mutable.Map[(Int,Int),Block]()
    (1 to rows).map(r => {
      (1 to columns).map(c =>
        state.get(r, c).map(b => newstate += (columns-c+1,r) -> b)
      )
    })
    new Piece(newstate,columns,rows)
  }

  def rotateRight: Piece = {
    val newstate = scala.collection.mutable.Map[(Int,Int),Block]()
    (1 to rows).map(r => {
      (1 to columns).map(c =>
        state.get(r, c).map(b => newstate += (c, rows-r+1) -> b)
      )
    })
    new Piece(newstate,columns,rows)
  }


}
*/

