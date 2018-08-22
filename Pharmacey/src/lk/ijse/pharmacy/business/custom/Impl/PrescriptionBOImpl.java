/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.pharmacy.business.custom.Impl;

import java.util.ArrayList;
import java.util.List;

import lk.ijse.pharmacy.busines.custom.PrescriptionBO;
import lk.ijse.pharmacy.dao.DAOFactory;
import lk.ijse.pharmacy.dao.custom.PatientDAO;
import lk.ijse.pharmacy.dao.custom.PrescriptionDAO;
import lk.ijse.pharmacy.dao.custom.PrescriptionDetailDAO;
import lk.ijse.pharmacy.db.HibernateUtil;
import lk.ijse.pharmacy.dto.PrescriptionDTO;
import lk.ijse.pharmacy.dto.PrescriptiondetailDTO;
import lk.ijse.pharmacy.entity.Patient;
import lk.ijse.pharmacy.entity.Prescription;
import lk.ijse.pharmacy.entity.Prescriptiondetail;
import lk.ijse.pharmacy.entity.Prescriptiondetail_PK;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


/**
 *
 * @author mcs
 */
public class PrescriptionBOImpl implements PrescriptionBO {

    PrescriptionDAO prescriptiondao;
    PrescriptionDetailDAO prescriptionDetailDAO;
    private SessionFactory sessionFactory;
    private PatientDAO patientDAO;

    public PrescriptionBOImpl() {
        this.patientDAO = (PatientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.Patient);
        this.prescriptiondao = (PrescriptionDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PreSCRIPTION);
        this.prescriptionDetailDAO= (PrescriptionDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PrescriptionDetail);
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public boolean SavePrescription(PrescriptionDTO presd,PrescriptiondetailDTO predetailto) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            prescriptiondao.setSession(session);
            prescriptionDetailDAO.setSession(session);
            session.getTransaction().begin();
            patientDAO.setSession(session);
            Patient patient = patientDAO.find(presd.getPatientID());
            Prescription prescription = new Prescription(presd.getPID(),patient,presd.getDate());
            prescriptiondao.save(prescription);
            Prescriptiondetail prescriptiondetail=new Prescriptiondetail(predetailto.getDoctorName(),predetailto.getPID(),predetailto.getMID());
            prescriptionDetailDAO.save(prescriptiondetail);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException exp) {
            return false;
        }

    }

    @Override
    public boolean UpdatePrescription(PrescriptionDTO presd, PrescriptiondetailDTO predetailto) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            prescriptiondao.setSession(session);
            prescriptionDetailDAO.setSession(session);
            session.getTransaction().begin();
            patientDAO.setSession(session);
            Patient patient = patientDAO.find(presd.getPatientID());
            Prescription prescription=prescriptiondao.find(presd.getPID());
            prescription.setPatient(patient);
            prescription.setDate(presd.getDate());
            Prescriptiondetail prescriptiondetail=prescriptionDetailDAO.find(new Prescriptiondetail_PK(predetailto.getPID(),predetailto.getMID()));
            prescriptiondetail.setDoctorName(predetailto.getDoctorName());
            session.getTransaction().commit();
            return true;
        } catch (HibernateException exp) {
            return false;
        }
    }


    @Override
    public boolean DeletePrescription(String id) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            prescriptiondao.setSession(session);
            session.getTransaction().begin();

            prescriptiondao.delete(id);
           // prescriptionDetailDAO.delete(new Prescriptiondetail_PK());

            session.getTransaction().commit();
            return true;
        } catch (HibernateException exp) {
            return false;
        }
    }


    @Override
    public ArrayList<PrescriptionDTO> getAllPrescriptions() throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            prescriptiondao.setSession(session);

            List<Prescription> allpres = prescriptiondao.getAll();


            session.getTransaction().commit();
            if(allpres == null){

                return null;
            }
            ArrayList<PrescriptionDTO> predto = new ArrayList<>();
            System.out.println(allpres);

            for (Prescription prescription : allpres) {

                //System.out.println("ccccccccccccccc"+prescription.getDate());

                PrescriptionDTO prescriptionDTO = new PrescriptionDTO(prescription.getPID(),"p001",prescription.getDate());
                predto.add(prescriptionDTO);
            }
            return predto;

        } catch (HibernateException exp) {
            return null;
        }

    }
}





