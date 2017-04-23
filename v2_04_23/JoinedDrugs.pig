prescribedDrugs = LOAD '/Project/ProcessedData/PrescribedDrugInfo/part-r-00000' AS (npi,drugname,state,genericName);
opioidDrugs = Load '/Project/ProjectData/OpioidDrug.csv' using PigStorage(',') AS (drugname,genericName);
joinedDrugs = JOIN prescribedDrugs BY drugname,opioidDrugs BY drugname;
STORE joinedDrugs INTO '/Project/ProcessedData/PrescribedOpioids';

