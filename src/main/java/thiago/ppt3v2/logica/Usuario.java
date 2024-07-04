package thiago.ppt3v2.logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import thiago.ppt3v2.persistencia.ControladoraPersistencia;


@Entity
@Table(name = "usuario")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByUsuarioId", query = "SELECT u FROM Usuario u WHERE u.usuarioId = :usuarioId"),
    @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre"),
    @NamedQuery(name = "Usuario.findByEstrellas", query = "SELECT u FROM Usuario u WHERE u.estrellas = :estrellas")})
public class Usuario extends Thread implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "usuarioId")
    private Integer usuarioId;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "estrellas")
    private int estrellas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioId")
    private Collection<CartaUsuario> cartaUsuarioCollection;

    public Usuario() {
    }

    
    public Usuario(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Usuario(Integer usuarioId, String nombre, int estrellas) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.estrellas = estrellas;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(int estrellas) {
        this.estrellas = estrellas;
    }

    public Collection<CartaUsuario> getCartaUsuarioCollection() {
        return cartaUsuarioCollection;
    }

    public void setCartaUsuarioCollection(Collection<CartaUsuario> cartaUsuarioCollection) {
        this.cartaUsuarioCollection = cartaUsuarioCollection;
    }
    
    
    /*public void juego(int desicion, Usuario ia){
        Random random = new Random();
        int eleccionIA = random.nextInt(ia.cartas.size());
        // implementar win/loose
        System.out.println("El jugador ("+this.apodo+") eligio "+this.cartas.get(desicion).getTipo()+"\nLa ia eligio "+ ia.cartas.get(eleccionIA).getTipo());
        verificar(ia, desicion, eleccionIA);
        //
        this.cartas.remove(desicion);

        ia.cartas.remove(eleccionIA);

    }*/

    
    public void juego(int desicion, Usuario ia, List<Cartas> cartaMano){
        ControladoraPersistencia controlP = new ControladoraPersistencia();
        
        // TENGO QUE VER LA MANO DE LA IA... YA SABEMOS QUE HACER PARA ESO
        List<CartaUsuario> manoIa = controlP.findByUsuarioId(ia.getUsuarioId());

        //AHORA VEO LAS CARTAS EN MANO QUE TIENE
        List<Cartas> cartaManoIa = new ArrayList<>();
        List<Cartas> todas = controlP.traerTodoCarta();
        for (CartaUsuario cartaUsuario : manoIa) {
            for (Cartas carta : todas) {
                if (Objects.equals(cartaUsuario.getCartaId().getCartaId(), carta.getCartaId())) {
                    cartaManoIa.add(carta);
                    break;  // Salir del bucle interno una vez que se encuentra la carta
                }
            }
        }

         Random random = new Random();
        int eleccionIA = random.nextInt(cartaManoIa.size());
        System.out.println("El jugador ("+ this.getNombre() +") eligió "+ cartaMano.get(desicion).getTipo() +"\nLa IA eligió "+ cartaManoIa.get(eleccionIA).getTipo());
    
        
        //verificar(ia, desicion, eleccionIA);
        CartaUsuario cartaUsuarioAEliminar = manoIa.get(desicion);
        try {
            controlP.eliminarCartaUsuario(cartaUsuarioAEliminar.getCartaUsuarioId());
        } catch (Exception e) {
            System.err.println("La carta ya no existe en la base de datos.");
        }
        
        /*
        this.cartas.remove(desicion);

        ia.cartas.remove(eleccionIA);*/
    }
    
    
    
    
    
    public void juegoPreliminar(int usuarioId, Usuario ia, List<CartaUsuario> mano){
        ControladoraPersistencia controlP = new ControladoraPersistencia();
        Scanner scan = new Scanner(System.in);
        // ANTES DE MOSTRAR LAS CARTAS, NECESITO TENERLAS JSJS
        // LO QUE TENGO ACA ES EL ID CORRESPONDIENTE A LOS TIPOS DE CARTAS QUE TIENE CADA UNO
        // QUE SIGNIFICA? QUE AHORA BASICAMENTE TENGO QUE HACER UN ARRAYLIST CON LAS CARTAS "VISUALES" CORRESPONDIENTES AL ID
        
        // CARTAS QUE VA A UTILIZAR Y/O VER EL USUARIO
        List<CartaUsuario> cartasUsuario = controlP.findByUsuarioId(usuarioId);
         // Obtener todas las cartas disponibles
        List<Cartas> todasLasCartas = controlP.traerTodoCarta();
        // Filtrar las cartas visuales que pertenecen al usuario actual
        List<Cartas> cartaMano = new ArrayList<>();
        
        for (CartaUsuario cartaUsuario : cartasUsuario) {
            for (Cartas carta : todasLasCartas) {
                if (Objects.equals(cartaUsuario.getCartaId().getCartaId(), carta.getCartaId())) {
                    cartaMano.add(carta);
                    break;  // Salir del bucle interno una vez que se encuentra la carta
                }
            }
        }
        
        // PONGO LAS CARTAS EN MANO DEL USUARIO
        int index = 1;
        System.out.println("¿Qué carta vas a utilizar?");
        for (Cartas carta : cartaMano) {
            System.out.println(index + ") " + carta.getTipo());
            index++;
        }
        // Leer la decisión del usuario
        int decision = scan.nextInt();
        while (decision < 1 || decision > cartaMano.size()) {
            System.out.println("Elija una carta válida:");
            decision = scan.nextInt();
        }
        juego(decision, ia, cartaMano);
        // borro la carta utilizada de la bdd
        
        CartaUsuario cartaUsuarioAEliminar = cartasUsuario.get(decision - 1);
        try {
            controlP.eliminarCartaUsuario(cartaUsuarioAEliminar.getCartaUsuarioId());
        } catch (Exception e) {
            System.err.println("La carta ya no existe en la base de datos.");
        }
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioId != null ? usuarioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuarioId == null && other.usuarioId != null) || (this.usuarioId != null && !this.usuarioId.equals(other.usuarioId))) {
            return false;
        }
        return true;
    }
    
    
    @Override
    public void run(){
        ControladoraPersistencia controlP = new ControladoraPersistencia();
        
        // TENGO QUE TRAER A TODOS LOS USUARIOS SEA PARA EL PLAYER O LAS IAS IGUALMENTE.
        List<Usuario> usuarios = new ArrayList<>();
        usuarios = controlP.traerTodosLosUsuarios();
        
        Random random = new Random();
        Scanner scan = new Scanner(System.in);

        List<CartaUsuario> mano = controlP.findByUsuarioId(this.usuarioId);
        if(this.usuarioId == 1){
            // EXPORTAMOS EL DECK ACA
            
            
            while(!mano.isEmpty() && this.estrellas != 0){
                // SELECCIONAR UNA IA
                System.out.println("Que ia vas a elegir?");
                for(int i=1 ; i < usuarios.size() ; i++ ){
                    System.out.println(i+ ") "+ usuarios.get(i).getNombre());
                }
                int opcion =(scan.nextInt());
                while(opcion < 1 || opcion > 5){
                    System.out.println("Ingrese un oponente valido");
                    opcion = (scan.nextInt());
                }
                this.juegoPreliminar(this.usuarioId, usuarios.get(opcion), mano);
                mano = controlP.findByUsuarioId(this.usuarioId);
            }
        }
    }
    
}
