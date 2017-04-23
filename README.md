# ADBMS
Domain: Health Analytics

US opiate overdoses:
Accidental death by fatal drug overdose is a rising trend in the United States.
This dataset contains summaries of prescription records for 250 common opioid and non-opioid drugs written by 25,000 unique licensed medical professionals in 2014 in the United States for citizens covered under Class D Medicare as well as some metadata about the doctors themselves. This data is sourced from cms.gov. The full dataset contains almost 24 million prescription instances in long format.
The increase in overdose fatalities is a well-known problem, and the search for possible solutions is an ongoing effort.
The data consists of the following characteristics for each prescriber
NPI – unique National Provider Identifier number
Gender - (M/F)
State - U.S. State by abbreviation
Credentials - set of initials indicative of medical degree
Specialty - description of type of medicinal practice
A long list of drugs with numeric values indicating the total number of prescriptions written for the year by that individual
Opioid.Prescriber - a boolean label indicating whether or not that individual prescribed opiate drugs more than 10 times in the year

For the analysis with reference to https://www.cms.gov/Research-Statistics-Data-and-Systems/Statistics-Trends-and-Reports/Medicare-Provider-Charge-Data/PartD2013.html detailed data for years 2013 and 2014 ranging upto 2.7 gb each year will be used.
https://www.cms.gov/Research-Statistics-Data-and-Systems/Statistics-Trends-and-Reports/Medicare-Provider-Charge-Data/Downloads/Prescriber_Methods.pdfhttps://www.cms.gov/Research-Statistics-Data-and-Systems/Statistics-Trends-and-Reports/Medicare-Provider-Charge-Data/Downloads/Prescriber_Methods.pdf
This data dictionary provides details about data to be used for analysis.
Following analysis can be performed over this dataset:

1. State-wise categorization of drug overdose
2. Top 10 most widely consumed drugs
3. Countrywide drug prescription
4. Total cost per drug
5. Drug categorization as per type
6. State-wise death count
7. Drug categorization according to the prescriber
8. Prescriber categorization as per the medical degree
9. Year-wise drug prescription per drug type

