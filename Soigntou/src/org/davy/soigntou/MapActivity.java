package org.davy.soigntou;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import objects.Pharmacie;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity {
	
	private GoogleMap map;
	private int rayon;
	private List<Pharmacie> listePharmas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		rayon = this.getIntent().getExtras().getInt("rayon");
		listePharmas = (List<Pharmacie>) this.getIntent().getExtras().getSerializable("listePharmas");		
		Location myLocation = getMaPosition(this);
		LatLng myPosition = new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
		Marker markerMe = map.addMarker(new MarkerOptions().position(myPosition).title("Ma position"));
		
		for(Pharmacie pharmacie : listePharmas){
			double distance = pharmacie.getDistanceFromMyPosition();
			String extDistance = "m";
			String pharmaDistance = "";
			if (distance > 1000) {
				distance = distance / 1000;
				distance = (double) Math.round(distance * 10)/10;
				extDistance = "km";
				pharmaDistance = distance+" "+extDistance;
			}
			else {
				int distance2 =  (int) Math.round(distance);
				pharmaDistance = distance2+" "+extDistance;
			}
			
			LatLng pharmLatLng = new LatLng(pharmacie.getLat(),pharmacie.getLng());
			Marker pharmMark = map.addMarker(new MarkerOptions()
				.position(pharmLatLng)
				.title(pharmacie.getRslongue()+" "+pharmaDistance)
				/*.icon(BitmapDescriptorFactory
			            .fromResource(R.drawable.ic_plusone_small_off_client))*/);
		}

		// Ajuste la camera sur Melun avec un zoom de 15
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 15));

		// Zoom possible
		map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.goAllPharma) {
			Intent intent = new Intent(this,AllPharmaciesActivity.class);
			intent.putExtra("rayon", rayon);
			this.startActivityForResult(intent, 10 );
			finish();
		}
		else if (id == R.id.goHome) {
			Intent intent = new Intent(this, MainActivity.class);
			this.startActivityForResult(intent, 10);
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	public final static Location getMaPosition(Context _context) {
		Location location = null;
		try {
			LocationManager locationManager = (LocationManager)
					_context.getSystemService(Context.LOCATION_SERVICE);
			List<String> providers = locationManager.getProviders(true);
			for (int i = providers.size() - 1; i >= 0; i--) {
				location = locationManager.getLastKnownLocation(providers.get(i));
			}
		} catch (Exception e) {}
		return location;
	}

}
