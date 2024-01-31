package manager;

import bean.Lieu;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class LieuManager {

    public void addLieu(String codeLieu, String designation, String province) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Lieu lieu = new Lieu();
        lieu.setCodeLieu(codeLieu);
        lieu.setDesignation(designation);
        lieu.setProvince(province);

        session.save(lieu);
        transaction.commit();
    }

    public void deleteLieu(String codeLieu) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Lieu lieu = (Lieu) session.get(Lieu.class, codeLieu);

        session.delete(lieu);
        transaction.commit();
    }

    public Lieu getLieuByCode(String codeLieu) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Lieu lieu = (Lieu) session.get(Lieu.class, codeLieu);

        transaction.commit();
        return lieu;
    }

    public void updateLieuByCode(String codeLieu, String designation, String province) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Lieu lieu = (Lieu) session.get(Lieu.class, codeLieu);
        lieu.setDesignation(designation);
        lieu.setProvince(province);

        transaction.commit();
    }

    public List<Lieu> getAllData() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        List<Lieu> lieuList = new ArrayList<>();

        try {
            tx = session.beginTransaction();

            // Use HQL to get all lieux
            Query query = session.createQuery("FROM Lieu");
            lieuList = query.list();

            // Commit the transaction
            tx.commit();
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
        return lieuList;
    }
}
