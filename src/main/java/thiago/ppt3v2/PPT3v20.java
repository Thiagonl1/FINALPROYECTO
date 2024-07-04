package thiago.ppt3v2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import thiago.ppt3v2.logica.CartaUsuario;
import thiago.ppt3v2.logica.Cartas;
import thiago.ppt3v2.logica.ControladoraLogica;
import thiago.ppt3v2.logica.Usuario;
import thiago.ppt3v2.persistencia.CartasJpaController;
import thiago.ppt3v2.persistencia.ControladoraPersistencia;
import thiago.ppt3v2.persistencia.UsuarioJpaController; 

/**
 *
 * @author Thiago Luque
 */

public class PPT3v20 {

    public static void main(String[] args) {
        // TENGO QUE INICIAR LA PERSISTENCIA ANTES QUE NADA
        ControladoraLogica control = new ControladoraLogica();
        
        //NO PUEDO CREAR UN USUARIO SI NO TENGO CARTAS ANTES, entonces hago un placeholder
        List<Cartas> cartasGlobal = new ArrayList<>();
        
        // CREO UNA LISTA DE USUARIOS A SER IMPORTADOS SI LO REQUIERE
        List<Usuario> usuarios = new ArrayList<>();
                
        
        // EN LAS QUERYS SIEMPRE FIJATE LAS ENTIDADES QUE ESTAN EN PERSISTENCE.XML, POR MAS QUE TE APAREZCA DIFERENTE EN EL DIAGRAMA DE CLASES.
        
        try{
            if(!control.verificarTablaVacia("Usuario")){
                System.out.println("Hay usuarios");
                usuarios = control.traerTodosLosUsuarios();
            }else{
                System.out.println("No hay usuarios");
                Usuario player = new Usuario(1, "null", 3);
                Usuario ia1 = new Usuario(2, "Sucucho", 3);
                Usuario ia2 = new Usuario(3, "Papucho", 3);
                Usuario ia3 = new Usuario(4, "Mengucho", 3);
                Usuario ia4 = new Usuario(5, "Carlos", 3);
                Usuario ia5 = new Usuario(6, "Rapanui", 3);

                usuarios.add(player);
                usuarios.add(ia1);
                usuarios.add(ia2);
                usuarios.add(ia3);
                usuarios.add(ia4);
                usuarios.add(ia5);            
                control.crearUsuarios(usuarios);
            }

            if(!control.verificarTablaVacia("Cartas")){
                // SI HAY CARTAS LAS IMPORTO A UNA LISTA LOCAL
                System.out.println("Hay cartas");



            }else{
                // SI NO HAY CARTAS LAS EXPORTO A LA BASE DE DATOS
                System.out.println("No hay cartas");
                Cartas c1 = new Cartas(1, "Piedra");
                Cartas c2 = new Cartas(2, "Papel");
                Cartas c3 = new Cartas(3, "Tijera");
                Cartas c4 = new Cartas(4, "Piedra");
                Cartas c5 = new Cartas(5, "Papel");
                Cartas c6 = new Cartas(6, "Tijera");
                Cartas c7 = new Cartas(7, "Piedra");
                Cartas c8 = new Cartas(8, "Papel");
                Cartas c9 = new Cartas(9, "Tijera");
                Cartas c10 = new Cartas(10, "Piedra");
                Cartas c11 = new Cartas(11, "Papel");
                Cartas c12 = new Cartas(12, "Tijera");
                cartasGlobal.add(c1);
                cartasGlobal.add(c2);
                cartasGlobal.add(c3);
                cartasGlobal.add(c4);
                cartasGlobal.add(c5);
                cartasGlobal.add(c6);
                cartasGlobal.add(c7);
                cartasGlobal.add(c8);
                cartasGlobal.add(c9);
                cartasGlobal.add(c10);
                cartasGlobal.add(c11);
                cartasGlobal.add(c12);
                control.crearCartas(cartasGlobal);   
            // Persistir la relación CartaUsuario en la base de datos
            }
            if (!control.verificarTablaVacia("CartaUsuario")) {
                System.out.println("Hay cartas en mano de jugadores");
            } else {
                System.out.println("No hay cartas en mano de jugadores");
                int cartaId = 1;
                for (Usuario usuario : usuarios) {
                    for (int j = 0; j < 12; j++) {
                        CartaUsuario cartaUsuario = new CartaUsuario(cartaId++);
                        cartaUsuario.setCartaId(cartasGlobal.get(j)); // Asignar la carta a la instancia de CartaUsuario
                        cartaUsuario.setUsuarioId(usuario); // Asignar el usuario a la instancia de CartaUsuario
                        control.crearCartaUsuario(cartaUsuario);
                    }
                }
            }
            
        }
        finally{
            for (Usuario usuario : usuarios) {
                 usuario.start();
            }
        }
        
        
        
        // LE DOY EL DECK A CADA USUARIO EN CASO DE NO TENER
        
        
        // PONERLE UN NOMBRE AL USUARIO
        
        
        
        /*for (Usuario usuario : usuarios) {
            Thread hiloUsuario = new Thread(usuario);
            hiloUsuario.start();
        }*/
        
        //TENGO QUE REVISAR SI EXISTEN USUARIOS EN TABLAS, ASIMISMO CARTAS 
        
        
        // AL FINAL DE TODO BORRAR CARTAS Y CARTAS_USER. GENERA PROBLEMAS.   
        control.eliminarTabla("CartaUsuario");
        control.eliminarTabla("Cartas");
    }
}
