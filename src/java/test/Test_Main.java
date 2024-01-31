package test;

import manager.AffecterManager;
import manager.LieuManager;
import manager.EmployeeManager;
import util.HibernateUtil;
import bean.AffecterId;
import bean.Employee;
import bean.Lieu;

import java.util.Date;

public class Test_Main {

    public static void main(String[] args) {
        AffecterManager affManager = new AffecterManager();
        LieuManager lieuManager = new LieuManager();
        EmployeeManager employeeManager = new EmployeeManager();
        // Display all Affecter entities
        //System.out.println("All Affecters:");
        //System.out.println(employeeManager.getEmployeesNotInAffecterTable());
        
        //affManager.deleteAffecter("E9194","L4736");

        // Create necessary objects (AffecterId, Date, Employee, Lieu)
        /*AffecterId affecterId = new AffecterId("E9194", "L4736");
        Date date = new Date();  // Example: You might want to use a specific date
        Employee employee = new Employee();
        Lieu lieu = new Lieu();

        // Add a new Affecter
        affManager.addAffecter(affecterId, date, employee, lieu);
        */
        // Display all Lieu entities
        //System.out.println("New Affecter:");
        //System.out.println(affManager.getAllData());

        // Close the Hibernate session factory
        HibernateUtil.sessionFactory.close();
    }
}
