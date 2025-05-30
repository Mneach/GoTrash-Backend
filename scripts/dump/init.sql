
-- Extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE SCHEMA IF NOT EXISTS "gotrash";

-- SCHEMA LIST
-- 1. users
-- 2. citizen
-- 4. waste_banks
-- 5. trash_categories
-- 6. trashes
-- 7. trash_histories
-- 8. trash_bins
-- 9. reward_categories
-- 10. rewards
-- 11. groups
-- 12. user_groups
-- 13. shipments
-- 14. notifications
-- 15. waste_bank_warehouses
-- 16. pending_trash_histories

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
  image_url TEXT,
  coin NUMERIC NOT NULL,
  rating NUMERIC NOT NULL,
  current_streak INT default 0,
  longest_streak INT default 0,
  last_trash_date DATE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_citizen_user FOREIGN KEY (user_id)
    REFERENCES gotrash.users(user_id)
);

-- 3. WASTE_BANKS TABLE
CREATE TABLE gotrash.waste_banks (
  user_id UUID PRIMARY KEY,
  name TEXT NOT NULL UNIQUE,
  latitude DOUBLE PRECISION NOT NULL,
  longitude DOUBLE PRECISION NOT NULL,
  address TEXT NOT NULL,
  image_url TEXT NOT NULL,
  phone_number TEXT NOT NULL,
  region TEXT NOT NULL,
  coin NUMERIC NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_waste_bank_user FOREIGN KEY (user_id)
    REFERENCES gotrash.users(user_id)
);

