/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

/**
 *
 * @author thyler
 */
public class Employee {
    
    private String codeEmp;
    private String nom;
    private String prenom;
    private String poste;
    
    public Employee(){
    }
    
    public Employee(String codeEmp, String nom, String prenom, String poste){
        super();
        this.codeEmp = codeEmp;
        this.nom = nom;
        this.prenom = prenom;
        this.poste = poste;
    }

    // Setters
    public void setCodeEmp(String codeEmp) {
        this.codeEmp = codeEmp;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    // Getters
    public String getCodeEmp() {
        return codeEmp;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getPoste() {
        return poste;
    } 
    
    @Override
    public String toString() {
        return "Employee{" +
               "codeEmp='" + codeEmp + '\'' +
               ", nom='" + nom + '\'' +
               ", prenom='" + prenom + '\'' +
               ", poste='" + poste + '\'' +
               '}';
    }
}
