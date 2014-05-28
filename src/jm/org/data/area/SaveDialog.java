package jm.org.data.area;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SaveDialog extends DialogFragment {

	public SaveDialog() {
		// TODO Auto-generated constructor stub
	}
/*	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.save_chart_dialogue, null);
		builder.setView(view);
	
		
		Dialog dialog = builder.create();
		return dialog;
		
	}*/
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		return inflater.inflate(R.layout.save_chart_dialogue, null);
	}
	

	}
