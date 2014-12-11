package org.davy.soigntou;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, OnSeekBarChangeListener{

	private Button but1;
	private SeekBar editKm;
	private String strDistance;
	private int distance;
	private TextView textProgress;
	private TextView textAction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		but1 = (Button)this.findViewById(R.id.but1);
		editKm = (SeekBar)this.findViewById(R.id.seekKm);
		editKm.setOnSeekBarChangeListener(this);
		but1.setOnClickListener(this);
		
		textProgress = (TextView)findViewById(R.id.kmValue);
		textAction = (TextView)findViewById(R.id.kmText);
		
		LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			new AlertDialog.Builder(this).setTitle("GPS desactivé")
			.setMessage("Impossible de se géolocaliser. Activer le GPS et redémarrer l'application.")
			.setPositiveButton("Ok", new DialogInterface.OnClickListener() {			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub				
				}		
			}).show();
		    but1.setOnClickListener(null);
		}
		
	}

	@Override
	public void onClick(View arg0) {
		Intent go = new Intent(this,AllPharmaciesActivity.class);
		strDistance = textProgress.getText().toString();
		if (strDistance.trim().equals("")) {
			new AlertDialog.Builder(this).setTitle("Champs non-rempli")
				.setMessage("Vous n'avez pas mis de rayon de recherche !")
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {			
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub				
					}		
				}).show();
		} 
		else {
			distance = Integer.parseInt(strDistance);
			go.putExtra("rayon",distance);
			this.startActivityForResult(go, 10);
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromAction) {
		
		textProgress.setText((progress+1)+"");
    	// change action text label to changing
    	textAction.setText(" km");
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}
		
	
}
