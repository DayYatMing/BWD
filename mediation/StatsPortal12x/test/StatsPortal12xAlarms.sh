#!/bin/bash


function print_date_time() {
	datenow=`date +%d/%m/%Y~%H:%M:%S`
	echo "$datenow - "$1
}


function send_email() {
   sendTo=$1
   message="This is to notify you that the service is now experiencing an error event that has breached a customer SLA.<br><br>"
   message=$message"Customer: "$3"<br>"
   message=$message"Service ID: "$4"<br>"
   message=$message"Device Name: "$5"<br>"
   message=$message"Resource: "$6"<br>"
   message=$message"Error Type: "$7"<br>"
   message=$message"Errored Bin: "$2" UTC<br>"
   message=$message"Number of Occurrences in last week: "$9"<br>"
   echo "***** Sending mail ***** "$2" "$3" "$4" "$5" "$6" "$7" "$8" "$9""
   mail -a 'Content-Type: text/html' -s "Service Error Event Alert for Service ID "$4" "$8"" $sendTo <<< $( echo -e $message)
}


#properties
emails="oyip@bw-digital.com"
FILE_PATH=/opt/HawaikiTools/StatsPortal12x/test
#emails="itdev@hawaikicable.co.nz,thasa.b@hawaikicable.co.nz,operations-noc@hawaikicable.co.nz"
#FILE_PATH=/opt/HawaikiTools/StatsPortal12x


print_date_time "Stats Portal Service Event starting..."
printf "\n"


print_date_time "Processing existing records..."
hasHeader=true
while IFS= read -r line; 
do
	if [ "$hasHeader" = true ]; then
		hasHeader=false
		continue
	fi
	
	echo $line
    echo $line >> $FILE_PATH/pm_12x_data_processed.csv
done < $FILE_PATH/pm_12x_data_diff_c_proc.csv
echo "date_time;customer;serviceid;pmsource;vendor;nodeid;pecin;pecout;sesin;sesout;esin;esout;count" > $FILE_PATH/pm_12x_data_diff.csv
echo "date_time;customer;serviceid;pmsource;vendor;nodeid;pecin;pecout;sesin;sesout;esin;esout;count" > $FILE_PATH/pm_12x_data_diff_c_proc.csv
print_date_time "Processed records."


print_date_time "Collecting data from DB..."
/hawaiki_il/data-integration/kitchen.sh -file $FILE_PATH/REST_Call_and_LoadCiena_Data_to_DB.kjb
print_date_time "Collected data from DB and producing *.csv files for different services."


print_date_time "Processing occurrence records..."
hasHeader=true
while IFS=";"
	read date_time_p customer_p serviceid_p pmsource_p vendor_p nodeid_p pecin_p pecout_p sesin_p sesout_p esin_p esout_p count_p
do	   
	if [ "$hasHeader" = true ]; then
		hasHeader=false
		continue
	fi
	
	occurences[${#occurences[*]}]=$customer_p"|"$serviceid_p"|"$pmsource_p"|"$vendor_p"|"$nodeid_p"|"$pecin_p"|"$pecout_p"|"$sesin_p"|"$sesout_p"|"$esin_p"|"$esout_p
	occurence_counts[${#occurence_counts[*]}]=$count_p
done < $FILE_PATH/pm_12x_data_no_occurrence.csv
print_date_time "Processed occurrence records."


print_date_time "Checking and sending email..."
hasHeader=true
while IFS=";"
	read date_time customer serviceid pmsource vendor nodeid pecin pecout sesin sesout esin esout count 
do
	if [ "$hasHeader" = true ]; then
		hasHeader=false
		continue
	fi
	
	record=$customer"|"$serviceid"|"$pmsource"|"$vendor"|"$nodeid"|"$pecin"|"$pecout"|"$sesin"|"$sesout"|"$esin"|"$esout
	counter=0;
	
	for i in ${!occurences[@]}
	do
		if [[ ${occurences[$i]} == ${record} ]]; then
			counter=${occurence_counts[$i]}
		fi
	done	
						
	if [[ "$pecin" != "0" ]]; then
		serviceType="Physical Error Count - In"
		deviceName=${pmsource:0:4}"-"$vendor
		resource=${pmsource:5}
		send_email "$emails" "$date_time" "$customer" "$serviceid" "$deviceName" "$resource" "$serviceType" "$pecin" "$counter"
		echo $customer"|"$serviceid"|"$deviceName"|"$resource"|"$nodeid"|"$serviceType"|"$pecin"|"$counter  
	fi

	if [[ "$pecout" != "0" ]]; then
		serviceType="Physical Error Count - Out"
		deviceName=${pmsource:0:4}"-"$vendor
		resource=${pmsource:5}
		send_email "$emails" "$date_time" "$customer" "$serviceid" "$deviceName" "$resource" "$serviceType" "$pecout" "$counter"
		echo $customer"|"$serviceid"|"$deviceName"|"$resource"|"$nodeid"|"$serviceType"|"$pecout"|"$counter 
	fi

	if [[ "$sesin" != "0" ]]; then
		serviceType="Severely Errored Seconds - In"
		deviceName=${pmsource:0:4}"-"$vendor
		resource=${pmsource:5}
		send_email "$emails" "$date_time" "$customer" "$serviceid" "$deviceName" "$resource" "$serviceType" "$sesin" "$counter"
		echo $customer"|"$serviceid"|"$deviceName"|"$resource"|"$nodeid"|"$serviceType"|"$sesin"|"$counter 
	fi

	if [[ "$sesout" != "0" ]]; then
		serviceType="Severely Errored Seconds - Out"
		deviceName=${pmsource:0:4}"-"$vendor
		resource=${pmsource:5}
		send_email "$emails" "$date_time" "$customer" "$serviceid" "$deviceName" "$resource" "$serviceType" "$sesout" "$counter"
		echo $customer"|"$serviceid"|"$deviceName"|"$resource"|"$nodeid"|"$serviceType"|"$sesout"|"$counter 
	fi

	if [[ "$esin" != "0" ]]; then
		serviceType="Errored Seconds - In"
		deviceName=${pmsource:0:4}"-"$vendor
		resource=${pmsource:5}
		send_email "$emails" "$date_time" "$customer" "$serviceid" "$deviceName" "$resource" "$serviceType" "$esin" "$counter"
		echo $customer"|"$serviceid"|"$deviceName"|"$resource"|"$nodeid"|"$serviceType"|"$esin"|"$counter 
	fi 
	
	if [[ "$esout" != "0" ]]; then
		serviceType="Errored Seconds - Out"
		deviceName=${pmsource:0:4}"-"$vendor
		resource=${pmsource:5}
		send_email "$emails" "$date_time" "$customer" "$serviceid" "$deviceName" "$resource" "$serviceType" "$esout" "$counter"
		echo $customer"|"$serviceid"|"$deviceName"|"$resource"|"$nodeid"|"$serviceType"|"$esout"|"$counter 
	fi
	
	echo $date_time";"$customer";"$serviceid";"$pmsource";"$vendor";"$nodeid";"$pecin";"$pecout";"$sesin";"$sesout";"$esin";"$esout";"$counter >> $FILE_PATH/pm_12x_data_diff_c_proc.csv
	
done < $FILE_PATH/pm_12x_data_diff.csv
print_date_time "Sent email completed."


printf "\n"
print_date_time "Stats Portal Service Event finished."
