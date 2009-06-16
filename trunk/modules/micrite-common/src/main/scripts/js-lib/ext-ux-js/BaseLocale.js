/* ===========================================================
 * $Id$
 * This file is part of Micrite
 * ===========================================================
 *
 * (C) Copyright 2009, by Gaixie.org and Contributors.
 * 
 * Project Info:  http://micrite.gaixie.org/
 *
 * Micrite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Micrite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Micrite.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

Ext.ns('micrite.base');
/**
 * 所有在前端需要国际化的公用字符串都在这里初始化，包括：
 * 公用的按钮，进度条等
 * 在这里增加后，需要修改js-lib/ext-us-js/locale中的相应国际化文件
 * 调用时，可以在任意地方直接通过mbLocale.searchButton方式
 *  
 */
micrite.base.locale = function(config) {}
Ext.extend(micrite.base.locale, {
    searchButton:'Search',                
    submitButton:'Save',
    closeButton:'Close',
    cancelButton:'Cancel',
    waitingMsg:'Saving Data...',
    loadingMsg:'Loading Data...'
});
mbLocale = new micrite.base.locale();