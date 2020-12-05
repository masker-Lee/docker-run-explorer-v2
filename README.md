# docker-run-explorer-v2
include nis-0.6.97, explorer-v2 and it's poll data all in one
How to run:
    1. install docker and docker-compose
    2. docker-compose up (run the nis, poll-data and mysql), mysql port:9306
    3. after nis is finished update, run:start-explorer-es.sh
Add nis data:
    1. copy nis's data.db with copyNisData.sh in the same dir
    2. after docker-compose up, press Ctrl + C to stop it
    3. run copyNisData.sh
    4. docker-compose up
