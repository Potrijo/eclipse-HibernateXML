package com.marc.Hibernate2;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
public class App {
static Scanner teclado;
public static void mostrarLibros() {
 SessionFactory sessionFactory = new
Configuration().configure().buildSessionFactory();
 Session session = sessionFactory.openSession();
 if (session != null) {
 System.out.println("Sesión abierta");
 } else {
 System.out.println("Fallo en la sesión");
 }
 Query<Libros> consulta = session.createQuery("from Libros");
 List<Libros> resultados = consulta.list();
 for (Object resultado : resultados) {
 Libros libro = (Libros) resultado;
 System.out.println(libro.getId() + ": " + libro.getTitulo() + ", de " +
libro.getAutores().getNombre());
 }
 session.close();
 }
public static void anyadir() {
	 System.out.print("Introduzca el código del libro: ");
	 int id = Integer.parseInt(teclado.nextLine());
	 System.out.print("Introduzca el título: ");
	 String titulo = teclado.nextLine();
	 System.out.print("Introduzca el autor: ");
	 String autor = teclado.nextLine();
	 SessionFactory sessionFactory = new
	Configuration().configure().buildSessionFactory();
	 Session session = sessionFactory.openSession();
	 Transaction trans = session.beginTransaction();
	 Libros libro = new Libros(id, new Autores(autor), titulo);
	 session.save(libro);
	 trans.commit();
	 session.close();
 }

public static void borrarPorId() {
	 System.out.print("Código del libro a borrar? ");
	 String id = teclado.nextLine();
	 SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	 Session sesion = sessionFactory.openSession();
	 try {
	 Query consulta = sesion.createQuery("FROM Libros WHERE id = " + id);
	 List<Libros> libros = consulta.list();
	 if (libros.size() > 0) {
	 System.out.println("¿Es este libro (S/N)? " + libros.get(0).getTitulo());
	 String opcion = teclado.nextLine().toUpperCase();
	 if (opcion.equals("S")) {
	 Transaction trans = sesion.beginTransaction();
	 sesion.delete(libros.get(0));
	trans.commit();
	 System.out.println("Libro borrado");
	 }
	 } else {
	 System.out.println("No existe un libro con ese código");
	 }
	 } catch (Exception e) {
	 System.out.println("Error al borrar el libro");
	 }
	 sesion.close();
	 }

public static void actualizarAutor(String codautor) {
	// Crear la conexión con la base de datos
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	Session session = sessionFactory.openSession();

	 Query consulta = session.createQuery("FROM Autores WHERE cod='" + codautor + "'");
	 List resultados = consulta.list();
	 Transaction trans = session.beginTransaction();
	 Autores autorAModificar = (Autores) resultados.get(0);
	 autorAModificar.setNombre("Francisco");
	 session.update(autorAModificar);
	trans.commit();
	// Cerramos la sesión
	session.close();
	 }

public static void main(String[] args) {
	 @SuppressWarnings("unused")
	 org.jboss.logging.Logger logger =
	org.jboss.logging.Logger.getLogger("org.hibernate"); 
	 
	 java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);
	 boolean terminado = false;
	 teclado = new Scanner(System.in);
	 do {
	 System.out.println("Escoja una opción:");
	 System.out.println("1. Añadir un nuevo dato");
	 System.out.println("2. Ver todos los datos existentes");
	 System.out.println("3. Actualitzar autor");
	 System.out.println("4. Borrar per id");
	 System.out.println("0. Salir");
	 String opcion = teclado.nextLine();
	 if (opcion.equals("1"))
	 anyadir();
	 else if (opcion.equals("2"))
	 mostrarLibros();
	 else if (opcion.equals("3"))
		 actualizarAutor("FROJA");
	 else if (opcion.equals("4"))
		 borrarPorId();
	 else if (opcion.equals("0"))
	 terminado = true;
	 } while (!terminado);
	 teclado.close();
	 }
}
