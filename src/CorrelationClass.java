public class CorrelationClass {
    public final double  max_for_euclid = 294.334843333235;
    public String data_name;
    public String course_name;
    public double JTE_234;
    public double ATE_003;
    public double TGL_013;
    public double PPL_239;
    public double WDM_974;
    public double GHL_823;
    public double HLU_200;
    public double MON_014;
    public double FEA_907;
    public double LPG_307;
    public double TSO_010;
    public double LDE_009;
    public double JJP_001;
    public double MTE_004;
    public double LUU_003;
    public double LOE_103;
    public double PLO_132;
    public double BKO_800;
    public double SLE_332;
    public double BKO_801;
    public double DSE_003;
    public double DSE_005;
    public double ATE_014;
    public double JTW_004;
    public double ATE_008;
    public double DSE_007;
    public double ATE_214;
    public double JHF_101;
    public double KMO_007;
    public double WOT_104;

    
    public CorrelationClass(String data_name, String course_name, String type) {
        String original_name = course_name.replace('_', '-');
        this.course_name = course_name;
        double[] corr = new double[30];
        if(type.equals("Correlation")){
        corr = DataFunc.correlationResNew(data_name, original_name);
        } else if(type.equals("Cosine similarity")){
            corr = DataFunc.similarityResNew(data_name, original_name);
        } else if(type.equals("Euclidean distance")){
            corr = DataFunc.euclidianResNew(data_name, original_name);
            for(int i = 0; i < corr.length; i++){
                corr[i] = Math.round((corr[i]/max_for_euclid) * 100)/100.0;
            }
        }
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


public String getCourse_name() {
    return course_name;
}
public void setCourse_name(String course_name) {
    this.course_name = course_name;
}

public double getJTE_234() {
    return JTE_234;
}

public void setJTE_234(double JTE_234) {
    this.JTE_234 = JTE_234;
}

// ... продолжение геттеров и сеттеров для других полей ...

public double getWOT_104() {
    return WOT_104;
}

public void setWOT_104(double WOT_104) {
    this.WOT_104 = WOT_104;
}

// Генерация геттеров и сеттеров для оставшихся полей
public double getATE_003() {
    return ATE_003;
}

public void setATE_003(double ATE_003) {
    this.ATE_003 = ATE_003;
}

public double getTGL_013() {
    return TGL_013;
}

public void setTGL_013(double TGL_013) {
    this.TGL_013 = TGL_013;
}

public double getPPL_239() {
    return PPL_239;
}

public void setPPL_239(double PPL_239) {
    this.PPL_239 = PPL_239;
}

public double getWDM_974() {
    return WDM_974;
}

public void setWDM_974(double WDM_974) {
    this.WDM_974 = WDM_974;
}

public double getGHL_823() {
    return GHL_823;
}

public void setGHL_823(double GHL_823) {
    this.GHL_823 = GHL_823;
}

public double getHLU_200() {
    return HLU_200;
}

public void setHLU_200(double HLU_200) {
    this.HLU_200 = HLU_200;
}

public double getMON_014() {
    return MON_014;
}

public void setMON_014(double MON_014) {
    this.MON_014 = MON_014;
}
public double getFEA_907() {
    return FEA_907;
}
public void setFEA_907(double FEA_907) {
    this.FEA_907 = FEA_907;
}
public double getLPG_307() {
    return LPG_307;
}
public void setLPG_307(double LPG_307) {
    this.LPG_307 = LPG_307;
}
public double getTSO_010() {
    return TSO_010;
}
public void setTSO_010(double TSO_010) {
    this.TSO_010 = TSO_010;
}
public double getLDE_009() {
    return LDE_009;
}
public void setLDE_009(double LDE_009) {
    this.LDE_009 = LDE_009;
}
public double getJJP_001() {
    return JJP_001;
}
public void setJJP_001(double JJP_001) {
    this.JJP_001 = JJP_001;
}
public double getMTE_004() {
    return MTE_004;
}
public void setMTE_004(double MTE_004) {
    this.MTE_004 = MTE_004;
}
public double getLUU_003() {
    return LUU_003;
}
public void setLUU_003(double LUU_003) {
    this.LUU_003 = LUU_003;
}
public double getLOE_103() {
    return LOE_103;
}
public void setLOE_103(double LOE_103) {
    this.LOE_103 = LOE_103;
}
public double getPLO_132() {
    return PLO_132;
}
public void setPLO_132(double PLO_132) {
    this.PLO_132 = PLO_132;
}
public double getBKO_800() {
    return BKO_800;
}
public void setBKO_800(double BKO_800) {
    this.BKO_800 = BKO_800;
}
public double getSLE_332() {
    return SLE_332;
}
public void setSLE_332(double SLE_332) {
    this.SLE_332 = SLE_332;
}
public double getBKO_801() {
    return BKO_801;
}
public void setBKO_801(double BKO_801) {
    this.BKO_801 = BKO_801;
}
public double getDSE_003() {
    return DSE_003;
}
public void setDSE_003(double DSE_003) {
    this.DSE_003 = DSE_003;
}
public double getDSE_005() {
    return DSE_005;
}
public void setDSE_005(double DSE_005) {
    this.DSE_005 = DSE_005;
}
public double getATE_014() {
    return ATE_014;
}
public void setATE_014(double ATE_014) {
    this.ATE_014 = ATE_014;
}
public double getJTW_004() {
    return JTW_004;
}
public void setJTW_004(double JTW_004) {
    this.JTW_004 = JTW_004;
}
public double getATE_008() {
    return ATE_008;
}
public void setATE_008(double ATE_008) {
    this.ATE_008 = ATE_008;
}
public double getDSE_007() {
    return DSE_007;
}
public void setDSE_007(double DSE_007) {
    this.DSE_007 = DSE_007;
}
public double getATE_214() {
    return ATE_214;
}
public void setATE_214(double ATE_214) {
    this.ATE_214 = ATE_214;
}
public double getJHF_101() {
    return JHF_101;
}
public void setJHF_101(double JHF_101) {
    this.JHF_101 = JHF_101;
}
public double getKMO_007() {
    return KMO_007;
}
public void setKMO_007(double KMO_007) {
    this.KMO_007 = KMO_007;
}


// ... и так далее для каждого поля ...
}

