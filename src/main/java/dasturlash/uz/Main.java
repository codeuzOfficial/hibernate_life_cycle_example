package dasturlash.uz;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
//        transientPersistent();
//        persistentDetached();
//        detached();
//        removed();
    }


    public static void transientPersistent() {
        // transient-persistent example
        StudentEntity student1 = new StudentEntity();
        student1.setName("Ali");
        student1.setSurname("Aliyev");
        student1.setCreatedDate(LocalDateTime.now());


        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        session.save(student1); // save student

        // make some changes
        student1.setName("Alishjon");

        t.commit();

        session.close();
        factory.close();
    }

    public static void persistentDetached() {// persistent detached example

        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        // mavjut bo'lga studentni ma'lumotlar bazasidan yuklash.
        StudentEntity student = session.get(StudentEntity.class, 1);

        // make some changes
        student.setName("Sherbek111");

        Transaction t = session.beginTransaction();
        t.commit();

        session.close();
        factory.close();
    }

    public static void detached() {// detached state example

        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        // mavjut bo'lga studentni ma'lumotlar bazasidan yuklash.
        StudentEntity student = session.get(StudentEntity.class, 1);

        session.evict(student); // detached student object

        student.setName("Asilbekjons");  // make some changes (this changes will not be saved)

        Transaction t = session.beginTransaction();
        t.commit();

        session.close();
        factory.close();
    }

    public static void removed() {// removed state example

        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        // mavjut bo'lga studentni ma'lumotlar bazasidan yuklash.
        StudentEntity student = session.get(StudentEntity.class, 4);

        Transaction t = session.beginTransaction();
        session.delete(student); // delete student

        t.commit();

        session.close();
        factory.close();
    }

}