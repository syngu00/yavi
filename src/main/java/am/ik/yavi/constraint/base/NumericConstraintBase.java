/*
 * Copyright (C) 2018-2021 Toshiaki Maki <makingx@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package am.ik.yavi.constraint.base;

import am.ik.yavi.core.Constraint;
import am.ik.yavi.core.ConstraintPredicate;

import java.util.function.Predicate;

import static am.ik.yavi.core.NullAs.VALID;
import static am.ik.yavi.core.ViolationMessage.Default.NUMERIC_GREATER_THAN;
import static am.ik.yavi.core.ViolationMessage.Default.NUMERIC_GREATER_THAN_OR_EQUAL;
import static am.ik.yavi.core.ViolationMessage.Default.NUMERIC_LESS_THAN;
import static am.ik.yavi.core.ViolationMessage.Default.NUMERIC_LESS_THAN_OR_EQUAL;
import static am.ik.yavi.core.ViolatedArguments.create;

public abstract class NumericConstraintBase<T, V, C extends Constraint<T, V, C>>
        extends ConstraintBase<T, V, C> {

    public C greaterThan(V min) {
        this.predicates().add(ConstraintPredicate.of(this.isGreaterThan(min),
                NUMERIC_GREATER_THAN, () -> create("min", min), VALID));
        return cast();
    }

    public C greaterThanOrEqual(V min) {
        this.predicates().add(ConstraintPredicate.of(this.isGreaterThanOrEqual(min),
                NUMERIC_GREATER_THAN_OR_EQUAL, () -> create("min", min), VALID));
        return cast();
    }

    public C lessThan(V max) {
        this.predicates().add(ConstraintPredicate.of(this.isLessThan(max),
                NUMERIC_LESS_THAN, () -> create("max", max), VALID));
        return cast();
    }

    public C lessThanOrEqual(V max) {
        this.predicates().add(ConstraintPredicate.of(this.isLessThanOrEqual(max),
                NUMERIC_LESS_THAN_OR_EQUAL, () -> create("max", max), VALID));
        return cast();
    }

    protected abstract Predicate<V> isGreaterThan(V min);

    protected abstract Predicate<V> isGreaterThanOrEqual(V min);

    protected abstract Predicate<V> isLessThan(V max);

    protected abstract Predicate<V> isLessThanOrEqual(V max);

}
