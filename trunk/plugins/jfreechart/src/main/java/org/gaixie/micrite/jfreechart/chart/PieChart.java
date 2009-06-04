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

import org.gaixie.micrite.jfreechart.data.DefindPieDataset;

/**
 * 饼状图bean
 *
 */
public class PieChart {
    //标题
    private String title = "";
    //结果集
    private List<DefindPieDataset> dataset = null;
    //模版名称，如：PIE1
    private String pieTemplate = "";
   
    public String getPieTemplate() {
        return pieTemplate;
    }
    public void setPieTemplate(String pieTemplate) {
        this.pieTemplate = pieTemplate;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<DefindPieDataset> getDataset() {
        return dataset;
    }
    public void setDataset(List<DefindPieDataset> dataset) {
        this.dataset = dataset;
    }
    
    
}
