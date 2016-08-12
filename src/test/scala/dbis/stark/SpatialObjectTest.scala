package dbis.stark

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import com.vividsolutions.jts.io.WKTReader

class SpatialObjectTest extends FlatSpec with Matchers {
    
  "A spatial object" should "intersect with a contained polygon and interval" in {
    
    val g = new WKTReader().read("POLYGON((0 0, 10 0, 10 10, 0 10, 0 0))")
    val t = Interval(10L, 100L)
    val o = SpatialObject(g, t)
    
    
    val qryG = new WKTReader().read("POLYGON((1 1, 8 1, 8 8, 1 8, 1 1))")
    val qryT = Interval(20L, 30L)
    val qryO = SpatialObject(qryG, qryT)
    
    withClue(s"$o intersects at $qryO"){ o.intersects(qryO) shouldBe true }
  }
  
  it should "intersect with a contained polygon and instant" in {
    
    val g = new WKTReader().read("POLYGON((0 0, 10 0, 10 10, 0 10, 0 0))")
    val t = Interval(10L, 100L)
    val o = SpatialObject(g, t)
    
    
    val qryG = new WKTReader().read("POLYGON((1 1, 8 1, 8 8, 1 8, 1 1))")
    val qryT = Instant(50L)
    val qryO = SpatialObject(qryG, qryT)
    
    withClue(s"$o intersects at $qryO"){ o.intersects(qryO) shouldBe true }
  }
  
  it should "intersect with a contained point and instant" in {
    
    val g = new WKTReader().read("POLYGON((0 0, 10 0, 10 10, 0 10, 0 0))")
    val t = Interval(10L, 100L)
    val o = SpatialObject(g, t)
    
    
    val qryG = new WKTReader().read("POINT(7 8)")
    val qryT = Instant(89L)
    val qryO = SpatialObject(qryG, qryT)
    
    withClue(s"$o intersects at $qryO"){ o.intersects(qryO) shouldBe true }
  }
  
  it should "intersect with itself" in {
    
    val g = new WKTReader().read("POLYGON((0 0, 10 0, 10 10, 0 10, 0 0))")
    val t = Interval(10L, 100L)
    val o = SpatialObject(g, t)
    
    
    val qryG = new WKTReader().read("POLYGON((0 0, 10 0, 10 10, 0 10, 0 0))")
    val qryT = Interval(10L, 100L)
    val qryO = SpatialObject(qryG, qryT)
    
    withClue(s"$o intersects at $qryO"){ o.intersects(qryO) shouldBe true }
  }
  
  it should "not intersect with defined vs undefined time in qry" in {

	  val g = new WKTReader().read("POLYGON((0 0, 10 0, 10 10, 0 10, 0 0))")
	  val t = Interval(10L, 100L)
	  val o = SpatialObject(g, t)


	  val qryG = new WKTReader().read("POLYGON((1 1, 8 1, 8 8, 1 8, 1 1))")
	  val qryO = SpatialObject(qryG, None)

	  withClue(s"$o intersects at $qryO"){ o.intersects(qryO) shouldBe false }
  }
  
  it should "not intersect with undefined time vs defined time in qry" in {

	  val g = new WKTReader().read("POLYGON((0 0, 10 0, 10 10, 0 10, 0 0))")
	  val t = None
	  val o = SpatialObject(g, t)


	  val qryG = new WKTReader().read("POLYGON((1 1, 8 1, 8 8, 1 8, 1 1))")
	  val qryT = Instant(89L)
	  val qryO = SpatialObject(qryG, qryT)

	  withClue(s"$o intersects at $qryO"){ o.intersects(qryO) shouldBe false }
  }
  
  it should "intersect with both undefined times" in {

	  val g = new WKTReader().read("POLYGON((0 0, 10 0, 10 10, 0 10, 0 0))")
	  val t = None
	  val o = SpatialObject(g, t)


	  val qryG = new WKTReader().read("POLYGON((1 1, 8 1, 8 8, 1 8, 1 1))")
	  val qryO = SpatialObject(qryG, None)

	  withClue(s"$o intersects at $qryO"){ o.intersects(qryO) shouldBe true }
  }
  
  it should "not intersect if geometry is not contained" in {

	  val g = new WKTReader().read("POLYGON((0 0, 10 0, 10 10, 0 10, 0 0))")
	  val t = Instant(89L)
	  val o = SpatialObject(g, t)


	  val qryG = new WKTReader().read("POINT( 100 100 )")
	  val qryT = Instant(89L)
	  val qryO = SpatialObject(qryG, qryT)

	  withClue(s"$o intersects at $qryO"){ o.intersects(qryO) shouldBe false }
  }

//########################################################################################
  
