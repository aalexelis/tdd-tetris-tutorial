package tetris

/**
 * Created by andreas on 8/8/14.
 */
class Piece(ps: scala.collection.mutable.Map[(Int,Int),Block], pr: Int, pc: Int) {

  private var rows: Int = pr
  private var columns: Int = pc
  private val state = ps

  def this(repr:String) = {
    this(scala.collection.mutable.Map[(Int,Int),Block](),0,0)
    fromString(repr)
  }

  def fromString(repr:String) = {
    if (Option(repr).isEmpty) throw new IllegalStateException("empty representation string")
    val rowarr = repr.split("\n")
    val rows = rowarr.length
    (1 to rows).map( r => {
      if ((columns > 0) && (rowarr(r-1).length != columns)) throw new IllegalStateException("malformed representation string")
      if (columns == 0) columns = rowarr(r-1).length
      (1 to columns).map(c => {
        val ch = rowarr(r-1).charAt(c-1)
        if (ch != '.') state += (r,c)-> Block(ch)
      })
    })
  }

  override def toString: String = {
    (1 to rows).map(r => {
      (1 to columns).map(c => state.get((r, c)).map(_.toString()).getOrElse(".")).mkString("")
    }).mkString("","\n","\n")
  }

  def rotateRight: Piece = {
    val newstate = scala.collection.mutable.Map[(Int,Int),Block]()
    (1 to rows).map(r => {
      (1 to columns).map(c =>
        state.get(r, c).map(b => newstate += (c, r) -> b)
      )
    })
    new Piece(newstate,columns,rows)
  }

  def rotateLeft: Piece = {
    val newstate = scala.collection.mutable.Map[(Int,Int),Block]()
    (1 to rows).map(r => {
      (1 to columns).map(c =>
        state.get(r, c).map(b => newstate += (columns - c, rows - r) -> b)
      )
    })
    new Piece(newstate,columns,rows)
  }

}
