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
	private Button boutonPlus;
	private Button boutonMoins;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		but1 = (Button)this.findViewById(R.id.but1);
		editKm = (SeekBar)this.findViewById(R.id.seekKm);
		textProgress = (TextView)findViewById(R.id.kmValue);
		boutonPlus = (Button)findViewById(R.id.plusButton);
		boutonMoins = (Button)findViewById(R.id.moinsButton);
		
		boutonPlus.setOnClickListener(new OnClickListener()   {
            @Override
            public void onClick(View v) {editKm.setProgress((int)editKm.getProgress() + 1);}}
		);
		boutonMoins.setOnClickListener(new OnClickListener()   {
            @Override
            public void onClick(View v) {editKm.setProgress((int)editKm.getProgress() - 1);}}
		);
		
		editKm.setOnSeekBarChangeListener(this);
		but1.setOnClickListener(this);
		
		
		
		LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			new AlertDialog.Builder(this).setTitle("GPS desactivé")
			.setMessage("Impossible de se géolocaliser. Activer le GPS et redémarrer l'application.")
			.setPositiveButton("Ok", new DialogInterface.OnClickListener() {			
				@Override
				public void onClick(DialogInterface dialog, int which) {
			
				}		
			}).show();
		    but1.setOnClickListener(null);
		}
		
	}

	@Override
	public void onClick(View arg0) {
		Intent go = new Intent(this,AllPharmaciesActivity.class);
		strDistance = textProgress.getText().toString();
		int strDistanceLength = strDistance.length();
		strDistance = strDistance.substring(0, strDistanceLength-3);

		distance = Integer.parseInt(strDistance);
		go.putExtra("rayon",distance);
		this.startActivityForResult(go, 10);
		
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromAction) {		
		textProgress.setText(String.valueOf(progress+1)+ " km");
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}
		
	
}
