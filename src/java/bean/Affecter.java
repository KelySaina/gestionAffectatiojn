package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Affecter implements Serializable {

    private AffecterId id;  // Composite identifier
    private Date date;

    // Include the employee and lieu field in the composite identifier
    private Employee employee;
    private Lieu lieu;

    public Affecter() {
    }

    public Affecter(AffecterId id, Date date, Employee employee) {
        this.id = id;
        this.date = date;
        this.employee = employee;
    }

    public AffecterId getId() {
        return id;
    }

    public void setId(AffecterId id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // Getter and Setter for the employee field
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    public Lieu getLieu() {
        return lieu;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    @Override
    public String toString() {
        return "Affecter{" +
               "id=" + id +
               ", date=" + date +
               '}';
    }

    // Implementing equals() and hashCode() is necessary for Hibernate

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Affecter affecter = (Affecter) o;
        return Objects.equals(id, affecter.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
