package cse512

import java.text.SimpleDateFormat
import java.util.Calendar
import java.sql.Timestamp

object HotcellUtils {
  val coordinateStep = 0.01

  def CalculateCoordinate(inputString: String, coordinateOffset: Int): Int =
  {
    // Configuration variable:
    // Coordinate step is the size of each cell on x and y
    var result = 0
    coordinateOffset match
    {
      case 0 => result = Math.floor((inputString.split(",")(0).replace("(","").toDouble/coordinateStep)).toInt
      case 1 => result = Math.floor(inputString.split(",")(1).replace(")","").toDouble/coordinateStep).toInt
      // We only consider the data from 2009 to 2012 inclusively, 4 years in total. Week 0 Day 0 is 2009-01-01
      case 2 => {
        val timestamp = HotcellUtils.timestampParser(inputString)
        result = HotcellUtils.dayOfMonth(timestamp) // Assume every month has 31 days
      }
    }
    return result
  }

  def timestampParser (timestampString: String): Timestamp =
  {
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
    val parsedDate = dateFormat.parse(timestampString)
    val timeStamp = new Timestamp(parsedDate.getTime)
    return timeStamp
  }

  def dayOfYear (timestamp: Timestamp): Int =
  {
    val calendar = Calendar.getInstance
    calendar.setTimeInMillis(timestamp.getTime)
    return calendar.get(Calendar.DAY_OF_YEAR)
  }

  def dayOfMonth (timestamp: Timestamp): Int =
  {
    val calendar = Calendar.getInstance
    calendar.setTimeInMillis(timestamp.getTime)
    return calendar.get(Calendar.DAY_OF_MONTH)
  }

  // YOU NEED TO CHANGE THIS PART
  /**
  * function to calculate adjacent hot cells 
  *@param minX, minY, minZ, maxX, maxY, maxZ are the boundaries 
  *@param X,Y,Z cell cordinates 
  */
    def computeAdjacentHotcell( minX: Int, minY: Int, minZ: Int, maxX: Int, maxY: Int, maxZ: Int, X: Int, Y: Int, Z: Int): Int = {
        var count = 0

        // Cell is on X
        if (X == minX || X == maxX) {
            count += 1
        }
        // Cell is on X and Y
        if (Y == minY || Y == maxY) {
            count += 1
        }
        // Cell is on X, Y, and Z
        if (Z == minZ || Z == maxZ) {
            count += 1
        }

        if (count == 1) {
              return 18;
        } else if (count == 2) {
              return 12;
        } else if (count == 3) {
            return 8;
        }
    
      return 27;
    }

    /**
    * function calcluate the Gscore based on http://sigspatial2016.sigspatial.org/giscup2016/problem
    * @param x - x coordinate
    * @param y - y coordinate
    * @param z - z coordinate
    * @param adjacentHotcell - adjacent hotcells
    * @param cellNumber - Hotcell number for given x and y coordinate
    * @param avg - average of the hotcell for all the points
    * @param standardDeviation - standard deviation
    */
    def GScore(numCells: Int, x: Int, y: Int, z: Int, adjacentHotcell: Int, cellNumber: Int , avg: Double, standardDeviation: Double): Double = {
        var numberOfCells: Double = numCells.toDouble
        var hotCell: Double = adjacentHotcell.toDouble

        (cellNumber.toDouble - (avg * hotCell)) / (standardDeviation * math.sqrt((( hotCell * numberOfCells) - (hotCell * hotCell)) / (numberOfCells - 1.0)))
    }
}
