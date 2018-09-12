package gildedrose

import gildedrose.SpecialItems.Sulfuras
import TestItems._

import org.scalatest.{BeforeAndAfter, FunSuite}

class GildedRoseTest extends FunSuite with BeforeAndAfter {

  val gildedRose = new GildedRose(testItems)

  test("testProcessConcertPasses") {
    //sellIn > 10 increase by 1
    assert(gildedRose.processConcertPasses(concertPass15).quality === concertPass15.quality + 1)

    //5 < sellIn <= 10 increase by 2
    assert(gildedRose.processConcertPasses(concertPass10).quality === concertPass10.quality + 2)
    assert(gildedRose.processConcertPasses(concertPass6).quality === concertPass6.quality + 2)

    //0 < sellIn <= 5 increase by 3
    assert(gildedRose.processConcertPasses(concertPass5).quality === concertPass5.quality + 3)
    assert(gildedRose.processConcertPasses(concertPass4).quality === concertPass4.quality + 3)

    // sellIn <= 0 is worthless
    assert(gildedRose.processConcertPasses(concertPass0).quality === 0)
    assert(gildedRose.processConcertPasses(concertPassNegative).quality === 0)

    assert(gildedRose.processConcertPasses(concertPass6).sellIn === concertPass6.sellIn - 1)
  }

  test("testProcessRegularItem") {
    assert(gildedRose.processRegularItem(regularNotExpired).quality === regularNotExpired.quality - 1)
    assert(gildedRose.processRegularItem(regularExpiringToday).quality === regularExpiringToday.quality - 2)
    assert(gildedRose.processRegularItem(regularExpired).quality === regularExpired.quality - 2)

    assert(gildedRose.processRegularItem(regularExpiringToday).sellIn === regularExpiringToday.sellIn - 1)
  }

  test("testProcessAgedBrie") {
    assert(gildedRose.processAgedBrie(agedBrieNotExpired).quality === agedBrieNotExpired.quality + 1)
    assert(gildedRose.processAgedBrie(agedBrieExpiringToday).quality === agedBrieExpiringToday.quality + 2)
    assert(gildedRose.processAgedBrie(agedBrieExpired).quality === agedBrieExpired.quality + 2)

    assert(gildedRose.processAgedBrie(agedBrieNotExpired).sellIn === agedBrieNotExpired.sellIn - 1)
  }

  test("testProcessConjuredItem") {
    assert(gildedRose.processConjuredItem(conjuredNotExpired).quality === conjuredNotExpired.quality - 2)
    assert(gildedRose.processConjuredItem(conjuredExpiringToday).quality == conjuredExpiringToday.quality - 4)
    assert(gildedRose.processConjuredItem(conjuredExpired).quality === conjuredExpired.quality - 4)

    assert(gildedRose.processConjuredItem(conjuredExpiringToday).sellIn === conjuredExpiringToday.sellIn - 1)
  }

  test("sulfuras' sellIn doesn't get updated") {
    val sulfurasSellIn = testItems.toList.filter(item => item.name == Sulfuras).map(item => item.sellIn)
    val updatedSulfurasSellIn = gildedRose.updateQuality.toList.filter(item => item.name == Sulfuras).map(item => item.sellIn)

    assert(updatedSulfurasSellIn == sulfurasSellIn)
  }

  test("test updateQuality: sulfuras always have quality 80") {
    val allUpdatedSulfuras = gildedRose.updateQuality.filter(item => item.name == Sulfuras)
    allUpdatedSulfuras.foreach(item => assert(item.quality === 80))
  }

  test("test updateQuality: quality is always non negative") {
    gildedRose.updateQuality.foreach(item => assert(item.quality >= 0))
  }

  test("test updateQuality: quality of non sulfuras is always <= 50") {
    val nonSulfuras = gildedRose.updateQuality.filterNot(item => item.name == Sulfuras)
    nonSulfuras.foreach(item => assert(item.quality <= 50))
  }

}

