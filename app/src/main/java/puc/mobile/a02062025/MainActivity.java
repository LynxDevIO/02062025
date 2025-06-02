package puc.mobile.a02062025;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    private EditText editTextCep;
    private Button buttonBuscar, buttonHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCep = findViewById(R.id.editTextCep);
        buttonBuscar = findViewById(R.id.buttonBuscar);
        buttonHistorico = findViewById(R.id.buttonHistorico);

        buttonBuscar.setOnClickListener(view -> {
            String cep = editTextCep.getText().toString().trim();
            if (cep.length() != 8 || !cep.matches("\\d+")) {
                editTextCep.setError("Digite um CEP válido com 8 números");
                return;
            }
            buscarCep(cep);
        });

        buttonHistorico.setOnClickListener(view -> {
            startActivity(new Intent(this, HistoricoActivity.class));
        });
    }

    private void buscarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String logradouro = response.getString("logradouro");
                        String bairro = response.getString("bairro");
                        String localidade = response.getString("localidade");
                        String uf = response.getString("uf");

                        Intent intent = new Intent(MainActivity.this, ResultadoActivity.class);
                        intent.putExtra("cep", cep);
                        intent.putExtra("logradouro", logradouro);
                        intent.putExtra("bairro", bairro);
                        intent.putExtra("localidade", localidade);
                        intent.putExtra("uf", uf);
                        startActivity(intent);
                    } catch (JSONException e) {
                        Toast.makeText(this, "Erro ao processar resposta", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Erro ao buscar o CEP", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }
}