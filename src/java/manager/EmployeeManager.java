package manager;

import bean.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import util.HibernateUtil;

import java.util.List;
import java.util.ArrayList;


/**
 *
 * @author thyler
 */
public class EmployeeManager {

    public void addEmployee(String codeEmp, String nom, String prenom, String poste) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Employee e = new Employee();

        e.setCodeEmp(codeEmp);
        e.setNom(nom);
        e.setPrenom(prenom);
        e.setPoste(poste);

        session.save(e);

        transaction.commit();

    }

    public void deleteEmployee(String codeEmp) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Employee e = (Employee)session.get(Employee.class, codeEmp);

        session.delete(e);

        transaction.commit();

    }

    public Employee getEmployeeByCode(String codeEmp) {
    Session session = null;
    try {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Employee employee = (Employee)session.get(Employee.class, codeEmp);

        transaction.commit();

        return employee;
    } catch (Exception e) {
        // Log the exception or print the stack trace for debugging
        e.printStackTrace();
    } finally {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    return null; // Return null in case of an exception
}
    
    public List<Employee> getEmployeeByName(String name) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        List<Employee> employeeList = new ArrayList<>();
        System.out.println("ok");
        try {
            tx = session.beginTransaction();

            // Use HQL to get all employees
            Query query = session.createQuery("FROM Employee WHERE nom LIKE '%"+name+"%' OR prenom LIKE '%"+name+"%'");
            
            employeeList = query.list();

            // Commit the transaction
            tx.commit();

            return employeeList;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace(); // Log or handle the exception appropriately
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        // Return an empty list in case of an exception
        return employeeList;
    }


    public List<Employee> getAllData() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        List<Employee> employeeList = new ArrayList<>();

        try {
            tx = session.beginTransaction();

            // Use HQL to get all employees
            Query query = session.createQuery("FROM Employee");
            employeeList = query.list();

            // Commit the transaction
            tx.commit();

            return employeeList;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace(); // Log or handle the exception appropriately
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        // Return an empty list in case of an exception
        return employeeList;
    }
    
     public void editEmployee(String codeEmp, String nom, String prenom, String poste) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // Retrieve the existing employee
        Employee e = (Employee) session.get(Employee.class, codeEmp);

        if (e != null) {
            // Update employee information
            e.setNom(nom);
            e.setPrenom(prenom);
            e.setPoste(poste);

            // Save the updated employee
            session.saveOrUpdate(e);
            transaction.commit();
        } else {
            // Handle the case where the employee with the given code is not found
            System.out.println("Employee with code " + codeEmp + " not found.");
            transaction.rollback();
        }
    }     

    public List<Employee> getEmployeesNotInAffecterTable() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        List<Employee> employeeList = new ArrayList<>();

        try {
            tx = session.beginTransaction();

            // Use HQL to get employees who are not in the "Affecter" table
            Query query = session.createQuery("FROM Employee e WHERE e.codeEmp NOT IN (SELECT a.id.codeEmp FROM Affecter a)");
            employeeList = query.list();

            // Commit the transaction
            tx.commit();

            return employeeList;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace(); // Log or handle the exception appropriately
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        // Return an empty list in case of an exception
        return employeeList;
    }

}
