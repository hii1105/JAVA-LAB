public class Flower {
    private String id;
    private String name;
    private String soil;
    private String origin;
    private VisualParameters visual;
    private GrowingTips growing;
    private String multiplying;

    public static class VisualParameters {
        private String stemColor;
        private String leafColor;
        private int averageSize;
        public VisualParameters() {}
        public String getStemColor() { return stemColor; }
        public void setStemColor(String stemColor) { this.stemColor = stemColor; }
        public String getLeafColor() { return leafColor; }
        public void setLeafColor(String leafColor) { this.leafColor = leafColor; }
        public int getAverageSize() { return averageSize; }
        public void setAverageSize(int averageSize) { this.averageSize = averageSize; }
        @Override
        public String toString() {
            return String.format("стебель:%s, листья:%s, размер:%d", stemColor, leafColor, averageSize);
        }
    }

    public static class GrowingTips {
        private int temperature;
        private boolean light;
        private int watering;
        public GrowingTips() {}
        public int getTemperature() { return temperature; }
        public void setTemperature(int temperature) { this.temperature = temperature; }
        public boolean isLight() { return light; }
        public void setLight(boolean light) { this.light = light; }
        public int getWatering() { return watering; }
        public void setWatering(int watering) { this.watering = watering; }
        @Override
        public String toString() {
            return String.format("темп.:%d°C, свет:%b, полив:%d мл", temperature, light, watering);
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSoil() { return soil; }
    public void setSoil(String soil) { this.soil = soil; }
    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }
    public VisualParameters getVisual() { return visual; }
    public void setVisual(VisualParameters visual) { this.visual = visual; }
    public GrowingTips getGrowing() { return growing; }
    public void setGrowing(GrowingTips growing) { this.growing = growing; }
    public String getMultiplying() { return multiplying; }
    public void setMultiplying(String multiplying) { this.multiplying = multiplying; }

    @Override
    public String toString() {
        return String.format("Растение: %s (%s) | Почва:%s | Происхождение:%s | %s | %s | Размножение:%s",
                name, id, soil, origin, visual, growing, multiplying);
    }
}