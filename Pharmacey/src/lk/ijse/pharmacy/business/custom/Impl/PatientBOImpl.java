/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.pharmacy.business.custom.Impl;

import java.util.ArrayList;
import java.util.List;

import lk.ijse.pharmacy.busines.custom.PatientBO;
import lk.ijse.pharmacy.dao.DAOFactory;
import lk.ijse.pharmacy.dao.custom.PatientDAO;
import lk.ijse.pharmacy.db.HibernateUtil;
import lk.ijse.pharmacy.dto.PatientDTO;
import lk.ijse.pharmacy.entity.Patient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author mcs
 */
public class PatientBOImpl implements PatientBO {
    private PatientDAO patientDAO;
    private SessionFactory sessionFactory;

    public PatientBOImpl() {
        this.patientDAO = (PatientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.Patient);
         sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public boolean savePatient(PatientDTO patient) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            patientDAO.setSession(session);
            session.getTransaction().begin();
            Patient patient1 = new Patient(patient.getPatientID(), patient.getAddress(), patient.getName());
            patientDAO.save(patient1);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException exp) {
            return false;
        }

    }

    @Override
    public boolean updatePatient(PatientDTO patient) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            patientDAO.setSession(session);
            session.getTransaction().begin();
            Patient patient1=patientDAO.find(patient.getPatientID());
            patient1.setAddress(patient.getAddress());
            patient1.setName(patient.getName());
            session.getTransaction().commit();
            return true;
        } catch (HibernateException exp) {
            return false;
        }
    }


    @Override
    public boolean DeletePatient(String id) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            patientDAO.setSession(session);
            session.getTransaction().begin();
            patientDAO.delete(id);
            session.getTransaction().commit();
        }
        return true;
    }


    @Override
    public ArrayList<PatientDTO> getAllPatient() throws Exception {
        try(Session session=sessionFactory.openSession()){
            patientDAO.setSession(session);
            session.getTransaction().begin();

            List<Patient> allpatients=patientDAO.getAll();

            session.getTransaction().commit();

            ArrayList<PatientDTO> pdtos=new ArrayList<>();
            for(Patient patient:allpatients){
                PatientDTO patientDTO=new PatientDTO(patient.getPatientID(),patient.getAddress(),patient.getName());
                pdtos.add(patientDTO);
            }
            return pdtos;
        }catch (HibernateException exp){
            return  null;
        }
    }
}
