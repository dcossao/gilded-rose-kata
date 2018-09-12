package gildedrose

import gildedrose.SpecialItems._

class GildedRose(val items: Array[Item]) {

  /** These Ints are vals in case the Inn decides to change quality values in the future */
  val HighestQuality = 50
  val LowestQuality = 0

  def updateQuality: Array[Item] = items.map(item => processCriteria(item))

  def processCriteria(item: Item): Item = {
    val name = item.name
    if (name == Sulfuras) item
    else if (name == AgedBrie) processAgedBrie(item)
    else if (name.contains(ConcertBackstagePasses)) processConcertPasses(item)
    else if (name.contains(Conjured)) processConjuredItem(item)
    else processRegularItem(item)
  }

  def increaseQualityByNumber(item: Item, number: Int): Int = {
    val increasedQuality = item.quality + number
    if (increasedQuality < HighestQuality) increasedQuality else HighestQuality
  }

  def decreaseQualityByNumber(item: Item, number: Int): Int = {
    val decreasedQuality = item.quality - number
    if (decreasedQuality > LowestQuality) decreasedQuality else LowestQuality
  }

  def processAgedBrie(item: Item): Item =
    if (item.sellIn > 0) makeNewItem(item, increaseQualityByNumber(item, 1))
    else makeNewItem(item, increaseQualityByNumber(item, 2))


  /** Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or
    * less but drops to 0 after the concert */
  def processConcertPasses(item: Item): Item = {
    val daysUntilConcert = item.sellIn
    if (daysUntilConcert <= 0) makeNewItem(item, 0)
    else if (daysUntilConcert <= 5) makeNewItem(item, increaseQualityByNumber(item, 3))
    else if (daysUntilConcert <= 10) makeNewItem(item, increaseQualityByNumber(item, 2))
    else makeNewItem(item, increaseQualityByNumber(item, 1))
  }

  def processRegularItem(item: Item): Item =
    if (item.sellIn > 0) makeNewItem(item, decreaseQualityByNumber(item, 1))
    else makeNewItem(item, decreaseQualityByNumber(item, 2))

  def processConjuredItem(item: Item): Item =
    if (item.sellIn > 0) makeNewItem(item, decreaseQualityByNumber(item, 2))
    else makeNewItem(item, decreaseQualityByNumber(item, 4))

  def makeNewItem(item: Item, updatedQuality: Int) = new Item(item.name, item.sellIn - 1, updatedQuality)

}

