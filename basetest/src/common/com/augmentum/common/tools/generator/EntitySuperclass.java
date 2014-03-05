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


/**
 * <a href="EntitySuperclass.java.html"><b><i>View Source</i></b></a>
 * 
 * @author Harry Mark
 * 
 */
public class EntitySuperclass implements Cloneable {

	private String _name;
	private String _packagePath;
	private String _discriminatorValue;
	private String _joinColumn;

	public EntitySuperclass(String name, String discriminatorValue, String joinColumn) {

		_name = name;
		_discriminatorValue = discriminatorValue;
		_joinColumn = joinColumn;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getPackagePath() {
		return _packagePath;
	}

	public void setPackagePath(String packagePath) {
		_packagePath = packagePath;
	}

	public String getDiscriminatorValue() {
		return _discriminatorValue;
	}

	public void setDiscriminatorValue(String discriminatorValue) {
		_discriminatorValue = discriminatorValue;
	}

	public String getJoinColumn() {
		return _joinColumn;
	}

	public void setJoinColumn(String joinColumn) {
		_joinColumn = joinColumn;
	}

	public Object clone() {
		return new EntitySuperclass(getName(), getDiscriminatorValue(), getJoinColumn());
	}

	public boolean equals(Object obj) {
		EntitySuperclass sup = (EntitySuperclass) obj;

		String name = sup.getName();

		if (_name.equals(name)) {
			return true;
		} else {
			return false;
		}
	}
}