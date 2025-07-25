#!/bin/bash

# Get the current file path
FILE_PATH="$1"
PROJECT_ROOT="/Users/dw/coding2/DSAgrind/HelloWorld"

# Change to project root
cd "$PROJECT_ROOT"

# Extract the file name without extension
FILE_NAME=$(basename "$FILE_PATH" .java)

# Extract the package name from the file
PACKAGE_NAME=$(grep "^package" "$FILE_PATH" | sed 's/package //;s/;//')

# Compile the file
echo "Compiling $FILE_PATH..."
javac -d bin "$FILE_PATH"

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    
    # Run the file
    if [ -n "$PACKAGE_NAME" ]; then
        echo "Running $PACKAGE_NAME.$FILE_NAME..."
        java -cp bin "$PACKAGE_NAME.$FILE_NAME"
    else
        echo "Running $FILE_NAME..."
        java -cp bin "$FILE_NAME"
    fi
else
    echo "Compilation failed!"
    exit 1
fi 