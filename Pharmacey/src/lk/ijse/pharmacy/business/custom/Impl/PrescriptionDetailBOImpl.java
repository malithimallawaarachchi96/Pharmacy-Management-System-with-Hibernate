/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.pharmacy.business.custom.Impl;

import java.util.ArrayList;
import lk.ijse.pharmacy.busines.custom.PrescriptionDetailBO;
import lk.ijse.pharmacy.dao.DAOFactory;
import lk.ijse.pharmacy.dao.custom.PrescriptionDetailDAO;
import lk.ijse.pharmacy.db.HibernateUtil;
import lk.ijse.pharmacy.dto.PrescriptiondetailDTO;
import lk.ijse.pharmacy.entity.Prescriptiondetail;
import lk.ijse.pharmacy.entity.Prescriptiondetail_PK;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author mcs
 */
public class PrescriptionDetailBOImpl implements PrescriptionDetailBO {

    PrescriptionDetailDAO prescriptionDetailDAO;
    private SessionFactory sessionFactory;

    public PrescriptionDetailBOImpl() {
        this.prescriptionDetailDAO = (PrescriptionDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PrescriptionDetail);
         sessionFactory = HibernateUtil.getSessionFactory();
    }


    @Override
    public boolean savePrescriptiondetail(PrescriptiondetailDTO presdet) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            prescriptionDetailDAO.setSession(session);
            session.getTransaction().begin();
            Prescriptiondetail prescriptiondetail = new Prescriptiondetail(presdet.getDoctorName(), presdet.getPID(), presdet.getMID());
            prescriptionDetailDAO.save(prescriptiondetail);
            session.getTransaction().commit();
            return true;
        }catch (HibernateException exp){
            return false;
        }

    }


    @Override
    public boolean DeletePrescriptiondetail(String PID, String MID) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            prescriptionDetailDAO.setSession(session);
            session.getTransaction().begin();
            prescriptionDetailDAO.delete(new Prescriptiondetail_PK(PID, MID));
            session.getTransaction().commit();
            return true;
        } catch (HibernateException exp) {
            return false;
        }

    }


    @Override
    public ArrayList<PrescriptiondetailDTO> getAllperesDetail() throws Exception {
        try (Session session = sessionFactory.openSession()) {
            prescriptionDetailDAO.setSession(session);
            session.getTransaction().begin();

            ArrayList<Prescriptiondetail> predetail = prescriptionDetailDAO.getAll();

            session.getTransaction().commit();

            ArrayList<PrescriptiondetailDTO> Presdto = new ArrayList<>();
            for (Prescriptiondetail presc : predetail) {
                PrescriptiondetailDTO predto = new PrescriptiondetailDTO(presc.getDoctorName(), presc.getPrescriptiondetail_PK().getPID(),
                        presc.getPrescriptiondetail_PK().getMID());

                Presdto.add(predto);
            }
            return Presdto;
        } catch (HibernateException exp) {
            return null;
        }

    }
}
