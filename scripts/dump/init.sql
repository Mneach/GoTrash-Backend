
-- Extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE SCHEMA IF NOT EXISTS "gotrash";

-- SCHEMA LIST
-- 1. users
-- 2. users
-- 3. trash_categories
-- 4. trashes
-- 5. trash_histories
-- 6. trash_bins
-- 7. reward_categories
-- 8. rewards
-- 9. groups
-- 10. user_groups
-- 11. exchanges
-- 12. notifications
-- 13. roles


-- 1. ROLES TABLE
CREATE TABLE gotrash.roles (
  role_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  name TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. USERS TABLE
CREATE TABLE gotrash.users (
    user_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    image_url TEXT,
    coin NUMERIC NOT NULL,
    role_id UUID NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_role FOREIGN KEY (role_id)
        REFERENCES gotrash.roles(role_id)
);

-- 3. TRASH_CATEGORIES TABLE
CREATE TABLE gotrash.trash_categories (
    trash_category_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. TRASHES TABLE
CREATE TABLE gotrash.trashes (
    trash_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    trash_category_id UUID NOT NULL,
    coin NUMERIC NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_trash_category FOREIGN KEY (trash_category_id)
        REFERENCES gotrash.trash_categories(trash_category_id)
);

-- 5. TRASH_HISTORIES TABLE
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

-- 6. TRASH_BINS TABLE
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

-- 7. REWARD_CATEGORIES TABLE
CREATE TABLE gotrash.reward_categories (
    reward_category_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 8. REWARDS TABLE
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

-- 9. GROUPS TABLE (renamed from "group")
CREATE TABLE gotrash.groups (
  group_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  reward_id UUID NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_group_reward FOREIGN KEY (reward_id)
    REFERENCES gotrash.rewards(reward_id)
);

-- 10. USER_GROUPS TABLE
CREATE TABLE gotrash.user_groups (
  user_group_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  user_id UUID NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_user_group_user FOREIGN KEY (user_id)
    REFERENCES gotrash.users(user_id)
);

-- 11. EXCHANGES TABLE
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

-- 12. NOTIFICATIONS TABLE
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
