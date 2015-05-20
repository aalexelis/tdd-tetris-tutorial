package tetris

/**
 * Created by andreas on 8/5/14.
 */

class Board(val rows: Int, val columns: Int) {

  var boardState = Map[(Int, Int),Block]()
  
  def hasFalling() = !boardState.values.filter(_.isFalling).isEmpty //TODO a way to put negation at the end?
  
  def refreshBoardState() = {
    boardState = boardState.values.map(v => ((v.y, v.x),v)).toMap
  }
  
  override def toString() = (1 to rows).map(y => {
    (1 to columns).map(x => {
      boardState.get((y,x)).map(_.render).getOrElse(".")
    }).mkString("","","\n")
  }).reverse.mkString("")
  

  def drop(block: Block) = {
    if (hasFalling()) throw new IllegalStateException("already falling")
    block.initialize(rows, columns/2+1)
    boardState = boardState.updated((block.y, block.x), block)
  }
  
  def tick() = {
    boardState.values.filter(_.isFalling).foreach(_.down(boardState))
    refreshBoardState()
  }

}

class Block(kind: Char){
  def render:String = kind.toString
  var y = 0
  var x = 0
  var isFalling = false
  def initialize(y: Int, x: Int) = {
    this.y = y
    this.x = x
    this.isFalling = true
  }
  def down(boardState: Map[(Int,Int),Block]) = {
    if (shouldStop(y-1,x,boardState)) isFalling = false
    if (canMove(y-1,x,boardState)) y = y -1 
  }
  
  def shouldStop(y: Int, x: Int, boardState: Map[(Int,Int),Block]) = (y == 0) || boardState.get((y,x)).filter { b => (b.y == y && b.x == x && !b.isFalling) }.isDefined
  def canMove(y: Int, x: Int, boardState: Map[(Int,Int),Block]) = (y > 0) && boardState.get((y,x)).filter { b => (b.y == y && b.x == x && !b.isFalling) }.isEmpty
}