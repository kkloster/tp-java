/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mavha.cursos.java.app.jpa.dao;

import com.mavha.cursos.java.app.jpa.modelo.Empleado;
import com.mavha.cursos.java.app.jpa.modelo.Tarea;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author mdominguez
 */
public class EmpleadoDaoJPA implements EmpleadoDao{

    private EntityManager em;
    
    @Override
    public Empleado guardar(Empleado e) {
      //TODO 2.1 implementar el guardado
        em = ConexionJPA.getInstance().em();
        em.getTransaction().begin();
        em.persist(e);
        // forzar el insert en la base de datos
        em.flush();
        // refrescar con los datos de la base de datos
        em.refresh(e);
        em.getTransaction().commit();
        em.close();   
        return e;
        //respuesta 2.1
    }

    @Override
    public void borrar(Integer e) {
      //TODO 2.2 implementar el guardado
        em = ConexionJPA.getInstance().em();
        em.getTransaction().begin();
        Empleado aBorrar = em.find(Empleado.class, e);
        em.remove(aBorrar);
        em.getTransaction().commit();
        em.close();
        //respuesta 2.2
    }
    
    @Override
    public Empleado actualizar(Empleado e) {
        em = ConexionJPA.getInstance().em();
        em.getTransaction().begin();
        Empleado empActualizado = em.merge(e);
        em.getTransaction().commit();
        em.close();        
        return empActualizado;
    }
    
    @Override
    public Empleado buscarPorId(Integer id) {
        em = ConexionJPA.getInstance().em();
        em.getTransaction().begin();
        Empleado e = em.find(Empleado.class, id);
        em.getTransaction().commit();
        em.close();        
        return e;
    }

    @Override
    public List<Empleado> buscarTodos() {
   String consulta ="SELECT e FROM Empleado e";
        em = ConexionJPA.getInstance().em();
        em.getTransaction().begin();
        Query query = em.createQuery(consulta);
        List<Empleado> resultado = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return resultado;
    }
   
    @Override
    public Double salarioPromedioTodos(){
        ////TODO 2.3 Ejecutar una consulta para conocer el salario promedio de todos los empleados de una empresa
   //String consulta ="SELECT AVG(e.salarioHora) FROM Empleado e";
        em = ConexionJPA.getInstance().em();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT AVG(e.salarioHora) FROM Empleado e");
        Object promedio_objeto = (Object)query.getSingleResult();
        Double promedio;
        if (promedio_objeto == null){
            promedio = 0.0;
        }
        else
        {
            promedio = (Double)promedio_objeto;
        }
        em.getTransaction().commit();
        em.close();
        return promedio;
        //respuesta 2.3
    }

    @Override
    public List<Tarea> tareasPendientes(Integer idEmpleado) {
        //TODO 2.4 ejecutar una consulta que retorne todas las tareas que tiene pendiente un Empleado. 
        // Las tareas pendientes son todas las tareas asignadas al empleado, que tiene fecha de fin NULL
   //String consulta ="";
        em = ConexionJPA.getInstance().em();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT t FROM Tarea as t where t.responsable.id = :empladoid and t.fechaFin is null");
        query.setParameter("empleadoid", idEmpleado);
        List<Tarea> tareaspen = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return tareaspen;
        //respuesta 2.4
    }

    
    
}
