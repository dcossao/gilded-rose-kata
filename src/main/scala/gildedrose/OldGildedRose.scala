package gildedrose

class OldGildedRose(val items: Array[Item]) {


  def updateQuality() {
    for (i <- 0 until items.length) {

      //the reasoning is (for non special cases): lower the quality by one here and if
      //moreover it is expired then lower by an extra one at the end

      //------------- lower quality by 1 if it's not already 0 and not in the special cases
      if (!items(i).name.equals("Aged Brie")
        && !items(i).name.equals("Backstage passes to a TAFKAL80ETC concert")) {
        if (items(i).quality > 0) {
          if (!items(i).name.equals("Sulfuras, Hand of Ragnaros")) {
            items(i).quality = items(i).quality - 1
          }
        }
        //-----------
      }

      //******************* special cases: if the quality is lower than 50 then increase, concerts dealt with specially
      else {
        if (items(i).quality < 50) {
          items(i).quality = items(i).quality + 1

          if (items(i).name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            if (items(i).sellIn < 11) {
              if (items(i).quality < 50) {
                items(i).quality = items(i).quality + 1
              }
            }

            if (items(i).sellIn < 6) {
              if (items(i).quality < 50) {
                items(i).quality = items(i).quality + 1
              }
            }
          }
        }
      }
      //*****************


      if (!items(i).name.equals("Sulfuras, Hand of Ragnaros")) {
        items(i).sellIn = items(i).sellIn - 1
      }


      //when item is expired
      if (items(i).sellIn < 0) {


        //if it's not Brie
        if (!items(i).name.equals("Aged Brie")) {

          //~~~~~~~~~~~ beginning of block: if it's NOT a backstage pass
          if (!items(i).name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            //if quality>0
            if (items(i).quality > 0) {
              //if the item is not Sulfuras
              if (!items(i).name.equals("Sulfuras, Hand of Ragnaros")) {
                //decrease quality by 1
                items(i).quality = items(i).quality - 1
              }
            }
          }

          //but if it IS a backstage pass then make the quality 0
          else {
            items(i).quality = items(i).quality - items(i).quality
          }
          //~~~~~~~~~~~~ending of block


        }

        //but if it IS brie
        else {
          if (items(i).quality < 50) {
            items(i).quality = items(i).quality + 1
          }
        }
      }
    }
  }
}