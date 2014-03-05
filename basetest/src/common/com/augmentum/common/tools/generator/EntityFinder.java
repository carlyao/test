/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.augmentum.common.tools.generator;

import java.util.List;

/**
 * <a href="EntityFinder.java.html"><b><i>View Source</i></b></a>
 * 
 * @author Brian Wing Shun Chan
 * 
 */
public class EntityFinder {

    private String _name;
    private String _returnType;
    private List<EntityColumn> _columns;
    private String _where;
    private boolean _dbIndex;

    private EntityOrder order;
    private boolean cacheable;

    public EntityFinder(String name, String returnType, List<EntityColumn> columns, String where,
            boolean dbIndex) {

        this(name, returnType, columns, where, dbIndex, null, false);
    }

    public EntityFinder(String name, String returnType, List<EntityColumn> columns, String where,
            boolean dbIndex, EntityOrder order, boolean cacheable) {

        _name = name;
        _returnType = returnType;
        _columns = columns;
        _where = where;
        _dbIndex = dbIndex;

        this.order = order;
        this.cacheable = cacheable;
    }

    public String getName() {
        return _name;
    }

    public String getReturnType() {
        return _returnType;
    }

    public boolean isCollection() {
        if (_returnType != null && _returnType.equals("Collection")) {
            return true;
        } else {
            return false;
        }
    }

    public List<EntityColumn> getColumns() {
        return _columns;
    }

    public String getWhere() {
        return _where;
    }

    public boolean isDBIndex() {
        return _dbIndex;
    }

    public EntityOrder getOrder() {
        return order;
    }

    public void setOrder(EntityOrder order) {
        this.order = order;
    }

    public boolean isCacheable() {
        return cacheable;
    }

    public void setCacheable(boolean cacheable) {
        this.cacheable = cacheable;
    }
}