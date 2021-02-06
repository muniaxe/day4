package facades;

import entities.Employee;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import utils.EMF_Creator;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class EmployeeFacade {

    public static EmployeeFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private EmployeeFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static EmployeeFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new EmployeeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Employee getEmployeeById(int id) {
        EntityManager em = instance.getEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }
    
    public Employee findEmployeesByName(String name){
        EntityManager em = instance.getEntityManager();
        try{
            Query q = em.createQuery("Select e FROM Employee e WHERE e.name = :name", Employee.class);
            q.setParameter("name", name);
            return (Employee) q.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    public List<Employee> getAllEmployees(){
        EntityManager em = instance.getEntityManager();
        try{
            Query q = em.createQuery("Select e from Employee e", Employee.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Employee createEmployee(String name, String address, BigDecimal salary){
        EntityManager em = instance.getEntityManager();
        Employee tmpEmployee = new Employee(name, address, salary);
        try{
            em.getTransaction().begin();
            em.persist(tmpEmployee);
            em.getTransaction().commit();
            return tmpEmployee;
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        EmployeeFacade fe = getFacadeExample(emf);
    }

}
