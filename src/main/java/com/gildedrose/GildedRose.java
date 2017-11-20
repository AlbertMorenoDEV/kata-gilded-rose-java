package com.gildedrose;

class GildedRose
{
	Item[] items;

	public GildedRose(Item[] items)
	{
		this.items = items;
	}

	void updateQuality()
	{
		for (Item item : items) {
			processItem(item);
		}
	}

	private void processItem(Item item)
	{
		if (isAgedBrie(item)) {
			processAgedBrie(item);
		} else if (isBackstagePasses(item)) {
			processBackstagePasses(item);
		} else if (isConjured(item)) {
			processConjured(item);
		} else if (isSulfuras(item)) {
			decreaseSellIn(item);
		} else {
			processOthers(item);
		}
	}

	private void processOthers(Item item)
	{
		if (item.quality > 0) {
            decreaseQuality(item);
        }

		if (item.sellIn < 1 && item.quality > 0) {
            decreaseQuality(item);
        }

		decreaseSellIn(item);
	}

	private void processConjured(Item item)
	{
		decreaseQuality(item);
		decreaseQuality(item);
		decreaseSellIn(item);
	}

	private void processBackstagePasses(Item item)
	{
		increaseQuality(item);

		if (item.sellIn < 11) {
            increaseQuality(item);
        }

		if (item.sellIn < 6) {
            increaseQuality(item);
        }

		if (item.sellIn < 1) {
            item.quality = 0;
        }

		decreaseSellIn(item);
	}

	private void processAgedBrie(Item item)
	{
		increaseQuality(item);
		if (item.sellIn < 1) {
            increaseQuality(item);
        }

		decreaseSellIn(item);
	}

	private void decreaseSellIn(Item item)
	{
		if (!isSulfuras(item)) {
			item.sellIn = item.sellIn - 1;
		}
	}

	private void increaseQuality(Item item)
	{
		if (item.quality < 50) {
			item.quality = item.quality + 1;
		}
	}

	private void decreaseQuality(Item item)
	{
		item.quality = item.quality - 1;
	}

	private boolean isSulfuras(Item item)
	{
		return item.name.equals("Sulfuras, Hand of Ragnaros");
	}

	private boolean isBackstagePasses(Item item)
	{
		return item.name.equals("Backstage passes to a TAFKAL80ETC concert");
	}

	private boolean isAgedBrie(Item item)
	{
		return item.name.equals("Aged Brie");
	}

	private boolean isConjured(Item item)
	{
		return item.name.equals("Conjured");
	}
}
