package com.example.android.madtadpoles;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


// ********************************** Damian's code start
public class Dialog extends AppCompatDialogFragment {

    private EditText editKM, editKT;
    private DialogListener listener;


    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog, null);
        builder.setView(view)

                .setPositiveButton(R.string.setNames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String teamA = editKM.getText().toString();
                        String teamB = editKT.getText().toString();
                        listener.applyTexts(teamA, teamB);

                    }
                });

        editKM = view.findViewById(R.id.etKM);
        editKT = view.findViewById(R.id.etKT);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + R.string.mustImplement);
        }
    }

    public interface DialogListener{
        void applyTexts(String teamA, String teamB);
    }
}

// ********************************** Damian's code end