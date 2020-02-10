package com.example.couponsProject.service.facade;

import com.example.couponsProject.exceptions.loginExceptions.EmailNotFoundException;
import com.example.couponsProject.exceptions.loginExceptions.WrongPasswordException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

//@Service
//@Transactional
public abstract class ClientFacade {

    /**
     * login to client user
     * @param email String
     * @param password String
     * @return boolean
     * @throws EmailNotFoundException if email not found
     * @throws WrongPasswordException if password is incorrect
     */
    public abstract boolean login(String email, String password) throws EmailNotFoundException, WrongPasswordException;

}
