package com.basiscomponents.bc;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.basiscomponents.db.ResultSet;

public class SqlTableBCConnetionTest {

	private String sql;
	private ResultSet rs;

	/**
	 * Loading the h2-Driver and creating the test databases.
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@BeforeAll
	public static void initialize()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver").newInstance();
		H2DataBaseProvider.createTestDataBaseForSQLRetrieve();
		H2DataBaseProvider.createTestDataBaseForNormalRetrieve();
	}

	/**
	 * Creates a SqlTableBC with a connection to a h2-DataBase.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@Test
	public void SqlTableBCConnectionSimpleTest()
			throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		try (Connection con = DriverManager.getConnection("jdbc:h2:./src/test/testH2DataBases/test1", "sa", "sa");) {
			SqlTableBC sqlTable = new SqlTableBC(con);
		}
	}

	/**
	 * Creates a SqlTableBC with a connection to a h2-DataBase. The active table is
	 * switched to REGISTRATION and its values are queried with retrieve(0,0). The
	 * resulting ResultSet is checked to contain the right data.
	 * 
	 * @throws Exception
	 */
//	@Test
	// This test cannot work at the moment, because pagination is not implemented
	// yet for H2DataBases.
	public void SqlTableBCGetDataSimpleParameterTest() throws Exception {
		try (Connection con = DriverManager.getConnection("jdbc:h2:./src/test/testH2DataBases/test2", "sa", "sa");
				Statement stmt = con.createStatement();) {

			// Set table and get its data with normal retrieve()
			SqlTableBC sqlTable = new SqlTableBC(con);
			sqlTable.setTable("PRIMARYKEY_REGISTRATION");
			rs = sqlTable.retrieve(0, 0);

			// Checking the ColumnNames and results
			assertTrue(rs.getColumnCount() == 3);
			assertTrue(rs.getColumnNames().contains("FIRST"));
			assertTrue(rs.getColumnNames().contains("AGE"));
			assertTrue(rs.getColumnNames().contains("CUSTOMERID"));

			assertEquals("Alfred", rs.get(0).getFieldValue("FIRST"));
			assertEquals(62, rs.get(0).getFieldValue("AGE"));
			assertEquals(0, rs.get(0).getFieldValue("CUSTOMERID"));
		}
	}

	/**
	 * Creates a SqlTableBC with a connection to a h2-DataBase. The active table is
	 * switched to REGISTRATION and its values are queried with retrieve(). The
	 * resulting ResultSet is checked to contain the right data.
	 * 
	 * @throws Exception
	 */
	@Test
	public void SqlTableBCGetDataSimpleTest() throws Exception {
		try (Connection con = DriverManager.getConnection("jdbc:h2:./src/test/testH2DataBases/test2", "sa", "sa");
				Statement stmt = con.createStatement();) {

			// Set table and get its data with normal retrieve()
			SqlTableBC sqlTable = new SqlTableBC(con);
			sqlTable.setTable("PRIMARYKEY_REGISTRATION");
			rs = sqlTable.retrieve();

			// Checking the ColumnNames and results
			assertTrue(rs.getColumnCount() == 3);
			assertTrue(rs.getColumnNames().contains("FIRST"));
			assertTrue(rs.getColumnNames().contains("AGE"));
			assertTrue(rs.getColumnNames().contains("CUSTOMERID"));

			assertEquals("Alfred", rs.get(0).getFieldValue("FIRST"));
			assertEquals(62, rs.get(0).getFieldValue("AGE"));
			assertEquals(0, rs.get(0).getFieldValue("CUSTOMERID"));
		}
	}

