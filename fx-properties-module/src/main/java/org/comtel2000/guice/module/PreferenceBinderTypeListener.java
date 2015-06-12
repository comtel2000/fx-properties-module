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

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

class PreferenceBinderTypeListener implements TypeListener {

	@Override
	public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
		for (Field field : type.getRawType().getDeclaredFields()) {
			if (field.getType() == PreferenceBinder.class && field.isAnnotationPresent(PreferenceContext.class)) {
				PreferenceContext annotation = field.getAnnotation(PreferenceContext.class);
				encounter.register(new PreferenceBinderMembersInjector<I>(field, annotation));
			}
		}

	}

}
