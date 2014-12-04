package org.davy.soigntou;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

/*import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;*/


import objects.Pharmacie;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.google.android.gms.maps.model.LatLng;

public class AllPharmaciesActivity extends ListActivity implements OnItemClickListener {

	private String JSON;
			
	private Vector<Pharmacie> pharmas;
	private String commune;
	private String voie;
	private int nofinesset;
	private String rs;
	private String rslongue;
	private String compldistrib;
	private String typvoie;
	private String telephone;
	private String nofinessej;
	private String telecopie;
	private int numvoie;
	private double lat;
	private double lng;
	private int cp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		JSON = lireJSON();
		getPharmacies();
		
		ListView lv = getListView();
		lv.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.all_pharmacies, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Toast.makeText(getApplicationContext(),pharmas.get(arg2).getRslongue(), Toast.LENGTH_SHORT).show();		
	}
	
	private void getPharmacies() {
		/*RequestQueue queue = Volley.newRequestQueue(this);
		StringRequest stringRequest =
				new StringRequest(Request.Method.GET, JSON_XMLRESSOURCE,
						new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {*/
						pharmas = parse(JSON);
						ArrayAdapter<Pharmacie> aa =
								new ArrayAdapter<Pharmacie>(AllPharmaciesActivity.this,
										R.layout.list_item, pharmas);
						AllPharmaciesActivity.this.setListAdapter(aa);
					/*}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(getApplicationContext(),"That didn't work!",
								Toast.LENGTH_SHORT).show();
					}
				});
		queue.add(stringRequest);*/
	}
	
	private Vector<Pharmacie> parse(String json) {
		Log.i("parse","launched");
		Vector<Pharmacie> array = new Vector<Pharmacie>();
		try{
			Log.i("try","launched");
			JSONObject reader = new JSONObject(json);
			//JSONObject liste  = reader.getJSONObject("pharmacies");
			Pharmacie pharma = null;
			Log.i("reader lenght",""+reader.length());
			for(int i=0;i<reader.length();i++){
				Log.i("i","="+i);
				JSONObject pharmacie  = reader.getJSONObject(Integer.toString(i));
				JSONObject fields = pharmacie.getJSONObject("fields");
				
				commune = fields.getString("commune");
				//voie = fields.getString("voie");
				//nofinesset = fields.getInt("nofinesset");
				//rs = fields.getString("rs");
				rslongue = fields.getString("rslongue");
				//compldistrib = fields.getString("compldistrib");
				//typvoie = fields.getString("typvoie");
				//telephone = fields.getString("telephone");
				//nofinessej = fields.getString("nofinessej");
				//telecopie = fields.getString("telecopie");
				//numvoie = fields.getInt("numvoie");
				//lat =  fields.getDouble("lat");
				//lng =  fields.getDouble("lng");
				//cp =  fields.getInt("cp");
								
				pharma = new Pharmacie();
				
				pharma.setCommune(commune);
				//pharma.setVoie(voie);
				//pharma.setNofinesset(nofinesset);
				//pharma.setRs(rs);
				pharma.setRslongue(rslongue);
				//pharma.setCompldistrib(compldistrib);
				//pharma.setTypvoie(typvoie);
				//pharma.setTelephone(telephone);
				//pharma.setNofinessej(nofinessej);
				//pharma.setTelecopie(telecopie);
				//pharma.setNumvoie(numvoie);
				//pharma.setLat(lat);
				//pharma.setLng(lng);
				//pharma.setCp(cp);
								
				array.add(pharma);
				Log.i("info", "element ajouté");
			}
		}
		catch (Exception e) {
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
			Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
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
}
