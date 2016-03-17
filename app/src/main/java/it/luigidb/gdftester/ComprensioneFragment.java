package it.luigidb.gdftester;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComprensioneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComprensioneFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CURSOR = "param1";
    private static final String INDEX = "param2";

    // TODO: Rename and change types of parameters
    private String[] mParam1;
    private int mParam2;


    public ComprensioneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param cursor Parameter 1.
     * @param index Parameter 2.
     * @return A new instance of fragment ComprensioneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComprensioneFragment newInstance(Cursor cursor, int index) {
        Log.v("ComprensioneFragment", "newIstance");
        ComprensioneFragment fragment = new ComprensioneFragment();

        //fetch cursor
        cursor.moveToFirst();
        String[] resources = new String[8];
        resources[0] = cursor.getString(cursor.getColumnIndex("domanda"));
        int temp = index + 1;
        Log.v("ComprensioneFragment", String.format("Index: %d", temp));
        boolean res = cursor.moveToPosition(temp);
        if (res) {
            resources[1] = cursor.getString(cursor.getColumnIndex("_id"));
            resources[2] = cursor.getString(cursor.getColumnIndex("domanda"));
            resources[3] = cursor.getString(cursor.getColumnIndex("a"));
            resources[4] = cursor.getString(cursor.getColumnIndex("b"));
            resources[5] = cursor.getString(cursor.getColumnIndex("c"));
            resources[6] = cursor.getString(cursor.getColumnIndex("d"));
            resources[7] = cursor.getString(cursor.getColumnIndex("risposta"));
        } else {
            throw new RuntimeException("index passed is not in the cursor");
        }

        Bundle args = new Bundle();
        args.putSerializable(CURSOR, resources);
        args.putInt(INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (String[])getArguments().getSerializable(CURSOR);
            mParam2 = getArguments().getInt(INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comprensione, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        TextView t;
        int[] viewId = new int[]{R.id.testo, R.id._id, R.id.question, R.id.opt_a, R.id.opt_b, R.id.opt_c, R.id.opt_d, R.id.response};
        for(int i = 0 ; i <= 7 ; i++) {
            t = (TextView)getActivity().findViewById(viewId[i]);
            t.setText(mParam1[i]);
        }
    }
}
