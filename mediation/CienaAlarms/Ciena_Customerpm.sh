#!/bin/bash

host="ncs-ausy.local.bw-digital.com"
user="ics-radapi"
pass="fE8aXgHbFBba"
otrs_broker_host="10.10.12.166:3000"
cienaCustomerPm = /opt/HawaikiTools/CienaAlarms/CienaCustomerPMs.csv
customerpm_email = niuday@bw-digital.com


currentTime=$(date +'%Y-%m-%dT%H:%M:00.000')
startTime=`date +'%Y-%m-%dT%H:%M:00.000' --date "$currentTime 2 minutes ago"`
endTime=`date +'%Y-%m-%dT%H:%M:59.000' --date "$currentTime 1 seconds ago"`


printf "\n"



function send_email_customerpm_attachment(){
 sendTo=$1
 attachment=$2
 
 message="Attached is the Ciena Customer PMS CSV file".
 
 echo "Sending mail *****************************"
 
 mail -a 'Content-Type: text/html' -a $attachment -s "Ciena Customer PM CSV" $sendTo <<< $( echo -e $message)
}





#send CienaCustomerPMs file

if [[ -f "$cienaCustomerPm" ]]; then
     send_email_customerpm_attachment "$customerpm_email" "$cienaCustomerPm"     
else
    echo "File not found: $cienaCustomerPm "
fi

