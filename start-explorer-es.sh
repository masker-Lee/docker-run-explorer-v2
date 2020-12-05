#!/bin/bash
docker cp ./NIS-Explorer-V2 nis:/usr/local/
docker exec -it nis /usr/local/NIS-Explorer-V2/run_explorer.sh
