package com.quantum.steps.db;

import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.custom.ap.OracleConnection;
import com.qmetry.qaf.automation.step.QAFTestStep;

public class OracleDBQueryStepDef extends OracleConnection {
		
	@QAFTestStep(description="query payment mode in db oracle {0} where {1} and store into variable {2}")
	//ensure string serviceName in the first parameter and string key in the last parameter 
	public void getPaymentMode(String serviceName, String where, String key) {
		JSONArray array = new JSONArray();		
		try {
			oracleConnection(serviceName.toUpperCase());
			stmt = con.createStatement();
			//update query here
			String query  = "select FLD_VALU, FLD_VALU_DESC FROM tfield_values where fld_nm = '"+where+"'";		
			//end update query
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {	
				//update result here as many columns you select in query
				JSONObject object = new JSONObject();				
				object.put("FLD_VALU_DESC", rs.getString("FLD_VALU_DESC"));
				object.put("FLD_VALU", rs.getString("FLD_VALU"));				
				array.put(object);
				//end update result
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getBundle().setProperty(key, array.toString());
	}
}
