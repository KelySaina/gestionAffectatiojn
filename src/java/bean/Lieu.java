package bean;

/**
 *
 * @author thyler
 */
public class Lieu {
    
    private String codeLieu;
    private String designation;
    private String province;
    
    public Lieu(){
    }
    
    public Lieu(String codeLieu, String designation, String province){
        super();
        this.codeLieu = codeLieu;
        this.designation = designation;
        this.province = province;
    }

    // Getters
    public String getCodeLieu() {
        return codeLieu;
    }

    public String getDesignation() {
        return designation;
    }

    public String getProvince() {
        return province;
    }

    // Setters
    public void setCodeLieu(String codeLieu) {
        this.codeLieu = codeLieu;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    
    @Override
    public String toString() {
        return "Lieu{" +
            "codeLieu='" + codeLieu + '\'' +
            ", designation='" + designation + '\'' +
            ", province='" + province + '\'' +
         '}';
    }
    
}