  it should "correctly contain a polygon and interval" in {
    
    val g = new WKTReader().read("POLYGON((0 0, 10 0, 10 10, 0 10, 0 0))")
    val t = Interval(10L, 100L)
    val o = SpatialObject(g, t)
    
    
    val qryG = new WKTReader().read("POLYGON((1 1, 8 1, 8 8, 1 8, 1 1))")
    val qryT = Interval(20L, 30L)
    val qryO = SpatialObject(qryG, qryT)
    
    withClue(s"$o contains at $qryO"){ o.contains(qryO) shouldBe true }
  }
  
  it should "correctly contain a polygon and instant" in {
    
    val g = new WKTReader().read("POLYGON((0 0, 10 0, 10 10, 0 10, 0 0))")
    val t = Interval(10L, 100L)
    val o = SpatialObject(g, t)
    
    
    val qryG = new WKTReader().read("POLYGON((1 1, 8 1, 8 8, 1 8, 1 1))")
    val qryT = Instant(50L)
    val qryO = SpatialObject(qryG, qryT)
    
    withClue(s"$o contains at $qryO"){ o.contains(qryO) shouldBe true }
  }
  
  it should "correctly contain a point and instant" in {
    
    val g = new WKTReader().read("POLYGON((0 0, 10 0, 10 10, 0 10, 0 0))")
    val t = Interval(10L, 100L)
    val o = SpatialObject(g, t)
    
    
    val qryG = new WKTReader().read("POINT(7 8)")
    val qryT = Instant(89L)
    val qryO = SpatialObject(qryG, qryT)
    
    withClue(s"$o contains at $qryO"){ o.contains(qryO) shouldBe true }
  }
  
  it should "correctly contain itself" in {
    
    val g = new WKTReader().read("POLYGON((0 0, 10 0, 10 10, 0 10, 0 0))")
    val t = Interval(10L, 100L)
    val o = SpatialObject(g, t)
    
    
    val qryG = new WKTReader().read("POLYGON((0 0, 10 0, 10 10, 0 10, 0 0))")
    val qryT = Interval(10L, 100L)
    val qryO = SpatialObject(qryG, qryT)
    
    withClue(s"$o contains at $qryO"){ o.contains(qryO) shouldBe true }
  }
  
  it should "not contain with defined vs undefined time in qry" in {

	  val g = new WKTReader().read("POLYGON((0 0, 10 0, 10 10, 0 10, 0 0))")
	  val t = Interval(10L, 100L)
	  val o = SpatialObject(g, t)


	  val qryG = new WKTReader().read("POLYGON((1 1, 8 1, 8 8, 1 8, 1 1))")
	  val qryO = SpatialObject(qryG, None)

	  withClue(s"$o contains at $qryO"){ o.contains(qryO) shouldBe false }
  }
  
  it should "not contain with undefined time vs defined time in qry" in {

	  val g = new WKTReader().read("POLYGON((0 0, 10 0, 10 10, 0 10, 0 0))")
	  val t = None
	  val o = SpatialObject(g, t)


	  val qryG = new WKTReader().read("POLYGON((1 1, 8 1, 8 8, 1 8, 1 1))")
	  val qryT = Instant(89L)
	  val qryO = SpatialObject(qryG, qryT)

	  withClue(s"$o contains at $qryO"){ o.contains(qryO) shouldBe false }
  }
  
  it should "correctly contain both undefined times" in {

	  val g = new WKTReader().read("POLYGON((0 0, 10 0, 10 10, 0 10, 0 0))")
	  val t = None
	  val o = SpatialObject(g, t)


	  val qryG = new WKTReader().read("POLYGON((1 1, 8 1, 8 8, 1 8, 1 1))")
	  val qryO = SpatialObject(qryG, None)

	  withClue(s"$o contains at $qryO"){ o.contains(qryO) shouldBe true }
  }
  
  it should "not contain if geometry is not contained" in {

	  val g = new WKTReader().read("POLYGON((0 0, 10 0, 10 10, 0 10, 0 0))")
	  val t = Instant(89L)
	  val o = SpatialObject(g, t)


	  val qryG = new WKTReader().read("POINT( 100 100 )")
	  val qryT = Instant(89L)
	  val qryO = SpatialObject(qryG, qryT)

	  withClue(s"$o contains at $qryO"){ o.contains(qryO) shouldBe false }
  }  
}