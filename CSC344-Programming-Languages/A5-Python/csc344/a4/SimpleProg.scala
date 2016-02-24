class Vehicle {
  var spentOnGas: Int = 50
  var costOfCar : Float = 20000.53f

  def totalSpent(spentOn : Int) : Float = {
    return spentOn + 12000.53f
  }
}

class Motorcycle extends Vehicle{

}