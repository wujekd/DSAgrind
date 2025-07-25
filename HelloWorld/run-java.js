#!/usr/bin/env node

const fs = require('fs');
const path = require('path');
const { execSync } = require('child_process');

// Get the file path from command line argument
const filePath = process.argv[2];

if (!filePath) {
    console.error('Please provide a Java file path');
    process.exit(1);
}

const projectRoot = '/Users/dw/coding2/DSAgrind/HelloWorld';
const fileName = path.basename(filePath, '.java');

// Change to project root
process.chdir(projectRoot);

try {
    // Read the file to check for package declaration
    const fileContent = fs.readFileSync(filePath, 'utf8');
    const packageMatch = fileContent.match(/^package\s+([^;]+);/m);
    
    // Compile the file
    console.log(`Compiling ${filePath}...`);
    execSync(`javac -d bin "${filePath}"`, { stdio: 'inherit' });
    
    // Run the file
    if (packageMatch) {
        const packageName = packageMatch[1];
        console.log(`Running ${packageName}.${fileName}...`);
        execSync(`java -cp bin ${packageName}.${fileName}`, { stdio: 'inherit' });
    } else {
        console.log(`Running ${fileName}...`);
        execSync(`java -cp bin ${fileName}`, { stdio: 'inherit' });
    }
} catch (error) {
    console.error('Error:', error.message);
    process.exit(1);
} 