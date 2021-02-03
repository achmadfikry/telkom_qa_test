@IDN-2407 
@US_IDN-2405 
Feature: coba service 


@withoutTokenApiOnlywithTestData 
	Scenario Outline: test API with test data
	Given tests are run with test data flagged as "<TCRun>" only
	And store expected value "<content>" into key "contentJson" 
	And store expected value "<method>" into key "methodJson" 
	And store expected value "<locationcountry>" into key "locationcountryJson" 
	When user requests with json data from path "src/main/resources/json/appsApi/00. SampleAPI/circuit.json" 
	Then response should have status code "200" 
	And response should have expected value contains "<expMRDataseries>" at "$.MRData.series" 
	And response should have expected value contains "2017" at "$.MRData.CircuitTable.season" 
	And response should have expected value contains "Albert Park Grand Prix Circuit" at "$.MRData..Circuits[?(@.circuitId=='albert_park')].circuitName" 
	And response should have header "Content-Length" 
	And store response body "$.MRData.series" into "jwtToken" 
	And show response value "jwtToken" in Console 
	And store response body "$.MRData.CircuitTable.season" into "season" 
	And show response value "season" in Console 
	And store response body "$.MRData..Circuits[?(@.Location.country=='${locationcountryJson}')].url" into "url" 
	And show response value "url" in Console 
	Examples: {'datafile':'src/main/resources/testdata/testdataAPI.xls','sheetName':'Sheet2'} 
	
	
@HandleJsonStringinsideJsonValue 
	Scenario Outline: test API with test data 
	Given tests are run with test data flagged as "<TCRun>" only
	And store expected value "<content>" into key "contentJson" 
	And store expected value "<method>" into key "methodJson"  
	And user requests with json data from path "src/main/resources/json/appsApi/00. SampleAPI/pegadaianGetPayload.json"
	And store response body "$.response" into "responsePayload"
	And store expected value "<method2>" into key "methodJson2"
	And user requests with json data from path "src/main/resources/json/appsApi/00. SampleAPI/pegadaianDecryptPayload.json"
	And store response body "$.encryptedPayload" into "responsePayload2"
	And show response value "responsePayload2" in Console
	And Read Json from String "responsePayload2" and store the response body "$.policyNumber" into variable "polnum"
	And show response value "polnum" in Console
	Examples: {'datafile':'resources/testdata/testdataAPI.xls','sheetName':'Sheet3'} 
	
@HandleJsonStringinsideJsonValue2
	Scenario Outline: test API with test data 
	Given store expected value "<content>" into key "contentJson" 
	And store expected value "<method>" into key "methodJson"  
	And user requests with json data from path "src/main/resources/json/appsApi/00. SampleAPI/pegadaianGetPayload.json"
	And store response body "$.response" into "responsePayload"
	And store expected value "<method2>" into key "methodJson2"
	And store expected value "<stringJSON>" into key "responsePayload"
	And show response value "responsePayload" in Console
	And user requests with json data "responsePayload"
	And store response body "$.policyNumber" into "Polnum"
	And show response value "Polnum" in Console
	Examples: {'datafile':'resources/testdata/testdataAPI.xls','sheetName':'Sheet3'} 
		
@withoutTokenWithWeb 
		Scenario: test API 
			Given tests are run with test data flagged as "Y" only
			When user requests with json data from path "src/main/resources/json/appsApi/00. SampleAPI/circuit.json" 
			Then response should have status code "200" 
			And response should have value contains "f1" at "$.MRData.series" 
			And response should have header "Content-Length" 
			And store response body "$.MRData.series" into "jwtToken" 
			And show response value "jwtToken" in Console 
			And I search "jwtToken" in seacrhfield 
			
@withToken 
		Scenario: crm direct query mvp 1
			Given tests are run with test data flagged as "Y" only 
			When user requests with json data from path "src/main/resources/json/appsApi/getToken.json" 
			And store response body "$.access_token" into "jwtToken" 
			When user requests with json data from path "src/main/resources/json/appsApi/GSPaymentInfo.json" 
			Then response should have status code "200" 
			And show response value "jwtToken" in Console 
			And I search "jwtToken" in seacrhfield