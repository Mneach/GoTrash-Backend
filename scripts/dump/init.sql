
-- Extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE SCHEMA IF NOT EXISTS "gotrash";

-- SCHEMA LIST
-- 1. users
-- 2. citizen
-- 3. governments
-- 4. waste_banks
-- 5. trash_categories
-- 6. trashes
-- 7. trash_histories
-- 8. trash_bins
-- 9. reward_categories
-- 10. rewards
-- 11. groups
-- 12. user_groups
-- 13. exchanges
-- 14. notifications
-- 15. shipments

-- 1. USERS TABLE
CREATE TABLE gotrash.users (
    user_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    role TEXT NOT NULL CHECK (role IN ('CITIZEN', 'WASTE_BANK', 'GOVERNMENT','COMPANY','GUEST')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- 2. CITIZENS TABLE
CREATE TABLE gotrash.citizens (
  user_id UUID PRIMARY KEY,
  name TEXT NOT NULL,
  phone_number VARCHAR(20) NOT NULL,
  image_name TEXT NOT NULL,
  image_url TEXT,
  coin NUMERIC NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_citizen_user FOREIGN KEY (user_id)
    REFERENCES gotrash.users(user_id)
);

-- 3. GOVERNMENTS TABLE
CREATE TABLE gotrash.governments (
  user_id UUID PRIMARY KEY,
  name TEXT NOT NULL UNIQUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_government_user FOREIGN KEY (user_id)
    REFERENCES gotrash.users(user_id)
);

-- 4. WASTE_BANKS TABLE
CREATE TABLE gotrash.waste_banks (
  user_id UUID PRIMARY KEY,
  name TEXT NOT NULL UNIQUE,
  latitude DOUBLE PRECISION NOT NULL,
  longitude DOUBLE PRECISION NOT NULL,
  address TEXT NOT NULL,
  image_name TEXT NOT NULL,
  image_url TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_waste_bank_user FOREIGN KEY (user_id)
    REFERENCES gotrash.users(user_id)
);

-- 5. WASTE_BANKS TABLE
CREATE TABLE gotrash.companies (
  user_id UUID PRIMARY KEY,
  name TEXT NOT NULL UNIQUE,
  address TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_company_user FOREIGN KEY (user_id)
    REFERENCES gotrash.users(user_id)
);

-- 6. TRASH_CATEGORIES TABLE
CREATE TABLE gotrash.trash_categories (
    trash_category_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 7. TRASHES TABLE
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

-- 8. TRASH_BINS TABLE
CREATE TABLE gotrash.trash_bins (
  trash_bin_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  waste_bank_id UUID NOT NULL,
  name TEXT NOT NULL,
  latitude DOUBLE PRECISION NOT NULL,
  longitude DOUBLE PRECISION NOT NULL,
  address TEXT NOT NULL,
  image_name TEXT NOT NULL,
  image_url TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_trash_bin_waste_bank FOREIGN KEY (waste_bank_id)
    REFERENCES gotrash.waste_banks(user_id)
);

-- 9. TRASH_HISTORIES TABLE
CREATE TABLE gotrash.trash_histories (
  trash_history_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  user_id UUID NOT NULL,
  trash_id UUID NOT NULL,
  trash_bin_id UUID NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_trash_history_user FOREIGN KEY (user_id)
    REFERENCES gotrash.users(user_id),
  CONSTRAINT fk_trash_history_trash FOREIGN KEY (trash_id)
    REFERENCES gotrash.trashes(trash_id),
  CONSTRAINT fk_trash_history_bin FOREIGN KEY (trash_bin_id)
    REFERENCES gotrash.trash_bins(trash_bin_id)
);

-- 10. REWARD_CATEGORIES TABLE
CREATE TABLE gotrash.reward_categories (
    reward_category_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 11. REWARDS TABLE
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

-- 11. GROUPS TABLE (renamed from "group")
CREATE TABLE gotrash.groups (
  group_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  reward_id UUID NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_group_reward FOREIGN KEY (reward_id)
    REFERENCES gotrash.rewards(reward_id)
);

-- 12. USER_GROUPS TABLE
CREATE TABLE gotrash.user_groups (
  user_group_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  user_id UUID NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_user_group_user FOREIGN KEY (user_id)
    REFERENCES gotrash.users(user_id)
);

-- 13. EXCHANGES TABLE
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

-- 14. NOTIFICATIONS TABLE
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

-- 15. SHIPMENTS TABLE
CREATE TABLE gotrash.shipments (
    shipment_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    waste_bank_id UUID NOT NULL,
    destination_company_id UUID NOT NULL,
    category TEXT NOT NULL CHECK (category IN ('PLASTIC', 'METAL', 'ORGANIC','GLASS', 'OTHERS')),
    weight DOUBLE PRECISION NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    status VARCHAR(50) NOT NULL CHECK (status IN ('WAITING_CONFIRMATION','CONFIRMED','RECEIVED', 'PICKED_UP', 'IN_TRANSIT', 'DELIVERED','RECEIVED','CANCELLED')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_waste_bank FOREIGN KEY (waste_bank_id)
        REFERENCES gotrash.waste_banks (user_id) ON DELETE CASCADE,

    CONSTRAINT fk_destination_company FOREIGN KEY (destination_company_id)
        REFERENCES gotrash.companies (user_id) ON DELETE CASCADE
);
