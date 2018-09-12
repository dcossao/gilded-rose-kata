package gildedrose

import SpecialItems._

object TestItems {

  val sulfuraItem = new Item(Sulfuras, -1, 80)

  val regularExpired = makeExpiredItem("Red tuxedo ribbon for large cat")
  val regularNotExpired = makeNonExpiredItem("Nothing special")
  val regularExpiringToday = makeItemExpiringToday("Elixir of the Mongoose")

  val conjuredExpired = makeExpiredItem(Conjured + " vest worn by Mark Rutte")
  val conjuredNotExpired = makeNonExpiredItem(Conjured + " Mana Cake")
  val conjuredExpiringToday = makeItemExpiringToday(Conjured + " garden hose")

  val agedBrieExpired = makeExpiredItem(AgedBrie)
  val agedBrieNotExpired = makeNonExpiredItem(AgedBrie)
  val agedBrieExpiringToday = makeItemExpiringToday(AgedBrie)

  val concertPass15 = new Item(ConcertBackstagePasses + " to Statler Brothers concert", 15, 40)
  val concertPass10 = new Item(ConcertBackstagePasses + " to Britney Spears concert", 10, 3)
  val concertPass6 = new Item(ConcertBackstagePasses + " to silent concert", 6, 15)
  val concertPass5 = new Item(ConcertBackstagePasses + " to silent concert", 5, 15)
  val concertPass4 = new Item(ConcertBackstagePasses + " to Backstreet Boys concert", 4, 0)
  val concertPass0 = new Item(ConcertBackstagePasses + " to Backstreet Boys concert", 0, 0)
  val concertPassNegative = new Item(ConcertBackstagePasses + "to children's choir concert after they've grown up", -65000, 49)

  def makeNonExpiredItem(name: String): Item = new Item(name, 5, 9)

  def makeItemExpiringToday(name: String): Item = new Item(name, 0, 20)

  def makeExpiredItem(name: String): Item = new Item(name, -2, 7)

  val testItems: Array[Item] = Array(
    sulfuraItem,
    regularNotExpired,
    regularExpiringToday,
    regularExpired,
    conjuredNotExpired,
    conjuredExpiringToday,
    conjuredExpired,
    agedBrieNotExpired,
    agedBrieExpiringToday,
    agedBrieExpired,
    concertPass15,
    concertPass10,
    concertPass6,
    concertPass5,
    concertPass4,
    concertPass0,
    concertPassNegative
  )

}
