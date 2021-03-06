package org.davy.soigntou;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*import com.android.volley.Request;
 import com.android.volley.RequestQueue;
 import com.android.volley.Response;
 import com.android.volley.VolleyError;
 import com.android.volley.toolbox.StringRequest;
 import com.android.volley.toolbox.Volley;*/
import objects.Pharmacie;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AllPharmaciesActivity extends ListActivity implements OnItemClickListener {

	private String JSON;
	private ArrayList<Pharmacie> listePharmas;
	private String commune;
	private String voie;
	private String rs;
	private String rslongue;
	private String typvoie;
	private int telephone;
	private int telecopie;
	private int numvoie;
	private double lat;
	private double lng;
	private int cp;
	private int rayon;
	private Location maPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rayon = this.getIntent().getExtras().getInt("rayon");
		maPosition = getMaPosition(this);
		JSON = lireJSON();
		getPharmacies();

		ListView lv = getListView();
		lv.setOnItemClickListener(this);
		if(listePharmas.size()==0){
			new AlertDialog.Builder(this)
			.setTitle("Informations:")
			.setMessage("Aucune pharmacie trouvée.")
					.setNeutralButton(android.R.string.yes, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) { 

						}
					})
					.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.goMap) {
			Intent intent = new Intent(this, MapActivity.class);
			intent.putExtra("rayon", rayon);
			intent.putExtra("listePharmas", listePharmas);
			this.startActivityForResult(intent, 10);
			finish();
		}
		else if (id == R.id.goHome) {
			Intent intent = new Intent(this, MainActivity.class);
			this.startActivityForResult(intent, 10);
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		//variable pour contruire le message du AlertDialogue
		final Pharmacie currentPharma = listePharmas.get(arg2);
		String pharmaInfo = currentPharma.getAdresse();
		String pharmaVille = currentPharma.getCommune() +" "+ currentPharma.getCp();
		
		String pharmaPhone = "";
		if(currentPharma.getTelephone()!=0){
			pharmaPhone = "0"+currentPharma.getTelephone();
		}
		else{
			pharmaPhone = "Non renseigné.";
		}
		
		String pharmaFax = "";
		if(currentPharma.getTelecopie()!=0){
			pharmaFax = "0"+currentPharma.getTelecopie();
		}
		else{
			pharmaFax = "Non renseigné.";
		}

		//Traitement pour récuperer la distance en m ou en km et arrondie
		double distance = currentPharma.getDistanceFromMyPosition();
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



		new AlertDialog.Builder(this)
		.setTitle(currentPharma.getRslongue())
		.setMessage("-Adresse : "+pharmaInfo+"\n"
				+"-Ville : "+pharmaVille+"\n"
				+"-Téléphone : "+pharmaPhone+"\n"
				+"-Fax : "+pharmaFax+"\n"
				+"-Distance : "+pharmaDistance)
				.setNeutralButton(android.R.string.yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 

					}
				})
				.setPositiveButton("Voir sur la carte", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						ArrayList<Pharmacie> onePharma = new ArrayList();
						onePharma.add(currentPharma);
						Intent intent = new Intent(AllPharmaciesActivity.this, MapActivity.class);
						intent.putExtra("rayon", rayon);
						intent.putExtra("listePharmas", onePharma);
						AllPharmaciesActivity.this.startActivityForResult(intent, 10);
						finish();
					}
				})
				.show();
	}

	private void getPharmacies() {
		/*
		 * RequestQueue queue = Volley.newRequestQueue(this); StringRequest
		 * stringRequest = new StringRequest(Request.Method.GET,
		 * JSON_XMLRESSOURCE, new Response.Listener<String>() {
		 * 
		 * @Override public void onResponse(String response) {
		 */
		listePharmas = parse(JSON);
		Collections.sort(listePharmas);
		ArrayAdapter<Pharmacie> aa = new ArrayAdapter<Pharmacie>(
				AllPharmaciesActivity.this, R.layout.list_item, listePharmas);
		AllPharmaciesActivity.this.setListAdapter(aa);
		/*
		 * } }, new Response.ErrorListener() {
		 * 
		 * @Override public void onErrorResponse(VolleyError error) {
		 * Toast.makeText(getApplicationContext(),"That didn't work!",
		 * Toast.LENGTH_SHORT).show(); } }); queue.add(stringRequest);
		 */
	}

	private ArrayList<Pharmacie> parse(String json) {
		ArrayList<Pharmacie> array = new ArrayList<Pharmacie>();
		try {
			JSONObject reader = new JSONObject(json);
			Pharmacie pharma = null;
			for (int i = 0; i < reader.length(); i++) {
				JSONObject pharmacie = reader
						.getJSONObject(Integer.toString(i));
				JSONObject fields = pharmacie.getJSONObject("fields");
				if (fields.has("lat")) {
					lat = fields.getDouble("lat");
				} else {
					lat = 0;
				}
				if (fields.has("lng")) {
					lng = fields.getDouble("lng");
				} else {
					lng = 0;
				}
				Location locPharm = new Location("Pharmacie");
				locPharm.setLatitude(lat);
				locPharm.setLongitude(lng);
				double distance = locPharm.distanceTo(maPosition) / 1000;
				if (distance < rayon) {
					Log.i("distance", distance + "");
					if (fields.has("commune")) {
						commune = fields.getString("commune");
					} else {
						commune = "Non renseigné";
					}
					if (fields.has("voie")) {
						voie = fields.getString("voie");
					} else {
						voie = "Non renseigné";
					}
					if (fields.has("rs")) {
						rs = fields.getString("rs");
					} else {
						rs = "Non renseigné";
					}
					if (fields.has("rslongue")) {
						rslongue = fields.getString("rslongue");
					} else {
						rslongue = rs;
					}
					if (fields.has("typvoie")) {
						typvoie = fields.getString("typvoie");
					} else {
						typvoie = "Non renseigné";
					}
					if (fields.has("telephone")) {
						telephone = fields.getInt("telephone");
					} else {
						telephone = 0;
					}
					if (fields.has("telecopie")) {
						telecopie = fields.getInt("telecopie");
					} else {
						telecopie = 0;
					}
					if (fields.has("numvoie")) {
						numvoie = fields.getInt("numvoie");
					} else {
						numvoie = 0;
					}
					if (fields.has("cp")) {
						cp = fields.getInt("cp");
					} else {
						cp = 0;
					}

					pharma = new Pharmacie();

					pharma.setCommune(commune);
					pharma.setVoie(voie);
					pharma.setRs(rs);
					pharma.setRslongue(rslongue);
					pharma.setTypvoie(typvoie);
					pharma.setTelephone(telephone);
					pharma.setTelecopie(telecopie);
					pharma.setNumvoie(numvoie);
					pharma.setLat(lat);
					pharma.setLng(lng);
					pharma.setCp(cp);

					pharma.makeDistance(maPosition);
					pharma.makeAdresse();

					array.add(pharma);
					Log.i("info", "element ajouté:" + rslongue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	public String lireJSON() {
		InputStream is = getResources().openRawResource(R.raw.data);
		Writer writer = new StringWriter();
		char[] buffer = new char[1024];

		try {
			Reader reader = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			int n;

			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return writer.toString();
	}

	public final static Location getMaPosition(Context _context) {
		Location location = null;
		try {
			LocationManager locationManager = (LocationManager) _context.getSystemService(Context.LOCATION_SERVICE);
			List<String> providers = locationManager.getProviders(true);
			for (int i = providers.size() - 1; i >= 0; i--) {
				location = locationManager.getLastKnownLocation(providers.get(i));
			}
		} catch (Exception e) {
		}
		return location;
	}

}
