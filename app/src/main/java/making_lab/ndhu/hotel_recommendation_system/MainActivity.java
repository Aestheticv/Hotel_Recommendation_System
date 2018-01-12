package making_lab.ndhu.hotel_recommendation_system;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Intent go = new Intent();
    private Bundle bd = new Bundle();

    private ListView list_function;

    private String UID,Name;
    private String [] func = new String[] {"所有飯店","查詢飯店","推薦飯店","登出系統"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bd = this.getIntent().getExtras();
        UID = bd.getString("UID");
        Name = bd.getString("Name");
        setTitle(Name+" 歡迎使用本系統");

        list_function = (ListView) findViewById(R.id.listView_finction);
        MyAdapter adapter = new MyAdapter(this);
        list_function.setAdapter(adapter);
        list_function.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        switch (i){
            case 0:
                go.setClass(MainActivity.this,ListHotelActivity.class);
                bd.putString("flag","0");
                go.putExtras(bd);
                startActivity(go);
                break;
            case 1:
                go.setClass(MainActivity.this,QueryHotelActivity.class);
                startActivity(go);
                break;
            case 2:
                go.setClass(MainActivity.this,QueryHotelActivity.class);
                bd.putString("flag","3");
                go.putExtras(bd);
                startActivity(go);
                break;
            case 3:
                go.setClass(MainActivity.this,AccountActivity.class);
                startActivity(go);
                MainActivity.this.finish();
                break;
        }
    }

    private class MyAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public MyAdapter(Context c){
            inflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return func.length;
        }

        @Override
        public Object getItem(int i) {
            return func[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflater.inflate(R.layout.layout_main_adapter,null);
            TextView sfunc;
            sfunc = view.findViewById(R.id.textView_function);
            sfunc.setText(func[i]);
            return view;
        }
    }
}