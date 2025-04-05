
-- Extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE SCHEMA IF NOT EXISTS "gotrash";

-- SCHEMA LIST
-- 1. users
-- 2. trash_categories
-- 3. trashes
-- 4. trash_histories
-- 5. trash_bins
-- 6. reward_categories
-- 7. rewards
-- 8. groups
-- 9. user_groups
-- 10. exchanges
-- 11. notifications

-- 1. USERS TABLE
CREATE TABLE gotrash.users (
    user_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    image_url TEXT,
    coin NUMERIC NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. TRASH_CATEGORIES TABLE
CREATE TABLE gotrash.trash_categories (
    trash_category_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. TRASHES TABLE
CREATE TABLE gotrash.trashes (
    trash_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    trash_category_id UUID NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_trash_category FOREIGN KEY (trash_category_id)
        REFERENCES gotrash.trash_categories(trash_category_id)
);

-- 4. TRASH_HISTORIES TABLE
CREATE TABLE gotrash.trash_histories (
    trash_history_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    trash_id UUID NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_trash_history_user FOREIGN KEY (user_id)
        REFERENCES gotrash.users(user_id),
    CONSTRAINT fk_trash_history_trash FOREIGN KEY (trash_id)
        REFERENCES gotrash.trashes(trash_id)
);

-- 5. TRASH_BINS TABLE
CREATE TABLE gotrash.trash_bins (
  trash_bin_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  latitude DOUBLE PRECISION NOT NULL,
  longitude DOUBLE PRECISION NOT NULL,
  address TEXT NOT NULL,
  image_name TEXT NOT NULL,
  image_url TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 6. REWARD_CATEGORIES TABLE
CREATE TABLE gotrash.reward_categories (
    reward_category_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 7. REWARDS TABLE
CREATE TABLE gotrash.rewards (
    reward_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    reward_category_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    coin NUMERIC NOT NULL,
    stock INTEGER NOT NULL,
    description TEXT NOT NULL,
    image_name VARCHAR(255) NOT NULL,
    image_url TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reward_category FOREIGN KEY (reward_category_id)
        REFERENCES gotrash.reward_categories(reward_category_id)
);

-- 8. GROUPS TABLE (renamed from "group")
CREATE TABLE gotrash.groups (
  group_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  reward_id UUID NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_group_reward FOREIGN KEY (reward_id)
    REFERENCES gotrash.rewards(reward_id)
);

-- 9. USER_GROUPS TABLE
CREATE TABLE gotrash.user_groups (
  user_group_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  user_id UUID NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_user_group_user FOREIGN KEY (user_id)
    REFERENCES gotrash.users(user_id)
);

-- 10. EXCHANGES TABLE
CREATE TABLE gotrash.exchanges (
  exchange_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  user_id UUID NOT NULL,
  reward_id UUID NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_exchange_user FOREIGN KEY (user_id)
    REFERENCES gotrash.users(user_id),
  CONSTRAINT fk_exchange_reward FOREIGN KEY (reward_id)
    REFERENCES gotrash.rewards(reward_id)
);

-- 11. NOTIFICATIONS TABLE
CREATE TABLE gotrash.notifications (
  notification_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  user_id UUID NOT NULL,
  title TEXT NOT NULL,
  description TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_notification_user FOREIGN KEY (user_id)
    REFERENCES gotrash.users(user_id)
);
