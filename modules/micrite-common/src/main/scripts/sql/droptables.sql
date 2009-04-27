-- ===========================================================
-- $Id: struts.xml 107 2009-03-24 11:25:57Z bitorb $
-- This file is part of Micrite
-- ===========================================================
--
-- (C) Copyright 2009, by Gaixie.org and Contributors.
-- 
-- Project Info:  http://micrite.gaixie.org/
--
-- Micrite is free software: you can redistribute it and/or modify
-- it under the terms of the GNU General Public License as published by
-- the Free Software Foundation, either version 3 of the License, or
-- (at your option) any later version.
--
-- Micrite is distributed in the hope that it will be useful,
-- but WITHOUT ANY WARRANTY; without even the implied warranty of
-- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
-- GNU General Public License for more details.
--
-- You should have received a copy of the GNU General Public License
-- along with Micrite.  If not, see <http://www.gnu.org/licenses/>.



-- drop tables with foreign key
DROP TABLE role_authority_map;
DROP TABLE user_role_map;
DROP TABLE customers;


-- drop non-associated tables
DROP TABLE customer_source;

-- drop core tables
DROP TABLE roles;
DROP TABLE userbase;
DROP TABLE authorities;