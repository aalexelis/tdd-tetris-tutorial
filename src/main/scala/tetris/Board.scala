package tetris

/**
 * Created by andreas on 8/5/14.
 */

class Board(val rows: Int, val columns: Int) {

  val boardState = scala.collection.mutable.Map[(Int, Int),Block]()
  override def toString() = (1 to rows).map(y => {
    (1 to columns).map(x => {
      boardState.get((y,x)).map(_.render).getOrElse(".")
    }).mkString("","","\n")
  }).reverse.mkString("")
  
  var hasFallingFlg = false
  def hasFalling() = hasFallingFlg
  def drop(block: Block) = {
    if (hasFallingFlg) throw new IllegalStateException("already falling")
    hasFallingFlg = true
    boardState.put((rows, columns/2+1),block) //TODO
  }
  
  def tick() = {
    boardState.clone.foreach{case ((y,x), b) =>
      boardState.put((y-1,x),b)
      boardState.remove((y,x))
    }
  }

}

class Block(kind: Char){
  def render:String = kind.toString
}