#!/bin/bash


function print_date_time() {
	datenow=`date +%d/%m/%Y~%H:%M:%S`
	echo "$datenow - "$1
}


function send_email() {
   sendTo=$1
   message="Hi NOC Team,<br><br>"
   message=$message"This is to notify you that the service with Service ID “"$2" "$3" "$4"” is now experiencing an error event that breached a customer SLA.<br>" 
   message=$message"Please check for associated port or service alarms and for any open tickets.<br>" 
   message=$message"If none are found, please open an internal ticket to review the event and manage to resolution of the issue."
   echo "***** Sending mail ***** "$2" "$3" "$4" "$5""
   mail -a 'Content-Type: text/html' -s "Service Error Event Alert for Service ID "$2" "$5"" $sendTo <<< $( echo -e $message)
}


#properties
#emails="oyip@bw-digital.com"
emails="itdev@hawaikicable.co.nz,thasa.b@hawaikicable.co.nz,operations-noc@hawaikicable.co.nz"
FILE_PATH=/opt/HawaikiTools/StatsPortal12x


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
done < $FILE_PATH/pm_12x_data_diff.csv
echo "date_time;serviceid;pmsource;nodeid;pecin;pecout;sesin;sesout;esin;esout" > $FILE_PATH/pm_12x_data_diff.csv
print_date_time "Processed records."


print_date_time "Collecting data from DB..."
/hawaiki_il/data-integration/kitchen.sh -file /opt/HawaikiTools/StatsPortal12x/REST_Call_and_LoadCiena_Data_to_DB.kjb
print_date_time "Collected data from DB and producing *.csv files for different services."


print_date_time "Checking and sending email..."
hasHeader=true
while IFS=";"
	read date_time serviceid pmsource nodeid pecin pecout sesin sesout esin esout 
do
	if [ "$hasHeader" = true ]; then
		hasHeader=false
		continue
	fi
						
	if [[ "$pecin" != "0" ]]; then
		serviceType="Physical Error Count - In"
		send_email "$emails" "$serviceid" "$pmsource" "$serviceType" "$pecin"
		echo $serviceid";"$pmsource";"$nodeid";"$serviceType";"$pecin 
	fi

	if [[ "$pecout" != "0" ]]; then
		serviceType="Physical Error Count - Out"
		send_email "$emails" "$serviceid" "$pmsource" "$serviceType" "$pecout"
		echo $serviceid";"$pmsource";"$nodeid";"$serviceType";"$pecout 
	fi

	if [[ "$sesin" != "0" ]]; then
		serviceType="Severely Errored Seconds - In"
		send_email "$emails" "$serviceid" "$pmsource" "$serviceType" "$sesin"
		echo $serviceid";"$pmsource";"$nodeid";"$serviceType";"$sesin 
	fi

	if [[ "$sesout" != "0" ]]; then
		serviceType="Severely Errored Seconds - Out"
		send_email "$emails" "$serviceid" "$pmsource" "$serviceType" "$sesout"
		echo $serviceid";"$pmsource";"$nodeid";"$serviceType";"$sesout 
	fi

	if [[ "$esin" != "0" ]]; then
		serviceType="Errored Seconds - In"
		send_email "$emails" "$serviceid" "$pmsource" "$serviceType" "$esin"
		echo $serviceid";"$pmsource";"$nodeid";"$serviceType";"$esin 
	fi 
	
	if [[ "$esout" != "0" ]]; then
		serviceType="Errored Seconds - Out"
		send_email "$emails" "$serviceid" "$pmsource" "$serviceType" "$esout"
		echo $serviceid";"$pmsource";"$nodeid";"$serviceType";"$esout 
	fi
done < $FILE_PATH/pm_12x_data_diff.csv
print_date_time "Sent email completed."


printf "\n"
print_date_time "Stats Portal Service Event finished."
