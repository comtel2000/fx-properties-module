package org.comtel2000.guice.module;

/*
 * #%L
 * FX Properties Module
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


import com.google.inject.AbstractModule;

public class PreferenceBinderModule extends AbstractModule {

	private final PreferenceBinder binder;

	public PreferenceBinderModule(Class<?> c) {
		binder = new PreferenceBinder(c);
	}

	public PreferenceBinderModule(Class<?> c, PreferenceRoot root) {
		binder = new PreferenceBinder(c, root);
	}

	@Override
	protected void configure() {
		bind(PreferenceBinder.class).toInstance(binder);
	}

}
