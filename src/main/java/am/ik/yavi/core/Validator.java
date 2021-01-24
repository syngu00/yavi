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
package am.ik.yavi.core;

import am.ik.yavi.fn.Either;
import am.ik.yavi.fn.Pair;
import am.ik.yavi.message.MessageFormatter;

import java.util.*;
import java.util.function.Consumer;

/**
 * Validates the target instances.
 * <p>
 * A <code>Validator</code> instance is immutable and can be used as a singleton.
 *
 * @param <T> the type of the instance to validate
 * @author Toshiaki Maki
 */
public class Validator<T> implements ValidatorSubset<T> {
    private final List<CollectionValidator<T, ?, ?>> collectionValidators;

    private final List<Pair<ConstraintCondition<T>, ValidatorSubset<T>>> conditionalValidators;

    private final MessageFormatter messageFormatter;

    private final String messageKeySeparator;

    private final List<ConstraintPredicates<T, ?>> predicatesList;

    private final String prefix;

    public Validator(String messageKeySeparator,
                     List<ConstraintPredicates<T, ?>> predicatesList,
                     List<CollectionValidator<T, ?, ?>> collectionValidators,
                     List<Pair<ConstraintCondition<T>, ValidatorSubset<T>>> conditionalValidators,
                     MessageFormatter messageFormatter) {
        this(messageKeySeparator, Collections.unmodifiableList(predicatesList),
                Collections.unmodifiableList(collectionValidators), conditionalValidators,
                messageFormatter, "");
    }

    private Validator(String messageKeySeparator,
                      List<ConstraintPredicates<T, ?>> predicatesList,
                      List<CollectionValidator<T, ?, ?>> collectionValidators,
                      List<Pair<ConstraintCondition<T>, ValidatorSubset<T>>> conditionalValidators,
                      MessageFormatter messageFormatter, String prefix) {
        this.messageKeySeparator = messageKeySeparator;
        this.predicatesList = predicatesList;
        this.collectionValidators = collectionValidators;
        this.conditionalValidators = conditionalValidators;
        this.messageFormatter = messageFormatter;
        this.prefix = prefix;
    }

    Validator<T> prefixed(String prefix) {
        return new Validator<>(this.messageKeySeparator, this.predicatesList,
                this.collectionValidators, this.conditionalValidators,
                this.messageFormatter, prefix);
    }

    /**
     * This method is supposed to be used only internally.
     *
     * @param action callback per <code>ConstraintPredicates</code>.
     */
    public void forEachPredicates(Consumer<ConstraintPredicates<T, ?>> action) {
        this.predicatesList.forEach(action);
    }

    /**
     * This method is supposed to be used only internally.
     *
     * @param action callback per <code>CollectionValidator</code>.
     */
    public void forEachCollectionValidator(
            Consumer<CollectionValidator<T, ?, ?>> action) {
        this.collectionValidators.forEach(action);
    }

    /**
     * This method is supposed to be used only internally.
     *
     * @param action callback per <code>Pair<ConstraintCondition<T>, Validator<T>></code>.
     */
    public void forEachConditionalValidator(
            Consumer<Pair<ConstraintCondition<T>, ValidatorSubset<T>>> action) {
        this.conditionalValidators.forEach(action);
    }

    /**
     * Validates all constraints on {@code target}. <br>
     * {@code Locale.getDefault()} is used to locate the violation messages.
     *
     * @param target          target to validate
     * @param constraintGroup constraint group to validate
     * @return constraint violations
     * @throws IllegalArgumentException if target is {@code null}
     */
    public ConstraintViolations validate(T target, ConstraintGroup constraintGroup) {
        return this.validate(target, Locale.getDefault(), constraintGroup);
    }

    /**
     * Validates all constraints on {@code target}.<br>
     * {@code ConstraintGroup.DEFAULT} is used as a constraint group.
     *
     * @param target target to validate
     * @param locale the locale targeted for the violation messages.
     * @return constraint violations
     * @throws IllegalArgumentException if target is {@code null}
     */
    public ConstraintViolations validate(T target, Locale locale) {
        return this.validate(target, locale, ConstraintGroup.DEFAULT);
    }

    /**
     * Validates all constraints on {@code target}.
     *
     * @param target          target to validate
     * @param locale          the locale targeted for the violation messages.
     * @param constraintGroup constraint group to validate
     * @return constraint violations
     * @throws IllegalArgumentException if target is {@code null}
     */
    public ConstraintViolations validate(T target, Locale locale,
                                         ConstraintGroup constraintGroup) {
        return this.validate(target, "", -1, locale, constraintGroup);
    }

    /**
     * Validates all constraints on {@code target}. <br>
     * {@code Locale.getDefault()} is used to locate the violation messages.
     * {@code ConstraintGroup.DEFAULT} is used as a constraint group.
     *
     * @param target target to validate
     * @return constraint violations
     * @throws IllegalArgumentException if target is {@code null}
     */
    public ConstraintViolations validate(T target) {
        return this.validate(target, Locale.getDefault(), ConstraintGroup.DEFAULT);
    }

