package org.thoughtcrime.securesms.groups.ui.managegroup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.thoughtcrime.securesms.R;
import org.thoughtcrime.securesms.conversationlist.AddEvent;
import org.thoughtcrime.securesms.recipients.RecipientId;
import org.thoughtcrime.securesms.wallpaper.ChatWallpaperActivity;

public class ManageGroupNoteActivity extends AppCompatActivity {

    EditText title;
    EditText location;
    EditText description;
    Button addEvent;

    private static final String EXTRA_RECIPIENT_ID = "extra.recipient.id";

    public static @NonNull
    Intent createIntent(@NonNull Context context) {
        return createIntent(context, null);
    }

    public static @NonNull Intent createIntent(@NonNull Context context, @Nullable RecipientId recipientId) {
        Intent intent = new Intent(context, ManageGroupNoteActivity.class);
        intent.putExtra(EXTRA_RECIPIENT_ID, recipientId);
        return intent;
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_addevent);

        title = findViewById(R.id.etTitle);
        location = findViewById(R.id.etLocation);
        description = findViewById(R.id.etDescription);
        addEvent = findViewById(R.id.btnAdd);

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!title.getText().toString().isEmpty() && !location.getText().toString().isEmpty() && !description
                        .getText().toString().isEmpty()) {

                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setData(CalendarContract.Events.CONTENT_URI);
                    intent.putExtra(CalendarContract.Events.TITLE, title.getText().toString());
                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location.getText().toString());
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, description.getText().toString());
                    intent.putExtra(CalendarContract.Events.ALL_DAY, true);

                    if(intent.resolveActivity(getPackageManager()) != null){
                        startActivity(intent);
                    }else{
                        Toast.makeText(ManageGroupNoteActivity.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(ManageGroupNoteActivity.this, "Please fill all the fields",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}