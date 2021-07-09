package com.example.codeforces_info;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FoldingCube;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class  Fragment1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment1 newInstance(String param1, String param2) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }

    }

    @Override
    public void onActivityCreated(@Nullable  Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    TextView emptyView;
    private int id=1;
    ProgressBar progressBar ;
    String url="https://codeforces.com/api/";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG, "onCreateView: HKKGGGHGHGHJJHHJ");
        View view= inflater.inflate(R.layout.fragment_1, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);

        emptyView = (TextView) view.findViewById(R.id.empty_view);
        
        progressBar = (ProgressBar)view.findViewById(R.id.progress);
        Sprite foldingCube = new FoldingCube();
        progressBar.setIndeterminateDrawable(foldingCube);



        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {


                Retrofit retrofit= new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();


                myapi api= retrofit.create(myapi.class);

                Call<model> call= api.getModels();


                call.enqueue(new Callback<model>() {
                    @Override
                    public void onResponse(Call<model> call, Response<model> response) {
                        try {
                            List<outline> data= response.body().getResult();

                            ArrayList<outline> limitedData= new ArrayList<>() ;

                            for(int i=Math.min(30,data.size()-1);i>=0;i--)
                            {
                                if(data.get(i).getPhase().equals("BEFORE"))
                                    limitedData.add(data.get(i));
                                Log.i(TAG, "onResponse: 111");
                            }

                            progressBar.setVisibility(GONE);
                            if(limitedData.isEmpty()==true)
                            {
                                recyclerView.setVisibility(GONE);
                                emptyView.setText("No future contests found");
                                emptyView.setVisibility(View.VISIBLE);
                            }
                            else{
                                recyclerView.setAdapter(new adapter(getContext(),limitedData));
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            }

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(Call<model> call, Throwable t) {
                        Toast.makeText(getContext(),"Something went wrong!!!",Toast. LENGTH_SHORT).show();
                    }
                });
        }
        else {
            progressBar.setVisibility(View.GONE);
            emptyView.setText("No internet connectivity");
            emptyView.setVisibility(View.VISIBLE);
        }

        return view;
    }
}