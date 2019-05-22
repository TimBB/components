/**
 * 
 */
package com.basiscomponents.db.util;

import com.basiscomponents.db.DataField;
import com.basiscomponents.db.DataRow;
import com.basiscomponents.db.ResultSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author damore
 *
 */
public class ResultSetJsonMapperTest {

	ResultSet rs;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
		rs = new ResultSet();
		DataRow dr = DataRowProvider.buildSampleDataRow(false);
		DataField df = new DataField(ResultSetProvider.createDefaultResultSet(false));
		dr.addDataField("EMBEDDED_RS", df);
		rs.add(dr);
	}

	@Disabled
	@Test
	public void test() throws Exception {

		String js = rs.toJson();
		ResultSet rs2 = ResultSet.fromJson(js);

		assertTrue(rs2.get(0).getDataField("EMBEDDED_RS").getValue() instanceof ResultSet);
		ResultSet rs3 = (ResultSet) rs2.get(0).getDataField("EMBEDDED_RS").getValue();



	}

}
