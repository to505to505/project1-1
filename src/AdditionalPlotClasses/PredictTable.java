package AdditionalPlotClasses;

public class PredictTable {
    public  double ATE_003;
    public double MON_014;
    public  double BKO_800;
    public  String name;

    public PredictTable(double ATE_003, double MON_014, double BKO_800, String name) {
        this.ATE_003 = ATE_003;
        this.MON_014 = MON_014;
        this.BKO_800 = BKO_800;
        this.name = name;
    }

    public  double getATE_003() {
        return ATE_003;
    }
    public  double getMON_014() {
        return MON_014;
    }
    public double getBKO_800() {
        return BKO_800;
    }
    public  String getName() {
        return name;
    }
    public void setATE_003(double ATE_003) {
        this.ATE_003 = ATE_003;
    }
    public void setMON_014(double MON_014) {
        this.MON_014 = MON_014;
    }
    public void setBKO_800(double BKO_800) {
        this.BKO_800 = BKO_800;
    }
    public  void setName(String name) {
        this.name = name;
    }
}
