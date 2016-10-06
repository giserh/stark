package dbis.stark.spatial

import org.apache.spark.Partitioner
import com.vividsolutions.jts.geom.Geometry
import scala.reflect.ClassTag
import org.apache.spark.rdd.RDD
import dbis.stark.STObject

object SpatialPartitioner {
  
  protected[stark] def getMinMax[G <: STObject, V](rdd: RDD[(G,V)]) = {
    val (minX, maxX, minY, maxY) = rdd.map{ case (g,_) =>
      val env = g.getEnvelopeInternal
      (env.getMinX, env.getMaxX, env.getMinY, env.getMaxY)
      
    }.reduce { (oldMM, newMM) => 
      val newMinX = if(oldMM._1 < newMM._1) oldMM._1 else newMM._1
      val newMaxX = if(oldMM._2 > newMM._2) oldMM._2 else newMM._2
      val newMinY = if(oldMM._3 < newMM._3) oldMM._3 else newMM._3
      val newMaxY = if(oldMM._4 > newMM._4) oldMM._4 else newMM._4
      
      (newMinX, newMaxX, newMinY, newMaxY)  
    }
    
    // do +1 for the max values to achieve right open intervals 
    (minX, maxX + 1, minY, maxY + 1)
  }
}

abstract class SpatialPartitioner/*[G <: STObject : ClassTag, V: ClassTag]*/(
    private val _minX: Double, private val _maxX: Double, private val _minY: Double, private val _maxY: Double
  ) extends Partitioner {

  def minX = _minX
  def maxX = _maxX
  def minY = _minY
  def maxY = _maxY
  
  def partitionBounds(idx: Int): Cell
  def partitionExtent(idx: Int): NRectRange
}

