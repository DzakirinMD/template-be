-- Create databases for order-management and loyalty-service
CREATE DATABASE identity_service_db;
CREATE DATABASE user_service_db;
CREATE DATABASE order_management_service_db;
CREATE DATABASE loyalty_service_db;

-- Connect to identity_service_db and create schema
\c identity_service_db;
CREATE SCHEMA IF NOT EXISTS identity_service;

-- Connect to user_service_db and create schema
\c user_service_db;
CREATE SCHEMA IF NOT EXISTS user_service;

-- Connect to order_management_service_db and create schema
\c order_management_service_db;
CREATE SCHEMA IF NOT EXISTS order_management_service;

-- Connect to loyalty_service_db and create schema
\c loyalty_service_db;
CREATE SCHEMA IF NOT EXISTS loyalty_service;