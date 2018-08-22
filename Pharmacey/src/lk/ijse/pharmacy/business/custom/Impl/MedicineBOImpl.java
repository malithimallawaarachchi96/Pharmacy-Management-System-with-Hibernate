/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.pharmacy.business.custom.Impl;

import java.util.ArrayList;
import java.util.List;

import lk.ijse.pharmacy.busines.custom.MedicineBO;
import lk.ijse.pharmacy.dao.DAOFactory;
import lk.ijse.pharmacy.dao.custom.MedicineDAO;
import lk.ijse.pharmacy.db.HibernateUtil;
import lk.ijse.pharmacy.dto.MedicineDTO;
import lk.ijse.pharmacy.entity.Medicine;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author mcs
 */
public class MedicineBOImpl implements MedicineBO {
    private MedicineDAO medicinedao;
    private SessionFactory sessionFactory;

    public MedicineBOImpl() {
        this.medicinedao = (MedicineDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.Medicine);
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public boolean saveMedicine(MedicineDTO medicine) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            medicinedao.setSession(session);
            session.getTransaction().begin();
            Medicine medicine1 = new Medicine(medicine.getMID(), medicine.getDescription(), medicine.getQty(), medicine.getApproval());
            medicinedao.save(medicine1);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException exp) {
            return false;
        }
    }

    @Override
    public boolean updateMedicine(MedicineDTO medicine) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            medicinedao.setSession(session);
            session.getTransaction().begin();
            Medicine medicine1=medicinedao.find(medicine.getMID());
            medicine1.setDescription(medicine.getDescription());
            medicine1.setQty(medicine1.getQty());
            medicine1.setApproval(medicine.getApproval());
            session.getTransaction().commit();
            return true;
        } catch (HibernateException exp) {
            return false;
        }
    }

    @Override
    public boolean deleteMedicine(String id) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            medicinedao.setSession(session);
            session.getTransaction().begin();
            medicinedao.delete(id);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException exp) {
            return false;
        }
    }

    @Override
    public ArrayList<MedicineDTO> getAllMedicine() throws Exception {
        try(Session session=sessionFactory.openSession()){
            medicinedao.setSession(session);
            session.getTransaction().begin();

            List<Medicine> medi=medicinedao.getAll();

            session.getTransaction().commit();

            ArrayList<MedicineDTO>medidto=new ArrayList<>();

            for(Medicine medicine:medi){
                MedicineDTO medto=new MedicineDTO(medicine.getMID(),medicine.getDescription(),medicine.getQty(),medicine.getApproval());
                medidto.add(medto);
            }
            return medidto;
        }catch (HibernateException exp){
            return null;
        }
    }

    @Override
    public boolean updateQty(int Qty, String id) throws Exception {
        try(Session session=sessionFactory.openSession()){
            medicinedao.setSession(session);
            session.getTransaction().begin();
            medicinedao.UpdateQty(Qty, id);
            session.getTransaction().commit();
        }

        return true;
    }
}
