package com.example.mobile2022_2.Repository;

import android.content.Context;

import com.example.mobile2022_2.Models.Lista;
import com.example.mobile2022_2.Models.Produto;
import com.example.mobile2022_2.Models.User;

import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {
    private final String TAG = "ProdutoRepository";
    private List<Produto> produtos;
    private static ProdutoRepository instance;
    private Context context;

    public ProdutoRepository(Context context) {
        super();
        this.context = context;
        CategoriaRepository catrepo = CategoriaRepository.getInstance(this.context);
        produtos = new ArrayList<>();
        produtos.add(new Produto(1,"Arroz", 25,true,catrepo.getCatsbyid(1)));
        produtos.add(new Produto(2,"pedra", 10,true,catrepo.getCatsbyid(1)));
        produtos.add(new Produto(3,"flor", 2.50,true,catrepo.getCatsbyid(3)));
        produtos.add(new Produto(4,"couve", 1.99,true,catrepo.getCatsbyid(4)));
    }

    public static ProdutoRepository getInstance(Context cont) {
        if(instance == null)
            instance = new ProdutoRepository(cont);
        return instance;
    }

    public List<Produto> getProdutos() {

        return produtos;
    }

    public Produto getProdutobyid(int id) {

        for (Produto item :
                this.getProdutos()) {
            if(item.getId() == id){
                return item;
            }
        }
        return null;
    }
}
