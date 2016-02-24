
object Expressifier {

  var inX = 0
  var inY = 0
  var inZ = 0
  var inA = 0

  var enteredValX : Boolean = false
  var enteredValY : Boolean = false
  var enteredValZ : Boolean = false
  var enteredValA : Boolean = false

  lazy val exp1: Tree = Plus(Var("x"),Times(Var("x"),Minus(Var("y"),Divide(Var("z"),Const(2)))));
  lazy val exp2: Tree = Plus(Minus(Var("z"),Const(2)),Times(Var("x"),Const(5)));
  lazy val exp3: Tree = Plus(Const(1),Var("a"));        


  abstract class Tree
  case class Plus (left:Tree,right:Tree) extends Tree{
    override def toString = "(" + left + "+" + right + ")"
  }
  case class Minus (left:Tree,right:Tree) extends Tree{
    override def toString = "(" + left + "-" + right + ")"
  }
  case class Times (left:Tree,right:Tree) extends Tree{
    override def toString = "(" + left + "*" + right + ")"
  }
  case class Divide (left:Tree,right:Tree) extends Tree{
    override def toString = "(" + left + "/" + right + ")"
  }
  case class Var (n:String) extends Tree{
    override def toString = n
  }
  case class Const (c:Int) extends Tree{
    override def toString = c.toString
  }

  def evaluate(t: Tree): Tree = t match {
    case Plus(left,right)=> Plus(evaluate(left),evaluate(right))

    case Minus(left,right)=>Minus(evaluate(left),evaluate(right))

    case Times(left,right)=> Times(evaluate(left),evaluate(right))

    case Divide(left,right) => Divide(evaluate(left),evaluate(right))


    case Var(n) if ((n == "x") && (enteredValX == true)) =>  Const(inX)
    case Var(n) if ((n == "y") && (enteredValY == true)) =>  Const(inY)
    case Var(n) if ((n == "z") && (enteredValZ == true)) =>  Const(inZ)
    case Var(n) if ((n == "a") && (enteredValA == true)) =>  Const(inA)

    case _ => t
  }


  def simplify(t:Tree, recurse:Boolean = true):Tree = t match {
    case Plus(Const(0),right)=>simplify(right)
    case Plus(left,Const(0))=>simplify(left)   
    case Plus (left,right) if recurse => simplify(Plus(simplify(left),simplify(right)),recurse = false)
    case Plus(Const(left), Const(right)) => Const (left + right)

    case Minus(left,Const(0))=>simplify(left)
    case Minus(left, right) if left == right => Const(0)
    case Minus(left,right) if recurse => simplify(Minus(simplify(left),simplify(right)),recurse = false)
    case Minus(Const(left), Const(right)) => Const (left - right)

    case Times(Const(1),right) => simplify(right)
    case Times(left,Const(1)) => simplify(left)
    case Times(Const(0),right) => Const(0)
    case Times(left,Const(0)) => Const(0)
    case Times (left,right) if recurse => simplify(Times(simplify(left),simplify(right)),recurse = false)
    case Times(Const(left), Const(right)) => Const (left * right)

    case Divide(Const(0),right) => Const(0)
    case Divide (Const(left),Const(right)) => Const(left/right)
    case Divide(left,Const(1)) => simplify(left)
    case Divide(left,right) if left == right => Const(1)
    case Divide(left,right) if recurse => simplify(Divide(simplify(left),simplify(right)),recurse = false)

    case _ => t
  }




   def main (args: Array [ String ]) {

     println("Welcome to the Expressifier")
     println("Below are the expressions you can simplify \n exp1 : " + exp1 + " \n exp2 :" + exp2 + " \n exp3:" + exp3)
     var cont:Boolean = true
     var input:String = ""

     while (cont){
       enteredValX = false
       enteredValY = false
       enteredValZ = false
       enteredValA = false

       println("Here are your possible inputs \n [ Q:to quit] \n [Eval to begin evaluation process]")
       input = readLine()
       if(input == "Q" || input == "q"){
          cont = false
       }else if(input.toLowerCase == "eval"){
          println("Enter expression you'd like to eval")
          input = readLine()
          if(input == "exp1"){
            println("Enter value for x or anything else to omit value for x")
            var rawX:String = readLine()

            println("Enter value for y or anything else to omit value for y")
            var rawY:String = readLine()

            println("Enter value for z or anything else to omit value for z")
            var rawZ:String = readLine()

            try {
               inX = rawX.toInt
               enteredValX = true
            }catch {
              case e: Exception =>
            }

            try {
              inY = rawY.toInt
              enteredValY = true
            }catch {
              case e: Exception =>
            }

            try {
              inZ = rawZ.toInt
              enteredValZ = true
            }catch {
              case e: Exception =>
            }

            var simpled:String = simplify(evaluate(exp1)).toString
            simpled = simpled.stripPrefix("(").stripSuffix(")").trim()

            println("Expression:" + exp1 + "Simplified: " + simpled)

          }else if(input == "exp2"){

              println("Enter value for z or anything else to omit value for z")
              var rawZ:String = readLine()

              println("Enter value for x or anything else to omit value for x")
              var rawX:String = readLine()

            try {
              inZ = rawZ.toInt
              enteredValZ = true
            }catch {
              case e: Exception =>
            }

            try {
              inX = rawX.toInt
              enteredValX = true
            }catch {
              case e: Exception =>
            }

              var simpled:String = simplify(evaluate(exp2)).toString
              simpled = simpled.stripPrefix("(").stripSuffix(")").trim()

              println("Expression:" + exp2 + "Simplified: " + simpled)
          }else if (input == "exp3"){
              println("Enter value for A or anything else to omit value for A")
              var rawA:String = readLine()
              try {
                inA = rawA.toInt
                enteredValA = true
              }catch {
                case e: Exception =>
              }

              var simpled:String = simplify(evaluate(exp3)).toString
              simpled = simpled.stripPrefix("(").stripSuffix(")").trim()
              println("Expression: " + exp3 + "Simplified: "+simpled)
          }

       }else{
         println("Invalid Input")
       }
     }
  }
}
