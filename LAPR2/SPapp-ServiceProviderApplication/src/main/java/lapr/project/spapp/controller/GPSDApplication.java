/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.spapp.controller;



import lapr.project.spapp.model.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author paulomaio
 */
public class GPSDApplication
{
       
    private final Company m_oCompany;
    
    private GPSDApplication()
    {
        Properties props = getProperties();
        this.m_oCompany = new Company(props.getProperty(Constants.PARAMS_EMPRESA_DESIGNACAO),
                        props.getProperty(Constants.PARAMS_EMPRESA_NIF));
        bootstrap();
    }
    
    public Company getCompany()
    {
        return this.m_oCompany;
    }

    
    private Properties getProperties()
    {
        Properties props = new Properties();
        
        // Adiciona propriedades e valores por omissão
        props.setProperty(Constants.PARAMS_EMPRESA_DESIGNACAO, "Default Lda.");
        props.setProperty(Constants.PARAMS_EMPRESA_NIF, "Default NIF");
        
        // Lê as propriedades e valores definidas 
        try
        {
            InputStream in = new FileInputStream(Constants.PARAMS_FICHEIRO);
            props.load(in);
            in.close();
        }
        catch(Exception ex)
        {
            
        }
        return props;
    }




    private void bootstrap()
    {
    }



    
    // Inspirado em https://www.javaworld.com/article/2073352/core-java/core-java-simply-singleton.html?page=2
    private static GPSDApplication singleton = null;
    public static GPSDApplication getInstance() 
    {
        if(singleton == null) 
        {
            synchronized(GPSDApplication.class) 
            { 
                singleton = new GPSDApplication();
            }
        }
        return singleton;
    }

    
}