	/**
	 * Creates a SqlTableBC with a connection to a h2-DataBase. The active table is
	 * switched to BOOLTABLE and its values are queried with retrieve(). The
	 * resulting ResultSet is checked to contain the right data, which are boolean
	 * types.
	 * 
	 * @throws Exception
	 */
	@Test
	public void SqlTableBCGetDataBoolTest() throws Exception {
		try (Connection con = DriverManager.getConnection("jdbc:h2:./src/test/testH2DataBases/test2", "sa", "sa");
				Statement stmt = con.createStatement();) {

			// Set table and get its data with normal retrieve()
			SqlTableBC sqlTable = new SqlTableBC(con);
			sqlTable.setTable("BOOLTABLE");
			rs = sqlTable.retrieve();

			// Checking the ColumnNames and results
			assertTrue(rs.getColumnCount() == 3);
			assertTrue(rs.getColumnNames().contains("BOOLTYPE"));
			assertTrue(rs.getColumnNames().contains("BOOLEANTYPE"));
			assertTrue(rs.getColumnNames().contains("BITTYPE"));

			assertEquals(true, rs.get(0).getFieldValue("BOOLTYPE"));
			assertEquals(false, rs.get(0).getFieldValue("BOOLEANTYPE"));
			assertEquals(true, rs.get(0).getFieldValue("BITTYPE"));
		}
	}

	/**
	 * Creates a SqlTableBC with a connection to a h2-DataBase. The active table is
	 * switched to BOOLTABLE and its values are queried with retrieve(). The
	 * resulting ResultSet is checked to contain the right data, which are normal
	 * sized integer types.
	 * 
	 * @throws Exception
	 */
	@Test
	public void SqlTableBCGetDataStandartIntegerTest() throws Exception {
		try (Connection con = DriverManager.getConnection("jdbc:h2:./src/test/testH2DataBases/test2", "sa", "sa");
				Statement stmt = con.createStatement();) {

			// Set table and get its data with normal retrieve()
			SqlTableBC sqlTable = new SqlTableBC(con);
			sqlTable.setTable("INTTABLE");
			rs = sqlTable.retrieve();

			// Checking the ColumnNames and results
			assertTrue(rs.getColumnCount() == 5);
			assertTrue(rs.getColumnNames().contains("INTTYPE"));
			assertTrue(rs.getColumnNames().contains("INTEGERTYPE"));
			assertTrue(rs.getColumnNames().contains("MEDIUMINTTYPE"));
			assertTrue(rs.getColumnNames().contains("INT4TYPE"));
			assertTrue(rs.getColumnNames().contains("SIGNEDTYPE"));

			assertEquals(1, rs.get(0).getFieldValue("INTTYPE"));
			assertEquals(-1, rs.get(0).getFieldValue("INTEGERTYPE"));
			assertEquals(0, rs.get(0).getFieldValue("MEDIUMINTTYPE"));
			assertEquals(-2147483648, rs.get(0).getFieldValue("INT4TYPE"));
			assertEquals(2147483647, rs.get(0).getFieldValue("SIGNEDTYPE"));
		}
	}

	/**
	 * Creates a SqlTableBC with a connection to a h2-DataBase. The active table is
	 * switched to BOOLTABLE and its values are queried with retrieve(). The
	 * resulting ResultSet is checked to contain the right data, which are special
	 * sized integer types.
	 * 
	 * @throws Exception
	 */
	@Test
	public void SqlTableBCGetDataSpecialIntegerTest() throws Exception {
		try (Connection con = DriverManager.getConnection("jdbc:h2:./src/test/testH2DataBases/test2", "sa", "sa");
				Statement stmt = con.createStatement();) {

			// Set table and get its data with normal retrieve()
			SqlTableBC sqlTable = new SqlTableBC(con);
			sqlTable.setTable("SPECIALINTTABLE");
			rs = sqlTable.retrieve();

			// Checking the ColumnNames and results
			assertTrue(rs.getColumnCount() == 3);
			assertTrue(rs.getColumnNames().contains("TINYINTTYPE"));
			assertTrue(rs.getColumnNames().contains("SMALLINTTYPE"));
			assertTrue(rs.getColumnNames().contains("BIGINTTYPE"));

			java.lang.Long l = Long.parseLong("9223372036854775807");
			assertEquals((byte) 127, rs.get(0).getFieldValue("TINYINTTYPE"));
			assertEquals((short) 32767, rs.get(0).getFieldValue("SMALLINTTYPE"));
			assertEquals(l, rs.get(0).getFieldValue("BIGINTTYPE"));

			sqlTable.setTable("BIGDECIMALTABLE");
			rs = sqlTable.retrieve();

			assertTrue(rs.getColumnCount() == 1);
			assertTrue(rs.getColumnNames().contains("BIGDECIMALTYPE"));

			java.math.BigDecimal bd = new BigDecimal("64543");
			assertEquals(bd, rs.get(0).getFieldValue("BIGDECIMALTYPE"));
		}
	}

