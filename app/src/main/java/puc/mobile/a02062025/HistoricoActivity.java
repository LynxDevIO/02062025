package puc.mobile.a02062025;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import puc.mobile.a02062025.adapter.HistoricoAdapter;
import puc.mobile.a02062025.database.DBHelper;
import puc.mobile.a02062025.model.CepData;

public class HistoricoActivity extends AppCompatActivity implements HistoricoAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private HistoricoAdapter adapter;
    private DBHelper dbHelper;
    private List<CepData> listaHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        recyclerView = findViewById(R.id.recyclerViewHistorico);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DBHelper(this);
        carregarHistorico();
    }

    private void carregarHistorico() {
        listaHistorico = dbHelper.getAllHistorico();
        if (listaHistorico.isEmpty()) {
            Toast.makeText(this, "Nenhum histórico encontrado.", Toast.LENGTH_SHORT).show();
        }
        adapter = new HistoricoAdapter(listaHistorico, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(CepData item) {
        Intent intent = new Intent(this, ResultadoActivity.class);
        intent.putExtra("cep", item.getCep());
        intent.putExtra("logradouro", item.getLogradouro());
        intent.putExtra("bairro", item.getBairro());
        intent.putExtra("localidade", item.getLocalidade());
        intent.putExtra("uf", item.getUf());
        startActivity(intent);
    }
}
