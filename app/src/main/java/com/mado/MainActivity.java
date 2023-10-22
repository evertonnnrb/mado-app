package com.mado;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mado.databinding.ActivityMainBinding;
import com.mado.domain.MailData;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.editSendTo.requestFocus();
        binding.buttonSend.setEnabled(false);
        binding.editSendTo.setOnFocusChangeListener((v, hasFocus) -> validateEmptyData(binding.editSendTo));
        binding.editSubject.setOnFocusChangeListener((v, hasFocus) -> validateEmptyData(binding.editSubject));

        binding.buttonSend.setOnClickListener(e -> {
            proccessData();
        });


    }

    private void proccessData() {
        //binding.buttonSend.setEnabled(true);
        if (binding.editMessage.getText().toString().equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Empty data").setMessage("Do you whish send empty message?")
                    .setPositiveButton("YES", (dialog, which) -> {
                        sender();
                    }).setNegativeButton("NO", (dialog, which) -> {

                    })
                    .show();
            builder.create();
        }
        sender();
        Toast.makeText(this, "Sending", Toast.LENGTH_SHORT).show();
    }

    private void sender() {
        MailData mailData = new MailData(
                binding.editSendTo.toString(), binding.editSubject.getText().toString(), binding.editMessage.getText().toString());

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, mailData.getTo());
        intent.putExtra(Intent.EXTRA_SUBJECT, mailData.getSubject());
        intent.putExtra(Intent.EXTRA_TEXT, new String[]{mailData.getMessage()});
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "send email"));
    }

    private boolean validateEmptyData(EditText editText) {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (editText.getText().toString().equals("")) {
                editText.setError("This fiel is required");
                binding.buttonSend.setEnabled(false);
            } else {
                binding.buttonSend.setEnabled(true);
            }
        });
        return true;
    }
} 