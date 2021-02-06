/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.sun.tools.doclint.HtmlTag;
import entities.Employee;
import facades.EmployeeFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Jack
 */
@Path("employee")
public class EmployeeResource {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        return "{\"msg\":\"Hello\"}";
    }

    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getEmployees() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Employee> tq = em.createQuery("Select e FROM Employee e", Employee.class);
            List<Employee> employees = tq.getResultList();
            return new Gson().toJson(employees);
        } finally {
            em.close();
        }
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getEmployeeById(@PathParam("id") long id) {
        EntityManager em = emf.createEntityManager();

        try {
            return new Gson().toJson(em.find(Employee.class, id));
        } finally {
            em.close();
        }
    }
    
    @Path("highestpaid")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getHighestPaid(){
        EntityManager em = emf.createEntityManager();
        
        try{
            Query q = em.createQuery("SELECT e FROM Employee e WHERE MAX(e.salary)");
            return new Gson().toJson(q.toString());
        } finally{
            em.close();
        }
    }
    
    @Path("name/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getEmployeeByName(@PathParam("name") String name){
        EntityManager em = emf.createEntityManager();
        
        try{
            Query q = em.createQuery("SELECT e FROM Employee e WHERE e.name = :name");
            q.setParameter("name", name);
            return new Gson().toJson(q);
        } finally {
            em.close();
        }
    }
    
}
