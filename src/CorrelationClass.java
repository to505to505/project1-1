public class CorrelationClass {
    private String data_name;
    private String course_name;
    private double JTE_234;
    private double ATE_003;
    private double TGL_013;
    private double PPL_239;
    private double WDM_974;
    private double GHL_823;
    private double HLU_200;
    private double MON_014;
    private double FEA_907;
    private double LPG_307;
    private double TSO_010;
    private double LDE_009;
    private double JJP_001;
    private double MTE_004;
    private double LUU_003;
    private double LOE_103;
    private double PLO_132;
    private double BKO_800;
    private double SLE_332;
    private double BKO_801;
    private double DSE_003;
    private double DSE_005;
    private double ATE_014;
    private double JTW_004;
    private double ATE_008;
    private double DSE_007;
    private double ATE_214;
    private double JHF_101;
    private double KMO_007;
    private double WOT_104;

    
    public CorrelationClass(String data_name, String course_name) {
        this.course_name = course_name;
        double[] corr = DataFunc.correlationResNew(data_name, course_name);
        this.JTE_234 = corr[0];
        this.ATE_003 = corr[1];
        this.TGL_013 = corr[2];
        this.PPL_239 = corr[3];
        this.WDM_974 = corr[4];
        this.GHL_823 = corr[5];
        this.HLU_200 = corr[6];
        this.MON_014 = corr[7];
        this.FEA_907 = corr[8];
        this.LPG_307 = corr[9];
        this.TSO_010 = corr[10];
        this.LDE_009 = corr[11];
        this.JJP_001 = corr[12];
        this.MTE_004 = corr[13];
        this.LUU_003 = corr[14];
        this.LOE_103 = corr[15];
        this.PLO_132 = corr[16];
        this.BKO_800 = corr[17];
        this.SLE_332 = corr[18];
        this.BKO_801 = corr[19];
        this.DSE_003 = corr[20];
        this.DSE_005 = corr[21];
        this.ATE_014 = corr[22];
        this.JTW_004 = corr[23];
        this.ATE_008 = corr[24];
        this.DSE_007 = corr[25];
        this.ATE_214 = corr[26];
        this.JHF_101 = corr[27];
        this.KMO_007 = corr[28];
        this.WOT_104 = corr[29];
        
}
}