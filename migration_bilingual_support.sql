-- ============================================
-- Bilingual Support Migration Script
-- ============================================
-- This script adds English and Arabic fields to the database schema
-- for FAQ, Research, and PreviousWork tables.
-- 
-- IMPORTANT: Backup your database before running this script!
-- 
-- Migration Strategy:
-- 1. Add new bilingual columns
-- 2. Migrate existing data to English columns (optional)
-- 3. Drop old single-language columns
-- ============================================

-- ============================================
-- 1. FAQ Table Migration
-- ============================================

-- Add new bilingual columns
ALTER TABLE faqs
ADD COLUMN english_question TEXT NOT NULL DEFAULT '',
ADD COLUMN english_answer TEXT NOT NULL DEFAULT '',
ADD COLUMN arabic_question TEXT NOT NULL DEFAULT '',
ADD COLUMN arabic_answer TEXT NOT NULL DEFAULT '';

-- Optional: Migrate existing data to English columns
-- Uncomment the following lines if you want to copy existing data to English fields
-- UPDATE faqs SET english_question = question, english_answer = answer;

-- Drop old columns
ALTER TABLE faqs
DROP COLUMN IF EXISTS question,
DROP COLUMN IF EXISTS answer;

-- Remove default constraints after migration (optional, for cleaner schema)
ALTER TABLE faqs
ALTER COLUMN english_question DROP DEFAULT,
ALTER COLUMN english_answer DROP DEFAULT,
ALTER COLUMN arabic_question DROP DEFAULT,
ALTER COLUMN arabic_answer DROP DEFAULT;


-- ============================================
-- 2. Research Table Migration
-- ============================================

-- Add new bilingual columns
ALTER TABLE researches
ADD COLUMN research_english_title VARCHAR(255) NOT NULL DEFAULT '',
ADD COLUMN research_english_body TEXT,
ADD COLUMN english_short_description VARCHAR(255),
ADD COLUMN research_arabic_title VARCHAR(255) NOT NULL DEFAULT '',
ADD COLUMN research_arabic_body TEXT,
ADD COLUMN arabic_short_description VARCHAR(255);

-- Optional: Migrate existing data to English columns
-- Uncomment the following lines if you want to copy existing data to English fields
-- UPDATE researches 
-- SET research_english_title = research_title,
--     research_english_body = research_body,
--     english_short_description = short_description;

-- Drop old columns
ALTER TABLE researches
DROP COLUMN IF EXISTS research_title,
DROP COLUMN IF EXISTS research_body,
DROP COLUMN IF EXISTS short_description;

-- Remove default constraints after migration (optional)
ALTER TABLE researches
ALTER COLUMN research_english_title DROP DEFAULT,
ALTER COLUMN research_arabic_title DROP DEFAULT;


-- ============================================
-- 3. PreviousWork Table Migration
-- ============================================

-- Add new bilingual columns
ALTER TABLE previous_works
ADD COLUMN english_previous_work_name VARCHAR(255) NOT NULL DEFAULT '',
ADD COLUMN english_summary TEXT,
ADD COLUMN english_case_name VARCHAR(255),
ADD COLUMN english_case_category VARCHAR(255),
ADD COLUMN arabic_previous_work_name VARCHAR(255) NOT NULL DEFAULT '',
ADD COLUMN arabic_summary TEXT,
ADD COLUMN arabic_case_name VARCHAR(255),
ADD COLUMN arabic_case_category VARCHAR(255);

-- Optional: Migrate existing data to English columns
-- Uncomment the following lines if you want to copy existing data to English fields
-- UPDATE previous_works 
-- SET english_previous_work_name = previous_work_name,
--     english_summary = summary,
--     english_case_name = case_name,
--     english_case_category = case_category;

-- Drop old columns
ALTER TABLE previous_works
DROP COLUMN IF EXISTS previous_work_name,
DROP COLUMN IF EXISTS summary,
DROP COLUMN IF EXISTS case_name,
DROP COLUMN IF EXISTS case_category;

-- Remove default constraints after migration (optional)
ALTER TABLE previous_works
ALTER COLUMN english_previous_work_name DROP DEFAULT,
ALTER COLUMN arabic_previous_work_name DROP DEFAULT;


-- ============================================
-- 4. Blog Table Migration (If Needed)
-- ============================================
-- NOTE: The Blog table already has bilingual fields in the entity,
-- but if your database schema still has old single-language columns,
-- uncomment and run the following:

/*
ALTER TABLE blogs
ADD COLUMN blog_english_title VARCHAR(255) NOT NULL DEFAULT '',
ADD COLUMN blog_english_body TEXT,
ADD COLUMN english_short_description VARCHAR(255),
ADD COLUMN blog_arabic_title VARCHAR(255) NOT NULL DEFAULT '',
ADD COLUMN blog_arabic_body TEXT,
ADD COLUMN arabic_short_description VARCHAR(255);

-- Optional: Migrate existing data
-- UPDATE blogs 
-- SET blog_english_title = blog_title,
--     blog_english_body = blog_body,
--     english_short_description = short_description;

-- Drop old columns
ALTER TABLE blogs
DROP COLUMN IF EXISTS blog_title,
DROP COLUMN IF EXISTS blog_body,
DROP COLUMN IF EXISTS short_description;

-- Remove defaults
ALTER TABLE blogs
ALTER COLUMN blog_english_title DROP DEFAULT,
ALTER COLUMN blog_arabic_title DROP DEFAULT;
*/

-- ============================================
-- Verification Queries
-- ============================================
-- Run these after migration to verify the changes:

-- SELECT * FROM faqs LIMIT 5;
-- SELECT * FROM researches LIMIT 5;
-- SELECT * FROM previous_works LIMIT 5;
-- SELECT * FROM blogs LIMIT 5;

-- ============================================
-- End of Migration Script
-- ============================================
