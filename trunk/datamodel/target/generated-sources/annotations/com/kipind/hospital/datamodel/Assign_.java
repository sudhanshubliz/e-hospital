package com.kipind.hospital.datamodel;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Assign.class)
public abstract class Assign_ extends com.kipind.hospital.datamodel.AbstractEntity_ {

	public static volatile SingularAttribute<Assign, Visit> visit;
	public static volatile SingularAttribute<Assign, Personal> prscPersonal;
	public static volatile SingularAttribute<Assign, String> prscText;
	public static volatile SingularAttribute<Assign, Date> prscDt;

	public static volatile SingularAttribute<Assign, Personal> resPersonal;
	public static volatile SingularAttribute<Assign, String> resText;
	public static volatile SingularAttribute<Assign, Date> resDt;

	public static volatile SingularAttribute<Assign, Long> periodGroupKey;
	// public static volatile SetAttribute<Assign, ResultSource> resSourseList;

}
