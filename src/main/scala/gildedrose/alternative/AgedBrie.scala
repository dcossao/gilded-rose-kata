package gildedrose.alternative

import gildedrose.SpecialItems

class AgedBrie (val sellIn: Int, val quality: Int) extends AlternativeItem {

  val name = SpecialItems.AgedBrie

  def updateQuality =
    if (sellIn > 0) new AgedBrie(sellIn - 1, increaseQualityByNumber(1))
    else new AgedBrie(sellIn - 1, increaseQualityByNumber(2))

}
