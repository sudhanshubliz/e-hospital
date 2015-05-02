package com.kipind.hospital.datamodel;

import com.kipind.hospital.datamodel.enam.EDischargeStatus;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Visit.class)
public abstract class Visit_ extends com.kipind.hospital.datamodel.AbstractEntity_ {

	public static volatile SingularAttribute<Visit, Integer> importantFlag;
	public static volatile SingularAttribute<Visit, Patient> patient;
	public static volatile SingularAttribute<Visit, Date> startDt;
	public static volatile SingularAttribute<Visit, Date> endDt;
	public static volatile SingularAttribute<Visit, EDischargeStatus> dischargeFlag;
	public static volatile SingularAttribute<Visit, Ward> ward;
	public static volatile SingularAttribute<Visit, String> firstDs;
	public static volatile SingularAttribute<Visit, String> lastDs;

}

