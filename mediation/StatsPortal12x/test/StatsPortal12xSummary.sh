#!/bin/bash


function print_date_time() {
	datenow=`date +%d/%m/%Y~%H:%M:%S`
	echo "$datenow - "$1
}


function send_email() {
   sendTo=$1
   message="<br>"
   message=$message""$2""
   echo "***** Sending summary report *****"
   mail -a 'Content-Type: text/html' -s "Weekly Email Summary Alert - Service Error Event " $sendTo <<< $( echo -e $message)
}


#properties
emails="oyip@bw-digital.com"
FILE_PATH=/opt/HawaikiTools/StatsPortal12x/test
#emails="itdev@hawaikicable.co.nz,thasa.b@hawaikicable.co.nz,operations-noc@hawaikicable.co.nz"
#FILE_PATH=/opt/HawaikiTools/StatsPortal12x


print_date_time "Summary Report preparing starting..."
printf "\n"


print_date_time "Processing summary report starting..."
hasHeader=true
body="<table border="1" width="100%"><tr><td>Date/Time</td><td>Service ID</td><td>Node</td><td>Physical Error Count - In</td><td>Physical Error Count - Out</td><td>Severely Errored Seconds - In</td><td>Severely Errored Seconds - Out</td><td>Errored Seconds - In</td><td>Errored Seconds - Out</td><td>No. of Occurrences in last week</td></tr>"
while IFS=";"
	read date_time_p customer_p serviceid_p pmsource_p vendor_p nodeid_p pecin_p pecout_p sesin_p sesout_p esin_p esout_p count_p
do
	if [ "$hasHeader" = true ]; then
		hasHeader=false
		continue
	fi
	
	body=$body"<tr><td>"$date_time_p"</td><td>"$serviceid_p"</td><td>"$pmsource_p"</td><td>"$pecin_p"</td><td>"$pecout_p"</td><td>"$sesin_p"</td><td>"$sesout_p"</td><td>"$esin_p"</td><td>"$esout_p"</td><td>"$count_p"</td></tr>"
						
	echo $date_time_p"|"$serviceid_p"|"$pmsource_p"|"$pecin_p"|"$pecout_p"|"$sesin_p "|"$sesout_p"|"$esin_p"|"$esout_p"|"$count_p
		
done < $FILE_PATH/pm_12x_data_processed.csv

body=$body"</table>"
send_email "$emails" "$body"

file_date=$(date '+%Y-%m-%d')
cp $FILE_PATH/pm_12x_data_full.csv $FILE_PATH/archived/pm_12x_data_full.csv
cp $FILE_PATH/pm_12x_data_full.csv $FILE_PATH/archived/pm_12x_data_full_$file_date.csv
echo "date_time;customer;serviceid;pmsource;vendor;nodeid;pecin;pecout;sesin;sesout;esin;esout;count" > $FILE_PATH/pm_12x_data_full.csv
echo "date_time_p;customer_p;serviceid_p;pmsource_p;vendor_p;nodeid_p;pecin_p;pecout_p;sesin_p;sesout_p;esin_p;esout_p;count_p" > $FILE_PATH/pm_12x_data_processed.csv
echo "date_time_p;customer_p;serviceid_p;pmsource_p;vendor_p;nodeid_p;pecin_p;pecout_p;sesin_p;sesout_p;esin_p;esout_p;count_p" > $FILE_PATH/pm_12x_data_no_occurrence.csv
print_date_time "Processed summary report."


print_date_time "Counting data from full list for occurrences..."
/hawaiki_il/data-integration/kitchen.sh -file $FILE_PATH/REST_Call_and_LoadCiena_Data_No_Occurrence.kjb
print_date_time "Counted data from full list for occurrences."


printf "\n"
print_date_time "Summary Report sent finished."
