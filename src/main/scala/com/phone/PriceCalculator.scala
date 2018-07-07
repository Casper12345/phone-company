package com.phone

import javax.inject.Inject

trait PriceCalculator {
  /**
    * Method for calculating total cost per customer.
    *
    * @return list (customerId, total cost in pence)
    */
  def totalCostPerCustomer: List[(String, Int)]

}

class PriceCalculatorImpl @Inject()(
                                     parsedCalls: List[Call]
                                   ) extends PriceCalculator {

  override def totalCostPerCustomer: List[(String, Int)] = {
    val longestCallRemoved = parsedCalls.sortWith((a, b) => a.duration.isAfter(b.duration)).tail

    longestCallRemoved.groupBy(a => a.customerId).toList.map { item =>
      val cost = item._2.map { call =>
        if (call.duration.getMinute < 3) {
          call.duration.toSecondOfDay * 5
        } else {
          call.duration.toSecondOfDay * 3
        }
      }
      (item._1, cost.sum / 100)
    }
  }

}
