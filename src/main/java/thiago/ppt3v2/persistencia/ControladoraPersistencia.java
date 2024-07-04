package thiago.ppt3v2.persistencia;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import thiago.ppt3v2.logica.CartaUsuario;
import thiago.ppt3v2.logica.Cartas;
import thiago.ppt3v2.logica.Usuario;
import thiago.ppt3v2.persistencia.exceptions.NonexistentEntityException;

public class ControladoraPersistencia {
    UsuarioJpaController usuarioJpa = new UsuarioJpaController();
    CartasJpaController cartaJpa = new CartasJpaController();
    CartaUsuarioJpaController cartaUserJpa = new CartaUsuarioJpaController();
    private EntityManagerFactory emf;

    public ControladoraPersistencia() {
        this.emf = Persistence.createEntityManagerFactory("dbPU");
    }
    
    // ------------------- USUARIO -----------------
    
    public void crearUsuario(Usuario usuario){
        usuarioJpa.create(usuario);
    }

    public void eliminarUsuario(int id) {
        try {
            usuarioJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editarUsuario(Usuario usuario) {
        try {
            usuarioJpa.edit(usuario);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Usuario traerUsuario(int id) {
       return usuarioJpa.findUsuario(id);
    }
    
    public void crearUsuarios(List<Usuario> usuarios) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        for (Usuario usuario : usuarios) {
            em.persist(usuario);
        }
        em.getTransaction().commit();
        em.close();
    }   
    
     public List<Usuario> traerTodosLosUsuarios() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT u FROM Usuario u");
        List<Usuario> usuarios = query.getResultList();
        em.close();
        return usuarios;
    }
    
     public void mergearUsuario(Usuario usuario) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(usuario); // Actualiza el usuario en la base de datos
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    
    
    
    
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    // --------------- CARTAS -----------------

    public void crearCartas(Cartas carta) {
        cartaJpa.create(carta);
    }

    public void eliminarCartas(int id) {
                try {
            cartaJpa.destroy(id);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editarCarta(Cartas carta) {
                try {
            cartaJpa.edit(carta);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Cartas traerCarta(int id) {
        return cartaJpa.findCartas(id);
    }
    
        public void crearCartas(List<Cartas> cartas) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        for (Cartas carta : cartas) {
            em.persist(carta);
        }
        em.getTransaction().commit();
        em.close();
    }
        
        
        // ---------------------- CARTAS USUARIOS ---------------------
       public void crearCartaUsuario(CartaUsuario cartaUsuario) {
        try {
            cartaUserJpa.create(cartaUsuario);
        } catch (Exception ex) {
            // Manejar cualquier excepción aquí
            ex.printStackTrace();
            // Puedes lanzar una excepción personalizada o registrar un mensaje de error
        }
       }
       
       
       
       
        public List<CartaUsuario> findByUsuarioId(Integer usuarioId) {
            EntityManager em = getEntityManager();
            try {
                TypedQuery<CartaUsuario> query = em.createQuery(
                    "SELECT cu FROM CartaUsuario cu WHERE cu.usuarioId.usuarioId = :usuarioId", CartaUsuario.class);
                query.setParameter("usuarioId", usuarioId);
                return query.getResultList();
            } finally {
                if (em != null && em.isOpen()) {
                    em.close();
                }
            }
        }
        
        // ----------------------- TABLAS ------------------
        
    /**
     *
     * @param nombreTabla
     * @return
     */
    public boolean tablaEstaVacia(String nombreTabla) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT COUNT(t) FROM " + nombreTabla + " t");
        long count = (long) query.getSingleResult();
        em.close();
        return count == 0;
    }
    
    
    // 



    public List<Cartas> traerTodoCarta() {
        return cartaJpa.findCartasEntities();
    }

    public void eliminarCartaUsuario(int id) {
            
        try {
            cartaUserJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            System.err.println("La carta con id " + id + " no existe en la base de datos.");
        } catch (Exception e) {
            System.err.println("Error al eliminar la carta con id " + id);
            e.printStackTrace();
        }
    }
    
    public void eliminarTabla(String entidad){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
        tx.begin();
        Query query = em.createQuery("DELETE FROM "+entidad);
        int count = query.executeUpdate();
        tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    
    
    
}
