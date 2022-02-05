package dgroomes;

/**
 * States of water (ice, liquid, gas)
 *
 * Note: This is a contrived example. This might as well be an enum.
 */
public record StateOfH20(String description, StateOfH20 nextState) {

    public static final StateOfH20 WATER_VAPOR = new StateOfH20("Water in the air is vapor (the gas state)", null);
    public static final StateOfH20 WATER = new StateOfH20("Water (the liquid state)", WATER_VAPOR);
    public static final StateOfH20 ICE = new StateOfH20("Frozen water is ice (the solid state)", WATER);
}
