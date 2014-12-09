package org.davy.soigntou;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;
import java.util.Vector;

import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
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
import android.widget.Toast;
/*import com.android.volley.Request;
 import com.android.volley.RequestQueue;
 import com.android.volley.Response;
 import com.android.volley.VolleyError;
 import com.android.volley.toolbox.StringRequest;
 import com.android.volley.toolbox.Volley;*/
import objects.Pharmacie;

public class AllPharmaciesActivity extends ListActivity implements
		OnItemClickListener {

	private String JSON;
	private Vector<Pharmacie> listePharmas;
	private String commune;
	private String voie;
	private int nofinesset;
	private String rs;
	private String rslongue;
	private String compldistrib;
	private String typvoie;
	private int telephone;
	private int nofinessej;
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.goMap) {
			Intent intent = new Intent(this, MapActivity.class);
			intent.putExtra("rayon", rayon);
			intent.putExtra("listePharmas", listePharmas);
			this.startActivityForResult(intent, 10);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Toast.makeText(getApplicationContext(), listePharmas.get(arg2).toString(),
				Toast.LENGTH_LONG).show();
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

	private Vector<Pharmacie> parse(String json) {
		Vector<Pharmacie> array = new Vector<Pharmacie>();
		try {
			JSONObject reader = new JSONObject(json);
			Pharmacie pharma = null;
			Log.i("reader lenght", "" + reader.length());
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
					if (fields.has("nofinesset")) {
						nofinesset = fields.getInt("nofinesset");
					} else {
						nofinesset = 0;
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
					if (fields.has("compldistrib")) {
						compldistrib = fields.getString("compldistrib");
					} else {
						compldistrib = "Non renseigné";
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
					if (fields.has("nofinessej")) {
						nofinessej = fields.getInt("nofinessej");
					} else {
						nofinessej = 0;
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
					pharma.setNofinesset(nofinesset);
					pharma.setRs(rs);
					pharma.setRslongue(rslongue);
					pharma.setCompldistrib(compldistrib);
					pharma.setTypvoie(typvoie);
					pharma.setTelephone(telephone);
					pharma.setNofinessej(nofinessej);
					pharma.setTelecopie(telecopie);
					pharma.setNumvoie(numvoie);
					pharma.setLat(lat);
					pharma.setLng(lng);
					pharma.setCp(cp);

					array.add(pharma);
					Log.i("info", "element ajouté:" + rslongue);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
			LocationManager locationManager = (LocationManager) _context
					.getSystemService(Context.LOCATION_SERVICE);
			List<String> providers = locationManager.getProviders(true);
			for (int i = providers.size() - 1; i >= 0; i--) {
				location = locationManager.getLastKnownLocation(providers
						.get(i));
			}
		} catch (Exception e) {
		}
		return location;
	}

}
