#!/bin/sh

prop() {
  grep "^${1}" config.properties | cut -d'=' -f2
}

duration_days=$(prop 'duration_days')

if [ "$duration_days" -gt 2 ]; then
  repeat=$(( (duration_days + 2 - 1) / 2 ))
  i=0
  while [ $i -lt $repeat ]; do
    date_start=$(date --date="$((repeat * 2 - i * 2)) days ago" "+%Y-%m-%d")
    date_end=$(date --date="$((repeat * 2 - i * 2 - 2)) days ago" "+%Y-%m-%d")
    echo "date_start=$date_start" > repeat.properties
    echo "date_end=$date_end" >> repeat.properties
    sleep 5
    echo "1 - /hawaiki_il/data-integration/kitchen.sh -file /hawaiki_il/bandwidth/REST_Call_and_LoadCiena_Data_to_DB.kjb"
    if [ $((i + 1)) -ne $repeat ]; then
      sleep 60
    fi
    i=$((i + 1))
  done
else
  echo "2 - /hawaiki_il/data-integration/kitchen.sh -file /hawaiki_il/bandwidth/REST_Call_and_LoadCiena_Data_to_DB.kjb"
fi

