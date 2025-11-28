#!/bin/bash
# Shell script for macOS/Linux - Run with: bash COMPILE_AND_RUN.sh

echo "========================================"
echo "  SmartLibraryPortal Compiler"
echo "========================================"
echo ""

echo "Step 1: Cleaning old files..."
rm -f *.class
echo "Done!"
echo ""

echo "Step 2: Compiling Java files..."

# Compile AIQuestionGenerator
javac AIQuestionGenerator.java
if [ $? -ne 0 ]; then
    echo "ERROR: Failed to compile AIQuestionGenerator.java"
    exit 1
fi
echo "[OK] AIQuestionGenerator.java"

# (SimilarPaperFinder removed) Skip compiling it

# Compile GUIManager
javac GUIManager.java
if [ $? -ne 0 ]; then
    echo "ERROR: Failed to compile GUIManager.java"
    echo ""
    echo "POSSIBLE FIXES:"
    echo "1. Check file name is exactly: GUIManager.java (capital M)"
    echo "2. Make sure User and Student classes are NOT marked as 'public'"
    echo "3. Check all imports are at the top"
    exit 1
fi
echo "[OK] GUIManager.java"

# Compile SmartLibraryPortal
javac SmartLibraryPortal.java
if [ $? -ne 0 ]; then
    echo "ERROR: Failed to compile SmartLibraryPortal.java"
    exit 1
fi
echo "[OK] SmartLibraryPortal.java"

echo ""
echo "========================================"
echo "  Compilation Successful!"
echo "========================================"
echo ""
echo "Starting SmartLibraryPortal..."
echo ""

java SmartLibraryPortal

if [ $? -ne 0 ]; then
    echo ""
    echo "ERROR: Failed to run application"
fi