-- 4. TRASH_CATEGORIES TABLE
CREATE TABLE gotrash.trash_categories (
    trash_category_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    image_url TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5. TRASHES TABLE
CREATE TABLE gotrash.trashes (
    trash_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    trash_category_id UUID NOT NULL,
    coin NUMERIC NOT NULL,
    rating NUMERIC NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_trash_category FOREIGN KEY (trash_category_id)
        REFERENCES gotrash.trash_categories(trash_category_id)
);

-- 6. TRASH_BINS TABLE
CREATE TABLE gotrash.trash_bins (
  trash_bin_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  waste_bank_id UUID NOT NULL,
  name TEXT NOT NULL,
  latitude DOUBLE PRECISION NOT NULL,
  longitude DOUBLE PRECISION NOT NULL,
  address TEXT NOT NULL,
  image_url TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_trash_bin_waste_bank FOREIGN KEY (waste_bank_id)
    REFERENCES gotrash.waste_banks(user_id)
);

-- 7. TRASH_HISTORIES TABLE
CREATE TABLE gotrash.trash_histories (
  trash_history_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  citizen_id UUID NOT NULL,
  trash_id UUID NOT NULL,
  trash_bin_id UUID NOT NULL,
  weight NUMERIC (19, 2) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_trash_history_user FOREIGN KEY (citizen_id)
    REFERENCES gotrash.citizens(user_id),
  CONSTRAINT fk_trash_history_trash FOREIGN KEY (trash_id)
    REFERENCES gotrash.trashes(trash_id),
  CONSTRAINT fk_trash_history_bin FOREIGN KEY (trash_bin_id)
    REFERENCES gotrash.trash_bins(trash_bin_id)
);

-- 8. REWARD_CATEGORIES TABLE
CREATE TABLE gotrash.reward_categories (
    reward_category_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 9. REWARDS TABLE
CREATE TABLE gotrash.rewards (
    reward_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    reward_category_id UUID NOT NULL,
    waste_bank_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    coin NUMERIC NOT NULL,
    stock INTEGER NOT NULL,
    description TEXT NOT NULL,
    image_url TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reward_category FOREIGN KEY (reward_category_id)
        REFERENCES gotrash.reward_categories(reward_category_id),
    CONSTRAINT fk_reward_waste_bank FOREIGN KEY (waste_bank_id)
        REFERENCES gotrash.waste_banks(user_id)
);

-- 10. GROUPS TABLE
CREATE TABLE gotrash.groups (
  group_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  owner_id UUID NOT NULL,
  name TEXT NOT NULL,
  coin NUMERIC NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  CONSTRAINT fk_group_user FOREIGN KEY (owner_id)
    REFERENCES gotrash.users(user_id)
);

-- 11. USER_GROUPS TABLE
CREATE TABLE gotrash.user_groups (
  user_group_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  user_id UUID NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_user_group_user FOREIGN KEY (user_id)
    REFERENCES gotrash.users(user_id)
);

-- 12. CITIZEN ADDRESSES TABLE
CREATE TABLE gotrash.citizen_addresses (
    citizen_address_id UUID PRIMARY KEY,
    citizen_id UUID NOT NULL,
    label VARCHAR(255) NOT NULL,
    address TEXT NOT NULL,
    note TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_citizen_address_citizen
        FOREIGN KEY (citizen_id)
        REFERENCES gotrash.citizens(user_id)
);

-- 13. SHIPMENTS TABLE
CREATE TABLE gotrash.shipments (
  shipment_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  citizen_id UUID NOT NULL,
  reward_id UUID NOT NULL,
  citizen_address_id UUID NOT NULL,
  status TEXT NOT NULL,
  quantity NUMERIC NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_shipment_citizens FOREIGN KEY (citizen_id)
    REFERENCES gotrash.citizens(user_id),
  CONSTRAINT fk_shipment_citizen_addresses FOREIGN KEY (citizen_address_id)
    REFERENCES gotrash.citizen_addresses(citizen_address_id),
  CONSTRAINT fk_shipment_reward FOREIGN KEY (reward_id)
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

-- 15. WASTE BANK WAREHOUSE TABLE
CREATE TABLE gotrash.waste_bank_warehouses (
    waste_bank_id UUID NOT NULL,
    trash_category_id UUID NOT NULL,
    total_weight NUMERIC(19, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    PRIMARY KEY (waste_bank_id, trash_category_id),

    CONSTRAINT fk_warehouse_wastebank
        FOREIGN KEY (waste_bank_id)
        REFERENCES gotrash.waste_banks(user_id),

    CONSTRAINT fk_warehouse_trashcategory
        FOREIGN KEY (trash_category_id)
        REFERENCES gotrash.trash_categories(trash_category_id)
);

-- 16. PENDING TRASH_HISTORIES TABLE
CREATE TABLE gotrash.pending_trash_histories (
  pending_trash_history_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  trash_id UUID NOT NULL,
  trash_bin_id UUID NOT NULL,
  weight NUMERIC (19, 2) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_pending_trash_history_trash FOREIGN KEY (trash_id)
    REFERENCES gotrash.trashes(trash_id),
  CONSTRAINT fk_pending_trash_history_bin FOREIGN KEY (trash_bin_id)
    REFERENCES gotrash.trash_bins(trash_bin_id)
);

-- 17. MISSIONS TABLE
CREATE TABLE gotrash.missions (
    mission_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    type TEXT NOT NULL,
    goal_type TEXT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    target_value NUMERIC NOT NULL,
    trash_category_id UUID,
    reward_coins NUMERIC NOT NULL,
    reward_ratings NUMERIC NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_daily_mission_category FOREIGN KEY (trash_category_id)
        REFERENCES gotrash.trash_categories(trash_category_id)
);

-- 18. DAILY_MISSION_PROGRESS TABLE
CREATE TABLE gotrash.daily_mission_progress (
    daily_mission_progress_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    mission_id UUID NOT NULL,
    citizen_id UUID NOT NULL,
    current_progress NUMERIC NOT NULL,
    is_reward_claimed BOOLEAN DEFAULT FALSE,
    active_date DATE NOT NULL,

    CONSTRAINT fk_citizen_daily_mission_citizen FOREIGN KEY (citizen_id)
        REFERENCES gotrash.citizens(user_id),
    CONSTRAINT fk_citizen_daily_mission_mission FOREIGN KEY (mission_id)
        REFERENCES gotrash.missions(mission_id)
);

-- 19. GROUP_MISSION_PROGRESS TABLE
CREATE TABLE gotrash.group_mission_progress (
    group_mission_progress_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    mission_id UUID NOT NULL,
    group_id UUID NOT NULL,
    current_progress NUMERIC NOT NULL,
    is_reward_claimed BOOLEAN DEFAULT FALSE,

    CONSTRAINT fk_group_mission_progress_group FOREIGN KEY (group_id)
        REFERENCES gotrash.groups(group_id),
    CONSTRAINT fk_group_mission_progress_mission FOREIGN KEY (mission_id)
        REFERENCES gotrash.missions(mission_id)
);

-- 20. GROUP MEMBER MISSION CONTRIBUTIONS TABLE
CREATE TABLE gotrash.group_member_mission_contributions (
    contribution_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    group_mission_progress_id UUID NOT NULL,
    citizen_id UUID NOT NULL,
    contribution NUMERIC NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_contribution_group_mission FOREIGN KEY (group_mission_progress_id)
        REFERENCES gotrash.group_mission_progress(group_mission_progress_id),

    CONSTRAINT fk_contribution_citizen FOREIGN KEY (citizen_id)
        REFERENCES gotrash.citizens(user_id),

    CONSTRAINT uq_user_group_mission UNIQUE (group_mission_progress_id, citizen_id)
);

