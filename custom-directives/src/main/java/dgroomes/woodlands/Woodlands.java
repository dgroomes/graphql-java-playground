package dgroomes.woodlands;

import java.util.List;

/**
 * A limited listing of woodlands. I had some fun learning and re-learning about types of forests and the difference
 * between a woodland, forest and shrubland. I have a lot more to learn, but below are some basic woodland examples.
 */
public class Woodlands {

    public static final Woodland TROPICAL_RAINFOREST = new Woodland(WoodlandType.TROPICAL,
            List.of("Jaguar", "Toucan", "Red-eyed tree frog"));

    public static final Woodland CONIFEROUS_FOREST = new Woodland(WoodlandType.TEMPERATE,
            List.of("Caribou", "Wolverine", "Spotted owl"));

    public static final Woodland DESERT_WOODLAND = new Woodland(WoodlandType.DESERT,
            List.of("Burrowing owl", "Roadrunner", "Jackrabbit"));

}
