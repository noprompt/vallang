package org.eclipse.imp.pdb.facts.impl.fast;

import org.eclipse.imp.pdb.facts.IRelationalAlgebra;
import org.eclipse.imp.pdb.facts.ISet;

public class RelationViewOnSet implements IRelationalAlgebra<ISet> {

	protected final ISet rel1;
	
	public RelationViewOnSet(ISet rel1) {
		this.rel1 = rel1;
	}
	
	@Override
	public ISet compose(ISet rel2) {
		return RelationalFunctionsOnSet.compose(rel1, rel2);
	}

	@Override
	public ISet closure() {
		return RelationalFunctionsOnSet.closure(rel1);
	}

	@Override
	public ISet closureStar() {
		return RelationalFunctionsOnSet.closureStar(rel1);
	}
	
	@Override
	public int arity() {
		return rel1.getElementType().getArity();
	}	
	
	@Override
	public ISet project(int... fieldIndexes) {
		return RelationalFunctionsOnSet.project(rel1, fieldIndexes);
	}

	@Override
	public ISet projectByFieldNames(String... fieldsNames) {
		return RelationalFunctionsOnSet.projectByFieldNames(rel1, fieldsNames);
	}

	@Override
	public ISet carrier() {
		return RelationalFunctionsOnSet.carrier(rel1);
	}

	@Override
	public ISet domain() {
		return RelationalFunctionsOnSet.domain(rel1);
	}

	@Override
	public ISet range() {
		return RelationalFunctionsOnSet.range(rel1);
	}

}
