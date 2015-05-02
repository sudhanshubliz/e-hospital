package com.kipind.hospital.datamodel;

import com.kipind.hospital.datamodel.enam.EHumanSex;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Patient.class)
public abstract class Patient_ extends com.kipind.hospital.datamodel.AbstractEntity_ {

	public static volatile SingularAttribute<Patient, String> firstName;
	public static volatile SingularAttribute<Patient, String> lastName;
	public static volatile SingularAttribute<Patient, Date> birthDt;
	public static volatile SingularAttribute<Patient, String> address;
	public static volatile SingularAttribute<Patient, EHumanSex> sex;
	public static volatile SingularAttribute<Patient, String> socialNumber;

}

