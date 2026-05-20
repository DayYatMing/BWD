#!/bin/bash

echo "------------------------------------------------------------------------"
echo "This process is to duplicate services from ETTP, PORT and OTUTTP to PTP"

input_file="/opt/HawaikiTools/CienaAlarms/ciena-customerpms-dictionary/CienaCustomerPMs.csv"
output_file="/opt/HawaikiTools/CienaAlarms/ciena-customerpms-dictionary/CienaCustomerPMsPlusPTP.csv"
echo > "$output_file"

string_ETTP="ETTP"
string_PORT="PORT"
string_OTUTTP="OTUTTP"
replacement_string="PTP"

while IFS= read -r line; do

  if [[ "$line" == *"$string_ETTP"* ]]; then
    echo "$line" >> "$output_file"
    line_ETTP="${line//$string_ETTP/$replacement_string}"
    echo "$line_ETTP" >> "$output_file"
  elif [[ "$line" == *"$string_PORT"* ]]; then
    echo "$line" >> "$output_file"
    line_PORT="${line//$string_PORT/$replacement_string}"
    echo "$line_PORT" >> "$output_file"
  elif [[ "$line" == *"$string_OTUTTP"* ]]; then
    echo "$line" >> "$output_file"
    line_OTUTTP="${line//$string_OTUTTP/$replacement_string}"
    echo "$line_OTUTTP" >> "$output_file"
  else
    echo "$line" >> "$output_file"
  fi

done < "$input_file"

echo "------------------------------------------------------------------------"

