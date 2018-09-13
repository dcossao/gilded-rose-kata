package gildedrose.alternative

trait AlternativeItem {

  val name: String
  val sellIn: Int
  val quality: Int

  val HighestQuality = 50
  val LowestQuality = 0

  def updateQuality: AlternativeItem

  def increaseQualityByNumber(increase: Int): Int  = {
    val increasedQuality = quality + increase
    if (increasedQuality < HighestQuality) increasedQuality
    else HighestQuality
  }

  def decreaseQualityByNumber(decrease: Int) : Int ={
    val decreasedQuality = quality - decrease
    if (decreasedQuality > LowestQuality) decreasedQuality
    else LowestQuality
  }

}
