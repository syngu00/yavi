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
package am.ik.yavi.constraint;

import am.ik.yavi.constraint.base.ConstraintBase;
import am.ik.yavi.core.ConstraintPredicate;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static am.ik.yavi.core.NullAs.VALID;
import static am.ik.yavi.core.ViolationMessage.Default.BOOLEAN_IS_FALSE;
import static am.ik.yavi.core.ViolationMessage.Default.BOOLEAN_IS_TRUE;

public class BooleanConstraint<T>
        extends ConstraintBase<T, Boolean, BooleanConstraint<T>> {

    @Override
    public BooleanConstraint<T> cast() {
        return this;
    }

    public BooleanConstraint<T> isFalse() {
        this.predicates().add(ConstraintPredicate.of(x -> !x, BOOLEAN_IS_FALSE,
                LinkedHashMap::new, VALID));
        return this;
    }

    public BooleanConstraint<T> isTrue() {
        this.predicates().add(ConstraintPredicate.of(x -> x, BOOLEAN_IS_TRUE,
                LinkedHashMap::new, VALID));
        return this;
    }
}
