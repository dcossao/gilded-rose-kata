package gildedrose.alternative

import gildedrose.SpecialItems

class Sulfuras(val sellIn: Int, val quality: Int) extends AlternativeItem {
  val name: String = SpecialItems.Sulfuras

  override def updateQuality: AlternativeItem = this
}