	/**
	 * Creates a SqlTableBC with a connection to a h2-DataBase. The active table is
	 * switched to BOOLTABLE and its values are queried with retrieve(). The
	 * resulting ResultSet is checked to contain the right data, which are double
	 * and float types.
	 * 
	 * @throws Exception
	 */
	@Test
	public void SqlTableBCGetDataDoubleTest() throws Exception {
		try (Connection con = DriverManager.getConnection("jdbc:h2:./src/test/testH2DataBases/test2", "sa", "sa");
				Statement stmt = con.createStatement();) {

			// Set table and get its data with normal retrieve()
			SqlTableBC sqlTable = new SqlTableBC(con);
			sqlTable.setTable("DOUBLETABLE");
			rs = sqlTable.retrieve();

			// Checking the ColumnNames and results
			assertTrue(rs.getColumnCount() == 4);
			assertTrue(rs.getColumnNames().contains("DOUBLETYPE"));
			assertTrue(rs.getColumnNames().contains("FLOATASDOUBLETYPE"));
			assertTrue(rs.getColumnNames().contains("REALTYPE"));
			assertTrue(rs.getColumnNames().contains("FLOATASFLOATTYPE"));

			assertEquals(127.43324344, rs.get(0).getFieldValue("DOUBLETYPE"));
			assertEquals(32767.534344, rs.get(0).getFieldValue("FLOATASDOUBLETYPE"));
			assertEquals((float) 1.23, rs.get(0).getFieldValue("REALTYPE"));
			assertEquals((float) 1.23, rs.get(0).getFieldValue("FLOATASFLOATTYPE"));
		}
	}

	/**
	 * Creates a SqlTableBC with a connection to a h2-DataBase. A SQL Statement is
	 * created and the SqlTableBC is queried with retrieve(String sql, null). The
	 * resulting ResultSet is checked to contain the right data.
	 * 
	 * @throws Exception
	 */
	@Test
	public void SqlTableBCGetDataWithSQLSimpleTest() throws Exception {
		try (Connection con = DriverManager.getConnection("jdbc:h2:./src/test/testH2DataBases/test1", "sa", "sa");
				Statement stmt = con.createStatement();) {

			SqlTableBC sqlTable = new SqlTableBC(con);

			// Collect data from the SqlTableBC
			sql = "SELECT * FROM REGISTRATION";
			rs = sqlTable.retrieve(sql, null);

			// Checking the ColumnNames and results
			assertTrue(rs.getColumnCount() == 3);
			assertTrue(rs.getColumnNames().contains("FIRST"));
			assertTrue(rs.getColumnNames().contains("AGE"));
			assertTrue(rs.getColumnNames().contains("CUSTOMERID"));

			assertEquals("Alfred", rs.get(0).getFieldValue("FIRST"));
			assertEquals(62, rs.get(0).getFieldValue("AGE"));
			assertEquals(1, rs.get(0).getFieldValue("CUSTOMERID"));
		}
	}

