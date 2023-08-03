package ru.kata.spring.boot_security.demo.DAO;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Roles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class RolesDAO {

    final private String userRole = "ROLE_user";
    final private String adminRole = "ROLE_admin";

    @PersistenceContext
    private EntityManager entityManager;
    private static String URL = "jdbc:mysql://localhost:3306/springbootDemo";

    private static String account = "root";

    private static String password = "827193++";

    private static Connection connection;


    static {

        try {
            System.out.println("Создание коннекта Roles");
            connection = DriverManager.getConnection(URL, account, password);
            System.out.println("коннект создан Roles");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Roles> getRoleSet(Set<String> role) {
        return new HashSet<>(entityManager.createQuery("SELECT r FROM roles r WHERE r.name in (:roleStringSet)")
                .setParameter("roleStringSet", role)
                .getResultList());
    }

    @Transactional
    public Roles getRoleByName(String name) {
        return entityManager.createQuery("SELECT r FROM roles r WHERE r.name = :roleName", Roles.class)
                .setParameter("roleName", name)
                .setMaxResults(1)
                .getSingleResult();
    }

    public List<Roles> listAllRoles() {
        return entityManager.createQuery("from roles", Roles.class
        ).getResultList();
    }

    public void setAdminRoleDefault() {

        Roles adminRole = new Roles();
        adminRole.setName("ROLE_admin");
        entityManager.persist(adminRole);
    }

    public void setUserRoleDefault() {
        Roles userRole = new Roles();
        userRole.setName("ROLE_user");
        entityManager.persist(userRole);
    }

}
