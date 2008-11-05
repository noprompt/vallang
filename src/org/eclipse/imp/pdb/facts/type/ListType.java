/*******************************************************************************
* Copyright (c) 2007 IBM Corporation.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    Robert Fuhrer (rfuhrer@watson.ibm.com) - initial API and implementation

*******************************************************************************/

package org.eclipse.imp.pdb.facts.type;

import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;

public class ListType extends Type {
	/*package*/ Type fEltType;
	
	/*package*/ ListType(Type eltType) {
		fEltType = eltType;
	}

	public Type getElementType() {
		return fEltType;
	}
	
	@Override
	public boolean isListType() {
		return true;
	}
	
	@Override
	public boolean isSubtypeOf(Type other) {
		if (this == other || other.isValueType()) {
			return true;
		}
		else if (other.isListType()) {
			ListType o = (ListType) other;
			return fEltType.isSubtypeOf(o.getElementType());
		}
		return false;
	}

	@Override
	public Type lub(Type other) {
		if (this == other) {
			return this;
		}
		else if (other.isValueType()) {
			return other;
		}
		else if (other.isListType()) {
			ListType o = (ListType) other;
			return TypeFactory.getInstance().listType(fEltType.lub(o.getElementType()));
		}
		else if (other.isNamedType()) {
			return lub(other.getBaseType());
		}
		
		return TypeFactory.getInstance().valueType();
	}
	
	@Override
	public String toString() {
		return "list[" + fEltType + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ListType) {
			ListType other = (ListType) o;
			return fEltType == other.fEltType;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return 75703 + 104543 * fEltType.hashCode();
	}
	
	@Override
	public IValue accept(ITypeVisitor visitor) {
		return visitor.visitList(this);
	}

	@Override
	public IValue make(IValueFactory f) {
		return f.list(fEltType);
	}

	@Override
	public IValue make(IValueFactory f, IValue... elems) {
		return f.list(elems);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IListWriter writer(IValueFactory f) {
		return f.listWriter(fEltType);
	}
}
