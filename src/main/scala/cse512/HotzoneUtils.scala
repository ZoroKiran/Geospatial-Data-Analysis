package cse512

object HotzoneUtils {

def stContains(queryRectangle:String, pointString:String) : Boolean = {
    val point = pointString.split(",")
    
    val x = point(0).trim().toDouble
    val y = point(1).trim().toDouble

    val rectangle = queryRectangle.split(",")
    val x1 = rectangle(0).trim().toDouble
    val y1 = rectangle(1).trim().toDouble
    val x2 = rectangle(2).trim().toDouble
    val y2 = rectangle(3).trim().toDouble

    var min_x: Double = 0
    var max_x: Double = 0
    if(x1 < x2) {
      min_x = x1
      max_x = x2
    } else {
      min_x = x2
      max_x = x1
    }

    var min_y: Double = 0
    var max_y: Double = 0
    if(y1 < y2) {
      min_y = y1
      max_y = y2
    } else {
      min_y = y2
      max_y = y1
    }

    if(x >= min_x && x <= max_x && y >= min_y && y <= max_y) {
      return true
    } else {
      return false
    }
  }

}
