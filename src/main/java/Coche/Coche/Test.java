package Coche.Coche;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class Test {
	private static SessionFactory factory;
	private static org.hibernate.service.ServiceRegistry serviceRegistry;

	private void insertUser(User u) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(u);
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}

	private void actualizarUser(User u, int id, String nombre_nuevo) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String sql = "UPDATE User SET username='" + nombre_nuevo + "' WHERE id=" + id;
			Query query = session.createQuery(sql);
			query.executeUpdate();
			System.out.println("Usuario actualizado correctamente\n");
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}

	private void eliminarUser(User u, int id) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String sql = "DELETE User WHERE id=" + id;
			Query query = session.createQuery(sql);
			query.executeUpdate();
			System.out.println("Usuario borrado correctamente\n");
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}

	private static List listUsers() {
		Session sesn = factory.openSession();
		Transaction tx = null;
		List users = new ArrayList();
		try {
			tx = sesn.beginTransaction();
			users = sesn.createQuery("From User").list();
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			sesn.close();
		}
		return users;
	}

	protected static void listarusuarios() {
		List<User> users2 = Test.listUsers();
		System.out.println("Total usuarios: " + users2.size());
		for (User u : users2) {
			System.out.println(u.getId() + " ");
			System.out.println(u.getFirstname() + " ");
			System.out.println(u.getLastname() + " ");
			System.out.println(u.getEmail() + " ");
			System.out.println(u.getUsername() + " ");
		}
	}

	private static List<String> listUsersNombre() {
		Session sesn = factory.openSession();
		Transaction tx = null;
		List<String> nombres = new ArrayList();
		try {
			tx = sesn.beginTransaction();
			String sql = "SELECT username FROM User";
			Query query = sesn.createQuery(sql);
			nombres = query.list();
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			sesn.close();
		}
		return nombres;
	}

	private static User verUsuario(int num) {
		Session sesn = factory.openSession();
		Transaction tx = null;
		User usuario = new User();
		try {
			tx = sesn.beginTransaction();
			String sql = "FROM User where id=:num";
			Query query = sesn.createQuery(sql);
			query.setParameter("num", num);
			usuario = (User) query.list().get(0);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			sesn.close();
		}
		return usuario;
	}

	public static void main(String[] args) {
		Configuration config = new Configuration();
		config.configure();
		config.addAnnotatedClass(User.class);
		config.addResource("User.hbm.xml");
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
		factory = config.buildSessionFactory(serviceRegistry);

		/*
		 * 
		 * User usuario = new User(1, "aaa", "bbbb", "sdfd", "tttt"); Test ejemplo = new
		 * Test();
		 *
		 * ///////////////////////////////////////
		 * 
		 * Insertar usuario
		 * 
		 * ejemplo.insertUser(usuario);
		 * 
		 * /////////////////////////////////////////
		 * 
		 * Eliminar usuario
		 * 
		 * ejemplo.eliminarUser(usuario);
		 * 
		 * /////////////////////////////////////////
		 * 
		 * Actualizar un usuario por username
		 * 
		 * ejemplo.actualizarUser(usuario, 1, "ccc");
		 *
		 * /////////////////////////////////////////
		 *
		 * Borrar usuario por id
		 * 
		 * ejemplo.eliminarUser(usuario, 1);
		 * 
		 * /////////////////////////////////////////
		 *
		 * Ver todos los usuarios
		 *
		 * listarusuarios();
		 *
		 * /////////////////////////////////////////
		 *
		 * Ver todos los usuarios por nombre
		 * 
		 * List<String> nombres = ejemplo.listUsersNombre();
		 * System.out.println("Total usernames: " + nombres.size()); for (String u :
		 * nombres) { System.out.println(u); }
		 * 
		 * /////////////////////////////////////////
		 *
		 * Ver un dato de un usuario
		 * 
		 * User resultado = verUsuario(1); System.out.println(resultado.getFirstname());
		 * 
		 */
	}

}