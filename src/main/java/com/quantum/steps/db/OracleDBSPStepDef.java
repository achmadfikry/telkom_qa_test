package com.quantum.steps.db;

import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.json.JSONArray;
import org.json.JSONObject;

import com.custom.ap.OracleConnection;
import com.custom.ap.Parsing;
import com.qmetry.qaf.automation.step.QAFTestStep;

import oracle.jdbc.OracleTypes;

public class OracleDBSPStepDef extends OracleConnection {
	
	Parsing parsing;
	
	public OracleDBSPStepDef() {
		parsing = new Parsing();
	}
	
	@QAFTestStep(description="sp billing in db oracle {0} with parameter {1} {2} and store into variable {3}")
	//ensure string serviceName in the first parameter and string key in the last parameter 
	public void getQueryBilling(String serviceName, long Polnum, String payChnl, String key) {
		JSONArray array = new JSONArray();
		try {
			oracleConnection(serviceName.toUpperCase());
			//update the parameter here
			callableStmt = con.prepareCall("begin pkg_pymt_chnl.get_inq_rspn_pegadaian(?,?,?,?,?); end;");
			callableStmt.setString(1, String.valueOf(Polnum));
			callableStmt.setString(2, payChnl);
			callableStmt.registerOutParameter(3, OracleTypes.CURSOR);
			callableStmt.registerOutParameter(4, Types.VARCHAR);
			callableStmt.registerOutParameter(5, Types.VARCHAR);
			//end update parameter
			callableStmt.execute();				
			rs = (ResultSet) callableStmt.getObject(3);
			
			while(rs.next()) {
				//update result here
				JSONObject object = new JSONObject();
				object.put("POL_NUM", rs.getString("pol_num")!=null ? rs.getString("pol_num"):"--");
				object.put("cli_nm", rs.getString("cli_nm"));
				object.put("plan_lng_desc", rs.getString("plan_lng_desc"));
				object.put("prod_cat", rs.getString("prod_cat"));
				object.put("pol_stat_cd", rs.getString("pol_stat_cd"));
				object.put("sharia_prod_ind", rs.getString("sharia_prod_ind"));
				object.put("pd_to_dt", parsing.parseDateRemoveTime(rs.getString("pd_to_dt")));
				object.put("prem_bill", rs.getString("prem_bill"));
				object.put("loan_amt", rs.getString("loan_amt"));
				object.put("min_pmt", rs.getString("min_pmt"));
				object.put("xchng_rate", rs.getString("xchng_rate"));
				object.put("pmt_mode", rs.getString("pmt_mode"));
				object.put("prod_crcy_code", rs.getString("prod_crcy_code"));
				object.put("bill_crcy_code", rs.getString("bill_crcy_code"));
				object.put("outstdg_period", rs.getString("outstdg_period"));
				object.put("pmt_susp", rs.getString("pmt_susp"));
				//end update result
				array.put(object);			
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getBundle().setProperty(key, array.toString());
	}
}
