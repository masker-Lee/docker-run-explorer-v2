#!/bin/bash
cd /usr/local
chmod +R 777 nis-0.6.97
cd ./nis-0.6.97/package
./nix.runNis.sh