package thiago.ppt3v2.logica;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import thiago.ppt3v2.logica.Cartas;
import thiago.ppt3v2.logica.Usuario;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-07-04T13:06:11", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(CartaUsuario.class)
public class CartaUsuario_ { 

    public static volatile SingularAttribute<CartaUsuario, Cartas> cartaId;
    public static volatile SingularAttribute<CartaUsuario, Integer> cartaUsuarioId;
    public static volatile SingularAttribute<CartaUsuario, Usuario> usuarioId;

}