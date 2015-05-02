package by.kipind.hospital.datamodel;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import by.kipind.hospital.datamodel.enam.EProf;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Personal.class)
public abstract class Personal_ extends by.kipind.hospital.datamodel.AbstractEntity_ {

	public static volatile SingularAttribute<Personal, String> firstName;
	public static volatile SingularAttribute<Personal, Boolean> delMarker;
	public static volatile SingularAttribute<Personal, Boolean> conMarker;
	public static volatile SingularAttribute<Personal, String> pass;
	public static volatile SingularAttribute<Personal, Integer> tabelNumber;
	public static volatile SingularAttribute<Personal, EProf> prof;
	public static volatile SetAttribute<Personal, Ward> wards;
	public static volatile SingularAttribute<Personal, String> secondName;

}
