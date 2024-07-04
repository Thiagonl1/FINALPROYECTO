
package thiago.ppt3v2.logica;
import java.util.List;
import thiago.ppt3v2.persistencia.ControladoraPersistencia;


public class ControladoraLogica {
    ControladoraPersistencia controlPersis = new ControladoraPersistencia();
    
    
    //--------------------------- USUARIO --------------------
    
    public void crearUsuario(Usuario usuario){
        controlPersis.crearUsuario(usuario);
    }
    
    public void eliminarUsuario(int id){
        controlPersis.eliminarUsuario(id);
    }
    
    public void editarUsuario(Usuario usuario){
        controlPersis.editarUsuario(usuario);
    }
    
        public Usuario traerUsuario(int id){
        return controlPersis.traerUsuario(id);
    }
        
    public void crearUsuarios(List<Usuario> usuarios) {
        controlPersis.crearUsuarios(usuarios);
    }
        
    public List<Usuario> traerTodosLosUsuarios() {
        return controlPersis.traerTodosLosUsuarios();
    }    
    
    public void mergearUsuario(Usuario usuario){
        controlPersis.mergearUsuario(usuario);
    }
    
   //----------------------------- CARTAS -------------------
        
    public void crearCartas(Cartas carta){
        controlPersis.crearCartas(carta);
    }
    
    public void eliminarCartas(int id){
        controlPersis.eliminarCartas(id);
    }
    
    public void editarCartas(Cartas carta){
        controlPersis.editarCarta(carta);
    }
    
    public Cartas traerCartas(int id){
        return controlPersis.traerCarta(id);
    }
    
    public void crearCartas(List<Cartas> cartas) {
        controlPersis.crearCartas(cartas);
    }
    
    // ------------------------- TABLAS -----------------------
    
    public boolean verificarTablaVacia(String nombreTabla) {
        return controlPersis.tablaEstaVacia(nombreTabla);
    }
    
    public void eliminarTabla(String nombreTabla){
        controlPersis.eliminarTabla(nombreTabla);
    }
    
    // ------------------------ CARTAUSUARIO ------------------------
    
    public void crearCartaUsuario(CartaUsuario cartaUsuario) {
        controlPersis.crearCartaUsuario(cartaUsuario);
    }
    
    public List<CartaUsuario> findByUsuarioId(Integer usuarioId){
        return controlPersis.findByUsuarioId(usuarioId);
    }
    
    public List<Cartas> traerTodoCarta(){
        return controlPersis.traerTodoCarta();
    }
    
    public void eliminarCartaUsuario(int id){
        controlPersis.eliminarCartaUsuario(id);
    }
    
}
