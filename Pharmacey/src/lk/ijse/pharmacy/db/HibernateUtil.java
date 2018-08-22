/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.pharmacy.db;

import lk.ijse.pharmacy.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import java.io.File;


/**
 *
 * @author mcs
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {

        System.out.println(new File("resources/application.properties").getAbsolutePath());
        File hibernateProp = new File("resources/application.properties");
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().loadProperties(hibernateProp)
                .build();

        Metadata metadata = new MetadataSources(registry)
                .addAnnotatedClass(Medicine.class)
                .addAnnotatedClass(Prescription.class)
                .addAnnotatedClass(Prescriptiondetail.class)
                .addAnnotatedClass(Receipt.class)
                .addAnnotatedClass(Patient.class)
                .buildMetadata();

        return metadata.getSessionFactoryBuilder().build();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
}
