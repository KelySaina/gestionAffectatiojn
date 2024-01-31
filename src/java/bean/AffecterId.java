package bean;

import java.io.Serializable;
import java.util.Objects;

public class AffecterId implements Serializable {

    private String codeEmp;
    private String codeLieu;

    // Default constructor is required for Hibernate
    public AffecterId() {
    }

    public AffecterId(String codeEmp, String codeLieu) {
        this.codeEmp = codeEmp;
        this.codeLieu = codeLieu;
    }

    // Getters and setters

    public String getCodeEmp() {
        return codeEmp;
    }

    public void setCodeEmp(String codeEmp) {
        this.codeEmp = codeEmp;
    }

    public String getCodeLieu() {
        return codeLieu;
    }

    public void setCodeLieu(String codeLieu) {
        this.codeLieu = codeLieu;
    }

    // Implementing equals() and hashCode() is necessary for Hibernate

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AffecterId that = (AffecterId) o;
        return Objects.equals(codeEmp, that.codeEmp) && Objects.equals(codeLieu, that.codeLieu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeEmp, codeLieu);
    }
    
    @Override
    public String toString() {
        return "AffecterId{" +
                "codeEmp=" + codeEmp +
                ", codeLieu=" + codeLieu +
                // ... other fields
                '}';
    }
}
