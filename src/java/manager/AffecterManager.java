package manager;

import bean.Affecter;
import bean.AffecterId;
import bean.Employee;
import bean.Lieu;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import manager.EmployeeManager;
import manager.LieuManager;

public class AffecterManager {

    public void addAffecter(AffecterId id, Date date, Employee employee, Lieu lieu) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Affecter affecter = new Affecter(id, date, employee);
        affecter.setLieu(lieu);

        session.save(affecter);

        transaction.commit();
    }

    public void deleteAffecter(String codeEmp, String codeLieu) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        AffecterId id = new AffecterId(codeEmp, codeLieu);

        Affecter affecter = (Affecter) session.get(Affecter.class, id);

        session.delete(affecter);

        transaction.commit();
    }

    public Affecter getAffecterById(AffecterId id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            Affecter affecter = (Affecter) session.get(Affecter.class, id);

            transaction.commit();

            return affecter;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return null;
    }

    public List<Affecter> getAffecterByEmployee(Employee employee) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        List<Affecter> affecterList = new ArrayList<>();

        try {
            tx = session.beginTransaction();

            Query query = session.createQuery("FROM Affecter WHERE employee = :employee");
            query.setParameter("employee", employee);

            affecterList = query.list();

            tx.commit();

            return affecterList;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return affecterList;
    }
    
    public List<Affecter> getAllData() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        List<Affecter> affecterList = new ArrayList<>();

        try {
            tx = session.beginTransaction();

            // Use HQL to get all Affecter entities
            Query query = session.createQuery("FROM Affecter");
            affecterList = query.list();

            tx.commit();

            return affecterList;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        // Return an empty list in case of an exception
        return affecterList;
    }
    
    public Employee getEmpDetails(String codeEmp){
        EmployeeManager em = new EmployeeManager();
        Employee e = em.getEmployeeByCode(codeEmp);    
        return e;
    }
    
    public Lieu getLieuDetails(String codeLieu){
        LieuManager em = new LieuManager();
        Lieu l = em.getLieuByCode(codeLieu);    
        return l;
    }
    
}
