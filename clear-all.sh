#!/bin/bash
docker stop nis poll mysql 
docker rm  nis poll mysql 
docker rmi $(basename "$PWD")_nis $(basename "$PWD")_poll
