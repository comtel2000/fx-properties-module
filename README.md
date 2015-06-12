# fx-properties-module
This guice module allows JavaFx applications to bind Property to Preferences and store and retrieve it from the OS-specific registry.

sample usage:
```java
public class Demo {

	@Inject
	PreferenceBinder persist;
	
	@FXML
	TextField userName;
	
	public Demo() {
		//store and retrieve user name
		persist.bind(userName.textProperty(), "user.name");
	}
}

```