    /**
     * Validates all constraints on {@code target} and returns {@code Either} object that
     * has constraint violations on the left or validated object on the right. <br>
     * {@code Locale.getDefault()} is used to locate the violation messages.<br>
     * {@code ConstraintGroup.DEFAULT} is used as a constraint group.
     *
     * @param target target to validate
     * @return either object that has constraint violations on the left or validated
     * object on the right
     * @throws IllegalArgumentException if target is {@code null}
     */
    public Either<ConstraintViolations, T> validateToEither(T target) {
        return this.validateToEither(target, Locale.getDefault(),
                ConstraintGroup.DEFAULT);
    }

    /**
     * Validates all constraints on {@code target} and returns {@code Either} object that
     * has constraint violations on the left or validated object on the right. <br>
     * {@code Locale.getDefault()} is used to locate the violation messages.
     *
     * @param target          target to validate
     * @param constraintGroup constraint group to validate
     * @return either object that has constraint violations on the left or validated
     * object on the right
     * @throws IllegalArgumentException if target is {@code null}
     */
    public Either<ConstraintViolations, T> validateToEither(T target,
                                                            ConstraintGroup constraintGroup) {
        return this.validateToEither(target, Locale.getDefault(), constraintGroup);
    }

    /**
     * Validates all constraints on {@code target} and returns {@code Either} object that
     * has constraint violations on the left or validated object on the right. <br>
     * {@code ConstraintGroup.DEFAULT} is used as a constraint group.
     *
     * @param target target to validate
     * @param locale the locale targeted for the violation messages.
     * @return either object that has constraint violations on the left or validated
     * object on the right
     * @throws IllegalArgumentException if target is {@code null}
     */
    public Either<ConstraintViolations, T> validateToEither(T target, Locale locale) {
        return this.validateToEither(target, locale, ConstraintGroup.DEFAULT);
    }

    /**
     * Validates all constraints on {@code target} and returns {@code Either} object that
     * has constraint violations on the left or validated object on the right. <br>
     *
     * @param target target to validate
     * @return either object that has constraint violations on the left or validated
     * object on the right
     * @throws IllegalArgumentException if target is {@code null}
     */
    public Either<ConstraintViolations, T> validateToEither(T target, Locale locale,
                                                            ConstraintGroup constraintGroup) {
        ConstraintViolations violations = this.validate(target, locale, constraintGroup);
        if (violations.isValid()) {
            return Either.right(target);
        } else {
            return Either.left(violations);
        }
    }

    private String indexedName(String name, String collectionName, int index) {
        if (index < 0) {
            return name;
        }
        if (name.isEmpty()) {
            return collectionName + "[" + index + "]";
        }
        return collectionName + "[" + index + "]" + this.messageKeySeparator + name;
    }

    private Map<String, Object> pad(String name, Map<String, Object> args, ViolatedValue violatedValue) {
        Map<String,Object> pad = new LinkedHashMap<>();
        pad.put("name", name);
        pad.putAll(args);
        pad.put("value", violatedValue.value());
        return pad;
    }

    @SuppressWarnings("unchecked")
    private ConstraintViolations validate(T target, String collectionName, int index,
                                          Locale locale, ConstraintGroup constraintGroup) {
        if (target == null) {
            throw new IllegalArgumentException("target must not be null");
        }
        ConstraintViolations violations = new ConstraintViolations();
        for (ConstraintPredicates<T, ?> predicates : this.predicatesList) {
            if (predicates instanceof NestedConstraintPredicates) {
                NestedConstraintPredicates<T, ?, ?> nested = (NestedConstraintPredicates<T, ?, ?>) predicates;
                Object nestedValue = nested.nestedValue(target);
                if (nestedValue == null) {
                    continue;
                }
            }
            for (ConstraintPredicate<?> constraintPredicate : predicates.predicates()) {
                Object v = predicates.toValue().apply(target);
                if (v == null && constraintPredicate.nullValidity().skipNull()) {
                    continue;
                }
                Optional<ViolatedValue> violated = ((ConstraintPredicate) constraintPredicate)
                        .violatedValue(v);
                violated.ifPresent(violatedValue -> {
                    String name = this.prefix
                            + this.indexedName(predicates.name(), collectionName, index);
                    Map<String, Object> args = pad(name, constraintPredicate.args().get(), violatedValue);
                    args.putAll(predicates.args());
                    violations.add(new ConstraintViolation(name,
                            constraintPredicate.messageKey(),
                            constraintPredicate.defaultMessageFormat(),
                            args, this.messageFormatter,
                            locale));
                });
            }
        }
        this.collectionValidators.forEach(collectionValidator -> {
            Collection collection = collectionValidator.toCollection().apply(target);
            if (collection != null) {
                Validator validator = collectionValidator.validator();
                int i = 0;
                for (Object element : collection) {
                    if (element != null) {
                        String nestedName = this.indexedName(collectionValidator.name(),
                                collectionName, index);
                        ConstraintViolations v = validator.validate(element, nestedName,
                                i++, locale, constraintGroup);
                        violations.addAll(v);
                    }
                }
            }
        });
        this.conditionalValidators.forEach(pair -> {
            ConstraintCondition<T> condition = pair.first();
            if (condition.test(target, constraintGroup)) {
                ValidatorSubset<T> validator = pair.second();
                ConstraintViolations v = validator.validate(target, locale,
                        constraintGroup);
                violations.addAll(v);
            }
        });
        return violations;
    }
}
