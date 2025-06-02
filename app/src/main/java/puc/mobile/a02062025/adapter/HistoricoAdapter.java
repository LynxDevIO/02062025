package puc.mobile.a02062025.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import puc.mobile.a02062025.R;
import puc.mobile.a02062025.model.CepData;

public class HistoricoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(CepData item);
    }

    private List<CepData> historicoList;
    private OnItemClickListener listener;

    public HistoricoAdapter(List<CepData> historicoList, OnItemClickListener listener) {
        this.historicoList = historicoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historico, parent, false);
        return new RecyclerView.ViewHolder(v) {};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView textCep = holder.itemView.findViewById(R.id.textItemCep);
        TextView textLogradouro = holder.itemView.findViewById(R.id.textItemLogradouro);

        CepData item = historicoList.get(position);
        textCep.setText(item.getCep());
        textLogradouro.setText(item.getLogradouro());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return historicoList.size();
    }
}