	/**
	 * Creates a SqlTableBC with a connection to a h2-DataBase. A SQL Statement is
	 * created and the SqlTableBC is queried with retrieve(String sql, null). The
	 * resulting ResultSet is checked to contain the right data.
	 * 
	 * @throws Exception
	 */
	@Test
	public void SqlTableBCGetDataWithSQLTest2() throws Exception {
		try (Connection con = DriverManager.getConnection("jdbc:h2:./src/test/testH2DataBases/test1", "sa", "sa");
				Statement stmt = con.createStatement();) {

			SqlTableBC sqlTable = new SqlTableBC(con);

			// Collect data from the SqlTableBC
			sql = "SELECT * FROM REGISTRATION, TREES";
			rs = sqlTable.retrieve(sql, null);

			// Checking the ColumnNames
			assertTrue(rs.getColumnCount() == 6);
			assertTrue(rs.getColumnNames().contains("FIRST"));
			assertTrue(rs.getColumnNames().contains("AGE"));
			assertTrue(rs.getColumnNames().contains("NAME"));
			assertTrue(rs.getColumnNames().contains("RINGS"));
			assertTrue(rs.getColumnNames().contains("HEIGHT"));

			// Checking DataRows 0 and 1
			assertEquals("Alfred", rs.get(0).getFieldValue("FIRST"));
			assertEquals(62, rs.get(0).getFieldValue("AGE"));
			assertEquals("tree1", rs.get(0).getFieldValue("NAME"));
			assertEquals(155, rs.get(0).getFieldValue("RINGS"));
			assertEquals(144.32, rs.get(0).getFieldValue("HEIGHT"));

			assertEquals("Alfred", rs.get(1).getFieldValue("FIRST"));
			assertEquals(62, rs.get(1).getFieldValue("AGE"));
			assertEquals("tree2", rs.get(1).getFieldValue("NAME"));
			assertEquals(132, rs.get(1).getFieldValue("RINGS"));
			assertEquals(1004.53, rs.get(1).getFieldValue("HEIGHT"));

			// Checking DataRows 2 and 3
			assertEquals("Simpson", rs.get(2).getFieldValue("FIRST"));
			assertEquals(42, rs.get(2).getFieldValue("AGE"));
			assertEquals("tree1", rs.get(2).getFieldValue("NAME"));
			assertEquals(155, rs.get(2).getFieldValue("RINGS"));
			assertEquals(144.32, rs.get(2).getFieldValue("HEIGHT"));

			assertEquals("Simpson", rs.get(3).getFieldValue("FIRST"));
			assertEquals(42, rs.get(3).getFieldValue("AGE"));
			assertEquals("tree2", rs.get(3).getFieldValue("NAME"));
			assertEquals(132, rs.get(3).getFieldValue("RINGS"));
			assertEquals(1004.53, rs.get(3).getFieldValue("HEIGHT"));
		}
	}

