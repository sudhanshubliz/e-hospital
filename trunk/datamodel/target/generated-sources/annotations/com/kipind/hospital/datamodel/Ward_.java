package com.kipind.hospital.datamodel;

import com.kipind.hospital.datamodel.enam.EWardComfort;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Ward.class)
public abstract class Ward_ extends com.kipind.hospital.datamodel.AbstractEntity_ {

	public static volatile SingularAttribute<Ward, EWardComfort> comfortLvl;
	public static volatile SingularAttribute<Ward, Integer> placeNumSum;
	public static volatile SingularAttribute<Ward, Integer> wardNum;
	public static volatile SetAttribute<Ward, Personal> personal;
	public static volatile SingularAttribute<Ward, Integer> placeNumBisy;

}

