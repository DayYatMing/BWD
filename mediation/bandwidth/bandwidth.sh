#!/bin/sh

prop() {
  grep "^${1}" /hawaiki_il/config.properties | cut -d'=' -f2
}

duration_days=$(prop 'duration_days')

#param 0 to run normal job, otherwise to run 8 hours job.
if [ -z "$1" ] || [ "$1" -eq 0 ]; then
  date_start=$(date "+%Y-%m-%d")
  date_end=$(date "+%Y-%m-%d")
  echo "date_start=$date_start" > /hawaiki_il/bandwidth/repeat.properties
  echo "date_end=$date_end" >> /hawaiki_il/bandwidth/repeat.properties
  sleep 3
  /hawaiki_il/data-integration/kitchen.sh -file /hawaiki_il/bandwidth/REST_Call_and_LoadCiena_Data_to_DB.kjb
elif [ "$duration_days" -gt 2 ]; then
  repeat=$(( (duration_days + 2 - 1) / 2 ))
  i=0
  while [ $i -lt $repeat ]; do
    date_start=$(date --date="$((repeat * 2 - i * 2)) days ago" "+%Y-%m-%d")
    date_end=$(date --date="$((repeat * 2 - i * 2 - 2)) days ago" "+%Y-%m-%d")
    echo "date_start=$date_start" > /hawaiki_il/bandwidth/repeat.properties
    echo "date_end=$date_end" >> /hawaiki_il/bandwidth/repeat.properties
    sleep 3
    /hawaiki_il/data-integration/kitchen.sh -file /hawaiki_il/bandwidth/REST_Call_and_LoadCiena_Data_to_DB.kjb
    i=$((i + 1))
  done
else
  date_start=$(date --date="$duration_days days ago" "+%Y-%m-%d")
  date_end=$(date "+%Y-%m-%d")
  echo "date_start=$date_start" > /hawaiki_il/bandwidth/repeat.properties
  echo "date_end=$date_end" >> /hawaiki_il/bandwidth/repeat.properties
  sleep 3
  /hawaiki_il/data-integration/kitchen.sh -file /hawaiki_il/bandwidth/REST_Call_and_LoadCiena_Data_to_DB.kjb
fi

