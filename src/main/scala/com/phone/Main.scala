package com.phone

import java.io.File
import com.google.inject.{Guice, Key, PrivateModule, Provides}
import net.codingwell.scalaguice.ScalaPrivateModule
import org.slf4j.{Logger, LoggerFactory}


object Main extends App {
  val logger: Logger = LoggerFactory.getLogger(this.getClass)
  val injector = Guice.createInjector(new MainModule)
  val priceCalculator = injector.getInstance(Key.get(classOf[PriceCalculator]))

  priceCalculator.totalCostPerCustomer.foreach( item =>
    logger.info(s"The total cost for customer: ${item._1} is ${item._2} pence")
  )
}

class MainModule extends PrivateModule with ScalaPrivateModule {
  override def configure(): Unit = {
    bind[FileParser].to[FileParserImpl]
    bind[PriceCalculator].to[PriceCalculatorImpl]
    expose[FileParser]
    expose[PriceCalculator]
  }


  @Provides
  def file: File = {
    new File("src/main/resources/calls.log")
  }

  @Provides
  def parsedCalls(fileParser: FileParser): List[Call] = {
    fileParser.parseFile.flatten
  }

}