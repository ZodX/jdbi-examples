package user;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()) {
            UserDao dao = handle.attach(UserDao.class);

            dao.createTable();

            User user1 = User.builder()
                    .username("007")
                    .password("user1_password")
                    .name("James Bond")
                    .email("bonderino.alajames@somerandomemail.com")
                    .gender(User.Gender.MALE)
                    .dob(LocalDate.parse("1920-11-11"))
                    .enabled(false)
                    .build();

            User user2 = User.builder()
                    .username("ZodX")
                    .password("notgonnatellyou")
                    .name("Andrew Miller")
                    .email("m.andras.imre00@gmail.com")
                    .gender(User.Gender.MALE)
                    .dob(LocalDate.parse("2000-02-04"))
                    .enabled(true)
                    .build();

            User user3 = User.builder()
                    .username("Hermione")
                    .password("user3_password")
                    .name("Emma Wattson")
                    .email("tricky.emy@hpseries.com")
                    .gender(User.Gender.FEMALE)
                    .dob(LocalDate.parse("1990-04-15"))
                    .enabled(true)
                    .build();

            //Inserting users into the database.
            dao.insert(user1);
            long ID = dao.insert(user2);
            dao.insert(user3);

            System.out.println("\nuser2's ID: " + ID);

            System.out.println("Finding user2 by the findById method: \n" + dao.findById(ID) + "\n");

            System.out.println("Finding user3 by the findByUsername method: \n" + dao.findByUsername("Hermione") + "\n");

            System.out.println("Deleting user1 by the delete method... \n");
            dao.delete(user1);

            List<User> users = dao.list();
            System.out.println("Currently stored users in the \"users\" table:");
            for (User i : users) {
                System.out.println(i);
            }

            System.out.println();
        }
    }
}
