echo "Making call to Api"

echo "Making single call to Nrms Api"

apiToken=$(curl -k -X POST \
     -H "Content-Type: application/json" \
     -d '{"username": "api", "password": "4BcdYqj55V!QXGoGfEwn"}' \
https://nrmsportalv2.local.bw-digital.com/api/authenticate | jq -r '.id_token')

echo "Api token is:"

echo $apiToken

cienaPM=$(curl -k -X GET \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer $apiToken" \
     -d '{"username": "api", "password": "4BcdYqj55V!QXGoGfEwn"}' \
https://nrmsportalv2.local.bw-digital.com/api/corelation/minimized) 

echo "Response is - Ciena PM:"

echo $cienaPM

echo "" > /opt/HawaikiTools/CienaAlarms/ciena-customerpms-dictionary/file-new.json
# Writing the recieved json to file
echo $cienaPM > /opt/HawaikiTools/CienaAlarms/ciena-customerpms-dictionary/file-new.json

cienaPM_BH=$(curl -k -X GET \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer $apiToken" \
     -d '{"username": "api", "password": "4BcdYqj55V!QXGoGfEwn"}' \
https://nrmsportalv2.local.bw-digital.com/api/backhauls) 

echo "Response is - Ciena PM BH:"

echo $cienaPM_BH

echo "" > /opt/HawaikiTools/CienaAlarms/ciena-customerpms-dictionary/file-new-bh.json
# Writing the recieved json to file
echo $cienaPM_BH > /opt/HawaikiTools/CienaAlarms/ciena-customerpms-dictionary/file-new-bh.json


exit