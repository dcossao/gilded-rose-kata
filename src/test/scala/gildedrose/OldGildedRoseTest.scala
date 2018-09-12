package gildedrose

import gildedrose.SpecialItems.{AgedBrie, Sulfuras}
import org.scalatest.{BeforeAndAfter, FunSuite}

/** The goal here is to check my understanding of the existing code */

class OldGildedRoseTest extends FunSuite with BeforeAndAfter {

  private val testForQuality = "quality"
  private val testForSellIn = "sellIn"

  private var items: Array[Item] = _
  private var oldGildedRose: OldGildedRose = _

  private val ConcertPasses = "Backstage passes to a TAFKAL80ETC concert"

  before {
    items = Array[Item](
      new Item("+5 Dexterity Vest", 10, 20),
      new Item(AgedBrie, 2, 0),
      new Item(AgedBrie, 0, 1),
      new Item(AgedBrie, -1, 4),
      new Item("Elixir of the Mongoose", 0, 7),
      new Item(Sulfuras, 0, 80),
      new Item(Sulfuras, -1, 80),
      new Item(ConcertPasses, 15, 20),
      new Item(ConcertPasses, 10, 20),
      new Item(ConcertPasses, 9, 22),
      new Item(ConcertPasses, 5, 15),
      new Item(ConcertPasses, 1, 7),
      new Item(ConcertPasses, 0, 2),
      new Item(ConcertPasses, -3, 7),

      //this doesn't work as it should yet (it's treated as a regular item)
      new Item("Conjured Mana Cake", 1, 6),
      new Item("Red tuxedo ribbon for large cat", -1, 45)
    )
    oldGildedRose = new OldGildedRose(items)
  }


  //for all items
  test("quality is always non-negative") {
    updateQualityXTimes(3)
    items.foreach(item => assert(item.quality >= 0))
  }

  //sellIn behavior
  test("sulfuras don't have sellIn updated") {
    generalTestStructure(items, item => item.name == Sulfuras, x => x, testForSellIn)
  }

  test("all non sulfura items's sellIn decreases by 1 each day") {
    generalTestStructure(items, item => item.name != Sulfuras, x => x - 1, testForSellIn)
  }


  //quality per item

  //sulfuras
  test("sulfuras quality stays at constant 80") {
    updateQualityXTimes(5)
    val sulfuraItem: Item = filterByName(Sulfuras)(0)
    assert(sulfuraItem.quality == 80)
  }

  //all non sulfuras
  test("quality of non sulfura is always less or equal than 50") {
    updateQualityXTimes(2)
    val allNonSulfuras = items.filterNot(item => item.name == Sulfuras)
    allNonSulfuras.foreach(item => assert(item.quality <= 50))
  }


  //aged brie
  test("Aged Brie's quality increases at the rate of 1 point per day while sellIn > 0") {
    generalTestStructure(filterByName(AgedBrie), item => item.sellIn > 0, x => x + 1, testForQuality)
  }

  test("Aged Brie's quality increases at the rate of 2 points per day when sellIn <= 0") {
    generalTestStructure(filterByName(AgedBrie), item => item.sellIn <= 0, x => x + 2, testForQuality)
  }


  //regular items
  test("regular items' quality decreases at the rate of 1 point per day while sellIn >0") {
    val regularItems = items.filterNot(item => item.name == Sulfuras || item.name == AgedBrie || item.name == ConcertPasses)
    generalTestStructure(regularItems, item => item.sellIn > 0, x => x - 1, testForQuality)
  }


  test("regular items' quality decreases at the rate of 2 points per day when sellIn <=0") {
    val regularItems = items.filterNot(item => item.name == Sulfuras || item.name == AgedBrie || item.name == ConcertPasses)
    generalTestStructure(regularItems, item => item.sellIn <= 0, x => x - 2, testForQuality)
  }

  //concert passes
  test("passes for a concert with sellIn = 0 or lower will be worth 0 tomorrow") {
    val concertPassesForTodayorBeforeThat = filterByName(ConcertPasses).filter(item => item.sellIn <= 0).toList
    updateQualityXTimes(1)
    val updatedQuality = getPropertyAsList(concertPassesForTodayorBeforeThat, testForQuality)
    updatedQuality.foreach(q => assert(q === 0))
  }

  test("passes for a concert with 0 < sellIn <= 5 will increase their quality by 3 points tomorrow") {
    generalTestStructure(filterByName(ConcertPasses), item => item.sellIn > 0 && item.sellIn <= 5, x => x + 3, testForQuality)
  }

  test("passes for a concert with 5 < sellIn <= 10 will increase their quality by 2 points tomorrow") {
    generalTestStructure(filterByName(ConcertPasses), item => item.sellIn > 5 && item.sellIn <= 10, x => x + 2, testForQuality)
  }

  test("passes for a concert with 10 < sellIn will increase their quality by 1 point tomorrow") {
    generalTestStructure(filterByName(ConcertPasses), item => item.sellIn > 10, x => x + 1, testForQuality)
  }

  def generalTestStructure(someItems: Array[Item], p: Item => Boolean, f: Int => Int, property: String) = {
    val wantedItems = someItems.filter(p).toList
    val initialValues = getPropertyAsList(wantedItems, property)
    updateQualityXTimes(1)
    val updatedValues = getPropertyAsList(wantedItems, property)
    assert(updatedValues === initialValues.map(x => f(x)))
  }

  def filterByName(name: String) = items.filter(item => item.name.contains(name))

  def getPropertyAsList(someItems: List[Item], property: String): List[Int] = {
    if (property == testForQuality) someItems.map(item => item.quality)
    else someItems.map(item => item.sellIn)
  }

  def updateQualityXTimes(x: Int) = {
    for (i <- 1 to x)
      oldGildedRose.updateQuality()
  }

  /** methods for visualizing */
  /*
    test("print unmodified items") {
      printDay(0)
      printItems(items)
    }

    test("print items after first day") {
      oldGildedRose.updateQuality()
      printDay(1)
      printItems(items)
    }

  def printItems(items: Array[Item]) = {
      println("name, days left for sale, quality")
      items.foreach(item =>
        println(
          item.name + ", " +
            item.sellIn + ", " +
            item.quality
        ))
      println()
    }

    def printDay(day: Int) = println("------------- Day " + day + " ----------------")

  */

}
