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

package org.gaixie.micrite.security.filter;

import java.lang.reflect.Method;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.ShadowMatch;

/**
 * Method匹配器实现，采用AspectJ PointCut表达式匹配目标方法。
 * 
 */
public class AspectJMethodMatcher {

    private PointcutParser pointcutParser = PointcutParser
            .getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution();

    public boolean match(String pattern, Method method) {
        pattern = "execution(" + pattern + ")";
        PointcutExpression pe = pointcutParser.parsePointcutExpression(pattern);
        ShadowMatch match = pe.matchesMethodExecution(method);
        return match.alwaysMatches();
    }

    public void setPointcutParser(PointcutParser pointcutParser) {
        this.pointcutParser = pointcutParser;
    }

    public PointcutParser getPointcutParser() {
        return pointcutParser;
    }
}
