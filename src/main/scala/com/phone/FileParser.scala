package com.phone

import java.io.{BufferedReader, File, FileReader}
import java.time.LocalTime
import javax.inject.Inject
import scala.compat.java8.StreamConverters._
import scala.util.Try

trait FileParser {

  /**
    * Method for parsing file to a list of call objects.
    *
    * @return a list of parsed call objects.
    */
  def parseFile: List[Option[Call]]

}

class FileParserImpl @Inject()(
                                file: File
                              ) extends FileParser {


  override def parseFile: List[Option[Call]] =
    new BufferedReader(new FileReader(file)).lines().toScala[Stream].map { line =>
      val values = line.split(" ")
      if (values.length == 3) {
        Try(LocalTime.parse(values(2))).toOption.map(time =>
          Call(values.head, values(1), time)
        )
      } else {
        None
      }
    }.toList

}


case class Call(customerId: String, calledNumber: String, duration: LocalTime)