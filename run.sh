#!/bin/bash

# D FOUCHE
# UCT CS HONS
# FCHDYL001

#Compile if necessary 
make

#Run driver
java -cp bin:include/json-20190722.jar driver "$@"