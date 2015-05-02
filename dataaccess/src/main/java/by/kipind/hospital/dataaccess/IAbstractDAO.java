package by.kipind.hospital.dataaccess;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

public interface IAbstractDAO<ID, Entity> {

	Entity getById(ID id);

	Entity getByIdFull(ID id);

	List<Entity> getAll();

	List<Entity> getAllByField(final SingularAttribute<? super Entity, ?> attribute, final Object value);

	Entity insert(Entity entity);

	Entity update(Entity entity);

	void delete(ID key);

	void delete(List<ID> ids);

	void dropAll(); //

}
