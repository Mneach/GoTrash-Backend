#!/bin/bash

# Exit on error
set -e

echo "📦 Building the Java app with Gradle..."
./gradlew build -x test

echo "🐳 Starting Docker containers..."
docker compose up --build
