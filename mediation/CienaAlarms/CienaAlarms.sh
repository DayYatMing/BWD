#!/bin/bash


mobileNumbers="0226790045"
emails="itdev@hawaikicable.co.nz,thasa.b@hawaikicable.co.nz,operations-noc@hawaikicable.co.nz"
#emails="magesh.cannane@hawaiki.co.nz"

FILE_PATH=/opt/HawaikiTools/CienaAlarms
host="ncs-ausy.local.bw-digital.com"
user="ics-radapi"
pass="fE8aXgHbFBba"
otrs_broker_host="10.10.12.166:3000"


currentTime=$(date +'%Y-%m-%dT%H:%M:00.000')
startTime=`date +'%Y-%m-%dT%H:%M:00.000' --date "$currentTime 2 minutes ago"`
endTime=`date +'%Y-%m-%dT%H:%M:59.000' --date "$currentTime 1 seconds ago"`


printf "\n"


function print_date_time() {
	datenow=`date +%d/%m/%Y~%H:%M:%S`
	echo "$datenow - "$1
}


function validate_http_status() {
	response=$1
	
	if [ $response != 200 ]; then
		echo "Error HTTP Status code: "$response
		exit
	fi	
}


function validate_response_body() {
	response=$1
	
	if [ ${#response} = 0 ]; then
		echo "No data received from server"
		exit
	fi		
}


function send_email() {
   sendTo=$1
  
   message="<b>New Ciena "${11}" alarm received</b> <br><br>"
   message=$message"AlarmID: "$2"<br>"
   message=$message"OTRS(Pre prod)-TicketNumber: "${13}"<br>"
   message=$message"Customer: "${12}"<br>"
   message=$message"ServiceID: "$3"<br>"
   message=$message"ConditionSeverity: "$4"<br>"
   message=$message"State: "${11}"<br>"
   message=$message"ServiceAffecting: "$5"<br>"
   message=$message"DeviceName: "$6"<br>"
   message=$message"Resource: "$7"<br>"
   message=$message"NodeType: "$8"<br>"
   message=$message"AdditionalText: "$9"<br>"
   message=$message"FirstRaiseTime: "${10}"<br>"
   message=$message"LastRaiseTime: "${14}"<br>"
   message=$message"NumberOfOccurrences: "${15}


   echo "Sending mail *****************************"

   mail -a 'Content-Type: text/html' -s "New Ciena "${11}" alarm - ${12} - $3" $sendTo <<< $( echo -e $message)
}


function send_email_cleared() {
   sendTo=$1
  
   message="<b>New Ciena "${11}" alarm received</b> <br><br>"
   message=$message"AlarmID: "$2"<br>"
   message=$message"OTRS(Pre prod)-TicketNumber: "${13}"<br>"
   message=$message"Customer: "${12}"<br>"
   message=$message"ServiceID: "$3"<br>"
   message=$message"ConditionSeverity: "$4"<br>"
   message=$message"State: "${11}"<br>"
   message=$message"ServiceAffecting: "$5"<br>"
   message=$message"DeviceName: "$6"<br>"
   message=$message"Resource: "$7"<br>"
   message=$message"NodeType: "$8"<br>"
   message=$message"AdditionalText: "$9"<br>"
   message=$message"FirstRaiseTime: "${10}"<br>"
   message=$message"LastRaiseTime: "${14}"<br>"
   message=$message"ClearTime: "${16}"<br>"
   message=$message"NumberOfOccurrences: "${15}
   
   echo "Sending mail *****************************"

   mail -a 'Content-Type: text/html' -s "New Ciena "${11}" alarm - ${12} - $3" $sendTo <<< $( echo -e $message)
}


function send_email_BH() {
   sendTo=$1
  
   message="<b>New Ciena "${11}" alarm received</b> <br><br>"
   message=$message"AlarmID: "$2"<br>"
   message=$message"OTRS(Pre prod)-TicketNumber: "${13}"<br>"
   message=$message"Customer: "${12}"<br>"
   message=$message"ServiceID: "$3"<br>"
   message=$message"ConditionSeverity: "$4"<br>"
   message=$message"State: "${11}"<br>"
   message=$message"ServiceAffecting: "$5"<br>"
   message=$message"DeviceName: "$6"<br>"
   message=$message"Resource: "$7"<br>"
   message=$message"NodeType: "$8"<br>"
   message=$message"AdditionalText: "$9"<br>"
   message=$message"FirstRaiseTime: "${10}"<br>"
   message=$message"LastRaiseTime: "${14}"<br>"
   message=$message"NumberOfOccurrences: "${15}"<br>"
   message=$message"Backhaul Vendor: "${16}"<br>"
   message=$message"Offnet Vendor: "${17}"<br>"
   message=$message"Offnet ID: "${18}"<br>"
   message=$message"Offnet Protected: "${19}"<br>"
   message=$message"Offnet A-End Details: "${20}"<br>"
   message=$message"Offnet B-End Details: "${21}

   echo "Sending mail *****************************"

   mail -a 'Content-Type: text/html' -s "New Ciena "${11}" alarm - ${12} - $3" $sendTo <<< $( echo -e $message)
}


function send_email_cleared_BH() {
   sendTo=$1
  
   message="<b>New Ciena "${11}" alarm received</b> <br><br>"
   message=$message"AlarmID: "$2"<br>"
   message=$message"OTRS(Pre prod)-TicketNumber: "${13}"<br>"
   message=$message"Customer: "${12}"<br>"
   message=$message"ServiceID: "$3"<br>"
   message=$message"ConditionSeverity: "$4"<br>"
   message=$message"State: "${11}"<br>"
   message=$message"ServiceAffecting: "$5"<br>"
   message=$message"DeviceName: "$6"<br>"
   message=$message"Resource: "$7"<br>"
   message=$message"NodeType: "$8"<br>"
   message=$message"AdditionalText: "$9"<br>"
   message=$message"FirstRaiseTime: "${10}"<br>"
   message=$message"LastRaiseTime: "${14}"<br>"
   message=$message"ClearTime: "${16}"<br>"
   message=$message"NumberOfOccurrences: "${15}"<br>"
   message=$message"Backhaul Vendor: "${17}"<br>"
   message=$message"Offnet Vendor: "${18}"<br>"
   message=$message"Offnet ID: "${19}"<br>"
   message=$message"Offnet Protected: "${20}"<br>"
   message=$message"Offnet A-End Details: "${21}"<br>"
   message=$message"Offnet B-End Details: "${22}
   
   echo "Sending mail *****************************"

   mail -a 'Content-Type: text/html' -s "New Ciena "${11}" alarm - ${12} - $3" $sendTo <<< $( echo -e $message)
}


function send_sms() {
  sendTo=$1
  
  message="Ciena%20Alarm%20-%20"
  message=$message"AlarmID:%20"$2",%20"
  message=$message"OTRS Ticket Number:%20"${12}",%20"
  message=$message"Customer:%20"${11}",%20"
  message=$message"ServiceID:%20"$3",%20"
  message=$message"ConditionSeverity:%20"$4",%20"
  message=$message"ServiceAffecting:%20"$5",%20"
  message=$message"DeviceName:%20"$6",%20"
  message=$message"Resource:%20"$7",%20"
  message=$message"NodeType:%20"$8",%20"
  message=$message"AdditionalText:%20"$9",%20"
  message=$message"FirstRaiseTime:%20"${10}"."
  
  curl "https://api-mapper.clicksend.com/http/v2/send.php?method=http&username=syshawaiki&key=378F0942-77DF-1662-7B14-C1C34220A775&to="$sendTo"&message="$message
}


function generate_post_data_for_otrs() {
   cat <<EOF
{
	   "Ticket":{
		   "Title":"Ciena Alarm - ${10} - $2",
		   "Queue":"NOC",
		   "State":"open",
		   "Type": "Incident",
		   "PriorityID":"3",
		   "CustomerUser":"sreeraj.sreenivasan@hawaiki.co.nz",
		   "CustomerID":"mario3"
					
	   },
		"Article":{
			   "Subject":"Ticket from Ciena Alarm - Test",
			   "Body":"<span style='font-family:Geneva,Helvetica,Arial,sans-serif; font-size: 14px;'>AlarmID: $1 <br>Customer: ${10} <br>ServiceID: $2 <br>ConditionSeverity: $3 <br>ServiceAffecting: $4 <br>DeviceName: $5 <br>Resource: $6 <br>NodeType: $7 <br>AdditionalText: $8 <br>FirstRaiseTime: $9 <br>LastRaiseTime: ${11} <br>NumberOfOccurrences: ${12}</span>",
			   "ContentType":"text/html; charset=utf8"
		}
}
EOF
}


print_date_time "Ciena Alarms polling started."

auth_token=`curl -X POST "http://$host/tron/api/v1/oauth2/tokens" -H  "accept: application/json"  -H  "Content-Type: application/x-www-form-urlencoded" -d "username=$user&tenant=master&password=$pass&grant_type=password" 2>/dev/null|jq ".|.accessToken"|sed "s/\"//g"`

validate_response_body $auth_token

Ciena_Alarms=`curl -X GET "http://$host/nsa/api/v2_0/alarms/filter/filteredAlarms?filter%5Bstate%5D%5B%5D=ACTIVE&filter%5Bseverity%5D%5B%5D=CRITICAL&filter%5Bseverity%5D%5B%5D=MAJOR&offset=0&pageSize=500" -H  "accept: application/json" -H  "Authorization: Bearer $auth_token" 2>/dev/null`

validate_response_body $Ciena_Alarms

Ciena_Alarms_Cleared=`curl -X GET "http://$host/nsa/api/v2_0/alarms/filter/filteredAlarms?filter%5Bstate%5D%5B%5D=CLEARED&filter%5Bseverity%5D%5B%5D=CRITICAL&filter%5Bseverity%5D%5B%5D=MAJOR&filter%5BlastRaisedTimeFrom%5D=$startTime&filter%5BlastRaisedTimeTo%5D=$endTime&offset=0&pageSize=500" -H "accept: application/json" -H "Authorization: Bearer $auth_token" 2>/dev/null`


if [ ${#Ciena_Alarms} = 0 ]; then
	exit
fi


echo $Ciena_Alarms > $FILE_PATH/Ciena_Alarms.txt

echo $Ciena_Alarms_Cleared > $FILE_PATH/Ciena_Alarms_Cleared.txt


echo "ID;AlarmID;NodeID;RaAlarmID;NodeType;State;Resource;ResourceID;NativeConditionType;ConditionSeverity;ServiceAffecting;ManualClearable;AdditionalText;FirstRaiseTime;LastRaiseTime;NumberOfOccurrences;AcknowledgeState;DeviceID;DeviceName;IpAddress;MacAddress" > $FILE_PATH/Ciena_Alarms.csv
cat $FILE_PATH/Ciena_Alarms.txt|jq '.data[]|.attributes["id"]
			+";"+.attributes["alarm-id"]
			+";"+.attributes["node-id"]
			+";"+.attributes["ra-alarm-id"]
			+";"+.attributes["node-type"]
			+";"+.attributes["state"]
			+";"+.attributes["resource"]
			+";"+.attributes["resource-id"]
			+";"+.attributes["native-condition-type"]
			+";"+.attributes["condition-severity"]
			+";"+.attributes["service-affecting"]
			+";"+(.attributes["manual-clearable"]|tostring)
			+";"+.attributes["additional-text"]
			+";"+.attributes["first-raise-time"]
			+";"+.attributes["last-raise-time"]
			+";"+(.attributes["number-of-occurrences"]|tostring)
			+";"+.attributes["acknowledge-state"]
			+";"+.attributes["device-id"]
			+";"+.attributes["device-name"]
			+";"+.attributes["ip-address"]
			+";"+.attributes["mac-address"]' |sed "s/\"//g" >> $FILE_PATH/Ciena_Alarms.csv
			
			
echo "ID;AlarmID;NodeID;RaAlarmID;NodeType;State;Resource;ResourceID;NativeConditionType;ConditionSeverity;ServiceAffecting;ManualClearable;AdditionalText;FirstRaiseTime;LastRaiseTime;ClearTime;NumberOfOccurrences;AcknowledgeState;DeviceID;DeviceName;IpAddress;MacAddress" > $FILE_PATH/Ciena_Alarms_Cleared.csv
cat $FILE_PATH/Ciena_Alarms_Cleared.txt|jq '.data[]|.attributes["id"]
			+";"+.attributes["alarm-id"]
			+";"+.attributes["node-id"]
			+";"+.attributes["ra-alarm-id"]
			+";"+.attributes["node-type"]
			+";"+.attributes["state"]
			+";"+.attributes["resource"]
			+";"+.attributes["resource-id"]
			+";"+.attributes["native-condition-type"]
			+";"+.attributes["condition-severity"]
			+";"+.attributes["service-affecting"]
			+";"+(.attributes["manual-clearable"]|tostring)
			+";"+.attributes["additional-text"]
			+";"+.attributes["first-raise-time"]
			+";"+.attributes["last-raise-time"]
			+";"+.attributes["clear-time"]
			+";"+(.attributes["number-of-occurrences"]|tostring)
			+";"+.attributes["acknowledge-state"]
			+";"+.attributes["device-id"]
			+";"+.attributes["device-name"]
			+";"+.attributes["ip-address"]
			+";"+.attributes["mac-address"]' |sed "s/\"//g" >> $FILE_PATH/Ciena_Alarms_Cleared.csv			
			
curl -X DELETE "http://$host/tron/api/v1/oauth2/tokens/$auth_token" -H  "accept: application/json" -H  "Authorization: Bearer $auth_token" 


#reading all the previous alarms for duplicate check
while IFS= read -r line
do		
	checksum[${#checksum[*]}]=$line
done < $FILE_PATH/CienaAlarmsChecksum.csv

#clearing the alarm details from checksum file
> $FILE_PATH/CienaAlarmsChecksum.csv


# getting all the PM's and service ID's
while IFS=";" 
	read customer sid pm nodeid
do	    
	Customers[${#Customers[*]}]=$customer
	ServiceIDs[${#ServiceIDs[*]}]=$sid
	pms[${#pms[*]}]=$pm	
	NodeIDs[${#NodeIDs[*]}]=$nodeid
done < $FILE_PATH/CienaCustomerPMs.csv


# getting all the PM's and service ID's of Backhaul
while IFS=";" 
	read customer sid offnetid offnetvendor offnetprotected offnetaenddetails offnetbenddetails customer2 pm nodeid
do	    
	CustomersBH[${#CustomersBH[*]}]=$customer
	ServiceIDsBH[${#ServiceIDsBH[*]}]=$sid
	OffnetIDsBH[${#OffnetIDsBH[*]}]=$offnetid
	OffnetVendorsBH[${#OffnetVendorsBH[*]}]=$offnetvendor
	OffnetProtectedsBH[${#OffnetProtectedsBH[*]}]=$offnetprotected
	OffnetAEndDetailsBH[${#OffnetAEndDetailsBH[*]}]=$offnetaenddetails
	OffnetBEndDetailsBH[${#OffnetBEndDetailsBH[*]}]=$offnetbenddetails
	Customers2BH[${#Customers2BH[*]}]=$customer2
	pmsBH[${#pmsBH[*]}]=$pm	
	NodeIDsBH[${#NodeIDsBH[*]}]=$nodeid
done < $FILE_PATH/CienaCustomerPMs_BH.csv


#Active alarms
isFirst=true
while IFS=";"
	read ID AlarmID NodeID RaAlarmID NodeType State Resource ResourceID NativeConditionType ConditionSeverity ServiceAffecting ManualClearable AdditionalText FirstRaiseTime LastRaiseTime NumberOfOccurrences AcknowledgeState DeviceID DeviceName IpAddress MacAddress;
do
	# skipping title row from the csv file
	if [ "$isFirst" = true ]; then
		isFirst=false
		continue
	fi
	
	#writing alarm details to checksum file
	echo $ID"~"$FirstRaiseTime >> $FILE_PATH/CienaAlarmsChecksum.csv
	
	#checking for duplicate alarm
	duplicateAlarm=false
	for i in ${checksum[@]}
	do			
		if [ $ID"~"$FirstRaiseTime = $i ]; then
			duplicateAlarm=true
		fi
	done
	
	#skipping if existed, otherwise proceeding
	if [ $duplicateAlarm = 'true' ]; then
		continue
	else 
		echo "New active alarm received: "$ID
	fi
	
	#check severity level, only CRITICAL will be proceeded
	if [ $ConditionSeverity = 'UNKNOWN' ]; then
		SEV=1
	elif [ $ConditionSeverity = 'NOTDEFINED' ]; then
		SEV=1
	elif [ $ConditionSeverity = 'CLEARED' ]; then
		SEV=2
	elif [ $ConditionSeverity = 'NORMAL' ]; then
		SEV=3
	elif [ $ConditionSeverity = 'WARNING' ]; then
		SEV=4
	elif [ $ConditionSeverity = 'MINOR' ]; then
		SEV=5
	elif [ $ConditionSeverity = 'MAJOR' ]; then
		SEV=6
	elif [ $ConditionSeverity = 'CRITICAL' ]; then
		SEV=7	
	else 
		SEV=1
	fi
	
	ServiceID="unassigned"
	Customer="na"
	nodeID="67"
	cienaUEI="uei.opennms.org/traps/Hawaiki-Ciena/cienaAlarmNotification"
	for i in ${!ServiceIDs[@]}
	do 	    
		if [[ ${Resource,,} == ${pms[$i],,} ]]; then 
		dID=${DeviceID:0:15}
		nID=${NodeIDs[$i]:0:15}
		if [[ $dID == $nID ]]; then
			ServiceID=${ServiceIDs[$i]} 
			Customer=${Customers[$i]}
			nodeID="140"
			cienaUEI="uei.opennms.org/traps/Hawaiki-Ciena/cienaAlarmNotificationWithServiceID"
			
			if [ $SEV == 7 ]; then 		
				#skipping all the alarms older than 1 hour
				startTime=$(date +'%Y-%m-%dT%H:%M:00.000Z' --date "$currentTime 1 hour ago")				
				if [ ${LastRaiseTime} > ${startTime} ]; then 
					FoundBH="false"
					OffnetIDBH="na"
					OffnetVendorBH="na"
					OffnetProtectedBH="na"
					OffnetAEndDetailBH="na"
					OffnetBEndDetailBH="na"
					
					for j in ${!ServiceIDsBH[@]}
					do					
						if [[ ${Resource,,} == ${pmsBH[$j],,} ]]; then 
						dID=${DeviceID:0:15}
						nID=${NodeIDsBH[$j]:0:15}
						if [[ $dID == $nID ]]; then 
							FoundBH="true"
							ServiceID=${ServiceIDsBH[$j]}
							Customer=${CustomersBH[$j]}
							OffnetIDBH=${OffnetIDsBH[$j]}
							OffnetVendorBH=${OffnetVendorsBH[$j]}
							OffnetProtectedBH=${OffnetProtectedsBH[$j]}
							OffnetAEndDetailBH=${OffnetAEndDetailsBH[$j]}
							OffnetBEndDetailBH=${OffnetBEndDetailsBH[$j]}
						fi
						fi
					done
					
					if [ $FoundBH == 'true' ]; then 
						body=$(generate_post_data_for_otrs "$AlarmID" "$ServiceID" "$ConditionSeverity" "$ServiceAffecting" "$DeviceName" "$Resource" "$NodeType" "$AdditionalText" "$FirstRaiseTime" "$Customer" "$LastRaiseTime" "$NumberOfOccurrences" "$OffnetVendorBH" "$OffnetVendorBH" "$OffnetIDBH" "$OffnetProtectedBH" "$OffnetAEndDetailBH" "$OffnetBEndDetailBH")
						send_email_BH "$emails" "$AlarmID" "$ServiceID" "$ConditionSeverity" "$ServiceAffecting" "$DeviceName" "$Resource" "$NodeType" "$AdditionalText" "$FirstRaiseTime" "$State" "$Customer" "$ticketNumber" "$LastRaiseTime" "$NumberOfOccurrences" "$OffnetVendorBH" "$OffnetVendorBH" "$OffnetIDBH" "$OffnetProtectedBH" "$OffnetAEndDetailBH" "$OffnetBEndDetailBH"
					else
						body=$(generate_post_data_for_otrs "$AlarmID" "$ServiceID" "$ConditionSeverity" "$ServiceAffecting" "$DeviceName" "$Resource" "$NodeType" "$AdditionalText" "$FirstRaiseTime" "$Customer" "$LastRaiseTime" "$NumberOfOccurrences")
						send_email "$emails" "$AlarmID" "$ServiceID" "$ConditionSeverity" "$ServiceAffecting" "$DeviceName" "$Resource" "$NodeType" "$AdditionalText" "$FirstRaiseTime" "$State" "$Customer" "$ticketNumber" "$LastRaiseTime" "$NumberOfOccurrences"
						#send_sms "$mobileNumbers" "$AlarmID" "$ServiceID" "$ConditionSeverity" "$ServiceAffecting" "$DeviceName" "$Resource" "$NodeType" "$AdditionalText" "$FirstRaiseTime" "$Customer" "$ticketNumber"		
					fi
					
					result=`curl -H "Content-Type: application/json" --data "$body" -X POST http://$otrs_broker_host/otrs/Ticket`			
					#echo akki body: $body result: $result 	
					ticketNumber=$(echo $result | jq -r '.TicketNumber')
				fi
			fi
			
			echo "$Customer" - "$ServiceID" - "$ticketNumber" - "$AlarmID" - "$Resource" - "$DeviceID"
		fi
		fi
	done
		
	/usr/share/opennms/bin/send-event.pl --nodeid $nodeID --interface 10.7.1.41 --service Ciena --severity $SEV $cienaUEI -p " $ServiceID" -p " $MacAddress" -p " $IpAddress" -p " $DeviceName" -p " $DeviceID" -p " $AcknowledgeState" -p " $NumberOfOccurrences" -p " $LastRaiseTime" -p " $FirstRaiseTime" -p " $AdditionalText" -p " $ManualClearable" -p " $ServiceAffecting" -p " $ConditionSeverity" -p " $NativeConditionType" -p " $ResourceID" -p " $Resource" -p " $State" -p " $NodeType" -p " $RaAlarmID" -p " $NodeID" -p " $AlarmID" -p " $ID"
done < $FILE_PATH/Ciena_Alarms.csv


#reading all the previous cleared alarms for duplicate check
while IFS= read -r line
do		
	checksumCleared[${#checksumCleared[*]}]=$line
done < $FILE_PATH/CienaAlarmsClearedChecksum.csv

#clearing the alarm details from checksum file
> $FILE_PATH/CienaAlarmsClearedChecksum.csv


#Cleared alarms
isFirst=true
while IFS=";"
	read ID AlarmID NodeID RaAlarmID NodeType State Resource ResourceID NativeConditionType ConditionSeverity ServiceAffecting ManualClearable AdditionalText FirstRaiseTime LastRaiseTime ClearTime NumberOfOccurrences AcknowledgeState DeviceID DeviceName IpAddress MacAddress;
do
	# skipping title row from the csv file
	if [ "$isFirst" = true ]; then
		isFirst=false
		continue
	fi
	
	#writing alarm details to checksum file for clearing
	echo $ID"~"$FirstRaiseTime >> $FILE_PATH/CienaAlarmsClearedChecksum.csv
		
	#checking for duplicate against active alarms
	duplicateAlarm=false
	for i in ${checksum[@]}
	do			
		if [ $ID"~"$FirstRaiseTime = $i ]; then
			duplicateAlarm=true
		fi
	done
	
	#skipping if existed, otherwise proceeding
	if [ $duplicateAlarm = 'true' ]; then
		continue
	fi
	
	#checking for duplicate against cleared alarms
	duplicateAlarm=false
	for i in ${checksumCleared[@]}
	do			
		if [ $ID"~"$FirstRaiseTime = $i ]; then
			duplicateAlarm=true
		fi
	done
	
	#skipping if existed, otherwise proceeding
	if [ $duplicateAlarm = 'true' ]; then
		continue
	else 
		echo "New cleared alarm received: "$ID
	fi
	
	#check severity level, only CRITICAL will be proceeded
	if [ $ConditionSeverity = 'UNKNOWN' ]; then
		SEV=1
	elif [ $ConditionSeverity = 'NOTDEFINED' ]; then
		SEV=1
	elif [ $ConditionSeverity = 'CLEARED' ]; then
		SEV=2
	elif [ $ConditionSeverity = 'NORMAL' ]; then
		SEV=3
	elif [ $ConditionSeverity = 'WARNING' ]; then
		SEV=4
	elif [ $ConditionSeverity = 'MINOR' ]; then
		SEV=5
	elif [ $ConditionSeverity = 'MAJOR' ]; then
		SEV=6
	elif [ $ConditionSeverity = 'CRITICAL' ]; then
		SEV=7	
	else 
		SEV=1
	fi
	
	ServiceID="unassigned"
	nodeID="67"
	cienaUEI="uei.opennms.org/traps/Hawaiki-Ciena/cienaAlarmNotification"
	for i in ${!ServiceIDs[@]}
	do 	    
		if [[ ${Resource,,} == ${pms[$i],,} ]]; then 
		dID=${DeviceID:0:15}
		nID=${NodeIDs[$i]:0:15}
		if [[ $dID == $nID ]]; then
			ServiceID=${ServiceIDs[$i]} 
			Customer=${Customers[$i]}
			nodeID="140"
			cienaUEI="uei.opennms.org/traps/Hawaiki-Ciena/cienaAlarmNotificationWithServiceID"
			
			if [ $SEV == 7 ]; then 
				#skipping all the alarms older than 1 hour
				startTime=$(date +'%Y-%m-%dT%H:%M:00.000Z' --date "$currentTime 1 hour ago")				
				if [[ "$LastRaiseTime" > "$startTime" ]]; then 	
					FoundBH="false"
					OffnetIDBH="na"
					OffnetVendorBH="na"
					OffnetProtectedBH="na"
					OffnetAEndDetailBH="na"
					OffnetBEndDetailBH="na"
					
					for j in ${!ServiceIDsBH[@]}
					do					
						if [[ ${Resource,,} == ${pmsBH[$j],,} ]]; then 
						dID=${DeviceID:0:15}
						nID=${NodeIDsBH[$j]:0:15}
						if [[ $dID == $nID ]]; then
							FoundBH="true"
							ServiceID=${ServiceIDsBH[$j]}
							Customer=${CustomersBH[$j]}
							OffnetIDBH=${OffnetIDsBH[$j]}
							OffnetVendorBH=${OffnetVendorsBH[$j]}
							OffnetProtectedBH=${OffnetProtectedsBH[$j]}
							OffnetAEndDetailBH=${OffnetAEndDetailsBH[$j]}
							OffnetBEndDetailBH=${OffnetBEndDetailsBH[$j]}
						fi
						fi
					done
					
					if [ $FoundBH == 'true' ]; then 		
						send_email_cleared_BH "$emails" "$AlarmID" "$ServiceID" "$ConditionSeverity" "$ServiceAffecting" "$DeviceName" "$Resource" "$NodeType" "$AdditionalText" "$FirstRaiseTime" "$State" "$Customer" "$ticketNumber" "$LastRaiseTime" "$NumberOfOccurrences" "$ClearTime" "$OffnetVendorBH" "$OffnetVendorBH" "$OffnetIDBH" "$OffnetProtectedBH" "$OffnetAEndDetailBH" "$OffnetBEndDetailBH"
					else			
						send_email_cleared "$emails" "$AlarmID" "$ServiceID" "$ConditionSeverity" "$ServiceAffecting" "$DeviceName" "$Resource" "$NodeType" "$AdditionalText" "$FirstRaiseTime" "$State" "$Customer" "$ticketNumber" "$LastRaiseTime" "$NumberOfOccurrences" "$ClearTime"
						#send_sms "$mobileNumbers" "$AlarmID" "$ServiceID" "$ConditionSeverity" "$ServiceAffecting" "$DeviceName" "$Resource" "$NodeType" "$AdditionalText" "$FirstRaiseTime" "$Customer" "$ticketNumber"		
					fi
					
					ticketNumber="No Ticket"				
				fi
			fi
			
			echo "$Customer" - "$ServiceID" - "$ticketNumber" - "$AlarmID" - "$Resource" - "$DeviceID"
		fi
		fi	
	done
	
	/usr/share/opennms/bin/send-event.pl --nodeid $nodeID --interface 10.7.1.41 --service Ciena --severity $SEV $cienaUEI -p " $ServiceID" -p " $MacAddress" -p " $IpAddress" -p " $DeviceName" -p " $DeviceID" -p " $AcknowledgeState" -p " $NumberOfOccurrences" -p " $LastRaiseTime" -p " $FirstRaiseTime" -p " $AdditionalText" -p " $ManualClearable" -p " $ServiceAffecting" -p " $ConditionSeverity" -p " $NativeConditionType" -p " $ResourceID" -p " $Resource" -p " $State" -p " $NodeType" -p " $RaAlarmID" -p " $NodeID" -p " $AlarmID" -p " $ID"
done < $FILE_PATH/Ciena_Alarms_Cleared.csv


print_date_time "Ciena Alarms polling finished."

: << comment
comment