	/**
	 * Creates a SqlTableBC with a connection to a h2-DataBase. A SQL Statement is
	 * created and the SqlTableBC is queried with retrieve(String sql, null). The
	 * UNION, UNION ALL and INTERSECT operator are used in this case. The resulting
	 * ResultSet is checked to contain the right data.
	 * 
	 * @throws Exception
	 */
	@Test
	public void SqlTableBCGetDataWithSQLUnionIntersectTest() throws Exception {
		try (Connection con = DriverManager.getConnection("jdbc:h2:./src/test/testH2DataBases/test1", "sa", "sa");
				Statement stmt = con.createStatement();) {

			SqlTableBC sqlTable = new SqlTableBC(con);

			// 1.
			// Checking the UNION operator
			sql = "SELECT * FROM REGISTRATION UNION SELECT * FROM REGISTRATION2";
			rs = sqlTable.retrieve(sql, null);

			// Checking the ColumnNames and results
			assertTrue(rs.getColumnCount() == 3);
			assertTrue(rs.getColumnNames().contains("FIRST"));
			assertTrue(rs.getColumnNames().contains("AGE"));

			assertEquals("Alfred", rs.get(0).getFieldValue("FIRST"));
			assertEquals(62, rs.get(0).getFieldValue("AGE"));
			assertEquals("Jasper", rs.get(1).getFieldValue("FIRST"));
			assertEquals(33, rs.get(1).getFieldValue("AGE"));
			assertEquals("Simpson", rs.get(2).getFieldValue("FIRST"));
			assertEquals(42, rs.get(2).getFieldValue("AGE"));

			// 2.
			// Checking the UNION ALL operator
			sql = "SELECT * FROM REGISTRATION UNION ALL SELECT * FROM REGISTRATION2";
			rs = sqlTable.retrieve(sql, null);

			// Checking the ColumnNames and results
			assertTrue(rs.getColumnCount() == 3);
			assertTrue(rs.getColumnNames().contains("FIRST"));
			assertTrue(rs.getColumnNames().contains("AGE"));

			assertEquals("Alfred", rs.get(0).getFieldValue("FIRST"));
			assertEquals(62, rs.get(0).getFieldValue("AGE"));
			assertEquals("Simpson", rs.get(1).getFieldValue("FIRST"));
			assertEquals(42, rs.get(1).getFieldValue("AGE"));
			assertEquals("Jasper", rs.get(2).getFieldValue("FIRST"));
			assertEquals(33, rs.get(2).getFieldValue("AGE"));
			assertEquals("Alfred", rs.get(3).getFieldValue("FIRST"));
			assertEquals(62, rs.get(3).getFieldValue("AGE"));

			// 3.
			// Checking the INTERSECT operator
			sql = "SELECT * FROM REGISTRATION INTERSECT SELECT * FROM REGISTRATION2";
			rs = sqlTable.retrieve(sql, null);

			// Checking the ColumnNames and results
			assertTrue(rs.getColumnCount() == 3);
			assertTrue(rs.getColumnNames().contains("FIRST"));
			assertTrue(rs.getColumnNames().contains("AGE"));

			assertEquals("Alfred", rs.get(0).getFieldValue("FIRST"));
			assertEquals(62, rs.get(0).getFieldValue("AGE"));
		}
	}

	/**
	 * Creates a SqlTableBC with a connection to a h2-DataBase. A SQL Statement is
	 * created and the SqlTableBC is queried with retrieve(String sql, null). The
	 * WHERE operator is used in this case. The resulting ResultSet is checked to
	 * contain the right data.
	 * 
	 * @throws Exception
	 */
	@Test
	public void SqlTableBCGetDataWithSQLWhereTest() throws Exception {
		try (Connection con = DriverManager.getConnection("jdbc:h2:./src/test/testH2DataBases/test1", "sa", "sa");
				Statement stmt = con.createStatement();) {

			// Collect data from the SqlTableBC
			SqlTableBC sqlTable = new SqlTableBC(con);
			sql = "SELECT * FROM REGISTRATION WHERE FIRST = 'Alfred' UNION ALL SELECT * FROM REGISTRATION2 WHERE FIRST = 'Alfred'";
			rs = sqlTable.retrieve(sql, null);

			// Checking the ColumnNames and results
			assertTrue(rs.getColumnCount() == 3);
			assertTrue(rs.getColumnNames().contains("FIRST"));
			assertTrue(rs.getColumnNames().contains("AGE"));

			assertEquals("Alfred", rs.get(0).getFieldValue("FIRST"));
			assertEquals(62, rs.get(0).getFieldValue("AGE"));
			assertEquals("Alfred", rs.get(1).getFieldValue("FIRST"));
			assertEquals(62, rs.get(1).getFieldValue("AGE"));
		}
	}

