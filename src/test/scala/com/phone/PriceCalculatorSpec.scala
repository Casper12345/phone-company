package com.phone

import java.time.LocalTime
import org.scalatest.{FreeSpec, Matchers}

class PriceCalculatorSpec extends FreeSpec with Matchers {

  def fixture = new {

    val listOfCalls = List(
      Call("A", "555-333-212", LocalTime.parse("00:02:03")),
      Call("A", "555-433-242", LocalTime.parse("00:06:41")),
      Call("A", "555-433-242", LocalTime.parse("00:01:03")),
      Call("B", "555-333-212", LocalTime.parse("00:01:20")),
      Call("A", "555-333-212", LocalTime.parse("00:01:10")),
      Call("B", "555-334-789", LocalTime.parse("00:10:03")),
      Call("A", "555-663-111", LocalTime.parse("00:02:03")),
      Call("B", "555-334-789", LocalTime.parse("00:09:53"))
    )

    val priceCalculator = new PriceCalculatorImpl(listOfCalls)
    val priceCalculatorLessThan1Pence = new PriceCalculatorImpl(
      List(
        Call("B", "555-334-789", LocalTime.parse("00:00:03")),
        Call("B", "555-334-789", LocalTime.parse("00:00:03"))
      )
    )

  }


  "should calculate expected price" in {
    val f = fixture
    val l = f.priceCalculator.totalCostPerCustomer

    l.head._2 shouldEqual 30
    l(1)._2 shouldEqual 21

  }

  "should not bill prices under 1 pence" in {
    val f = fixture
    val l = f.priceCalculatorLessThan1Pence.totalCostPerCustomer
    
    l.head._2 shouldEqual 0

  }

}
