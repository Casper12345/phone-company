package com.phone

import java.io.File
import java.time.LocalTime
import org.scalatest.{FreeSpec, Matchers}

class FileParserSpec extends FreeSpec with Matchers {

  def fixture = new {
    val goodFileParser = new FileParserImpl(new File("src/test/resources/goodTest.log"))
    val badFileParser = new FileParserImpl(new File("src/test/resources/badTest.log"))

  }

  "on a correct file" - {

    "should parse the whole file to a list of objects" in {

      val l = List(
        Some(Call("A", "555-333-212", LocalTime.parse("00:02:03"))),
        Some(Call("A", "555-433-242", LocalTime.parse("00:06:41"))),
        Some(Call("A", "555-433-242", LocalTime.parse("00:01:03"))),
        Some(Call("B", "555-333-212", LocalTime.parse("00:01:20"))),
        Some(Call("A", "555-333-212", LocalTime.parse("00:01:10"))),
        Some(Call("B", "555-334-789", LocalTime.parse("00:00:03"))),
        Some(Call("A", "555-663-111", LocalTime.parse("00:02:03"))),
        Some(Call("B", "555-334-789", LocalTime.parse("00:00:53")))
      )

      val f = fixture
      val parsedList = f.goodFileParser.parseFile

      parsedList shouldEqual l
    }

  }

  "on an incorrect file" - {

    "should parse well-formed lines to objects and malformed lines to none" in {

      val l = List(
        None,
        None,
        None,
        Some(Call("B", "555-333-212", LocalTime.parse("00:01:20"))),
        Some(Call("A", "555-333-212", LocalTime.parse("00:01:10"))),
        Some(Call("B", "555-334-789", LocalTime.parse("00:00:03"))),
        Some(Call("A", "555-663-111", LocalTime.parse("00:02:03"))),
        Some(Call("B", "555-334-789", LocalTime.parse("00:00:53"))),
        None
      )

      val f = fixture

      val parsedList = f.badFileParser.parseFile

      parsedList shouldEqual l

    }
  }


}
