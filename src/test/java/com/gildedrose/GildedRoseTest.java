package com.gildedrose;

import static org.junit.Assert.*;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class GildedRoseTest
{
    private static final String FOO_NAME = "foo";
    private static final String AGED_BRIE_NAME = "Aged Brie";
    private static final String SULFURAS_NAME = "Sulfuras, Hand of Ragnaros";
    private static final String BACKSTAGE_NAME = "Backstage passes to a TAFKAL80ETC concert";
    private static final String CONJURED_NAME = "Conjured";

    @Test
    public void should_lower_sell_in_value()
    {
        Item item = setUpItem(FOO_NAME, 9, 3);

        assertEquals(8, item.sellIn);
    }

    @Test
    public void should_lower_quality_value()
    {
        Item item = setUpItem(FOO_NAME, 9, 3);

        assertEquals(2, item.quality);
    }

    @Test
    @Parameters({
            "0",
            "-1"
    })
    public void should_lower_quality_value_twice_when_expired(int sellIn)
    {
        Item item = setUpItem(FOO_NAME, sellIn, 9);

        assertEquals(7, item.quality);
    }

    @Test
    @Parameters({
            "0, 1",
            "1, 0"
    })
    public void should_never_has_negative_quality(int sellIn, int quality)
    {
        Item item = setUpItem(FOO_NAME, sellIn, quality);

        assertEquals(0, item.quality);
    }

    @Test
    public void aged_brie_should_increase_quality()
    {
        Item item = setUpItem(AGED_BRIE_NAME, 8, 23);

        assertEquals(24, item.quality);
    }

    @Test
    public void quality_should_never_be_more_than_fifty()
    {
        Item item = setUpItem(AGED_BRIE_NAME, 8, 50);

        assertEquals(50, item.quality);
    }

    @Test
    public void sulfuras_should_never_decrease_sell_in()
    {
        Item item = setUpItem(SULFURAS_NAME, 8, 34);

        assertEquals(8, item.sellIn);
    }

    @Test
    public void sulfuras_should_never_decrease_quality()
    {
        Item item = setUpItem(SULFURAS_NAME, 8, 80);

        assertEquals(80, item.quality);
    }

    @Test
    @Parameters({
            "15, 6, 7",
            "15, 50, 50"
    })
    public void backstage_increases_quality(int sellIn, int quality, int expected)
    {
        Item item = setUpItem(BACKSTAGE_NAME, sellIn, quality);

        assertEquals(expected, item.quality);
    }

    @Test
    @Parameters({
            "10",
            "9"
    })
    public void backstage_increases_quality_twice_on_ten_days_or_less(int sellIn)
    {
        Item item = setUpItem(BACKSTAGE_NAME, sellIn, 6);

        assertEquals(8, item.quality);
    }

    @Test
    @Parameters({
            "5",
            "4",
            "1"
    })
    public void backstage_increases_quality_by_three_on_five_days_or_less(int sellIn)
    {
        Item item = setUpItem(BACKSTAGE_NAME, sellIn, 6);

        assertEquals(9, item.quality);
    }

    @Test
    @Parameters({
            "0",
            "-1"
    })
    public void backstage_drops_quality_zero_after_concert(int sellIn)
    {
        Item item = setUpItem(BACKSTAGE_NAME, sellIn, 6);

        assertEquals(0, item.quality);
    }

    @Test
    @Parameters({
            "10, 34, 32",
            "0, 36, 34",
            "45, 0, -2"
    })
    public void conjured_should_degrade_quality_twice(int sellIn, int quality, int expected)
    {
        Item item = setUpItem(CONJURED_NAME, sellIn, quality);

        assertEquals(expected, item.quality);
    }

    private Item setUpItem(String name, int sellIn, int quality)
    {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        return app.items[0];
    }

}
