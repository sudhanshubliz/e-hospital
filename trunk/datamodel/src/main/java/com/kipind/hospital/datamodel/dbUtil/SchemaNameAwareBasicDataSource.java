package com.kipind.hospital.datamodel.dbUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchemaNameAwareBasicDataSource extends BasicDataSource {

	private static final Logger LOGGER = LoggerFactory.getLogger(SchemaNameAwareBasicDataSource.class);

	private String schema;

	/**
	 * @return the schema
	 */
	public String getSchema() {
		return schema;
	}

	/**
	 * @param schema
	 *            the schema to set
	 */
	public void setSchema(final String schema) {
		Validate.notEmpty(schema, "Illegal schema name");

		this.schema = schema;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return switchSchema(super.getConnection());
	}

	@Override
	public Connection getConnection(final String username, final String password) throws SQLException {
		return switchSchema(super.getConnection(username, password));
	}

	private Connection switchSchema(final Connection connection) throws SQLException {
		final Statement stmt = connection.createStatement();
		try {
			LOGGER.debug("Setting db defalt schema");
			stmt.execute("SET search_path TO " + schema);

		} finally {

			stmt.close();
		}

		return connection;
	}

}
