#!/bin/bash

# 1. Start PostgreSQL container
echo "🟢 Starting PostgreSQL container..."
docker-compose up -d

# 2. Waiting until postgres to start
while ! docker exec "postgres" pg_isready -U postgres; do
  echo "⏳ Waiting for postgres to start..."
  sleep 1
done

echo "✅ PostgreSQL is ready!"

# 3. Run Spring Boot app
echo "🚀 Running Spring Boot app..."
./gradlew build
java -jar build/libs/*.jar