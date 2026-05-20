#!/bin/sh

prop() {
  grep "^${1}" /hawaiki_il/config.properties | cut -d'=' -f2
}

duration_days=$(prop 'duration_days')

i=0
while [ $i -lt $duration_days ]; do	
	base_date=$(date --date="$((i+1)) days ago" "+%Y-%m-%d")

    j=0
    while [ $j -lt 96 ]; do
        # compute start and end times in 15 min steps
        date_start=$(date -d "$base_date +$((j*15)) minutes" "+%Y-%m-%d %H:%M:%S")
        date_end=$(date -d "$base_date +$(((j+1)*15)) minutes" "+%Y-%m-%d %H:%M:%S")

		echo "date_start=$date_start" > /hawaiki_il/bandwidth/repeat.properties
		echo "date_end=$date_end" >> /hawaiki_il/bandwidth/repeat.properties
		sleep 3
		/hawaiki_il/data-integration/kitchen.sh -file /hawaiki_il/bandwidth/REST_Call_and_LoadCiena_Data_to_DB_all_re-collect.kjb

        j=$((j+1))
    done
	
	i=$((i+1))
done


