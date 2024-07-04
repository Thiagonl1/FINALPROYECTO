package thiago.ppt3v2.logica;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import thiago.ppt3v2.logica.CartaUsuario;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-07-04T13:06:11", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Cartas.class)
public class Cartas_ { 

    public static volatile SingularAttribute<Cartas, String> tipo;
    public static volatile CollectionAttribute<Cartas, CartaUsuario> cartaUsuarioCollection;
    public static volatile SingularAttribute<Cartas, Integer> cartaId;

}