package chapter6;

import entities.Dish;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static entities.Dish.dishTags;
import static entities.Dish.menu;
import static java.util.stream.Collectors.*;

public class Grouping {
    public static void main(String[] args) {
        Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType));
        System.out.println("Dishes grouped by type: " + dishesByType);

        // Group items using custom caloric level
        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = groupDishesByCaloricLevel();
        System.out.println("Dish by caloric level: " + dishesByCaloricLevel);

        // Group Caloric Dishes by Type
        Map<Dish.Type, List<Dish>> mostCaloricDishesByType = caloricDishesByType();
        System.out.println("Most Caloric Dishes By Type: " + mostCaloricDishesByType);

        Map<Dish.Type, List<String>> dishNamesByType = dishNamesByType();
        System.out.println("Dish Names By Type: " + dishNamesByType);

        Map<Dish.Type, Set<String>> tagsByDishType = tagsByDishType();
        System.out.println("Tags by Dish Type: " + tagsByDishType);
    }

    private static Map<CaloricLevel, List<Dish>> groupDishesByCaloricLevel() {
        return menu.stream().collect(
                groupingBy(dish -> {
                    if (dish.getCalories() <= 400) {
                        return CaloricLevel.DIET;
                    } else if (dish.getCalories() <= 700) {
                        return CaloricLevel.NORMAL;
                    } else {
                        return CaloricLevel.FAT;
                    }
                })
        );
    }

    private static Map<Dish.Type, List<Dish>> caloricDishesByType() {
        return menu.stream().collect(
                groupingBy(Dish::getType, filtering(dish -> dish.getCalories() > 500, toList()))
        );
    }

    private static Map<Dish.Type, List<String>> dishNamesByType() {
        return menu.stream()
                .collect(groupingBy(Dish::getType,
                        mapping(Dish::getName, toList())));
    }

    private static Map<Dish.Type, Set<String>> tagsByDishType() {
        return menu.stream()
                .collect(groupingBy(Dish::getType,
                        flatMapping(dish -> dishTags.get(dish.getName()).stream(), toSet())));
    }
}
