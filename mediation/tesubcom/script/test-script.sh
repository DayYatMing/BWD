#!/bin/bash

#su - mediation -c "scp -r -P 404 /hawaiki_il/test/test-scp/och mediation@10.7.2.190:/mediation/tesubcom"

#su - mediation -c "rsync -avz --remove-source-files -e 'ssh -p 404' /hawaiki_il/test/test-scp/och mediation@10.7.2.190:/mediation/tesubcom"

chmod -R 777 /hawaiki_il/tesubcom/och

su - mediation -c "rsync -avz --remove-source-files -e 'ssh -p 404' /hawaiki_il/tesubcom/och mediation@10.7.2.190:/mediation/tesubcom"

