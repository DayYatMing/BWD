#!/bin/sh
echo "==========Start process status=========="

/hawaiki_il/data-integration/kitchen.sh -file /hawaiki_il/backhaul/REST_Call_and_LoadBackhaul_Status_Data_to_DB.kjb

echo "==========Process status completed=========="
