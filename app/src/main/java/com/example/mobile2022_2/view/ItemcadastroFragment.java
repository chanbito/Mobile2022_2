package com.example.mobile2022_2.view;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.FilterQueryProvider;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mobile2022_2.Models.Produto;
import com.example.mobile2022_2.R;
import com.example.mobile2022_2.Repository.ProdutoRepository;
import com.example.mobile2022_2.databinding.FragmentItemCadastroBinding;

import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.Locale;


public class ItemcadastroFragment extends Fragment {
    private final String TAG = "ItemcadastroFragment";
    private FragmentItemCadastroBinding binding;
    private Produto selecionado;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentItemCadastroBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProdutoRepository rep = ProdutoRepository.getInstance(this.getContext());
        ArrayList<String> sugest = new ArrayList<>();

        for (Produto p :
                rep.getProdutos()) {
            sugest.add(p.getDesc());
        }


        String[] columns = {
                BaseColumns._ID,
                SearchManager.SUGGEST_COLUMN_TEXT_1,
                SearchManager.SUGGEST_COLUMN_INTENT_DATA
        };
        final MatrixCursor c = new MatrixCursor(columns);

        for (int i = 0; i < sugest.size(); i++) {
            c.addRow(new Object[]{i,sugest.get(i),null});
        }

        final CursorAdapter suggestionAdapter = new SimpleCursorAdapter(
                ItemcadastroFragment.this.getContext(),
                android.R.layout.simple_list_item_1,
                c,
                new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1},
                new int[]{android.R.id.text1},
                0);

        suggestionAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence charSequence) {
                Log.e(TAG,"bati aqui: ");
                for (String s:
                        sugest) {
                    if(s.equalsIgnoreCase(charSequence.toString())){

                    }
                }
                return null;
            }
        });

        binding.SearchView.setSuggestionsAdapter(suggestionAdapter);
        binding.SearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                selecionado = null;
                for (Produto p :
                        rep.getProdutos()) {
                    if (p.getDesc().equalsIgnoreCase(s)) {
                        selecionado = p;
                    }
                }

                if (selecionado != null) {
                    popularTela();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Item não encontrado, deseja criar?")
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Criar Produto
                                }
                            })
                            .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    return;
                                }
                            });
                    builder.create().show();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                suggestionAdapter.getFilter().filter(s);
                suggestionAdapter.notifyDataSetChanged();
                return true;
            }
        });
        binding.CancelarButton.setOnClickListener(view1 -> NavHostFragment.findNavController(ItemcadastroFragment.this)
                .navigate(R.id.action_itemcadastroFragment_to_Item_Fragment));
        binding.SearchView.setIconifiedByDefault(true);
        binding.SearchView.setFocusable(true);
        binding.SearchView.setIconified(false);
        binding.SearchView.clearFocus();
        binding.SearchView.requestFocusFromTouch();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ItemcadastroFragment.this.getContext(),
                R.array.MetricasArray, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        binding.MetricaSpinner.setAdapter(adapter);
    }

    private void popularTela() {
        binding.DescTextView.setText(selecionado.getDesc());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
