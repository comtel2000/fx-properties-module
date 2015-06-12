# fx-properties-module
This [Guice](https://github.com/google/guice) module allows JavaFX applications to bind FX Properties to Preferences and store and retrieve it from the OS-specific registry. It's inspired by the fx-guice (https://github.com/cathive/fx-guice) preferences injection.

sample usage:
```java

	@PreferenceContext
	PreferenceBinder persist;

	@FXML
	TextField userName;

	public Demo() {
		// store and retrieve user name from OS registry
		persist.bind(userName.textProperty(), "user.name");
	}

	void close() {
		persist.flush();
	}

```
