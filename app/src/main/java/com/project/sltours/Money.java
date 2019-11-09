package com.project.sltours;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;

public class Money extends AppCompatActivity implements TextToSpeech.OnInitListener  {

    Spinner spinner_firstChose, spinner_secondChose;
    HashMap<String, String> hm;
    EditText edt_firstCountry, edt_secondCountry;
    TextView txtview_result, TextView_date;
    String date;
    private TextToSpeech tts;
    ImageButton tts_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        hm = new HashMap<String, String>();
        initilize();
        getOnlineMoneyData();
        addTextWatcher();
        spinnerListener();

    }

    private void initilize() {

        spinner_firstChose = (Spinner) findViewById(R.id.spinner_firstChose);
        edt_firstCountry = (EditText) findViewById(R.id.edt_firstCountry);
        txtview_result = (TextView) findViewById(R.id.txtview_result);
        edt_firstCountry.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(5, 2)});
        spinner_firstChose.setSelection(0);

    }

    private void addTextWatcher() {

        edt_firstCountry.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                calculateAndSetResult();
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

    }

    private void spinnerListener() {

        spinner_firstChose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calculateAndSetResult();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void calculateAndSetResult() {

        if (!edt_firstCountry.getText().toString().equals("")) {

            String inititCurrency = spinner_firstChose.getSelectedItem().toString();
            String targetCurrency = "LKR";

            try {
                double baseRate = Double.valueOf(hm.get("LKR"));
                double initRate = Double.valueOf(hm.get(inititCurrency));
                double targetRate = Double.valueOf(hm.get(targetCurrency));
                double first_input = Double.valueOf(edt_firstCountry.getText().toString());
                String resultFinal = String.valueOf(String.format("%.2f", ((initRate * first_input) / targetRate)));
                txtview_result.setText(edt_firstCountry.getText().toString() + " "
                        + inititCurrency + " = "+ resultFinal + " " + targetCurrency);

            } catch (Exception e) {

            }
        }
    }

    private void getOnlineMoneyData() {

        hm.clear();
        hm.put("LKR", "1");

        String url = "https://free.currconv.com/api/v7/convert?q=USD_LKR,BDT_LKR,BGN_LKR,BRL_LKR,CAD_LKR,CHF_LKR,INR_LKR&compact=ultra&apiKey=71cc433ecece87a28334";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Tag", response.toString());

                try {

                    String USD = response.getString("USD_LKR");
                    String BGN = response.getString("BGN_LKR");
                    String BDT = response.getString("BDT_LKR");
                    String BRL = response.getString("BRL_LKR");
                    String CAD = response.getString("CAD_LKR");
                    String CHF = response.getString("CHF_LKR");
                    String INR = response.getString("INR_LKR");

                    hm.put("USD", USD);
                    hm.put("BDT", BDT);
                    hm.put("BGN", BGN);
                    hm.put("BRL", BRL);
                    hm.put("CAD", CAD);
                    hm.put("CHF", CHF);
                    hm.put("INR", INR);

                    calculateAndSetResult();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjReq);

    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            if (tts.isLanguageAvailable(Locale.US) == TextToSpeech.LANG_AVAILABLE)
                tts.setLanguage(Locale.US);
        } else if (status == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }
}
