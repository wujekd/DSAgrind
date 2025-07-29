#!/bin/bash

cd /Users/dw/coding2/DSAgrind/HelloWorld/src

if [[ $1 == *.java ]]; then
    filename=$(basename "$1" .java)
    package_path=$(dirname "$1" | sed 's|^/Users/dw/coding2/DSAgrind/HelloWorld/src/||')
    
    if [ "$package_path" = "." ]; then
        javac "$filename.java"
        java "$filename"
    else
        javac "$1"
        java "$package_path.$filename"
    fi
else
    echo "Not a Java file: $1"
fi 