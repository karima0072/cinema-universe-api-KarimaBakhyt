package utils;

import model.PricedItem;
import java.util.Comparator;
import java.util.List;

public class SortingUtils {

    public static <T extends PricedItem> void sortByFinalPriceAsc(List<T> items) {
        items.sort(Comparator.comparingDouble(PricedItem::getFinalPrice));
    }

    public static <T extends PricedItem> void sortByFinalPriceDesc(List<T> items) {
        items.sort(Comparator.comparingDouble(PricedItem::getFinalPrice).reversed());
    }
}