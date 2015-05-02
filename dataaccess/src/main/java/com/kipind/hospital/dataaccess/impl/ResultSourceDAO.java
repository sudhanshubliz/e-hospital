package com.kipind.hospital.dataaccess.impl;

import org.springframework.stereotype.Repository;

import com.kipind.hospital.dataaccess.IResultSourceDAO;
import com.kipind.hospital.datamodel.ResultSource;

@Repository
public class ResultSourceDAO extends AbstractDAO<Long, ResultSource> implements IResultSourceDAO {

	protected ResultSourceDAO() {
		super(ResultSource.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ResultSource getByIdFull(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
