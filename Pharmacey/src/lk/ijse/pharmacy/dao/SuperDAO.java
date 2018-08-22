/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.pharmacy.dao;

import org.hibernate.Session;

/**
 *
 * @author mcs
 */
public interface SuperDAO {

    void setSession(Session session);
    
}
