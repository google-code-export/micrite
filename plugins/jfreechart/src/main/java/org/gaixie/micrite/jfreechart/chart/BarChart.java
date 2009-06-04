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
package org.gaixie.micrite.jfreechart.chart;

import java.util.List;

import org.gaixie.micrite.jfreechart.data.DefindDefaultCategoryDataset;
/**
 * 柱状图bean
 *
 */
public class BarChart {
    //标题
    private String title="";
    //X轴下标
    private String categoryAxisLabel="";
    //Y轴下标
    private String valueAxisLabel="";
    //模版 如：BAR1
    private String barTemplate="";
    //结果集
    private List<DefindDefaultCategoryDataset> list = null;

    public String getBarTemplate() {
        return barTemplate;
    }
    public void setBarTemplate(String barTemplate) {
        this.barTemplate = barTemplate;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCategoryAxisLabel() {
        return categoryAxisLabel;
    }
    public void setCategoryAxisLabel(String categoryAxisLabel) {
        this.categoryAxisLabel = categoryAxisLabel;
    }
    public String getValueAxisLabel() {
        return valueAxisLabel;
    }
    public void setValueAxisLabel(String valueAxisLabel) {
        this.valueAxisLabel = valueAxisLabel;
    }
    public List<DefindDefaultCategoryDataset> getList() {
        return list;
    }
    public void setList(List<DefindDefaultCategoryDataset> list) {
        this.list = list;
    }
    
}
