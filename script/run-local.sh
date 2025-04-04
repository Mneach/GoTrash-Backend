#!/bin/bash

# 1. Start PostgreSQL container
echo "🟢 Starting PostgreSQL container..."
docker-compose up -d

# 2. Wait for the DB to be ready (adjust sleep if needed)
echo "⏳ Waiting for PostgreSQL to be ready..."
until docker exec my_postgres pg_isready -U application > /dev/null 2>&1
do
  echo "⏳ Still waiting..."
  sleep 1
done

echo "✅ PostgreSQL is ready!"

# 3. Run Spring Boot app
echo "🚀 Running Spring Boot app..."
./mvnw spring-boot:run