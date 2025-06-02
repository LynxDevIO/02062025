package puc.mobile.a02062025;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import puc.mobile.a02062025.database.DBHelper;
import puc.mobile.a02062025.model.CepData;

public class ResultadoActivity extends AppCompatActivity {
    private TextView textLogradouro, textBairro, textCidade, textEstado;
    private Button buttonSalvar;

    private String cep, logradouro, bairro, localidade, uf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        textLogradouro = findViewById(R.id.textLogradouro);
        textBairro = findViewById(R.id.textBairro);
        textCidade = findViewById(R.id.textCidade);
        textEstado = findViewById(R.id.textEstado);
        buttonSalvar = findViewById(R.id.buttonSalvar);

        cep = getIntent().getStringExtra("cep");
        logradouro = getIntent().getStringExtra("logradouro");
        bairro = getIntent().getStringExtra("bairro");
        localidade = getIntent().getStringExtra("localidade");
        uf = getIntent().getStringExtra("uf");

        textLogradouro.setText("Logradouro: " + logradouro);
        textBairro.setText("Bairro: " + bairro);
        textCidade.setText("Cidade: " + localidade);
        textEstado.setText("Estado: " + uf);

        buttonSalvar.setOnClickListener(v -> {
            DBHelper dbHelper = new DBHelper(ResultadoActivity.this);
            CepData data = new CepData(cep, logradouro, bairro, localidade, uf);
            boolean inserted = dbHelper.insertHistorico(data);
            if (inserted) {
                Toast.makeText(this, "Salvo no histórico!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Erro ao salvar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
