package olectronix.hottie.config.fragments;

import olectronix.hottie.R;
import olectronix.hottie.general.access.SMSHandler;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class RegisterPhoneFragment extends Fragment {
	public final static String NUMBER1 = "no1";
	public final static String NUMBER2 = "no2";
	public final static String NUMBER3 = "no3";
	boolean no1Changed = false;
	boolean no2Changed = false;
	boolean no3Changed = false;
	SMSHandler smsHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_register_phone,
				container, false);

		return view;
		// here is your arguments
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {

		smsHandler = new SMSHandler(getActivity());
		// Inflate the layout for this fragment
		Bundle bundle = getArguments();
		// here is your list array
		String no1 = bundle.getString(NUMBER1);
		String no2 = bundle.getString(NUMBER2);
		String no3 = bundle.getString(NUMBER3);
		EditText editText1 = (EditText) getActivity().findViewById(
				R.id.registered_number1);
		EditText editText2 = (EditText) getActivity().findViewById(
				R.id.registered_number2);
		EditText editText3 = (EditText) getActivity().findViewById(
				R.id.registered_number3);

        setDefaultNumber(editText1, no1);
        setDefaultNumber(editText2, no2);
        setDefaultNumber(editText3, no3);

		editText1.addTextChangedListener(new TextWatcher() {
			private String initialText;

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				if (!no1Changed)
					initialText = s.toString();
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!initialText.equals(s.toString())) {
					no1Changed = true;
				} else {
					no1Changed = false;
				}
				enableButton();
			}
		});
		editText2.addTextChangedListener(new TextWatcher() {
			private String initialText;

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				if (!no2Changed)
					initialText = s.toString();
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!initialText.equals(s.toString())) {
					no2Changed = true;
				} else {
					no2Changed = false;
				}
				enableButton();
			}
		});
		editText3.addTextChangedListener(new TextWatcher() {
			private String initialText;

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				if (!no3Changed)
					initialText = s.toString();
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!initialText.equals(s.toString())) {
					no3Changed = true;
				} else {
					no3Changed = false;
				}
				enableButton();
			}
		});

		Button button1 = (Button) getActivity().findViewById(R.id.save_phone_numbers);
	    button1.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(final View v) {
	        	registerPhoneNumbers(v);
	        }
	    });

		super.onStart();
	}

	private void enableButton() {
		Button saveButton = (Button) getActivity().findViewById(
				R.id.save_phone_numbers);
		if (no1Changed || no2Changed || no3Changed) {
			saveButton.setEnabled(true);
		} else {
			saveButton.setEnabled(false);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	public void registerPhoneNumbers(View v) {
		if (no1Changed) {
			EditText editText1 = (EditText) getActivity().findViewById(
					R.id.registered_number1);
			String no = editText1.getText().toString();
			String command = String.format(
					getResources().getString(R.string.registerPhoneCommand), 1, no);
			smsHandler.sendSMS(command);
		}
		if (no2Changed) {
			EditText editText2 = (EditText) getActivity().findViewById(
					R.id.registered_number2);
			String no = editText2.getText().toString();
			String command = String.format(
					getResources().getString(R.string.registerPhoneCommand), 2, no);
			smsHandler.sendSMS(command);
		}
		if (no3Changed) {
			EditText editText3 = (EditText) getActivity().findViewById(
					R.id.registered_number3);
			String no = editText3.getText().toString();
			String command = String.format(
					getResources().getString(R.string.registerPhoneCommand), 3, no);
			smsHandler.sendSMS(command);
		}
        getActivity().finish();
	}


    private void setDefaultNumber (EditText editText, String number){
        if (number.equals("None") || number.equals(getString(R.string.unknown_phone))) {
            editText.setHint(number);
        } else {
            editText.setText(number);
        }
    }
}
