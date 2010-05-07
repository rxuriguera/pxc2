package speerker.mobile;

import java.io.IOException;

import speerker.App;
import speerker.types.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

	private static final String LOGTAG = "SpeerkerMobile";

	private EditText username;
	private EditText password;
	private Button loginButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.username = (EditText) findViewById(R.id.usernameEditText);
		this.password = (EditText) findViewById(R.id.passwordEditText);
		this.loginButton = (Button) findViewById(R.id.loginButton);
	}

	public void loginClicked(View view) {
		User user = new User();
		user.setUsername(this.username.getText().toString());
		user.setPassword(this.password.getText().toString());

		Control.username = user.getUsername();
		Control.password = user.getPassword();

		this.initializeApplication();

		Intent intent = new Intent(LoginActivity.this, PlayerActivity.class);
		startActivity(intent);
	}

	public void initializeApplication() {
		try {
			App.setPropertiesFile(getAssets().open("speerker.properties"));
		} catch (IOException e) {
			Control.toastMessage(this, "Could not open properties");
		}

		try {
			Control.startSearchManager();
		} catch (Exception e) {
			App.logger.error("Error while creating P2P layer", e);
			Control.toastMessage(this, "Could not start P2P layer");
			this.finish();
		}
	}
}
