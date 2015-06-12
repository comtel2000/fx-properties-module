package org.comtel2000.guice.module;

/*
 * #%L
 * JavaFx Properties Module
 * %%
 * Copyright (C) 2015 comtel2000
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import java.lang.reflect.Field;
import java.util.Objects;

import javax.inject.Inject;

import org.comtel2000.guice.module.PreferenceContext.Root;

import com.google.inject.Injector;
import com.google.inject.MembersInjector;

class PreferenceBinderMembersInjector<T> implements MembersInjector<T> {

	@Inject
	Injector injector;
	
	private final Field field;
	private final PreferenceContext annotation;

	PreferenceBinderMembersInjector(Field field, PreferenceContext annotation) {
		this.field = field;
		this.annotation = annotation;
		field.setAccessible(true);
		
	}

	@Override
	public void injectMembers(T t) {

		Root root = Objects.requireNonNull(annotation.root());
		Class<?> tree = annotation.tree();
		try {
			field.set(t, new PreferenceBinder(tree != null ? tree : field.getDeclaringClass(), root));
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
