package gildedrose.alternative

class RegularItem(val name: String, val sellIn: Int, val quality: Int) extends AlternativeItem {

  def updateQuality =
    if (sellIn > 0) new RegularItem(name, sellIn - 1, decreaseQualityByNumber(1))
    else new RegularItem(name, sellIn - 1, decreaseQualityByNumber(2))
}
