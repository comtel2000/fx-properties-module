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


import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.prefs.Preferences;

@Documented
@Retention(RUNTIME)
@Target({ FIELD })
public @interface PreferenceContext {

	/**
	 * Select the preference package node tree
	 * 
	 * @return node tree from class package
	 */
	public Class<?>tree() default PreferenceBinder.class;

	/**
	 * Select the preference root tree
	 * 
	 * @return preference tree
	 */
	public Root root() default Root.USER;

	/**
	 * Select a preference tree
	 * 
	 * @see Preferences
	 *
	 */
	public enum Root {
		/** use the user's preference tree */
		USER, /** use the system preference tree */
		SYSTEM
	}

}
