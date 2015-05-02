package by.kipind.hospital.datamodel;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Checkup.class)
public abstract class Checkup_ extends by.kipind.hospital.datamodel.AbstractEntity_ {

	public static volatile SingularAttribute<Checkup, String> diagnosis;
	public static volatile SingularAttribute<Checkup, Personal> personal;
	public static volatile SingularAttribute<Checkup, Visit> visit;
	public static volatile SingularAttribute<Checkup, Date> chDt;
	public static volatile SingularAttribute<Checkup, String> interview;

}

