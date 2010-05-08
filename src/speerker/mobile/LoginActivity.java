package speerker.mobile;

import java.io.IOException;

import speerker.App;
import speerker.types.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity {
	private EditText username;
	private EditText password;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Control.currentContext = this;

		this.username = (EditText) findViewById(R.id.usernameEditText);
		this.password = (EditText) findViewById(R.id.passwordEditText);
		
		
	}

	public void loginClicked(View view) {
		User user = new User();
		user.setUsername(this.username.getText().toString());
		user.setPassword(this.password.getText().toString());

		Control.username = user.getUsername();
		Control.password = user.getPassword();

		this.initializeApplication();

		Intent intent = new Intent(LoginActivity.this, SearchActivity.class);
		startActivity(intent);
		this.finish();
	}

	public void initializeApplication() {
		try {
			App.setPropertiesFile(getAssets().open("speerker.properties"));
		} catch (IOException e) {
			App.logger.error("Could not open properties", e);
			Control.toastMessage(this, "Could not open properties");
		}
		Control.initializeApplication();
	}
}
