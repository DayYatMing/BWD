#!/bin/sh

echo "==============================Start ... Refresh new PM list and BH services================================"

/opt/HawaikiTools/CienaAlarms/ciena-customerpms-dictionary/Rest-request-Nrms.sh >> /opt/HawaikiTools/CienaAlarms/PMs-retrieval.log
sleep 60

/opt/HawaikiTools/CienaAlarms/ciena-customerpms-dictionary/ciena_pm.sh >> /opt/HawaikiTools/CienaAlarms/PMs-retrieval.log
sleep 60

cp /opt/HawaikiTools/CienaAlarms/ciena-customerpms-dictionary/CienaCustomerPMs_BH.csv /opt/HawaikiTools/CienaAlarms/CienaCustomerPMs_BH.csv

/opt/HawaikiTools/CienaAlarms/ciena-customerpms-dictionary/CienaCustomerPMsPlusPTP.sh >> /opt/HawaikiTools/CienaAlarms/PMs-retrieval.log
sleep 10
cp /opt/HawaikiTools/CienaAlarms/ciena-customerpms-dictionary/CienaCustomerPMsPlusPTP.csv /opt/HawaikiTools/CienaAlarms/CienaCustomerPMs.csv

echo "==============================End ... ==============================="
