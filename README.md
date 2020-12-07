# docker-run-explorer-v2
include nis-0.6.97, explorer-v2 and it's poll data, mysql all in one
# How to run:
    1. install docker and docker-compose
    2. cd ./nis run:`docker-compose up` (run the nis)
    3. after nis is finished update, cd ./explorer-es run:`docker-compose up` (run the explorer)
# How to Add nis data (if you need it):
    1. copy `nis's data.db` into the ./nis, when nis' docker-compose is not running
