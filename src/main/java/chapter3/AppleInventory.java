package chapter3;

import entities.Apple;

import java.util.Arrays;
import java.util.List;

public class AppleInventory {
    public static final List<Apple> inventory = Arrays.asList(
            new Apple(80, Apple.Color.GREEN),
            new Apple(180, Apple.Color.GREEN),
            new Apple(160, Apple.Color.RED),
            new Apple(90, Apple.Color.RED)
    );
}
