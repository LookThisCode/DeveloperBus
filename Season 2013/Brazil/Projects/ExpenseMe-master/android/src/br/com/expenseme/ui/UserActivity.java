package br.com.expenseme.ui;

import static br.com.expenseme.core.Constants.Extra.USER;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.expenseme.R;
import br.com.expenseme.core.User;

import com.squareup.picasso.Picasso;

import butterknife.InjectView;

public class UserActivity extends ExpensemeActivity {

    @InjectView(R.id.iv_avatar) protected ImageView avatar;
    @InjectView(R.id.tv_name) protected TextView name;

    protected User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_view);

        if(getIntent() != null && getIntent().getExtras() != null) {
            user = (User) getIntent().getExtras().getSerializable(USER);
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Picasso.with(this).load(user.getAvatarUrl()).placeholder(R.drawable.gravatar_icon).into(avatar);

        name.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));

    }


}
