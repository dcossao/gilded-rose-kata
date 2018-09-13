package gildedrose.alternative

class AlternativeGildedRose(val items: List[AlternativeItem]) {

  def updateAllItems: List[AlternativeItem] = items.map(item => item.updateQuality)

}
