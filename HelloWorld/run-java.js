#!/usr/bin/env node

const path = require('path');
const { execSync } = require('child_process');

const filePath = process.argv[2];

if (!filePath || !filePath.endsWith('.java')) {
    console.error('Usage: node run-java.js <path-to-java-file>');
    process.exit(1);
}

const projectRoot = '/Users/dw/coding2/DSAgrind/HelloWorld';
const srcDir = path.join(projectRoot, 'src');

const absoluteFilePath = path.resolve(filePath);
const relativePath = path.relative(srcDir, absoluteFilePath);
const className = path.basename(filePath, '.java');
const packagePath = path.dirname(relativePath);

let javaClassName;
if (packagePath === '.') {
    javaClassName = className;
} else {
    javaClassName = packagePath.replace(/\//g, '.') + '.' + className;
}

try {
    console.log(`Compiling: ${absoluteFilePath}`);
    execSync(`javac "${absoluteFilePath}"`, { cwd: srcDir, stdio: 'inherit' });
    
    console.log(`Running: java ${javaClassName}`);
    execSync(`java ${javaClassName}`, { cwd: srcDir, stdio: 'inherit' });
} catch (error) {
    console.error('Error:', error.message);
    process.exit(1);
} 