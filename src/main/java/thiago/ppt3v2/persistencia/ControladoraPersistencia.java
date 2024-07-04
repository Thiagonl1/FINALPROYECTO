package thiago.ppt3v2.persistencia;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
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
                return em.createQuery("SELECT c FROM CartaUsuario c WHERE c.usuarioId.usuarioId = :usuarioId", CartaUsuario.class)
                         .setParameter("usuarioId", usuarioId)
                         .getResultList();
            } finally {
                em.close();
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

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Cartas> traerTodoCarta() {
        return cartaJpa.findCartasEntities();
    }

    public void eliminarCartaUsuario(int id) {
        try {
            cartaUserJpa.destroy(id);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
