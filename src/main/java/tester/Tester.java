package tester;

import entity.Employee;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Tester {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new Employee("xa12tt", "Kurt", "Wonnegut", new BigDecimal(335567)));
            em.persist(new Employee("hyu654", "Hanne", "Olsen", new BigDecimal(435867)));
            em.persist(new Employee("uio876", "Jan", "Olsen", new BigDecimal(411567)));
            em.persist(new Employee("klo999", "Irene", "Petersen", new BigDecimal(33567)));
            em.persist(new Employee("jik666", "Tian", "Wonnegut", new BigDecimal(56567)));
            em.getTransaction().commit();
            

            Query query = em.createQuery("Select e FROM Employee e WHERE e.salary > 100000");
            List<Employee> result = query.getResultList();
            result.forEach(e -> {
                System.out.println(e.getSalary());
            });

            query = em.createQuery("Select e FROM Employee e WHERE e.id = :id");
            query.setParameter("id", "klo999");
            Employee result2 = (Employee) query.getSingleResult();
            System.out.println(result2.getFirstName());

            // Query for a single data element.
            query = em.createQuery("Select MAX(e.salary) FROM Employee e");
            BigDecimal result3 = (BigDecimal) query.getSingleResult();
            System.out.println(result3);

            query = em.createQuery("Select e.firstName FROM Employee e");
            List<String> result4 = query.getResultList();
            result4.forEach(fn -> {
                System.out.println(fn);
            });

            query = em.createQuery("SELECT COUNT(e) FROM Employee e");
            Long employeeCount = (long) query.getSingleResult();
            System.out.println("Total: " + employeeCount);

            query = em.createQuery("select e from Employee e where e.salary = (Select MAX(e2.salary) FROM Employee e2)");
            Employee e = (Employee) query.getSingleResult();
            System.out.println(e);

        } finally {
            em.close();
            emf.close();
        }
    }

}