	/**
	 * Creates a SqlTableBC with a connection to a h2-DataBase. A SQL Statement is
	 * created and the SqlTableBC is queried with retrieve(String sql, null). The
	 * GROUP BY and ORDER BY operators are used in this case. The resulting
	 * ResultSet is checked to contain the right data.
	 * 
	 * @throws Exception
	 */
	@Test
	public void SqlTableBCGetDataWithSQLGroupByOrderByTest() throws Exception {
		try (Connection con = DriverManager.getConnection("jdbc:h2:./src/test/testH2DataBases/test1", "sa", "sa");
				Statement stmt = con.createStatement();) {

			SqlTableBC sqlTable = new SqlTableBC(con);

			// 1.
			// Checking the GROUP BY operator
			sql = "SELECT COUNT(CUSTOMERID), COUNTRY FROM CUSTOMERS GROUP BY COUNTRY";
			rs = sqlTable.retrieve(sql, null);

			// Checking the ColumnNames
			assertTrue(rs.getColumnCount() == 2);
			assertTrue(rs.getColumnNames().contains("COUNT(CUSTOMERID)"));
			assertTrue(rs.getColumnNames().contains("COUNTRY"));

			// Checking the values of the resulting GROUP BY
			assertEquals("Australia", rs.get(0).getFieldValue("COUNTRY"));
			assertEquals((long) 1, rs.get(0).getFieldValue("COUNT(CUSTOMERID)"));
			assertEquals("England", rs.get(1).getFieldValue("COUNTRY"));
			assertEquals((long) 3, rs.get(1).getFieldValue("COUNT(CUSTOMERID)"));
			assertEquals("USA", rs.get(2).getFieldValue("COUNTRY"));
			assertEquals((long) 4, rs.get(2).getFieldValue("COUNT(CUSTOMERID)"));

			// 2.
			// Checking the ORDER BY operator
			sql = "SELECT * FROM CUSTOMERS ORDER BY COUNTRY";
			rs = sqlTable.retrieve(sql, null);

			// Checking the ColumnNames
			assertTrue(rs.getColumnCount() == 3);
			assertTrue(rs.getColumnNames().contains("CUSTOMERID"));
			assertTrue(rs.getColumnNames().contains("COUNTRY"));
			assertTrue(rs.getColumnNames().contains("NAME"));

			// Checking the order of the country fields
			assertEquals("Australia", rs.get(0).getFieldValue("COUNTRY"));
			assertEquals("England", rs.get(1).getFieldValue("COUNTRY"));
			assertEquals("England", rs.get(2).getFieldValue("COUNTRY"));
			assertEquals("England", rs.get(3).getFieldValue("COUNTRY"));
			assertEquals("USA", rs.get(4).getFieldValue("COUNTRY"));
			assertEquals("USA", rs.get(5).getFieldValue("COUNTRY"));
			assertEquals("USA", rs.get(6).getFieldValue("COUNTRY"));
			assertEquals("USA", rs.get(7).getFieldValue("COUNTRY"));
		}
	}

	/**
	 * Cleans up the databases.
	 * 
	 * @throws Exception
	 */
	@AfterAll
	public static void cleanUp() throws Exception {
		try (Connection con = DriverManager.getConnection("jdbc:h2:./src/test/testH2DataBases/test1", "sa", "sa");
				Statement stmt = con.createStatement();) {

			// Get tableNames
			String sql = "SHOW TABLES;";
			stmt.execute(sql);
			java.sql.ResultSet rs = stmt.getResultSet();
			ArrayList<String> tableNames = new ArrayList<String>();
			while (rs.next()) {
				tableNames.add(rs.getString("TABLE_NAME"));
			}

			// Drop all tables
			for (int i = 0; i < tableNames.size(); i++) {
				sql = "DROP TABLE " + tableNames.get(i);
				stmt.executeUpdate(sql);
			}
		}

		try (Connection con = DriverManager.getConnection("jdbc:h2:./src/test/testH2DataBases/test2", "sa", "sa");
				Statement stmt = con.createStatement();) {

			// Get tableNames
			String sql = "SHOW TABLES;";
			stmt.execute(sql);
			java.sql.ResultSet rs = stmt.getResultSet();
			ArrayList<String> tableNames = new ArrayList<String>();
			while (rs.next()) {
				tableNames.add(rs.getString("TABLE_NAME"));
			}

			// Drop all tables
			for (int i = 0; i < tableNames.size(); i++) {
				sql = "DROP TABLE " + tableNames.get(i);
				stmt.executeUpdate(sql);
			}
		}
	}

}
