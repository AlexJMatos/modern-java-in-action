package chapter4;

import entities.Dish;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Streams {
    private static final List<Dish> menu = List.of(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH));

    private static final List<Dish> specialMenu = List.of(
            new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER));

    public static void main(String[] args) {
        List<String> threeHighCaloricDishNames = menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .map(Dish::getName)
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(threeHighCaloricDishNames);

        Optional<String> highestCaloricDish = menu.stream()
                .max(Comparator.comparing(Dish::getCalories))
                .map(Dish::getName);
        highestCaloricDish.ifPresent(System.out::println);

        List<Dish> vegetarianDishes = menu.stream()
                .filter(Dish::isVegetarian)
                .collect(Collectors.toList());
        System.out.println(vegetarianDishes);

        List<Integer> numbers = List.of(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);

        List<Dish> lightDishes = specialMenu.stream()
                .takeWhile(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList());
        System.out.println("Light Dishes: " + lightDishes);

        List<Dish> caloricDishes = specialMenu.stream()
                .dropWhile(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList());
        System.out.println("Caloric Dishes: " + caloricDishes);

        List<Dish> limitToThreeDishes = specialMenu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(limitToThreeDishes);

        List<Dish> skipTwoDishes = menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .skip(2)
                .collect(Collectors.toList());
        System.out.println(skipTwoDishes);

        // Quiz 5.1: Filtering first two meat dishes
        menu.stream()
                .filter(dish -> dish.getType() == Dish.Type.MEAT)
                .limit(2)
                .forEach(System.out::println);

        List<String> words = Arrays.asList("Modern", "Java", "In", "Action");
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(Collectors.toList());
        System.out.println(wordLengths);

        String result = Stream.of("Hello", "World")
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.joining());
        System.out.println(result);

        List<Integer> squareNumbers = Stream.of(1, 2, 3, 4, 5)
                .map(n -> (int) Math.pow(n, 2))
                .collect(Collectors.toList());
        System.out.println(squareNumbers);

        List<int[]> pairs = Stream.of(1, 2, 3)
                .flatMap(i -> Stream.of(3, 4)
                        .filter(j -> (i + j) % 3 == 0)
                        .map(j -> new int[]{i, j}))
                .collect(Collectors.toList());

        List<String> collect = pairs.stream()
                .map(array -> String.format("(%s)", Arrays.stream(array)
                        .mapToObj(String::valueOf).collect(Collectors.joining(", "))))
                .collect(Collectors.toList());
        System.out.println(collect);

        if (menu.stream().anyMatch(Dish::isVegetarian)) {
            System.out.println("The menu is somewhat vegetarian friendly");
        }

        System.out.printf("The menu is healthy: %s%n", menu.stream()
                .allMatch(dish -> dish.getCalories() < 1000));
        System.out.printf("The menu is healthy: %s%n", menu.stream()
                .noneMatch(dish -> dish.getCalories() >= 1000));

        Optional<Dish> optionalDish = menu.stream()
                .filter(Dish::isVegetarian)
                .findAny();

        numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Integer sumOfSquares = numbers.stream()
                .reduce(0, (a, b) -> a + (int) Math.pow(b, 2));
        System.out.println(sumOfSquares);

        // Maximum
        Optional<Integer> max = numbers.stream()
                .reduce(Integer::max);
        System.out.println(max.orElse(1000));

        // Minimum
        Optional<Integer> min = numbers.stream()
                .reduce(Integer::min);
        System.out.println(min.orElse(1000));
    }
}
